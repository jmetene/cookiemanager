package com.metene.common;

import org.springframework.web.context.request.WebRequest;

public class JWTUtils {

    private JWTUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String extractTokenFromRequest(WebRequest request) {
        return request.getHeader("Authorization");
    }

}
