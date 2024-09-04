package org.senla.woodygray.controller;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.*;
import org.senla.woodygray.exceptions.OfferAlreadyExistException;
import org.senla.woodygray.exceptions.OfferChangeStatusException;
import org.senla.woodygray.exceptions.OfferSearchException;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.model.User;
import org.senla.woodygray.service.OfferService;
import org.senla.woodygray.service.UserService;
import org.senla.woodygray.util.JwtTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offer")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    @GetMapping("/search")
    public ResponseEntity<List<OfferSearchResponse>> searchOffer(@RequestBody OfferSearchRequest searchOfferRequest) throws OfferSearchException {

        List<OfferSearchResponse> result = offerService.searchOffer(
                searchOfferRequest.keyword(),
                searchOfferRequest.minPrice(),
                searchOfferRequest.maxPrice()
        );

        return ResponseEntity.ok(result);

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
    ) throws UserNotFoundException, OfferChangeStatusException {

        return ResponseEntity.ok(offerService.changeStatus(offerUpdateRequest, id));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<OfferUpdateResponse> updateOffer(
            @RequestBody OfferUpdateRequest offerDto,
            @PathVariable Long id) {

        return ResponseEntity.ok(offerService.update(offerDto, id));
    }


}
