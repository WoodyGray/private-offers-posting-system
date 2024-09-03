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
                searchOfferRequest.getKeyword(),
                searchOfferRequest.getMinPrice(),
                searchOfferRequest.getMaxPrice()
        );

        return ResponseEntity.ok(result);

    }

    @PostMapping("/{id}")
    public ResponseEntity<?> createOffer(
            @RequestBody OfferUpdateRequest offerDto,
            @RequestParam Long userId) throws UserNotFoundException, OfferAlreadyExistException {

        offerService.createOffer(offerDto, userId);
        //TODO: может передавать номер телефона пользователя?

        return new ResponseEntity<>(HttpStatus.OK);
        //TODO: вернуть id
    }

    @PatchMapping("/change.status")
    public ResponseEntity<?> changeStatus(
            @RequestBody ChangeOfferStatusDto changeOfferStatusDto
    ) throws UserNotFoundException, OfferChangeStatusException {

        if (changeOfferStatusDto.getOfferID() != null) {
            offerService.changeStatus(changeOfferStatusDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateOffer(@RequestBody OfferDto offerDto) {
        offerService.update(offerDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
