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

    @GetMapping("/sold/{idUser}")
    public ResponseEntity<List<OfferGetSoldResponse>> getSoldOffer(
            @PathVariable Long idUser) throws OfferNotFoundException {
        return ResponseEntity.ok(offerService.getSold(idUser));
    }

    @PostMapping()
    public ResponseEntity<OfferUpdateResponse> createOffer(
            @RequestBody OfferUpdateRequest offerDto,
            @RequestHeader(value = "Authorization") String token) throws UserNotFoundException, OfferAlreadyExistException {

        var or = offerService.createOffer(offerDto, token.substring(7));
        return ResponseEntity.ok(or);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<OfferUpdateResponse> changeStatus(
            @RequestBody OfferUpdateRequest offerUpdateRequest,
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String token
    ) throws OfferChangeStatusException {

        return ResponseEntity.ok(offerService.changeStatus(offerUpdateRequest, id, token));
    }

    @PutMapping("/photo/{id}")
    public ResponseEntity<OfferUpdateResponse> changePhoto(
            @RequestBody OfferUpdateRequest offerUpdateRequest,
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String token
     ){
        return ResponseEntity.ok(offerService.changePhotos(offerUpdateRequest, id, token));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<OfferUpdateResponse> updateOffer(
            @RequestBody OfferUpdateRequest offerDto,
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String token) {

        return ResponseEntity.ok(offerService.update(offerDto, id, token));
        //TODO: только host должен иметь изменять офер
    }


}
