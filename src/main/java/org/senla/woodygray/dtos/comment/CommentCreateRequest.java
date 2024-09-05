package org.senla.woodygray.dtos.comment;

import java.time.LocalDateTime;

public record CommentCreateRequest(
        Long idSender,
        String message,
        LocalDateTime sentAt
) {
}
