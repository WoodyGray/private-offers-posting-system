package org.senla.woodygray.dtos.offer;


public record OfferSearchRequest(
    Double maxPrice,
    String keyword,
    Double minPrice
){
}
