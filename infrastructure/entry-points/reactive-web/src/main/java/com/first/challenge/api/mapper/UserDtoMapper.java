package com.first.challenge.api.mapper;


import com.first.challenge.api.dto.CreateUserDto;
import com.first.challenge.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    CreateUserDto toResponse(User user);
    User toEntity(CreateUserDto dto);
}
