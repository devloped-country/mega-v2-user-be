package com.app.mega.dto.response.notice;

import com.app.mega.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
  @AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoticeResponse {
  private Long id;
  private String title;
  private String content;
  private String author;
  private List<NoticeTagResponse> tags;
  private LocalDateTime createdTime;
  private String textContent;
  private String thumbnail;

  public NoticeResponse(Notice notice) {
    this.id = notice.getId();
    this.title = notice.getTitle();
    this.content = notice.getContent();
    this.author = notice.getAuthor();
    this.tags = notice.getNoticeTags().stream().map(NoticeTagResponse::new).collect(Collectors.toList());
    this.createdTime = notice.getCreatedTime();
    this.textContent = notice.getContent();
    this.thumbnail = notice.getThumbnail();
  }
}
