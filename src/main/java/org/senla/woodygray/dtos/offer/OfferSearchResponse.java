package org.senla.woodygray.dtos.offer;

import org.senla.woodygray.dtos.PhotoDto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;



public record OfferSearchResponse(
    Long offerID,
    Long userId,
    String title,
    String description,
    Double price,
    List<PhotoDto> photos,
    LocalDate promotionBegin,
    LocalDate promotionEnd
){
}
