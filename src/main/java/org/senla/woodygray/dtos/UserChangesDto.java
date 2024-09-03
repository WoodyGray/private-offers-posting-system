package org.senla.woodygray.dtos;

import org.senla.woodygray.model.Role;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public record UserChangesDto(
        String phoneNumber,
        String firstName,
        String lastName,
        Integer rating){
}
