package org.senla.woodygray.dtos;

import lombok.Data;


public record OfferSearchRequest(
    Double maxPrice,
    String keyword,
    Double minPrice
){
}
