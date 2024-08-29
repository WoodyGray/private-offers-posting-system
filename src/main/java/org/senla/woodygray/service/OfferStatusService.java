package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.senla.woodygray.model.OfferStatus;
import org.senla.woodygray.repository.OfferStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferStatusService {

    private final OfferStatusRepository offerStatusRepository;

    @Transactional
    public OfferStatus getPublishedStatus() {
        Optional<OfferStatus> offerStatus = offerStatusRepository.getPublishedStatus();
        return offerStatus.get();
        //TODO: что-то нужно сделать с optional
    }
}
