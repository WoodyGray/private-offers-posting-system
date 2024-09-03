package org.senla.woodygray.dtos.mapper;

import org.mapstruct.*;
import org.senla.woodygray.dtos.UserChangesDto;
import org.senla.woodygray.model.User;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserChangesDto dto, @MappingTarget User entity);
}