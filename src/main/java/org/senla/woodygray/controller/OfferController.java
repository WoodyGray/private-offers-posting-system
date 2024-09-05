package org.senla.woodygray.controller;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.offer.*;
import org.senla.woodygray.exceptions.*;
import org.senla.woodygray.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offer")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/search")
    public ResponseEntity<List<OfferSearchResponse>> searchOffer(@RequestBody OfferSearchRequest searchOfferRequest) throws OfferSearchException {

        List<OfferSearchResponse> result = offerService.searchOffer(
                searchOfferRequest.keyword(),
                searchOfferRequest.minPrice(),
                searchOfferRequest.maxPrice()
        );

        return ResponseEntity.ok(result);

    }

    @GetMapping("/sold/{userId}")
    public ResponseEntity<List<OfferGetSoldResponse>> getSoldOffer(@PathVariable Long userId) throws OfferNotFoundException {
        return ResponseEntity.ok(offerService.getSold(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OfferUpdateResponse> createOffer(
            @RequestBody OfferUpdateRequest offerDto,
            @PathVariable Long userId) throws UserNotFoundException, OfferAlreadyExistException {

        var or = offerService.createOffer(offerDto, userId);
        return ResponseEntity.ok(or);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<OfferUpdateResponse> changeStatus(
            @RequestBody OfferUpdateRequest offerUpdateRequest,
            @PathVariable Long id
    ) throws OfferChangeStatusException {

        return ResponseEntity.ok(offerService.changeStatus(offerUpdateRequest, id));

    }

    @PutMapping("/photo/{id}")
    public ResponseEntity<OfferUpdateResponse> changePhoto(
            @RequestBody OfferUpdateRequest offerUpdateRequest,
            @PathVariable Long id
     ){
        return ResponseEntity.ok(offerService.changePhotos(offerUpdateRequest, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OfferUpdateResponse> updateOffer(
            @RequestBody OfferUpdateRequest offerDto,
            @PathVariable Long id) {

        return ResponseEntity.ok(offerService.update(offerDto, id));
    }


}
