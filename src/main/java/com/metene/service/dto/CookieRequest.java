package com.metene.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CookieRequest {
    private String name;
    private String type;
    private String description;
    private String provider;
    private String duration;
    private String sameSite;
    private boolean httpOnly;
    private boolean secure;
}
