package org.senla.woodygray.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationUserDto {

    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
}
