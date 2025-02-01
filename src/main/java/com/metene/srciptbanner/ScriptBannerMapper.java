package com.metene.srciptbanner;

import com.metene.cookie.dto.CookieResponse;

import java.util.List;

public class ScriptBannerMapper {
    private ScriptBannerMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ScriptBannerResponse toDTO(ScriptBanner banner, List<CookieResponse> cookies, String bannerInfo) {
        return ScriptBannerResponse
                .builder()
//                .codigoCss(banner.getCodigoCss())
//                .codigoJs(banner.getCodigoJs())
                .mensajeBienvenidad(bannerInfo)
                .dominio(banner.getDominio())
                .cookies(cookies)
                .build();
    }
}
