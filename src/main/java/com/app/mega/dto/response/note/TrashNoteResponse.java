package com.app.mega.dto.response.note;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrashNoteResponse {
    Long id;
    String title;
    String content;
    String from;
}
