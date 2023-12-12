package com.app.mega.service.jpa;


import com.app.mega.dto.request.note.NoteSendRequest;
import com.app.mega.dto.response.note.ReceivedNoteResponse;
import com.app.mega.dto.response.note.ReceiverResponse;
import com.app.mega.dto.response.note.SendedNoteResponse;
import com.app.mega.dto.response.note.TrashNoteResponse;
import com.app.mega.entity.*;
import com.app.mega.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteSendRepository noteSendRepository;
    private final NoteReceiveRepository noteReceiveRepository;
    private final AdminRepository adminRepository;


    //교육기관에 해당하는 매니저 불러오기
    public List<ReceiverResponse> readReceiver (Institution institution) {
        //List<User> receivers = courseRepository.findById(courseId).get().getUsers();
        List<Admin> receivers = institution.getAdmin();
        List<ReceiverResponse> receiversInfo = new ArrayList<>();
        for(Admin receiver:receivers) {
            ReceiverResponse receiverResponse = ReceiverResponse.builder()
                    .id(receiver.getId())
                    .name(receiver.getName())
                    .email(receiver.getEmail())
                    .build();
            receiversInfo.add(receiverResponse);
        }
        return receiversInfo;
    }

    //쪽지 저장 (발신)
    public void registerNote(NoteSendRequest request, User user) {
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
                .build();
        noteSendRepository.save(noteSend);

        //수신쪽지 데이터 저장
        for(Admin receiver : to) {
            NoteReceive noteReceive = NoteReceive.builder()
                    .noteSend(noteSend)
                    .admin(receiver)
                    .build();
            noteReceiveRepository.save(noteReceive);
        }
    }

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
        return notesInfo;
    }

    //수신쪽지 불러오기
    public List<ReceivedNoteResponse> readNoteReceive(User user) {
        System.out.println("NoteService.readNoteReceive");
        List<NoteReceive> receivedNotes = noteReceiveRepository.findAllByIsDeletedAndUser(false, user);  //수신인 정보
        List<ReceivedNoteResponse> notesInfo = new ArrayList<>();
        for(NoteReceive receivedNote:receivedNotes) {
            NoteSend note = receivedNote.getNoteSend();
            ReceivedNoteResponse receivedNoteResponse = ReceivedNoteResponse.builder()
                    .id(receivedNote.getId())
                    .title(note.getTitle())
                    .content(note.getContent())
                    .isRead(receivedNote.getIsRead())
                    .from(note.getAdmin().getName())
                    .time(note.getCreateTime())
                    .build();
            notesInfo.add(receivedNoteResponse);
        }
        return notesInfo;
    }

    //휴지통 불러오기 (수신)
    public List<TrashNoteResponse> readTrashNote(User user) {
        List<NoteReceive> trashNotes = noteReceiveRepository.findAllByIsDeletedAndIsRealDeletedAndUser(true, false, user);
        List<TrashNoteResponse> notesInfo = new ArrayList<>();
        for(NoteReceive trashNote : trashNotes) {
            NoteSend note = trashNote.getNoteSend();
            TrashNoteResponse trashNoteResponse = TrashNoteResponse.builder()
                .id(trashNote.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .from(note.getAdmin().getName())
                .build();
            notesInfo.add(trashNoteResponse);
        }
        return notesInfo;
    }

    //수신쪽지 삭제 (휴지통 넣기)
    public List<ReceivedNoteResponse> deleteReceivedNotes (List<Long> noteIdsForDelete, User user) {
        for(Long noteId : noteIdsForDelete) {
            NoteReceive noteReceive = noteReceiveRepository.findById(noteId).get();
            noteReceive.setIsDeleted(true);
            noteReceiveRepository.save(noteReceive);
        }
        return readNoteReceive(user);
    }

    //수신쪽지 완전삭제
    public List<TrashNoteResponse> realDeleteReceivedNotes (List<Long> noteIdsForDelete, User user) {
        for(Long noteId : noteIdsForDelete) {
            NoteReceive noteReceive = noteReceiveRepository.findById(noteId).get();
            noteReceive.setIsRealDeleted(true);
            noteReceiveRepository.save(noteReceive);
        }
        return readTrashNote(user);
    }

    //발신쪽지 완전삭제
    public List<SendedNoteResponse> realDeleteSendedNotes (List<Long> noteIdsForDelete, User user) {
        for(Long noteId : noteIdsForDelete) {
            NoteSend noteSend = noteSendRepository.findById(noteId).get();
            noteSend.setIsRealDeleted(true);
            noteSendRepository.save(noteSend);
        }
        return readNoteSend(user);
    }
}
