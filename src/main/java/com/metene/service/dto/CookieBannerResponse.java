package com.metene.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CookieBannerResponse {
    private String title;
    private String description;
    private String logo;
    private String legislation;
    private String legislationDescription;
    private String lang;
}
