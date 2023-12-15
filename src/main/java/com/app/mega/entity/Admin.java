package com.app.mega.entity;

import jakarta.persistence.*;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table
@NoArgsConstructor
@Data
@ToString(exclude = {"adminNotifications", "institution"})
public class Admin {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; //관리자 아이디

  @NotNull
  private String name; //이름

  @NotNull
  private String email; //이메일

  @NotNull
  private String password; //비밀번호

  @NotNull
  @Column(length = 30)
  private String phone; //휴대폰번호

  @NotNull
  @ColumnDefault("1")
  @Column(columnDefinition = "TINYINT(1)")
  private Boolean isManager; //기관0, 매니저1

  @OneToMany(mappedBy = "admin", fetch= FetchType.LAZY)
  private List<AdminNotification> adminNotifications;

  @ManyToOne
  @JoinColumn(name = "institution_id")
  private Institution institution;
}