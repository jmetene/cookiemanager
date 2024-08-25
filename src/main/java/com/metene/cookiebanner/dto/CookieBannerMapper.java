package com.metene.cookiebanner.dto;

import com.metene.cookiebanner.ConsentType;
import com.metene.cookiebanner.CookieBanner;

public class CookieBannerMapper {
    private CookieBannerMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CookieBanner toEntity(CookieBannerRequest banner) {
        return CookieBanner
                .builder()
                .title(banner.getTitle())
                .cookieDeclaration(banner.getCookieDeclaration())
                .companyLogo(banner.getLogo())
                .legislation(banner.getLegislation())
                .legislationDesc(banner.getLegislationDescription())
                .consentType(ConsentType.valueOf(banner.getConsentType()))
                .cookiePolicy(banner.getCookiePolicy())
                .privacyPolicy(banner.getPrivacyPolicy())
                .lang(banner.getLang())
                .build();
    }

    public static CookieBannerResponse toDto(CookieBanner banner) {
        return CookieBannerResponse
                .builder()
                .title(banner.getTitle())
                .cookieDeclaration(banner.getCookieDeclaration())
                .logo(banner.getCompanyLogo())
                .legislation(banner.getLegislation())
                .legislationDescription(banner.getLegislationDesc())
                .cookiePolicy(banner.getCookiePolicy())
                .privacyPolicy(banner.getPrivacyPolicy())
                .consentType(String.valueOf(banner.getConsentType()))
                .lang(banner.getLang())
                .build();
    }
}
