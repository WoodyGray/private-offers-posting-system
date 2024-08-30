package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.ChangeOfferStatusDto;
import org.senla.woodygray.dtos.OfferDto;
import org.senla.woodygray.exceptions.OfferAlreadyExistException;
import org.senla.woodygray.exceptions.OfferChangeStatusException;
import org.senla.woodygray.exceptions.OfferSearchException;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.model.Offer;
import org.senla.woodygray.model.Photo;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.OfferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final OfferStatusService offerStatusService;

    @Transactional
    public List<OfferDto> searchOffer(String keyword, Double minPrice, Double maxPrice) throws OfferSearchException {
        List<OfferDto> offers;
        if (keyword == null) {
            offers = offerRepository.findAll();
        } else {
            offers = offerRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword);
        }

        if (offers.isEmpty()) {
            throw new OfferSearchException("Result offer list is empty");
        }

        offers.stream().forEach(offer -> {
            offer.initPhotosFilePath();
            offer.setPhotos(null);
        });

        if (maxPrice == null && minPrice == null) {
            return offers;
        } else if (maxPrice == null) {
            return offers.stream()
                    .filter(o -> o.getPrice().compareTo(minPrice) >= 0)
                    .toList();
        } else if (minPrice == null) {
            return offers.stream()
                    .filter(o -> o.getPrice().compareTo(maxPrice) <= 0)
                    .toList();
        } else {
            return offers.stream()
                    .filter(o -> o.getPrice().compareTo(minPrice) >= 0 &&
                            o.getPrice().compareTo(maxPrice) <= 0)
                    .toList();
        }
    }

    @Transactional
    public void createOffer(OfferDto offerDto, User user) throws OfferAlreadyExistException {
        //TODO: сменить логику в пален аргументов
        if (offerRepository.findOfferIdByTitle(offerDto.getTitle()).isEmpty()) {
            Offer offer = new Offer();
            offer.setUser(user);
            offer.setOfferStatus(offerStatusService.getPublishedStatus());
            offer.setTitle(offerDto.getTitle());
            offer.setDescription(offerDto.getDescription());
            offer.setPrice(offerDto.getPrice());
            //TODO: mapstruct

            List<Photo> photos = offerDto.getPhotosFilePath().stream()
                    .map(filePath -> new Photo(offer, filePath))
                    .toList();

            offer.setPhotos(photos);

            offerRepository.save(offer);
        } else {
            throw new OfferAlreadyExistException(String.format("Can't create offer with title %s, cause offer already exist", offerDto.getTitle()));
        }
    }

    @Transactional
    public void changeStatus(ChangeOfferStatusDto offerDto) throws OfferChangeStatusException, UserNotFoundException {

        Optional<Offer> optionalOffer = offerRepository.findById(offerDto.getOfferID());
        //TODO: is empty
        Offer offer;

        if (optionalOffer.isPresent() && !(offer = optionalOffer.get()).getOfferStatus().getId().equals(offerDto.getOfferStatusId())) {
            offerRepository.updateStatus(offer.getId(), offerDto.getOfferStatusId());
        } else {
            throw new OfferChangeStatusException("offer is not exist or offer status id is not correct");
        }
        //TODO: не уверен что это хорошая реализация
    }

    public void update(OfferDto offerDto) {

        Optional<Offer> optionalOffer = offerRepository.findById(offerDto.getOfferID());

        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
            if (offerDto.getTitle() != null) offer.setTitle(offerDto.getTitle());
            if (offerDto.getDescription() != null) offer.setDescription(offerDto.getDescription());
            if (offerDto.getPrice() != null) offer.setPrice(offerDto.getPrice());
            if (offerDto.getPromotionBegin() != null) offer.setPromotionBegin(offerDto.getPromotionBegin());
            if (offerDto.getPromotionEnd() != null) offer.setPromotionEnd(offerDto.getPromotionEnd());
            if (offerDto.getPhotosFilePath() != null) {
                List<Photo> addPhotos = offerDto.getPhotosFilePath().stream()
                        .map(filePath -> new Photo(offer, filePath))
                        .toList();
                offer.getPhotos().addAll(addPhotos);
            }
            //TODO: mapstruct

            offerRepository.update(offer);


        }
    }
}
