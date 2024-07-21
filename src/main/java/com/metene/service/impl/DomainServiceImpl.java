package com.metene.service.impl;

import com.metene.domain.entity.Cookie;
import com.metene.domain.entity.CookieBanner;
import com.metene.domain.entity.Domain;
import com.metene.domain.entity.User;
import com.metene.domain.repository.DomainRepository;
import com.metene.domain.repository.UserRepository;
import com.metene.service.IDomainService;
import com.metene.service.JWTService;
import com.metene.service.dto.CookieBannerRequest;
import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.DomainRequest;
import com.metene.service.dto.DomainResponse;
import com.metene.service.mapper.CookieBannerMapper;
import com.metene.service.mapper.CookieMapper;
import com.metene.service.mapper.DomainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DomainServiceImpl implements IDomainService {

    private static final String ACTIVO = "Activo";
    private final UserRepository userRepository;
    private final DomainRepository domainRepository;
    private final JWTService jwtService;

    @Override
    public void create(DomainRequest request, String token) {
        User user = userRepository.findByUsername(jwtService.getUsernameFromToken(token)).orElseThrow();

        user.addDomain(DomainMapper.toEntity(request));
        userRepository.save(user);
    }

    @Override
    public DomainResponse getDetails(Long id) {
        Domain domain = domainRepository.findById(id).orElseThrow();

        return DomainMapper.toDto(domain);
    }

    @Override
    public List<DomainResponse> getAll(String token) {
        User user = userRepository.findByUsername(jwtService.getUsernameFromToken(token)).orElseThrow();

        return domainRepository.findByUserName(user.getUsername()).stream().map(DomainMapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        Domain domain = domainRepository.findById(id).orElseThrow();

        User user = domain.getUser();
        user.removeDomain(domain);
        userRepository.save(user);
    }

    @Override
    public void update(Long id, DomainRequest data) {
        Domain currentDomain = domainRepository.findById(id).orElseThrow();

        Domain domainToUpdate = DomainMapper.toEntity(data);
        domainToUpdate.setFechaCreacion(currentDomain.getFechaCreacion());
        domainToUpdate.setId(currentDomain.getId());
        domainToUpdate.setUser(currentDomain.getUser());

        domainRepository.save(domainToUpdate);
    }

    @Override
    public void addCookies(Long id, List<CookieRequest> cookies) {
        Domain domain = domainRepository.findById(id).orElseThrow();

        // Solo se pueden añadir las cookies si el dominio está activo
        if (!domain.getEstado().equals(ACTIVO)) return;

        List<Cookie> cookiesToSave =  cookies.stream().map(CookieMapper::toEntity).toList();

        // Actualizamos la fecha de sincronización de las cookies
        domain.setLastCookieScan(LocalDateTime.now());
        domain.addAllCookies(cookiesToSave);
        domainRepository.save(domain);
    }

    @Override
    public void addBanner(Long id, CookieBannerRequest request) {
        Domain domain = domainRepository.findById(id).orElseThrow();

        domain.setBanner(CookieBannerMapper.toEntity(request));
        domainRepository.save(domain);
    }

    @Override
    public void addCookie(Long id, CookieRequest request) {
        Domain domain = domainRepository.findById(id).orElseThrow();

        // Actualizamos la fecha de la última sincronización de cookies
        domain.setLastCookieScan(LocalDateTime.now());
        domain.addCookie(CookieMapper.toEntity(request));
        domainRepository.save(domain);
    }

    @Override
    public void updateBanner(CookieBanner banner, CookieBannerRequest bannerRequest) {
        CookieBanner bannerToUpdate = CookieBannerMapper.toEntity(bannerRequest);

        Domain domain = banner.getDomain();
        domain.setBanner(bannerToUpdate);

        domainRepository.save(domain);

    }

    @Override
    public void deleteBanner(CookieBanner banner) {
        Domain domain = banner.getDomain();
        domain.setBanner(null);
        
        domainRepository.save(domain);
    }
}
