package org.senla.woodygray.dtos.comment;

import java.time.LocalDateTime;

public record CommentGetResponse(
        Long id,
        Long senderId,
        String message,
        LocalDateTime sendAt
) {
}
