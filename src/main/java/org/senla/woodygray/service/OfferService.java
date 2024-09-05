package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.mapper.OfferMapper;
import org.senla.woodygray.dtos.mapper.PhotoMapper;
import org.senla.woodygray.dtos.offer.OfferSearchResponse;
import org.senla.woodygray.dtos.offer.OfferUpdateRequest;
import org.senla.woodygray.dtos.offer.OfferUpdateResponse;
import org.senla.woodygray.exceptions.*;
import org.senla.woodygray.model.Offer;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.OfferRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final OfferStatusService offerStatusService;
    private final OfferMapper offerMapper;
    private final UserService userService;
    private final PhotoMapper photoMapper;

    @Transactional
    public List<OfferSearchResponse> searchOffer(String keyword, Double minPrice, Double maxPrice) throws OfferSearchException {

        List<Offer> offers;
        if (keyword == null) {
            offers = offerRepository.findAll();
        } else {
            offers = offerRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword);
        }

        List<OfferSearchResponse> offerSearchResponses = offerMapper.toOfferSearchResponseList(offers);

        if (offerSearchResponses.isEmpty()) {
            throw new OfferSearchException("Result offer list is empty");
        }

        if (maxPrice == null && minPrice == null) {
            return offerSearchResponses;
        } else if (maxPrice == null) {
            return offerSearchResponses.stream()
                    .filter(o -> o.price().compareTo(minPrice) >= 0)
                    .toList();
        } else if (minPrice == null) {
            return offerSearchResponses.stream()
                    .filter(o -> o.price().compareTo(maxPrice) <= 0)
                    .toList();
        } else {
            return offerSearchResponses.stream()
                    .filter(o -> o.price().compareTo(minPrice) >= 0 &&
                            o.price().compareTo(maxPrice) <= 0)
                    .toList();
        }
    }

    @Transactional
    public OfferUpdateResponse createOffer(OfferUpdateRequest offerDto, Long userId) throws OfferAlreadyExistException, UserNotFoundException {

        User user = userService.findById(userId);

        boolean offerExist = offerRepository.findAllByUserId(userId).stream()
                .anyMatch(offer -> offer.getTitle().equalsIgnoreCase(offerDto.title()));

        if (offerExist) {
            throw new OfferAlreadyExistException(String.format(
                    "Can't create offer with title %s, cause offer already exist at user with id %s",
                    offerDto.title(),
                    userId)
            );
        }
        Offer offer = offerMapper.toOffer(offerDto);
        offer.setId(null);
        offer.setUser(user);
        offer.setOfferStatus(offerStatusService.getPublishedStatus());

        offer.getPhotos()
                .forEach(photo -> photo.setOffer(offer));

        offerRepository.save(offer);
        return offerMapper.toOfferUpdateResponse(offer);
    }

    @Transactional
    public OfferUpdateResponse changeStatus(OfferUpdateRequest offerDto, Long offerId) throws OfferChangeStatusException {

        if (offerDto.statusId() == null) {
            throw new BadCredentialsException("offer id or status id is required");
        }

        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        if (optionalOffer.isEmpty()) {
            throw new OfferChangeStatusException(String.format("can't find offer with id %s", offerId));
        }

        Offer offer = optionalOffer.get();
        if (offer.getOfferStatus().getId().equals(offerDto.statusId())) {
            throw new OfferChangeStatusException("offer status id is not correct");
        }

        offerRepository.updateStatus(offer.getId(), offerDto.statusId());
        return offerMapper.toOfferUpdateResponse(offer);

    }

    @Transactional
    public OfferUpdateResponse update(OfferUpdateRequest offerDto, Long offerId) {

        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        if (optionalOffer.isEmpty()) {
            throw new OfferUpdateException(String.format("can't find offer with id %s", offerId));
        }

        Offer offer = optionalOffer.get();

        offerMapper.updateOfferFromDto(offerDto, offer);
        if (offerDto.photos() != null) {
            if (offer.getPhotos() == null || offer.getPhotos().isEmpty()) {
                offer.setPhotos(photoMapper.toPhotos(offerDto.photos()));
            }else {
                photoMapper.updatePhotoList(offerDto.photos(), offer.getPhotos());
            }
            offer.getPhotos().stream()
                    .filter(photo -> photo.getOffer() == null)
                    .forEach(photo -> photo.setOffer(offer));
        }

        offerRepository.update(offer);

        return offerMapper.toOfferUpdateResponse(offer);
    }

    @Transactional
    public OfferUpdateResponse changePhotos(OfferUpdateRequest offerUpdateRequest, Long id) {
        //TODO: сделать проверку на пользователя
        if (offerUpdateRequest.photos() == null) {
            throw new BadCredentialsException("offer photos is required");
        }
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        if (optionalOffer.isEmpty()) {
            throw new OfferUpdateException(String.format("can't find offer with id %s", id));
        }

        Offer offer = optionalOffer.get();
        offerRepository.deletePhotosFromOffer(id);

        offer.setPhotos(photoMapper.toPhotos(offerUpdateRequest.photos()));
        offer.getPhotos()
                .forEach(photo -> photo.setOffer(offer));

        offerRepository.update(offer);

        return offerMapper.toOfferUpdateResponse(offer);
    }

    public Offer findById(Long offerId) {
        return offerRepository.findById(offerId).orElse(null);
    }
}
