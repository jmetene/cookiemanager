package com.metene.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetRequest {
    @Email(message = "The email must be valid")
    private String email;
    @Size(min = 8, message = "Password length must be longer than 8")
    private String password;
}
