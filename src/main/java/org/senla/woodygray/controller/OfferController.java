package org.senla.woodygray.controller;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.OfferDto;
import org.senla.woodygray.dtos.SearchOfferRequest;
import org.senla.woodygray.exceptions.OfferSearchException;
import org.senla.woodygray.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/offer")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/search")
    public ResponseEntity<List<OfferDto>> searchOffer(@RequestBody SearchOfferRequest searchOfferRequest) throws OfferSearchException {

        List<OfferDto> result = offerService.searchOffer(
                searchOfferRequest.getKeyword(),
                searchOfferRequest.getMinPrice(),
                searchOfferRequest.getMaxPrice()
        );

        return ResponseEntity.ok(result);

    }
}
