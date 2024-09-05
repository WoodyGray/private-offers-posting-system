package org.senla.woodygray.dtos.chatmessage;

import java.time.LocalDateTime;

public record ChatMessageCreateRequest(
        Long idSender,
        Long idOpponent,
        String message,
        LocalDateTime sentAt
) {
}
