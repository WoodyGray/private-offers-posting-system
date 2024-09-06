package org.senla.woodygray.dtos;

import java.time.LocalDateTime;

public record ReviewGetResponse(
        Long id,
        Long idSender,
        String message,
        Integer grade,
        LocalDateTime sentAt
) {
}
