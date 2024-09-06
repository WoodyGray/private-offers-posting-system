package org.senla.woodygray.controller;


import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.ReviewCreateRequest;
import org.senla.woodygray.dtos.ReviewCreateResponse;
import org.senla.woodygray.dtos.UserChangesDto;
import org.senla.woodygray.exceptions.UserModificationException;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.service.ReviewService;
import org.senla.woodygray.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReviewService reviewService;

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody UserChangesDto userChangesDto,
            @RequestHeader(value = "Authorization") String auth) throws UserNotFoundException, UserModificationException {
        return userService.updateUser(id, userChangesDto, auth.substring(7));
        //TODO:любой авторизированный пользователь не может изменить другого по id
    }

    @PostMapping("/review/{idSeller}")
    public ResponseEntity<ReviewCreateResponse> reviewUser(
            @PathVariable Long idSeller,
            @RequestBody ReviewCreateRequest reviewCreateRequest,
            @RequestHeader(value = "Authorization") String token
    ){
        return ResponseEntity.ok(
                reviewService.createReview(idSeller, reviewCreateRequest, token.substring(7)));
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String token
    ){
        reviewService.deleteReview(id, token.substring(7));
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping("/secured")
    public String getSecuredInfo() {
        return "Info for authenticate users";
    }

    @GetMapping("/unsecured")
    public String getUnsecuredInfo() {
        return "Public info";
    }

    @GetMapping("/admin")
    public String getAdminInfo() {
        return "Admin info";
    }

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }

}
