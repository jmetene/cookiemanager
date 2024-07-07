package com.metene.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotNull(message = "username cannot be null")
    @NotEmpty(message = "username cannot be an empty value")
    private String username;
    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be an empty value")
    @Pattern(regexp = "^(?=[\\w\\s]{3,40}$)(?!^[\\W_])(?!.*[\\W_]$)[\\w\\s]+$\n",
            message = "El nombre debe tener entre 3 y 40 caracteres, no debe comenzar ni terminar con un carácter especial")
    private String name;
    @NotNull(message = "firstname cannot be null")
    @NotEmpty(message = "firstname cannot be an empty value")
    private String firstName;
    private String lastName;
    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be an empty value")
    private String password;
    @Email(message = "Email must be a valid email")
    private String email;
    @NotNull(message = "company cannot be null")
    @NotEmpty(message = "company cannot be an empty value")
    private String company;
}
