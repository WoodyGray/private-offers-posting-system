package org.senla.woodygray.controller;


import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.ReviewCreateRequest;
import org.senla.woodygray.dtos.ReviewCreateResponse;
import org.senla.woodygray.dtos.ReviewGetResponse;
import org.senla.woodygray.dtos.UserChangesDto;
import org.senla.woodygray.exceptions.UserModificationException;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.service.ReviewService;
import org.senla.woodygray.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReviewService reviewService;

    @PatchMapping()
    public ResponseEntity<?> updateUser(
            @RequestBody UserChangesDto userChangesDto,
            @RequestHeader(value = "Authorization") String token) throws UserNotFoundException, UserModificationException {
        return userService.updateUser(userChangesDto, token.substring(7));
        //TODO:любой авторизированный пользователь не может изменить другого по id
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
