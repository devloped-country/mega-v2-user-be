package com.app.mega.repository;

import com.app.mega.entity.Notice;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
  Page<Notice> findAll(Pageable pageable);
  List<Notice> findAllByCourseId(Long courseId);
}
