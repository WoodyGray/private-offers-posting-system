package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.mapper.OfferMapper;
import org.senla.woodygray.dtos.mapper.PhotoMapper;
import org.senla.woodygray.dtos.offer.OfferGetSoldResponse;
import org.senla.woodygray.dtos.offer.OfferSearchResponse;
import org.senla.woodygray.dtos.offer.OfferUpdateRequest;
import org.senla.woodygray.dtos.offer.OfferUpdateResponse;
import org.senla.woodygray.exceptions.*;
import org.senla.woodygray.model.Offer;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.OfferRepository;
import org.senla.woodygray.util.JwtTokenUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final OfferStatusService offerStatusService;
    private final OfferMapper offerMapper;
    private final UserService userService;
    private final PhotoMapper photoMapper;
    private final JwtTokenUtils jwtTokenUtils;

    @Transactional
    public List<OfferSearchResponse> searchOffer(String keyword, Double minPrice, Double maxPrice) throws OfferSearchException {

        List<Offer> offers;
        if (keyword == null) {
            offers = offerRepository.findAll();
        } else {
            offers = offerRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword);
        }

        LocalDate now = LocalDate.now();
        List<Offer> sortedOffers = offers.stream()
                .collect(Collectors.partitioningBy(
                        offer -> offer.getPromotionEnd() != null && offer.getPromotionEnd().isAfter(now)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<Boolean, List<Offer>>comparingByKey().reversed())
                .flatMap(entry -> entry.getValue().stream())
                .toList();

        List<OfferSearchResponse> offerSearchResponses = offerMapper
                .toOfferSearchResponseList(sortedOffers);

        return createResultList(minPrice, maxPrice, offerSearchResponses);
    }

    private List<OfferSearchResponse> createResultList(Double minPrice, Double maxPrice, List<OfferSearchResponse> offers) {

        if (offers.isEmpty()) {
            throw new OfferSearchException("Result offer list is empty");
        }

        if (maxPrice == null && minPrice == null) {
            return offers;
        } else if (maxPrice == null) {
            return offers.stream()
                    .filter(o -> o.price().compareTo(minPrice) >= 0)
                    .toList();
        } else if (minPrice == null) {
            return offers.stream()
                    .filter(o -> o.price().compareTo(maxPrice) <= 0)
                    .toList();
        } else {
            return offers.stream()
                    .filter(o -> o.price().compareTo(minPrice) >= 0 &&
                            o.price().compareTo(maxPrice) <= 0)
                    .toList();
        }
    }

    @Transactional
    public OfferUpdateResponse createOffer(OfferUpdateRequest offerDto, String token) throws OfferAlreadyExistException, UserNotFoundException {

        User user = userService.findByToken(token);
        Long userId = user.getId();

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
        //TODO:попробовать кеш сделать

        offer.getPhotos()
                .forEach(photo -> photo.setOffer(offer));

        //TODO:use mapstruckt
        offerRepository.save(offer);
        return offerMapper.toOfferUpdateResponse(offer);
    }

    @Transactional
    public OfferUpdateResponse changeStatus(@Valid OfferUpdateRequest offerDto, Long offerId, String token) throws OfferChangeStatusException {

        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        if (optionalOffer.isEmpty()) {
            throw new OfferChangeStatusException(String.format("can't find offer with id %s", offerId));
        }

        Offer offer = optionalOffer.get();
        Long userId = jwtTokenUtils.getUserId(token);
        if (offer.getUser().getId() != userId) {
            throw new OfferChangeStatusException("Only host of offer can change status");
        }
        if (offer.getOfferStatus().getId().equals(offerDto.statusId())) {
            throw new OfferChangeStatusException("offer status id is not correct");
        }

        offerRepository.updateStatus(offer.getId(), offerDto.statusId());
        return offerMapper.toOfferUpdateResponse(offer);

    }

    @Transactional
    public OfferUpdateResponse update(OfferUpdateRequest offerDto, Long offerId, String token) {

        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        if (optionalOffer.isEmpty()) {
            throw new OfferUpdateException(String.format("can't find offer with id %s", offerId));
        }

        Offer offer = optionalOffer.get();
        if (!offer.getUser().getId().equals(jwtTokenUtils.getUserId(token))) {
            throw new HostException("Only host of offer can update status");
        }

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

    public void update(Offer offer){
        offerRepository.update(offer);
    }

    @Transactional
    public OfferUpdateResponse changePhotos(OfferUpdateRequest offerUpdateRequest, Long id, String token) {

        Optional<Offer> optionalOffer = offerRepository.findById(id);
        if (optionalOffer.isEmpty()) {
            throw new OfferUpdateException(String.format("can't find offer with id %s", id));
        }

        Offer offer = optionalOffer.get();
        if (!offer.getUser().getId().equals(jwtTokenUtils.getUserId(token))) {
            throw new HostException("Only host of offer can change photos");
        }
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

    @Transactional
    public List<OfferGetSoldResponse> getSold(Long idUser) {
        userService.findById(idUser);
        List<Offer> offers = offerRepository.findSoldByUserId(idUser);

        return offerMapper.toOfferGetSoldResponseList(offers);
    }
}
