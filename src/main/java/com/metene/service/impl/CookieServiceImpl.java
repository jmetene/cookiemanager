package com.metene.service.impl;

import com.metene.domain.entity.Cookie;
import com.metene.domain.repository.CookieRepository;
import com.metene.service.CookieService;
import com.metene.service.JWTService;
import com.metene.service.dto.CookieResponse;
import com.metene.service.mapper.CookieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {

    private final CookieRepository cookieRepository;
    private final JWTService jwtService;

    @Override
    public CookieResponse getCookie(String token, String cookieName) {

        Cookie cookie = cookieRepository.findByNameAndUser(cookieName, getUserName(token)).orElseThrow();

        return CookieMapper.toDto(cookie);
    }

    @Override
    public List<CookieResponse> getAllCookies(String token) {
        List<Cookie> cookies = cookieRepository.findByUser(getUserName(token)).orElseThrow();
        return cookies.stream().map(CookieMapper::toDto).toList();
    }

    @Override
    public boolean deleteCookie(String token, String cookieName) {
        return false;
    }

    private String getUserName(String token) {
        return jwtService.getUsernameFromToken(token);
    }
}
