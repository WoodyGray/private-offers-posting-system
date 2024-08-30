package org.senla.woodygray.dtos;

import lombok.Data;

@Data
public class ChangeOfferStatusDto {
    private Long offerID;
    private String title;
    private Long offerStatusId;
    //TODO: есть ли смысл в этом dto?
}
