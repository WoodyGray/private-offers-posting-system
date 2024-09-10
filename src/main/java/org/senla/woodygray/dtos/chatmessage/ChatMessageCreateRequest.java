package org.senla.woodygray.dtos.chatmessage;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ChatMessageCreateRequest(
        @NotNull(message = "id opponent can't be null") Long idOpponent,
        @NotNull(message = "massage can't be null") String message,
        @NotNull(message = "sent at can't be null") LocalDateTime sentAt
) {
}
