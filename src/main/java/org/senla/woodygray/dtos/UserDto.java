package org.senla.woodygray.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.senla.woodygray.model.Offer;
import org.senla.woodygray.model.Role;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String phoneNumber;
    private String firstName;
}

