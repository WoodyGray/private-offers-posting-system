package org.senla.woodygray.controller;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.JwtRequest;
import org.senla.woodygray.dtos.JwtResponse;
import org.senla.woodygray.dtos.RegistrationUserDto;
import org.senla.woodygray.dtos.UserDto;
import org.senla.woodygray.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;
import java.net.PasswordAuthentication;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) throws RoleNotFoundException {
        return authService.createNewUser(registrationUserDto, passwordEncoder);
    }

}
