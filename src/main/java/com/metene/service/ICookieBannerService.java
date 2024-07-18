package com.metene.service;

import com.metene.service.dto.CookieBannerRequest;
import com.metene.service.dto.CookieBannerResponse;

public interface ICookieBannerService {
    CookieBannerResponse getCookieBanner(Long id);
    void deleteCookieBanner(Long id);
    void updateCookieBanner(Long domain, CookieBannerRequest bannerRequest);
}
