package org.senla.woodygray.dtos;

import java.util.Date;
import java.util.List;

public record OfferUpdateRequest(
        String title,
        String description,
        Double price,
        Long statusId,
        List<PhotoDto> photos,
        Date promotionBegin,
        Date promotionEnd
){
}
