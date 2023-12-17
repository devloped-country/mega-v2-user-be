package com.app.mega.dto.response.note;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteResponse {
    private String content;
    private String from;
    private List<String> to;
    private String time;
    private String title;
}
