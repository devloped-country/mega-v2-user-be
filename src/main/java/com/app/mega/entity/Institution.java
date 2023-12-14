package com.app.mega.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table
@NoArgsConstructor
@Data
@ToString(exclude = {"courseList", "admin", "user"})
public class Institution {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String name;

  @NotNull
  private String address;

  @Column(columnDefinition = "FLOAT(7,5)")
  @ColumnDefault("0")
  private Float latitude;

  @Column(columnDefinition = "FLOAT(9,6)")
  @ColumnDefault("0")
  private Float longitude;

  @UpdateTimestamp
  private LocalDate subscriptionStartDate;

  @ColumnDefault("0")
  @Column(columnDefinition = "TINYINT(1)")
  private boolean subscriptionStatus;

  @OneToMany(mappedBy = "institution", fetch = FetchType.EAGER)
  private List<Course> courseList;

  @OneToMany(mappedBy = "institution", fetch = FetchType.EAGER)
  private List<Admin> admin;

  @OneToMany(mappedBy = "institution", fetch = FetchType.EAGER)
  private List<User> user;
}
