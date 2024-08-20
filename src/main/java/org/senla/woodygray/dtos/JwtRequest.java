package org.senla.woodygray.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String userPhoneNumber;
    private String password;
}
