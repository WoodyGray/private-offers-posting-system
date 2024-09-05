package org.senla.woodygray.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.senla.woodygray.exceptions.NotFoundException;
import org.senla.woodygray.exceptions.OfferChangeStatusException;
import org.senla.woodygray.exceptions.OfferSearchException;
import org.senla.woodygray.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.relation.RoleNotFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Uncorrected phone number or password");
    }

    @ExceptionHandler(OfferSearchException.class)
    public ResponseEntity<String> handleOfferSearchException(OfferSearchException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(e.getMessage());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleOfferSearchException(RoleNotFoundException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleOfferSearchException(UserNotFoundException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleOfferSearchException(IllegalArgumentException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }


    @ExceptionHandler(OfferChangeStatusException.class)
    public ResponseEntity<String> handleOfferSearchException(OfferChangeStatusException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
                .body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleOfferSearchException(RuntimeException e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOfferSearchException(Exception e) {
        logException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }


    private void logException(Exception e) {
        log.error(String.format("%s in %s", e.getMessage(), e.getStackTrace()[0]));
    }
}
