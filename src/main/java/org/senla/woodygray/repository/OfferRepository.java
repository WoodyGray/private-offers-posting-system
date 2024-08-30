package org.senla.woodygray.repository;

import org.senla.woodygray.dtos.OfferDto;
import org.senla.woodygray.model.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    List<OfferDto> findAll();

    Optional<Long> findOfferIdByTitle(String title);

    List<OfferDto> findByTitleContainingOrDescriptionContaining(String title, String description);

    void save(Offer offer);


    void updateStatus(Long offerId, Long offerStatusId);

    List<Offer> findAllByUserPhoneNumber(String userPhoneNumber);

    Optional<Offer> findById(Long offerID);

    void update(Offer offer);


    //TODO: оставить модификатор?
}
