package com.metene.service.impl;

import com.metene.domain.entity.Cookie;
import com.metene.domain.entity.Domain;
import com.metene.domain.entity.User;
import com.metene.domain.repository.DomainRepository;
import com.metene.domain.repository.UserRepository;
import com.metene.service.IDomainService;
import com.metene.service.JWTService;
import com.metene.service.dto.CookieBannerRequest;
import com.metene.service.dto.CookieRequest;
import com.metene.service.dto.DomainResponse;
import com.metene.service.mapper.CookieBannerMapper;
import com.metene.service.mapper.CookieMapper;
import com.metene.service.mapper.DomainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DomainServiceImpl implements IDomainService {

    private final UserRepository userRepository;
    private final DomainRepository domainRepository;
    private final JWTService jwtService;

    @Override
    public void create(String name, String token) {
        User user = userRepository.findByUsername(jwtService.getUsernameFromToken(token)).orElseThrow();

        Domain domain = new Domain();
        domain.setName(name);
        domain.setUser(user);

        domainRepository.save(domain);
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
        if (!domainRepository.existsById(id)) throw new NoSuchElementException();
        domainRepository.deleteById(id);
    }

    @Override
    public void update(Long id, String name) {
        Domain domainToUpdate = domainRepository.findById(id).orElseThrow();
        domainToUpdate.setName(name);
        domainToUpdate.setId(id);

        domainRepository.save(domainToUpdate);
    }

    @Override
    public void addCookies(Long id, List<CookieRequest> cookies) {
        Domain domain = domainRepository.findById(id).orElseThrow();

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
        Domain domain = domainRepository.getReferenceById(id);

        // Actualizamos la fecha de la última sincronización de cookies
        domain.setLastCookieScan(LocalDateTime.now());
        domain.addCookie(CookieMapper.toEntity(request));
        domainRepository.save(domain);
    }
}
