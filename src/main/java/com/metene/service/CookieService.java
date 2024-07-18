package com.metene.service;

import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.CookieResponse;

import java.util.List;

public interface CookieService {
    CookieResponse getCookie(Long domain, String name);
    List<CookieResponse> getAllCookies(Long domain);
    void delete(Long domain, String cookieName);
    void update(Long domainId, CookieRequest request);
    void addCookie(Long domainId, CookieRequest request);
}
