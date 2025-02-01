package com.metene.domain;

import com.metene.auth.JWTService;
import com.metene.cookie.Cookie;
import com.metene.cookie.dto.CookieResponse;
import com.metene.cookiebanner.CookieBanner;
import com.metene.cookiebanner.dto.CookieBannerResponse;
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
import jakarta.persistence.EntityNotFoundException;
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

    public DomainResponse create(DomainRequest request, String token) {

        User user = userRepository.findByEmail(jwtService.getUserFromToken(token)).orElseThrow();

        Domain domainToSave;
        Domain response;
        try {
            domainToSave = DomainMapper.toEntity(request);
            domainToSave.setUser(user);
            response = domainRepository.saveAndFlush(domainToSave);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return DomainMapper.toDto(response);
    }

    public DomainResponse getDetails(Long id) {
        Domain domain = domainRepository.findById(id).orElseThrow();

        return DomainMapper.toDto(domain);
    }

    public List<DomainResponse> getAll(String token) {
        User user = userRepository.findByEmail(jwtService.getUserFromToken(token)).orElseThrow();

        return domainRepository.findByUserName(user.getUsername()).stream().map(DomainMapper::toDto).toList();
    }

    public void delete(Long id) {
        Domain domain = domainRepository.findById(id).orElseThrow();

        User user = domain.getUser();
        user.removeDomain(domain);
        userRepository.save(user);
    }

    public DomainResponse update(Long id, DomainRequest data) {
        Domain currentDomain = domainRepository.findById(id)
                .orElseThrow(() -> new EntityExistsException("Ya existe un dominio con este identificador"));

        Domain domainToUpdate = DomainMapper.toEntity(data);
        domainToUpdate.setFechaCreacion(currentDomain.getFechaCreacion());
        domainToUpdate.setId(currentDomain.getId());
        domainToUpdate.setUser(currentDomain.getUser());

        return DomainMapper.toDto(domainRepository.saveAndFlush(domainToUpdate));
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

        // TODO: Antes de guardar el banner crear el script del banner
//        ScriptBanner script = generarScript(codigoCss, codigoJs, dominio);

        domain.setBanner(CookieBannerMapper.toEntity(request));
        domainRepository.save(domain);
    }

    public CookieResponse addCookie(Long id, CookieRequest request) {
        Domain domain = domainRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dominio no encontrado"));

        Optional<Cookie> cookie = cookieRepository.findByNameAndProvider(request.getName(), request.getProvider());

        if (cookie.isPresent()) throw new EntityExistsException();

        // Actualizamos la fecha de la última sincronización de cookies
        domain.setLastCookieScan(LocalDateTime.now());
        Cookie cookieToSave = CookieMapper.toEntity(request);

        cookieToSave.setDomain(domain);
        return CookieMapper.toDto(cookieRepository.saveAndFlush(cookieToSave));
    }

    public void deleteBanner(CookieBanner banner) {
        Domain domain = banner.getDomain();
        domain.setBanner(null);

        domainRepository.save(domain);
    }

    /**
     * Devuelve la información del banner a partir del identificador del dominio
     * @param id identificador del dominio
     * @return {@return  CookieBannerResponse}
     */
    public CookieBannerResponse getBanner(Long id) {
        Domain existingDomain = domainRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No existe un dominio con este ID"));

        CookieBanner banner = existingDomain.getBanner();

        return banner != null ? CookieBannerMapper.toDto(banner) : null;
    }
}
