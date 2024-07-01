package com.metene.service;

import com.metene.service.dto.CookieResponse;

import java.util.List;

public interface CookieService {
    CookieResponse getCookie(String token, String name);
    List<CookieResponse> getAllCookies(String token);
    boolean deleteCookie(String token, String cookieName);
}
