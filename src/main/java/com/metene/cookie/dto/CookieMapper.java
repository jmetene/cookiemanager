package com.metene.cookie.dto;

import com.metene.cookie.COOKIEType;
import com.metene.cookie.Cookie;
import com.metene.cookie.SameSiteType;

public class CookieMapper {
    private CookieMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Cookie toEntity(CookieRequest request) {
        return Cookie
                .builder()
                .name(request.getName())
                .type(COOKIEType.valueOf(request.getType()))
                .description(request.getDescription())
                .provider(request.getProvider())
                .duration(request.getDuration())
                .sameSite(SameSiteType.valueOf(request.getSameSite()))
                .httpOnly(request.isHttpOnly())
                .secure(request.isSecure())
                .build();
    }

    public static CookieResponse toDto(Cookie cookie) {
        return CookieResponse
                .builder()
                .name(cookie.getName())
                .description(cookie.getDescription())
                .provider(cookie.getProvider())
                .duration(cookie.getDuration())
                .type(String.valueOf(cookie.getType()))
                .build();
    }
}
