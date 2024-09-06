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

    @GetMapping("/sold")
    public ResponseEntity<List<OfferGetSoldResponse>> getSoldOffer(
            @RequestHeader(value = "Authorization") String token) throws OfferNotFoundException {
        return ResponseEntity.ok(offerService.getSold(token.substring(7)));
        //TODO: нет смысла в токене так как любой может смотреть иторию
    }

    @PostMapping()
    public ResponseEntity<OfferUpdateResponse> createOffer(
            @RequestBody OfferUpdateRequest offerDto,
            @RequestHeader(value = "Authorization") String token) throws UserNotFoundException, OfferAlreadyExistException {

        var or = offerService.createOffer(offerDto, token.substring(7));
        return ResponseEntity.ok(or);
        //TODO:добавить проверку с токеном
        //нельзя чтобы один пользователь смог создать offer другому
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<OfferUpdateResponse> changeStatus(
            @RequestBody OfferUpdateRequest offerUpdateRequest,
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String token
    ) throws OfferChangeStatusException {

        return ResponseEntity.ok(offerService.changeStatus(offerUpdateRequest, id, token));
        //TODO: только host должен иметь право менять статус
    }

    @PutMapping("/photo/{id}")
    public ResponseEntity<OfferUpdateResponse> changePhoto(
            @RequestBody OfferUpdateRequest offerUpdateRequest,
            @PathVariable Long id
     ){
        return ResponseEntity.ok(offerService.changePhotos(offerUpdateRequest, id));
        //TODO: только host должен иметь право менять фотки
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OfferUpdateResponse> updateOffer(
            @RequestBody OfferUpdateRequest offerDto,
            @PathVariable Long id) {

        return ResponseEntity.ok(offerService.update(offerDto, id));
        //TODO: только host должен иметь изменять офер
    }


}
