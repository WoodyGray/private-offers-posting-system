package org.senla.woodygray.dtos.chatmessage;

import java.time.LocalDateTime;

public record ChatMessageGetResponse (
        Long id,
        Long idSender,
        String message,
        LocalDateTime sentAt
){
}
