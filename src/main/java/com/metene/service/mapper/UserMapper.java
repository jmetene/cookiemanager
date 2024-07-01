package com.metene.service.mapper;

import com.metene.domain.entity.User;
import com.metene.service.dto.UserRequest;
import com.metene.service.dto.UserResponse;

public class UserMapper {
    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static UserResponse toDTO(User user) {
        return UserResponse
                .builder()
                .name(user.getName())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .company(user.getCompany())
                .build();
    }

    public static User toEntity(UserRequest dto) {
        return User
                .builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .username(dto.getUsername())
                .company(dto.getCompany())
                .build();
    }
}
