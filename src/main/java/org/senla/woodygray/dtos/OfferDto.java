package org.senla.woodygray.dtos;


import lombok.Getter;
import lombok.Setter;
import org.senla.woodygray.model.Photo;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OfferDto {
    private Long offerID;
    private Long userId;
    private String title;
    private String description;
    private Double price;
    private List<Photo> photos;
    private List<String> photosFilePath;
    private Date promotionBegin;
    private Date promotionEnd;

    public OfferDto(long userId, String title, String description, double price) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public void initPhotosFilePath() {
        photosFilePath = photos.stream()
                .map(Photo::getFilePath)
                .toList();
    }
}
