package com.app.mega.dto.request.notice;

import com.app.mega.entity.Notice;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoticeRequest {
  private Long id;
  @NotBlank(message = "제목은 필수 입력 값입니다.")
  private String title;
  @NotBlank(message = "내용은 필수 입력 값입니다.")
  private String content;
  private String textContent;
  private String author;
//  @NotBlank(message = "태그는 필수 입력 값입니다.")
  private List<String> tags;
  private LocalDateTime createdTime;
  private String thumbnail;

  public Notice toEntity() {
    return Notice.builder()
        .id(id)
        .title(title)
        .content(content)
        .textContent(textContent)
        .author(author)
        .createdTime(LocalDateTime.now())
        .thumbnail(thumbnail)
        .build();
  }
}
