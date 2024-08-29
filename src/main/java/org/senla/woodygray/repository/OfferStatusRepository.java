package org.senla.woodygray.repository;

import org.senla.woodygray.model.OfferStatus;

import java.util.Optional;

public interface OfferStatusRepository {
    public Optional<OfferStatus> getPublishedStatus();
}
