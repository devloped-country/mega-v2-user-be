package com.app.mega.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@ToString(exclude = {"institution", "noteSends", "noteReceives", "userNotifications", "attendances", "appliances"})
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String name;

  @NotNull
  private String email;

  @NotNull
  private String password;

  @NotNull
  @Column(length = 30)
  private String phone;

  @NotNull
  @Column(length = 500)
  private String imageUrl;

  @NotNull
  @ColumnDefault("0")
  @Column(columnDefinition = "TINYINT(1)")
  private Boolean isIdentified;

  @ManyToOne
  @JoinColumn(name = "institution_id")
  private Institution institution;

  @OneToMany(mappedBy = "user")
  private List<UserNotification> userNotifications;

  @OneToMany(mappedBy = "user")
  private List<Attendance> attendances;

  @ManyToOne
  @JoinColumn(name = "course_id")
  private Course course;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
//    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
