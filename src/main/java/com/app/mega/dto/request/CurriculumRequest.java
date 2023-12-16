package com.app.mega.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurriculumRequest {
    private Long courseId;
    private Long id;
    private String subject;
    private Integer time;
    private String startDate;
    private String endDate;
    private List<String> contents;
    private Integer orderList;
}
