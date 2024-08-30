package org.senla.woodygray.controller;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.ChangeOfferStatusDto;
import org.senla.woodygray.dtos.OfferDto;
import org.senla.woodygray.dtos.SearchOfferRequest;
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
    public ResponseEntity<List<OfferDto>> searchOffer(@RequestBody SearchOfferRequest searchOfferRequest) throws OfferSearchException {

        List<OfferDto> result = offerService.searchOffer(
                searchOfferRequest.getKeyword(),
                searchOfferRequest.getMinPrice(),
                searchOfferRequest.getMaxPrice()
        );

        return ResponseEntity.ok(result);

    }

    @PostMapping()
    public ResponseEntity<?> createOffer(
            @RequestBody OfferDto offerDto,
            @RequestHeader(value = "Authorization") String auth) throws UserNotFoundException, OfferAlreadyExistException {
        User user = userService.findByToken(auth);

        offerService.createOffer(offerDto, user);
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
