package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.OfferDto;
import org.senla.woodygray.exceptions.OfferSearchException;
import org.senla.woodygray.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;

    @Transactional
    public List<OfferDto> searchOffer(String keyword, Double minPrice, Double maxPrice) throws OfferSearchException{
        List<OfferDto> offers = offerRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword);
        if (offers.isEmpty()){
            throw new OfferSearchException("Result offer list is empty");
        }


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
}
