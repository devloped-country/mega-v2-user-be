package com.app.mega.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Data
@ToString(exclude = {"attendance", "user"})
@Table
public class Appliance {
  @EmbeddedId
  private ApplianceId id;

  @NotNull
  @ColumnDefault("0")
  private Integer status;

  @NotNull
  @Lob
  private String reason;

  @NotNull
  @CreationTimestamp
  private LocalDateTime time;

  @OneToOne
  @MapsId("attendanceId")
  private Attendance attendance;

  @ManyToOne
  @MapsId("userId")
  private User user;
}
