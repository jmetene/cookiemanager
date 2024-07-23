package com.metene.service.impl;

import com.metene.domain.entity.Cookie;
import com.metene.domain.entity.Domain;
import com.metene.domain.repository.CookieRepository;
import com.metene.domain.repository.DomainRepository;
import com.metene.service.CookieService;
import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.CookieResponse;
import com.metene.service.dto.Statistic;
import com.metene.service.mapper.CookieMapper;
import com.metene.service.mapper.CookieStatisticMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {

    private final CookieRepository cookieRepository;
    private final DomainRepository domainRepository;

    @Override
    public CookieResponse getCookie(Long id) {

        Cookie cookie = cookieRepository.findById(id).orElseThrow();

        return CookieMapper.toDto(cookie);
    }

    @Override
    public List<CookieResponse> getAllCookies(Long domain) {
        List<Cookie> cookies = cookieRepository.findByDomain(domain).orElseThrow();
        return cookies.stream().map(CookieMapper::toDto).toList();
    }

    @Override
    public void delete(Long cookieId) {
        Cookie cookie = cookieRepository.findById(cookieId).orElseThrow();
        Domain domainToUpdate = cookie.getDomain();

        domainToUpdate.removeCookie(cookie);
        domainRepository.save(domainToUpdate);
    }

    @Override
    public void update(Long cookieId, CookieRequest request) {
        Cookie currentCookie = cookieRepository.findById(cookieId).orElseThrow();

        Cookie cookieToUpdate = CookieMapper.toEntity(request);
        cookieToUpdate.setDomain(currentCookie.getDomain());
        cookieToUpdate.setId(currentCookie.getId());

       cookieRepository.save(cookieToUpdate);
    }

    @Override
    public void cargarEstadisticas(Long cookieId, List<Statistic> statistics) {
        Cookie currentCookie = cookieRepository.findById(cookieId).orElseThrow();
        Domain domain = currentCookie.getDomain();

        currentCookie.addStatistics(CookieStatisticMapper.toEntityList(statistics));
        domain.addCookie(currentCookie);
        domainRepository.save(domain);
    }
}
