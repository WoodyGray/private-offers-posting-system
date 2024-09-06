package org.senla.woodygray.dtos;

import net.bytebuddy.asm.Advice;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReviewCreateRequest(
        @NotNull(message = "id seller can't be null") Long idSeller,
        String message,
        @Min(value = 1, message = "grade must be at least 1")
        @Max(value = 5, message = "grade must be at most 5")
        Integer grade,
        @NotNull(message = "sent at can't be null")LocalDateTime sentAt
) {
}
