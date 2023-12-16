package com.app.mega.dto.response.note;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WebsocketNoteResponse {
    private String action;
    private String type;
    private String content;
    private String title;
    private String from;
    private Long noteSendId;
    private List<Long> to;
}
