package org.senla.woodygray.dtos.offer;


import lombok.Data;
import org.senla.woodygray.model.Photo;

import java.util.Date;
import java.util.List;

@Data
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

}
