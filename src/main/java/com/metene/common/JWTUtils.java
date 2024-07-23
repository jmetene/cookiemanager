package com.metene.common;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

public class JWTUtils {

    private static final List<String> PARAMETROS = List.of("fechaInicio", "fechaFin", "estado", "pais", "plataforma");
    private JWTUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String extractTokenFromRequest(WebRequest request) {
        // Get the Authorization header from the request
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Check if the Authorization header is not null and starts with "Bearer "
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            // Extract the JWT token (remove "Bearer " prefix)
            return authorizationHeader.substring(7);
        }
        // If the Authorization header is not valid, return null
        return null;
    }

    public static List<String> getParametrosNoValidos(WebRequest request) {

        List<String> parametrosNoValidos = new ArrayList<>();

        request.getParameterMap().keySet().forEach(parametro -> {
            if (!PARAMETROS.contains(parametro))
                parametrosNoValidos.add(parametro);

        });
        return parametrosNoValidos;
    }
}
