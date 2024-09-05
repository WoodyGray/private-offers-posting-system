package org.senla.woodygray.dtos.chatmessage;

import javax.validation.constraints.NotNull;

public record ChatMessageGetRequest(
        @NotNull(message = "id sender can't be null") Long idSender,
        @NotNull(message = "id opponent can't be null") Long idOpponent
) {
}
