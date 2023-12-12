package com.app.mega.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

  @OneToMany(mappedBy = "institution")
  private List<Course> courseList;

  @OneToMany(mappedBy = "institution")
  private List<Admin> admin;

  @OneToMany(mappedBy = "institution")
  private List<User> user;
}
