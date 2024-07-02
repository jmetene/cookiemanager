package com.metene.service;

import com.metene.service.dto.*;

import java.util.List;

public interface UserService {
    void saveCookies(List<CookieRequest> cookies, String token);
    void updateUser(UserRequest userRequest, String token);
    void deleteCookie(String token, String cookieName);
    void saveCookieBanner(String token, CookieBannerRequest banner);
    List<CookieBannerResponse> getCookieBanners(String token);
    void updateCookieBanner(String token, CookieBannerRequest banner, Long id) throws NoSuchFieldException;
    UserResponse getUser(String token);
}
