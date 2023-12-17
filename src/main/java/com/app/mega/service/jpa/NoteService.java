package com.app.mega.service.jpa;


import com.app.mega.dto.request.note.NoteSendRequest;
import com.app.mega.dto.response.note.*;
import com.app.mega.entity.*;
import com.app.mega.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteSendRepository noteSendRepository;
    private final NoteReceiveRepository noteReceiveRepository;
    private final AdminRepository adminRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    //교육기관에 해당하는 매니저 불러오기
    public List<ReceiverResponse> readReceiver (Institution institution) {
        //List<User> receivers = courseRepository.findById(courseId).get().getUsers();
        List<Admin> receivers = institution.getAdmin();
        List<ReceiverResponse> receiversInfo = new ArrayList<>();
        for(Admin receiver:receivers) {
            if(Boolean.TRUE.equals(receiver.getIsManager())) {
                ReceiverResponse receiverResponse = ReceiverResponse.builder()
                        .id(receiver.getId())
                        .name(receiver.getName())
                        .email(receiver.getEmail())
                        .build();
                receiversInfo.add(receiverResponse);
            }
        }
        System.out.println(receiversInfo);
        return receiversInfo;
    }

    @Transactional
    //쪽지 저장 (발신)
    public AfterNoteSendResponse registerNote(NoteSendRequest request, User user) {
        System.out.println("registerNote");

        String title = request.getTitle();
        String content = request.getContent();

        List<Admin> to = new ArrayList<>();
        for(Long receiverId : request.getTo()) {
            Admin admin = adminRepository.findById(receiverId).get();
            to.add(admin);
        }

        //발신쪽지 데이터 저장
        NoteSend noteSend = NoteSend.builder()
                .title(title)
                .content(content)
                .createTime(LocalDateTime.now())
                .user(user)
                .isRealDeleted(false)
                .build();
        noteSendRepository.save(noteSend);

        //수신쪽지 데이터 저장
        for(Admin receiver : to) {
            NoteReceive noteReceive = NoteReceive.builder()
                    .noteSend(noteSend)
                    .admin(receiver)
                    .isRealDeleted(false)
                    .isDeleted(false)
                    .isRead(false)
                    .build();
            noteReceiveRepository.save(noteReceive);
        }
        return AfterNoteSendResponse.builder().myName(user.getName()).noteSendId(noteSend.getId()).build();
    }

    @Transactional
    //발신쪽지 불러오기
    public List<SendedNoteResponse> readNoteSend(User user) {
        List<NoteSend> sentNotes = noteSendRepository.findAllByIsRealDeletedAndUser(false, user);
        List<SendedNoteResponse> notesInfo = new ArrayList<>();
        for(NoteSend sentNote: sentNotes) {
            //List<NoteReceive> noteTos = noteReceiveRepository.findAllByNoteSend(sentNote);
            List<NoteReceive> noteTos = sentNote.getNoteReceives();
            List<String> noteToNames = new ArrayList<>();
            for (NoteReceive noteTo : noteTos) {
                noteToNames.add(noteTo.getAdmin().getName());
            }
            SendedNoteResponse sentNoteResponse = SendedNoteResponse.builder()
                    .id(sentNote.getId())
                    .title(sentNote.getTitle())
                    .content(sentNote.getContent())
                    .to(noteToNames)
                    .time(sentNote.getCreateTime())
                    .build();
            notesInfo.add(sentNoteResponse);
        }
        notesInfo.sort(Comparator.comparing(SendedNoteResponse::getTime).reversed());
        return notesInfo;
    }

    @Transactional
    //수신쪽지 불러오기
    public List<ReceivedNoteResponse> readNoteReceive(User user) {
        System.out.println("NoteService.readNoteReceive");
        List<NoteReceive> receivedNotes = noteReceiveRepository.findAllByIsDeletedAndUser(false, user);  //수신인 정보
        List<ReceivedNoteResponse> notesInfo = new ArrayList<>();
        for(NoteReceive receivedNote:receivedNotes) {
            NoteSend note = receivedNote.getNoteSend();
            ReceivedNoteResponse receivedNoteResponse = ReceivedNoteResponse.builder()
                    .id(note.getId())
                    .title(note.getTitle())
                    .content(note.getContent())
                    .isRead(receivedNote.getIsRead())
                    .from(note.getAdmin().getName())
                    .time(note.getCreateTime())
                    .build();
            notesInfo.add(receivedNoteResponse);
        }
        notesInfo.sort(Comparator.comparing(ReceivedNoteResponse::getTime).reversed());
        return notesInfo;
    }

    @Transactional
    //휴지통 불러오기 (수신)
    public List<TrashNoteResponse> readTrashNote(User user) {
        List<NoteReceive> trashNotes = noteReceiveRepository.findAllByIsDeletedAndIsRealDeletedAndUser(true, false, user);
        List<TrashNoteResponse> notesInfo = new ArrayList<>();
        for(NoteReceive trashNote : trashNotes) {
            NoteSend note = trashNote.getNoteSend();
            TrashNoteResponse trashNoteResponse = TrashNoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .from(note.getAdmin().getName())
                .build();
            notesInfo.add(trashNoteResponse);
        }
        return notesInfo;
    }

    @Transactional
    //수신쪽지 삭제 (휴지통 넣기)
    public List<ReceivedNoteResponse> deleteReceivedNotes (List<Long> noteIdsForDelete, User user) {
        for(Long noteId : noteIdsForDelete) {
            NoteSend noteSend = noteSendRepository.findById(noteId).get();
            NoteReceive noteReceive = noteReceiveRepository.findByUserAndNoteSend(user, noteSend);
            noteReceive.setIsDeleted(true);
            noteReceiveRepository.save(noteReceive);
        }
        return readNoteReceive(user);
    }

    @Transactional
    //수신쪽지 완전삭제
    public List<TrashNoteResponse> realDeleteReceivedNotes (List<Long> noteIdsForDelete, User user) {
        for(Long noteId : noteIdsForDelete) {
            NoteSend noteSend = noteSendRepository.findById(noteId).get();
            NoteReceive noteReceive = noteReceiveRepository.findByUserAndNoteSend(user, noteSend);
            noteReceive.setIsRealDeleted(true);
            noteReceiveRepository.save(noteReceive);
        }
        return readTrashNote(user);
    }

    @Transactional
    //발신쪽지 완전삭제
    public List<SendedNoteResponse> realDeleteSendedNotes (List<Long> noteIdsForDelete, User user) {
        for(Long noteId : noteIdsForDelete) {
            NoteSend noteSend = noteSendRepository.findById(noteId).get();
            noteSend.setIsRealDeleted(true);
            noteSendRepository.save(noteSend);
        }
        return readNoteSend(user);
    }

    public NoteResponse readNote(Long id, User user) {
        NoteSend noteSend = noteSendRepository.findById(id).get();
        NoteReceive noteReceive = noteReceiveRepository.findByUserAndNoteSend(user, noteSend);
        if(noteReceive != null) {
            noteReceive.setIsRead(true);
            noteReceiveRepository.save(noteReceive);
            return NoteResponse.builder()
                    .content(noteSend.getContent())
                    .from(noteSend.getAdmin().getName())
                    .to(Arrays.asList(user.getName()))
                    .title(noteSend.getTitle())
                    .time(String.valueOf(noteSend.getCreateTime()))
                    .build();
        }else {
            //발신쪽지확인
            List<String> to = new ArrayList<>();
            for(NoteReceive note:noteSend.getNoteReceives()) {
                to.add(note.getAdmin().getName());
            }
            return NoteResponse.builder()
                    .content(noteSend.getContent())
                    .from(user.getName())
                    .to(to)
                    .title(noteSend.getTitle())
                    .time(String.valueOf(noteSend.getCreateTime()))
                    .build();
        }
    }
}
