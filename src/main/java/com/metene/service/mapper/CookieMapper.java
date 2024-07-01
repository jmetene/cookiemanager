package com.metene.service.mapper;

import com.metene.domain.entity.COOKIEType;
import com.metene.domain.entity.Cookie;
import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.CookieResponse;
import org.springframework.util.StringUtils;

public class CookieMapper {
    private CookieMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Cookie toEntity(CookieRequest request) {
        return Cookie
                .builder()
                .name(request.getName())
                .type(generateTypeFromRequest(request.getType()))
                .description(request.getDescription())
                .domain(request.getDomain())
                .sameSite(request.isSameSite())
                .httpOnly(request.isHttpOnly())
                .secure(request.isSecure())
                .build();
    }

    public static CookieResponse toDto(Cookie cookie) {
        return CookieResponse
                .builder()
                .name(cookie.getName())
                .description(cookie.getDescription())
                .domain(cookie.getDomain())
                .type(String.valueOf(cookie.getType()))
                .build();
    }

    private static COOKIEType generateTypeFromRequest(String type) {
        String typeName = StringUtils.capitalize(type);

        return switch (typeName) {
            case "SESSION" -> COOKIEType.SESSION;
            case "PERSISTENT" -> COOKIEType.PERSISTENT;
            case "THIRD_PARTY" -> COOKIEType.THIRD_PARTY;
            case "SECURE" -> COOKIEType.SECURE;
            case "PERFORMANCE" -> COOKIEType.PERFORMANCE;
            case "ANALYTICS" -> COOKIEType.ANALYTICS;
            case "ADVERTISING" -> COOKIEType.ADVERTISING;
            default -> throw new IllegalStateException("Unexpected value: " + typeName);
        };
    }
}
