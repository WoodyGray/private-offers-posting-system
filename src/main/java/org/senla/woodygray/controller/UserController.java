package org.senla.woodygray.controller;


import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.UserChangesDto;
import org.senla.woodygray.exceptions.UserModificationException;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.senla.woodygray.model.User;
import org.senla.woodygray.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        try {
//            List<User> users = new ArrayList<>(userService.getAllUsers());
//
//            if (users.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(users, HttpStatus.OK);
//        } catch (Exception ex) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody UserChangesDto userChangesDto,
            @RequestHeader(value = "Authorization") String auth) throws UserNotFoundException, UserModificationException {
        return userService.updateUser(id, userChangesDto, auth.substring(7));
        //TODO:любой авторизированный пользователь может изменить другого по id, оставить это
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
