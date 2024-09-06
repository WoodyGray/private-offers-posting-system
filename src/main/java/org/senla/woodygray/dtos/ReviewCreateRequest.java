package org.senla.woodygray.dtos;

import net.bytebuddy.asm.Advice;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReviewCreateRequest(
        @NotNull(message = "id seller can't be null") Long idSeller,
        String message,
        Integer grade,
        @NotNull(message = "sent at can't be null")LocalDateTime sentAt
) {
}
