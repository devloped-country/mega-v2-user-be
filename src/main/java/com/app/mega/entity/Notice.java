package com.app.mega.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

@Entity
@Table
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@ToString(exclude = {"noticeImgs", "noticeTags", "admin", "course"})
public class Notice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String title;

  @NotNull
  @Lob
  private String content;

  @NotNull
  private String author;

  @NotNull
  private LocalDateTime createdTime;

  private String thumbnail;

  @OneToMany(mappedBy = "notice")
  private List<NoticeImg> noticeImgs;

  @OneToMany(mappedBy = "notice")
  private List<NoticeTag> noticeTags;

  @ManyToOne
  @JoinColumn(name = "admin_id")
  private Admin admin;

  @ManyToOne
  @JoinColumn(name="course_id")
  private Course course;
}