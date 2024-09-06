package org.senla.woodygray.dtos.offer;

import org.senla.woodygray.dtos.PhotoDto;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public record OfferUpdateRequest(
        String title,
        String description,
        Double price,
        @NotNull(message = "status id can't be null") Long statusId,
        List<PhotoDto> photos,
        Date promotionBegin,
        Date promotionEnd
){
}
