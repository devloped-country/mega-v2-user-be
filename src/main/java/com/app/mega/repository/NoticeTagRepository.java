package com.app.mega.repository;

import com.app.mega.entity.NoticeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeTagRepository extends JpaRepository<NoticeTag, Long> {
  List<NoticeTag> findByNoticeId(Long id);
}
