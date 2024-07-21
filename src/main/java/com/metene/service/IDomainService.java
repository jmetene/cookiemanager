package com.metene.service;

import com.metene.domain.entity.CookieBanner;
import com.metene.service.dto.CookieBannerRequest;
import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.DomainRequest;
import com.metene.service.dto.DomainResponse;

import java.util.List;

public interface IDomainService {
    void create(DomainRequest request, String token);
    DomainResponse getDetails(Long id);
    List<DomainResponse> getAll(String token);
    void delete(Long id);
    void update(Long id, DomainRequest data);
    void addCookies(Long id, List<CookieRequest> cookies);
    void addBanner(Long id, CookieBannerRequest request);
    void addCookie(Long id, CookieRequest request);
    void updateBanner(CookieBanner banner, CookieBannerRequest bannerRequest);
    void deleteBanner(CookieBanner banner);
}
