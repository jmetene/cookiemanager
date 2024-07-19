package com.metene.service;

import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.CookieResponse;
import com.metene.service.dto.Statistic;

import java.util.List;

public interface CookieService {
    CookieResponse getCookie(Long domain, String name);
    List<CookieResponse> getAllCookies(Long domain);
    void delete(Long domain, String cookieName);
    void update(Long domainId, String name,CookieRequest request);
    void addCookie(Long domainId, CookieRequest request);
    void cargarEstadisticas(Long domainId, String name, List<Statistic> statistics);
}
