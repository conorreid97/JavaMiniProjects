package com.tsbgroup.practice.chatapp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private Long id;
    private String sender;
    private String content;
}
