package org.senla.woodygray.dtos.offer;

import java.time.LocalDate;

public record PromotionResponse(
        LocalDate promotionBegin,
        LocalDate promotionEnd
) {
}
