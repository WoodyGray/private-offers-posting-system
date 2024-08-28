package org.senla.woodygray.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.senla.woodygray.exceptions.OfferSearchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e){
        logException(e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Uncorrected phone number or password");
    }

    @ExceptionHandler(OfferSearchException.class)
    public ResponseEntity<String> handleOfferSearchException(OfferSearchException e){
        logException(e);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOfferSearchException(Exception e){
        logException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    private void logException(Exception e){
        log.error(String.format("%s in %s", e.getMessage(), e.getStackTrace()[0]));
    }
}
