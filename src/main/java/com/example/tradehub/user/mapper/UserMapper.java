package com.example.tradehub.user.mapper;

import com.example.tradehub.user.dto.response.UserResponseDto;
import com.example.tradehub.user.model.User;

public final class UserMapper {

    private UserMapper() {}

    public static UserResponseDto toResponse(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getRole()
        );
    }
}


