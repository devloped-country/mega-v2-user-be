package com.app.mega.service.jpa;

import com.app.mega.dto.request.notice.NoticeRequest;
import com.app.mega.dto.request.notice.NoticeTagsRequest;
import com.app.mega.dto.response.notice.NoticeResponse;
import com.app.mega.entity.Notice;
import com.app.mega.entity.NoticeTag;
import com.app.mega.repository.NoticeRepository;
import com.app.mega.repository.NoticeTagRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeService {

  private final NoticeRepository noticeRepository;
  private final NoticeTagRepository noticeTagRepository;

  public NoticeService(NoticeRepository noticeRepository, NoticeTagRepository noticeTagRepository) {
    this.noticeRepository = noticeRepository;
    this.noticeTagRepository = noticeTagRepository;
  }

  public void paging() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

    Page<Notice> result = noticeRepository.findAll(pageable);
    System.out.println(result);
  }


  @Transactional
  public Page<NoticeResponse> readNotices(Pageable pageable) {
    Page<Notice> page = noticeRepository.findAll(pageable);
    System.out.println(page);
    return page.map(NoticeResponse::new);
//    return noticeRepository.findAll().stream().map(NoticeResponse::new).collect(Collectors.);
  }

  @Transactional
  public NoticeResponse readNotice(Long id) throws Exception {
    Notice notice = noticeRepository.findById(id).orElseThrow(Exception::new);
    return new NoticeResponse(notice);
  }

  @Transactional
  public void createNotice(NoticeRequest noticeRequest) {
    Notice savedNotice = noticeRepository.save(noticeRequest.toEntity());

    noticeRequest.getTags().forEach(tag -> {
      noticeTagRepository.save(new NoticeTagsRequest().toEntity(savedNotice, tag));
    });
  }

  @Transactional
  public void updateNotice(NoticeRequest noticeRequest, Long id) {
    Notice notice = noticeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    List<NoticeTag> noticeTags = noticeTagRepository.findByNoticeId(id);

    noticeTags.forEach((noticeTag -> noticeTagRepository.deleteById(noticeTag.getId())));
    noticeRequest.getTags().forEach(tag -> {
      noticeTagRepository.save(new NoticeTagsRequest().toEntity(notice, tag));
    });

    notice.setContent(noticeRequest.getContent());
    notice.setTitle(noticeRequest.getTitle());
  }

  @Transactional
  public void deleteNotice(Long id) {
    noticeRepository.deleteById(id);
  }
}
