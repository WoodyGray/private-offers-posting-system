package org.senla.woodygray.dtos;

import lombok.Data;
import org.senla.woodygray.model.Photo;

import java.util.Date;
import java.util.List;



public record OfferSearchResponse(
    Long offerID,
    Long userId,
    String title,
    String description,
    Double price,
    List<PhotoDto> photos,
    Date promotionBegin,
    Date promotionEnd
){
}
