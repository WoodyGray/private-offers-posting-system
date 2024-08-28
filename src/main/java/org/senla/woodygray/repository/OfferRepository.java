package org.senla.woodygray.repository;

import org.senla.woodygray.dtos.OfferDto;

import java.util.List;

public interface OfferRepository {
    List<OfferDto> findByTitleContainingOrDescriptionContaining(String title, String description);

}
