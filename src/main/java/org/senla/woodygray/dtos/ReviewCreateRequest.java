package org.senla.woodygray.dtos;

import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

public record ReviewCreateRequest(
        String message,
        Integer grade,
        LocalDateTime sentAt
) {
}
