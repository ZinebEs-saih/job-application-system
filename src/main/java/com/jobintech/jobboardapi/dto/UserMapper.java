package com.jobintech.jobboardapi.dto;

import com.jobintech.jobboardapi.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponseDTO(User user);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(UserRequestDTO dto, @MappingTarget User user);
}

