package com.metene.service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "username cannot be an empty or null")
    private String username;
    @NotBlank(message = "username cannot be an empty or null")
    private String name;
    @NotBlank(message = "firstname cannot be an empty value or null")
    private String firstName;
    private String lastName;
    @NotBlank(message = "password cannot be an empty value or null")
    private String password;
    @Email(message = "Email must be a valid email")
    private String email;
    @NotBlank(message = "company cannot be an empty value or null")
    private String company;
    @NotBlank(message = "Suscription plan cannot be empty or null")
    private String suscriptionPlan;
}
