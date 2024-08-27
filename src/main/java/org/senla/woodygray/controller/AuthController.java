package org.senla.woodygray.controller;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.JwtRequest;
import org.senla.woodygray.dtos.JwtResponse;
import org.senla.woodygray.service.UserService;
import org.senla.woodygray.util.JwtTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
//    private final UserService userService;
//    private final JwtTokenUtils jwtTokenUtils;
//    private final AuthenticationManager authenticationManager;

//    @PostMapping("/auth")
//    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest){
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserPhoneNumber(), authRequest.getPassword()));
//        } catch (BadCredentialsException e){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUserPhoneNumber());
//        String token = jwtTokenUtils.generateToken(userDetails);
//        return ResponseEntity.ok(new JwtResponse(token));
//    }

}
