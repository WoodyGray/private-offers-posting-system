package org.senla.woodygray.repository;

import org.senla.woodygray.model.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    List<Offer> findAll();
    List<Offer> findAllByUserId(Long userId);

    List<Offer> findByTitleContainingOrDescriptionContaining(String title, String description);

    void save(Offer offer);

    void updateStatus(Long offerId, Long offerStatusId);

    Optional<Offer> findById(Long offerID);

    void update(Offer offer);

    void deletePhotosFromOffer(Long id);

    List<Offer> findSoldByUserId(Long userId);


    //TODO: оставить модификатор?
}
