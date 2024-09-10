package org.senla.woodygray.dtos.comment;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CommentCreateRequest(
        @NotNull(message = "Id sender can't be null") Long idSender,
        String message,
        @NotNull(message = "Sent at can't be null") LocalDateTime sentAt
) {
}
