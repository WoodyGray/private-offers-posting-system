package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.OfferDto;
import org.senla.woodygray.exceptions.OfferSearchException;
import org.senla.woodygray.model.Offer;
import org.senla.woodygray.model.OfferStatus;
import org.senla.woodygray.model.Photo;
import org.senla.woodygray.model.User;
import org.senla.woodygray.repository.OfferRepository;
import org.senla.woodygray.repository.OfferStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final OfferStatusService offerStatusService;

    @Transactional
    public List<OfferDto> searchOffer(String keyword, Double minPrice, Double maxPrice) throws OfferSearchException{
        List<OfferDto> offers;
        if (keyword == null){
            offers = offerRepository.findAll();
        }else {
            offers = offerRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword);
        }

        if (offers.isEmpty()){
            throw new OfferSearchException("Result offer list is empty");
        }

        offers.stream().forEach(offer -> {
            offer.initPhotosFilePath();
            offer.setPhotos(null);
        });

        if (maxPrice == null && minPrice == null){
            return offers;
        } else if (maxPrice == null) {
            return offers.stream()
                    .filter(o -> o.getPrice().compareTo(minPrice) >= 0)
                    .toList();
        } else if (minPrice == null) {
            return  offers.stream()
                    .filter(o -> o.getPrice().compareTo(maxPrice) <= 0)
                    .toList();
        }else {
            return offers.stream()
                    .filter(o -> o.getPrice().compareTo(minPrice) >= 0 &&
                            o.getPrice().compareTo(maxPrice) <= 0)
                    .toList();
        }
    }

    @Transactional
    public void createOffer(OfferDto offerDto, User user) {
        //TODO: проверка на существующий заказ
        Offer offer = new Offer();
        offer.setUser(user);
        offer.setOfferStatus(offerStatusService.getPublishedStatus());
        offer.setTitle(offerDto.getTitle());
        offer.setDescription(offerDto.getDescription());
        offer.setPrice(offerDto.getPrice());

        List<Photo> photos = offerDto.getPhotosFilePath().stream()
                .map(filePath -> new Photo(offer, filePath))
                .toList();

        offer.setPhotos(photos);

        offerRepository.save(offer);
    }

}
