package com.metene.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Username cannot be null or empty")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min=8, message = "Password length must be longer than 8")
    private String password;
}
