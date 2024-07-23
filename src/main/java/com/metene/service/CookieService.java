package com.metene.service;

import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.CookieResponse;
import com.metene.service.dto.Statistic;

import java.util.List;

public interface CookieService {
    CookieResponse getCookie(Long cookieId);

    List<CookieResponse> getAllCookies(Long domain);

    void delete(Long cookieId);

    void update(Long cookieId, CookieRequest request);

    void cargarEstadisticas(Long cookieId, List<Statistic> statistics);
}
