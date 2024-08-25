package com.metene.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String username;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String company;
    private String suscriptionPlan;
}
