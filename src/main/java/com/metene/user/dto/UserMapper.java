package com.metene.user.dto;

import com.metene.user.User;

public class UserMapper {
    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static UserResponse toDTO(User user) {
        return UserResponse
                .builder()
                .role(user.getRole().name().toLowerCase())
                .name(user.getName())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .company(user.getCompany())
                .suscriptionPlan(user.getSuscriptionPlan())
                .build();
    }

    public static User toEntity(UserRequest dto) {
        return User
                .builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
//                .username(dto.getUsername())
                .company(dto.getCompany())
                .suscriptionPlan(dto.getSuscriptionPlan())
                .build();
    }
}
