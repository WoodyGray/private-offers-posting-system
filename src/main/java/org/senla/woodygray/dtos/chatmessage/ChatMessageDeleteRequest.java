package org.senla.woodygray.dtos.chatmessage;

public record ChatMessageDeleteRequest(
        Long idSender,
        Long idOpponent
) {
}
