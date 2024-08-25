package com.metene.domain;

import com.metene.auth.JWTService;
import com.metene.cookie.Cookie;
import com.metene.cookiebanner.CookieBanner;
import com.metene.domain.dto.DomainMapper;
import com.metene.domain.dto.DomainRequest;
import com.metene.domain.dto.DomainResponse;
import com.metene.user.User;
import com.metene.cookie.CookieRepository;
import com.metene.user.UserRepository;
import com.metene.cookiebanner.dto.CookieBannerRequest;
import com.metene.cookie.dto.CookieRequest;
import com.metene.cookiebanner.dto.CookieBannerMapper;
import com.metene.cookie.dto.CookieMapper;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DomainService {

    private static final String ACTIVO = "Activo";

    private final UserRepository userRepository;
    private final DomainRepository domainRepository;
    private final JWTService jwtService;
    private final CookieRepository cookieRepository;

    public void create(DomainRequest request, String token) {
        User user = userRepository.findByUsername(jwtService.getUserFromToken(token)).orElseThrow();

        user.addDomain(DomainMapper.toEntity(request));
        userRepository.save(user);
    }

    public DomainResponse getDetails(Long id) {
        Domain domain = domainRepository.findById(id).orElseThrow();

        return DomainMapper.toDto(domain);
    }

    public List<DomainResponse> getAll(String token) {
        User user = userRepository.findByUsername(jwtService.getUserFromToken(token)).orElseThrow();

        return domainRepository.findByUserName(user.getUsername()).stream().map(DomainMapper::toDto).toList();
    }

    public void delete(Long id) {
        Domain domain = domainRepository.findById(id).orElseThrow();

        User user = domain.getUser();
        user.removeDomain(domain);
        userRepository.save(user);
    }

    public void update(Long id, DomainRequest data) {
        Domain currentDomain = domainRepository.findById(id).orElseThrow();

        Domain domainToUpdate = DomainMapper.toEntity(data);
        domainToUpdate.setFechaCreacion(currentDomain.getFechaCreacion());
        domainToUpdate.setId(currentDomain.getId());
        domainToUpdate.setUser(currentDomain.getUser());

        domainRepository.save(domainToUpdate);
    }

    public void addCookies(Long id, List<CookieRequest> cookies) {
        Domain domain = domainRepository.findById(id).orElseThrow();

        // Solo se pueden añadir las cookies si el dominio está activo
        if (!domain.getEstado().equals(ACTIVO)) return;

        List<Cookie> cookiesToSave = cookies.stream().map(CookieMapper::toEntity).toList();

        // Actualizamos la fecha de sincronización de las cookies
        domain.setLastCookieScan(LocalDateTime.now());
        domain.addAllCookies(cookiesToSave);
        domainRepository.save(domain);
    }

    public void addBanner(Long id, CookieBannerRequest request) {
        Domain domain = domainRepository.findById(id).orElseThrow();

        domain.setBanner(CookieBannerMapper.toEntity(request));
        domainRepository.save(domain);
    }

    public void addCookie(Long id, CookieRequest request) {
        Domain domain = domainRepository.findById(id).orElseThrow();

        Optional<Cookie> cookie = cookieRepository.findByNameAndProvider(request.getName(), request.getProvider());

        if (cookie.isPresent()) throw new EntityExistsException();

        // Actualizamos la fecha de la última sincronización de cookies
        domain.setLastCookieScan(LocalDateTime.now());
        domain.addCookie(CookieMapper.toEntity(request));

        domainRepository.save(domain);
    }

    public void deleteBanner(CookieBanner banner) {
        Domain domain = banner.getDomain();
        domain.setBanner(null);

        domainRepository.save(domain);
    }
}
