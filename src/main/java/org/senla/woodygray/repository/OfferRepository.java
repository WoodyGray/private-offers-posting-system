package org.senla.woodygray.repository;

import org.senla.woodygray.dtos.OfferDto;
import org.senla.woodygray.model.Offer;

import java.util.List;

public interface OfferRepository {

    List<OfferDto> findAll();
    List<OfferDto> findByTitleContainingOrDescriptionContaining(String title, String description);

    void save(Offer offer);

    //TODO: оставить модификатор?
}
