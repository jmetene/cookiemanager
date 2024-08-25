package com.metene.cookie;

import com.metene.cookie.dto.CookieMapper;
import com.metene.cookie.dto.CookieRequest;
import com.metene.domain.Domain;
import com.metene.domain.DomainRepository;
import com.metene.cookie.dto.CookieResponse;
import com.metene.statistics.dto.Statistic;
import com.metene.statistics.dto.CookieStatisticMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CookieService {

    private final CookieRepository cookieRepository;
    private final DomainRepository domainRepository;
    private static final String COOKIE_NOT_FOUND_MESSAGE = "Cookie not found";

    public CookieResponse getCookie(Long id) {

        Cookie cookie = cookieRepository.findById(id)
                .orElseThrow(() -> new CookieNotFoundException(COOKIE_NOT_FOUND_MESSAGE));

        return CookieMapper.toDto(cookie);
    }

    public List<CookieResponse> getAllCookies(Long domain) {
        List<Cookie> cookies = cookieRepository.findByDomain(domain)
                .orElseThrow(() -> new CookieNotFoundException(COOKIE_NOT_FOUND_MESSAGE));
        return cookies.stream().map(CookieMapper::toDto).toList();
    }

    public void delete(Long cookieId) {
        Cookie cookie = cookieRepository.findById(cookieId)
                .orElseThrow(() -> new CookieNotFoundException(COOKIE_NOT_FOUND_MESSAGE));
        Domain domainToUpdate = cookie.getDomain();

        domainToUpdate.removeCookie(cookie);
        domainRepository.save(domainToUpdate);
    }

    public void update(Long cookieId, CookieRequest request) {
        Cookie currentCookie = cookieRepository.findById(cookieId)
                .orElseThrow(() -> new CookieNotFoundException(COOKIE_NOT_FOUND_MESSAGE));

        Cookie cookieToUpdate = CookieMapper.toEntity(request);
        cookieToUpdate.setDomain(currentCookie.getDomain());
        cookieToUpdate.setId(currentCookie.getId());

       cookieRepository.save(cookieToUpdate);
    }

    public void cargarEstadisticas(Long cookieId, List<Statistic> statistics) {
        Cookie currentCookie = cookieRepository.findById(cookieId)
                .orElseThrow(() -> new CookieNotFoundException(COOKIE_NOT_FOUND_MESSAGE));
        Domain domain = currentCookie.getDomain();

        currentCookie.addStatistics(CookieStatisticMapper.toEntityList(statistics));
        domain.addCookie(currentCookie);
        domainRepository.save(domain);
    }
}
