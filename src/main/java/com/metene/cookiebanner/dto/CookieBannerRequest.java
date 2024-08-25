package com.metene.cookiebanner.dto;

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
    private String cookieDeclaration;
    private String logo;
    private String legislation;
    private String legislationDescription;
    private String cookiePolicy;
    private String privacyPolicy;
    private String consentType;
    private String lang;
}
