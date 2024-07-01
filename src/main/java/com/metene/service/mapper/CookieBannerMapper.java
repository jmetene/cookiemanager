package com.metene.service.mapper;

import com.metene.domain.entity.*;
import com.metene.service.dto.CookieBannerRequest;
import com.metene.service.dto.CookieBannerResponse;

public class CookieBannerMapper {
    private CookieBannerMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CookieBanner toEntity(CookieBannerRequest banner) {
        return CookieBanner
                .builder()
                .title(banner.getTitle())
                .description(banner.getDescription())
                .companyLogo(banner.getLogo())
                .legislation(Legislation.valueOf(banner.getLegislation()))
                .legislationDescription(banner.getLegislationDescription())
                .method(ComplienceMethod.valueOf(banner.getComplienceMethod()))
                .type(ComplienceType.valueOf(banner.getComplienceType()))
                .territoryForVisitor(Visitor.valueOf(banner.getVisitorTerritory()))
                .style(banner.getStyle())
                .lang(banner.getLang())
                .build();
    }

    public static CookieBannerResponse toDto(CookieBanner banner) {
        return CookieBannerResponse
                .builder()
                .title(banner.getTitle())
                .description(banner.getDescription())
                .logo(banner.getCompanyLogo())
                .legislation(String.valueOf(banner.getLegislation()))
                .legislationDescription(banner.getLegislationDescription())
                .lang(banner.getLang())
                .build();
    }
}
