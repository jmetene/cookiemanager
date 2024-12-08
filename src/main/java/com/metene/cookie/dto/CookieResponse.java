package com.metene.cookie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CookieResponse {
    private Long id;
    private String name;
    private String type;
    private String description;
    private String provider;
    private String duration;
}
