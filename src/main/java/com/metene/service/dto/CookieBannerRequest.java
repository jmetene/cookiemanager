package com.metene.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CookieBannerRequest {
    private String title;
    private String description;
    private String logo;
    private String legislation;
    private String legislationDescription;
    private String complienceMethod;
    private String complienceType;
    private String visitorTerritory;
    private String style;
    private String lang;
}
