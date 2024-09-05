package org.senla.woodygray.dtos.chatmessage;

public record ChatMessageGetRequest(
        Long idSender,
        Long idOpponent
) {
}
