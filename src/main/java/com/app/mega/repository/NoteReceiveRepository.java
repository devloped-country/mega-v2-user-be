package com.app.mega.repository;


import com.app.mega.entity.Admin;
import com.app.mega.entity.NoteReceive;
import com.app.mega.entity.NoteSend;
import com.app.mega.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteReceiveRepository  extends JpaRepository<NoteReceive, Long> {
    List<NoteReceive> findAllByIsDeletedAndIsRealDeletedAndUser(Boolean isDeleted, Boolean isRealDeleted, User user);
    List<NoteReceive> findAllByIsDeletedAndUser(Boolean isDeleted, User user);
    List<NoteReceive> findAllByNoteSend(NoteSend noteSend);
    //List<NoteReceive> findAllByIsDeletedAndUser(Boolean isRealDeleted);
}