package com.app.mega.controller;

import com.app.mega.dto.request.note.NoteIdRequest;
import com.app.mega.dto.request.note.NoteSendRequest;
import com.app.mega.dto.request.note.ReceiverRequest;
import com.app.mega.dto.response.note.*;
import com.app.mega.entity.Admin;
import com.app.mega.entity.User;
import com.app.mega.service.jpa.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/note")
public class NoteController {
    private final NoteService noteService;

    //교육기관에 해당하는 매니저 불러오기 (쪽지쓰기 선택시)
    @GetMapping("/receivers")
    public List<ReceiverResponse> readReceiver (@AuthenticationPrincipal User user) {
        System.out.println("readReceiver");
        return noteService.readReceiver(user.getInstitution());
    }

    //발신시 쪽지 저장
    @PostMapping("/register")
    public AfterNoteSendResponse registerNote (@RequestBody NoteSendRequest request, @AuthenticationPrincipal User user) throws Exception {
        System.out.println("registerNote");
        System.out.println(request);
        return noteService.registerNote(request, user);
    }

    //수신쪽지함 불러오기
    @GetMapping("/received")
    public List<ReceivedNoteResponse> readNoteReceive(@AuthenticationPrincipal User user) {
        return noteService.readNoteReceive(user);
    }

    //발신쪽지함 불러오기
    @GetMapping("/sent")
    public List<SendedNoteResponse> readNoteSend(@AuthenticationPrincipal User user) {
        return noteService.readNoteSend(user);
    }


    //휴지통 불러오기
    @GetMapping("/trash")
    public List<TrashNoteResponse> readTrashNote(@AuthenticationPrincipal User user) {
        return noteService.readTrashNote(user);
    }

    //수신쪽지 삭제 (휴지통 넣기)
    @PutMapping("/delete_received")
    public List<ReceivedNoteResponse> deleteReceivedNotes (@RequestBody NoteIdRequest request, @AuthenticationPrincipal User user) {
        return noteService.deleteReceivedNotes(request.getSelectedNoteId(), user);
    }

    //수신쪽지 완전삭제
    @PutMapping("/real_delete_received")
    public List<TrashNoteResponse> realDeleteReceivedNotes (@RequestBody NoteIdRequest request, @AuthenticationPrincipal User user) {
        return noteService.realDeleteReceivedNotes(request.getSelectedNoteId(), user);
    }


    //발신쪽지 완전삭제
    @PutMapping("/real_delete_sent")
    public List<SendedNoteResponse> realDeleteSendedNotes (@RequestBody NoteIdRequest request, @AuthenticationPrincipal User user) {
        return noteService.realDeleteSendedNotes(request.getSelectedNoteId(), user);
    }

    @GetMapping("/{id}")
    public NoteResponse readNote (@PathVariable Long id, @AuthenticationPrincipal User user) {
        return noteService.readNote(id, user);
    }
}
