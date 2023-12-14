package com.app.mega.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table
@NoArgsConstructor
@Data
@ToString(exclude = {"user"})
public class Attendance {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime startTime;
  private LocalDateTime endTime;

  @NotNull
  @ColumnDefault("0")
  private Integer status;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Attendance(Long id, LocalDateTime startTime, LocalDateTime endTime, Integer status, User user) {
  }

  public void patch(Attendance attendance) {
  }
}
