package com.metene.srciptbanner;

import com.metene.cookie.dto.CookieResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScriptBannerResponse {
    // TODO: Esta información se guarda al crear el banner y tiene que ir en otra petición.
//    private String codigoCss;
//    private String codigoJs;
    // Información a devolver
    private String dominio;
    private String mensajeBienvenidad;
    private List<CookieResponse> cookies;
}
