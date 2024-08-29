package org.senla.woodygray.controller;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.OfferDto;
import org.senla.woodygray.dtos.SearchOfferRequest;
import org.senla.woodygray.exceptions.OfferSearchException;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.model.User;
import org.senla.woodygray.service.OfferService;
import org.senla.woodygray.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/offer")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<OfferDto>> searchOffer(@RequestBody SearchOfferRequest searchOfferRequest) throws OfferSearchException {

        List<OfferDto> result = offerService.searchOffer(
                searchOfferRequest.getKeyword(),
                searchOfferRequest.getMinPrice(),
                searchOfferRequest.getMaxPrice()
        );

        return ResponseEntity.ok(result);

    }

    @PostMapping("/new")
    public ResponseEntity<?> createOffer(
            @RequestBody OfferDto offerDto,
            @RequestHeader(value = "Authorization") String auth) throws UserNotFoundException {
        User user;
        try {
            user = userService.findByToken(auth.substring(7)).get();
        }catch (NoSuchElementException e){
            throw new UserNotFoundException("Can't find user by token");
        }

        offerService.createOffer(offerDto, user);

        return null;

    }
}
