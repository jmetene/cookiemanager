package com.metene.service.impl;

import com.metene.domain.entity.Cookie;
import com.metene.domain.entity.Domain;
import com.metene.domain.repository.CookieRepository;
import com.metene.domain.repository.DomainRepository;
import com.metene.service.CookieService;
import com.metene.service.IDomainService;
import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.CookieResponse;
import com.metene.service.mapper.CookieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {

    private final CookieRepository cookieRepository;
    private final DomainRepository domainRepository;
    private final IDomainService domainService;

    @Override
    public CookieResponse getCookie(Long id, String cookieName) {

        Cookie cookie = cookieRepository.findByDomainAndName(id, cookieName).orElseThrow();

        return CookieMapper.toDto(cookie);
    }

    @Override
    public List<CookieResponse> getAllCookies(Long domain) {
        List<Cookie> cookies = cookieRepository.findByDomain(domain).orElseThrow();
        return cookies.stream().map(CookieMapper::toDto).toList();
    }

    @Override
    public void delete(Long domain, String cookieName) {
        Cookie cookie = cookieRepository.findByDomainAndName(domain, cookieName).orElseThrow();
        Domain domainToUpdate = cookie.getDomain();

        domainToUpdate.removeCookie(cookie);
        domainRepository.save(domainToUpdate);
    }

    @Override
    public void update(Long domainId, CookieRequest request) {
        Cookie cookie = cookieRepository.findByDomainAndName(domainId, request.getName()).orElseThrow();

        Cookie cookieToUpdate = CookieMapper.toEntity(request);
        cookieToUpdate.setId(cookie.getId());

        cookieRepository.save(cookieToUpdate);
    }

    @Override
    public void addCookie(Long domainId, CookieRequest request) {
        domainService.addCookie(domainId, request);
    }
}
