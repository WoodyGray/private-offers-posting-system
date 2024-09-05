package org.senla.woodygray.exceptions;

import java.util.function.Supplier;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(Long id){
        super("User with id " + id + " not found");
    }
}
