package org.senla.woodygray.service;

import lombok.RequiredArgsConstructor;
import org.senla.woodygray.dtos.JwtRequest;
import org.senla.woodygray.dtos.JwtResponse;
import org.senla.woodygray.dtos.RegistrationUserDto;
import org.senla.woodygray.dtos.UserDto;
import org.senla.woodygray.model.User;
import org.senla.woodygray.util.JwtTokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.management.relation.RoleNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserPhoneNumber(), authRequest.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUserPhoneNumber());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto, PasswordEncoder passwordEncoder) throws RoleNotFoundException {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }
        if (userService.findByPhoneNumber(registrationUserDto.getPhoneNumber()).isPresent()){
            return ResponseEntity.badRequest().body("Phone number already in use");
        }
        User user = userService.createNewUser(registrationUserDto, passwordEncoder);
        return ResponseEntity.ok(new UserDto(
                user.getId(),
                user.getPhoneNumber(),
                user.getFirstName()
        ));
    }

}
