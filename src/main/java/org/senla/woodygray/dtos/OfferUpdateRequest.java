package org.senla.woodygray.dtos;

import java.util.Date;
import java.util.List;

public record OfferUpdateRequest(
        Long offerId,
        String title,
        String description,
        Double price,
        List<PhotoDto> photos,
        Date promotionBegin,
        Date promotionEnd
){
}
