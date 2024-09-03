package org.senla.woodygray.exceptions;

public class UserModificationException extends Exception{
    public UserModificationException(String userPhoneNumber, Long userModifiedId) {
        super("User with phone number " + userPhoneNumber + " try modified user with id " + userModifiedId);
    }
}
