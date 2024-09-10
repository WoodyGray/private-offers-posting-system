package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.offer.PromotionResponse;
import org.senla.woodygray.model.Offer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Temporal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final OfferService offerService;


    @Transactional
    public PromotionResponse promoteOffer(Long offerId) {
        LocalDate promotionBegin = LocalDate.now();
        LocalDate promotionEnd = promotionBegin.plusDays(30);

        Offer offer = offerService.findById(offerId);
        offer.setPromotionBegin(promotionBegin);
        offer.setPromotionEnd(promotionEnd);

        offerService.update(offer);

        return new PromotionResponse(promotionBegin, promotionEnd);
    }


    @Transactional
    public PromotionResponse getInfo(Long offerId) {
        Offer offer = offerService.findById(offerId);
        return new PromotionResponse(offer.getPromotionBegin(), offer.getPromotionEnd());
    }
}
