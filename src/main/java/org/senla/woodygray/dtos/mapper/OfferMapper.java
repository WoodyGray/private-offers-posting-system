package org.senla.woodygray.dtos.mapper;

import org.mapstruct.*;
import org.senla.woodygray.dtos.offer.OfferGetSoldResponse;
import org.senla.woodygray.dtos.offer.OfferSearchResponse;
import org.senla.woodygray.dtos.offer.OfferUpdateRequest;
import org.senla.woodygray.dtos.offer.OfferUpdateResponse;
import org.senla.woodygray.model.Offer;
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

    @Mapping(source = "photos", target = "photos", qualifiedByName = "toPhotos")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Offer toOffer(OfferUpdateRequest offerUpdateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateOfferFromDto(OfferUpdateRequest offerUpdateRequest, @MappingTarget Offer offer);

    @Mapping(source = "id", target = "offerId")
    public abstract OfferUpdateResponse toOfferUpdateResponse(Offer offer);

    public abstract List<OfferSearchResponse> toOfferSearchResponseList(List<Offer> offers);

    @Mapping(source = "id", target = "offerID")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "photos", target = "photos", qualifiedByName = "toPhotoDtos")
    public abstract OfferGetSoldResponse toOfferGetSoldResponse(Offer offer);

    public abstract List<OfferGetSoldResponse> toOfferGetSoldResponseList(List<Offer> offers);
}