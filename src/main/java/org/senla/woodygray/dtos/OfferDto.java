package org.senla.woodygray.dtos;

import lombok.Data;
import lombok.Getter;

@Getter
public class OfferDto {
    private Long userId;
    private String title;
    private String description;
    private Double price;

    public OfferDto(long userId, String title, String description, double price) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.price = price;
    }
}
