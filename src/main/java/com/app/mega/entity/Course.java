package com.app.mega.entity;

import jakarta.persistence.*;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@Data
@ToString(exclude = {"notices", "institution"})
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String name;

  @OneToMany(mappedBy = "course")
  private List<Curriculum> curriculumList;

  @OneToMany(mappedBy = "course")
  private List<Notice> notices;

  @ManyToOne
  @JoinColumn(name = "institution_id")
  private Institution institution;

  @OneToMany(mappedBy = "course")
  private List<User> users;
}
