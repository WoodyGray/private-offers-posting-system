package org.senla.woodygray.dtos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.senla.woodygray.dtos.OfferSearchResponse;
import org.senla.woodygray.dtos.PhotoDto;
import org.senla.woodygray.model.Offer;
import org.senla.woodygray.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PhotoMapper.class})
public abstract class OfferMapper {

    @Autowired
    private PhotoMapper photoMapper;

    @Mapping(source = "id", target = "offerID")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "promotionBegin", target = "promotionBegin")
    @Mapping(source = "promotionEnd", target = "promotionEnd")
    @Mapping(source = "photos", target = "photos", qualifiedByName = "toPhotoDtos")
    public abstract OfferSearchResponse toOfferSearchResponse(Offer offer);

    public abstract List<OfferSearchResponse> toOfferSearchResponseList(List<Offer> offers);

}