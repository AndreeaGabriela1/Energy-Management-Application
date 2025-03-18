package com.application.chat.entities;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Message
{
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;
}