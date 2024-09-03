package org.senla.woodygray.dtos;

public record PhotoDto(
        Long id,
        String fileName,
        String format,
        Integer size,
        String base64Image
) {
}
