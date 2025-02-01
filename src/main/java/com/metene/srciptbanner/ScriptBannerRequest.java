package com.metene.srciptbanner;

import com.metene.cookie.dto.CookieResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScriptBannerRequest {
    private List<CookieResponse> cookies;
    private String apiKey;
    private String dominio;

    /// Información para las estadísticas.
    private String pais;
    private String plataforma;
    private String fechaActual;
    private String categoria;
    // ACEPTADO o RECHAZADO la lista de cookies
    // para lasa estadísitcas
    private String estado;
}
