package org.senla.woodygray.dtos;

import lombok.Data;

@Data
public class SearchOfferRequest {
    private String keyword;
    private Double minPrice;
    private Double maxPrice;
}
