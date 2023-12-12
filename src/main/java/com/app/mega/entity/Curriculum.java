package com.app.mega.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@Data
@ToString(exclude = "detailSubjects")
public class Curriculum {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String subject; //교과목명

  @NotNull
  private Integer time; //시간

  @NotNull
  private LocalDate startDate; //시작날짜

  @NotNull
  private LocalDate endDate; //종료날짜

  @NotNull
  private Integer listOrder; //목록순서

  @OneToMany(mappedBy = "curriculum")
  private List<DetailSubject> detailSubjects;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;
}
