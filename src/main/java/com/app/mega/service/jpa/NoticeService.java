package com.app.mega.service.jpa;

import com.app.mega.repository.NoticeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
  private NoticeRepository noticeRepository;

  public NoticeService(NoticeRepository noticeRepository) {
    this.noticeRepository = noticeRepository;
  }

  public void findAllNotice() {
    System.out.println(noticeRepository.findAll());
  }
}
