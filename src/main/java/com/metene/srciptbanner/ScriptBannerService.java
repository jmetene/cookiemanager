package com.metene.srciptbanner;

import com.metene.cookie.Cookie;
import com.metene.cookie.CookieRepository;
import com.metene.cookie.dto.CookieMapper;
import com.metene.cookie.dto.CookieResponse;
import com.metene.domain.Domain;
import com.metene.domain.DomainRepository;
import com.metene.statistics.CookieStatistics;
import com.metene.statistics.CookieStatisticsRepository;
import com.metene.statistics.dto.Statistic;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ScriptBannerService {
    private final ScriptBannerRepository scriptBannerRepository;
    private final CookieStatisticsRepository cookieStatisticsRepository;
    private final DomainRepository domainRepository;
    private final CookieRepository cookieRepository;


    public ScriptBannerResponse getScriptBanner(String apiKey) {
        ScriptBanner scriptBanner = scriptBannerRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new EntityNotFoundException("API key no válido"));

        // Recuperamos primero el dominio
        Domain domain = domainRepository.findByNombre(scriptBanner.getDominio())
                .orElseThrow(() -> new EntityNotFoundException("Dominio no encontrado"));

        String bannerInfo = domain.getBanner().getCookieDeclaration();

        // Recuperamos las cookies asociadas a este dominio
        List<CookieResponse> cookies = domain.getCookies().stream().map(CookieMapper::toDto).toList();

        return ScriptBannerMapper.toDTO(scriptBanner, cookies, bannerInfo);
    }

    private boolean isValidAPIKey(String apiKey) {
        return scriptBannerRepository.findByApiKey(apiKey).isPresent();
    }

    public String createStatistics(ScriptBannerRequest scriptBannerRequest) {
        AtomicReference<String> saved = new AtomicReference<>("KO");

        if (!isValidAPIKey(scriptBannerRequest.getApiKey()))
            throw new IllegalArgumentException("Invalid API key");

        Domain domain = domainRepository.findByNombre(scriptBannerRequest.getDominio())
                .orElseThrow(() -> new EntityNotFoundException("Domain not found"));

        if (scriptBannerRequest.getCookies().isEmpty())
            throw new IllegalArgumentException("Cookie list can't be null or empty");

        List<Statistic> statistics = new ArrayList<>();

        scriptBannerRequest.getCookies().forEach(cookie -> {
            Statistic statistic = Statistic.builder().cookieId(cookie.getId()).domainId(domain.getId()).build();
            statistics.add(statistic);
        });


        // Agregamos la información común
        List<Statistic> statisticsWithFullInfo = statistics.stream()
                .map(statistic -> {
                    if (statistic.getCookieId() == null && statistic.getDomainId() == null)
                        throw new IllegalArgumentException("Cookie id and domain id cannot be null");

                    return Statistic.builder()
                            .cookieId(statistic.getCookieId())
                            .domainId(statistic.getDomainId())
                            .pais(scriptBannerRequest.getPais())
                            .estado(scriptBannerRequest.getEstado())
                            .plataforma(scriptBannerRequest.getEstado())
                            .fecha(scriptBannerRequest.getFechaActual())
                            .build();
        }).toList();

        statisticsWithFullInfo.forEach(statistic -> {
            CookieStatistics cookieStatistics = CookieStatistics.builder()
                    .cookie(cookieRepository
                            .findById(statistic.getCookieId())
                            .orElseThrow(() -> new EntityNotFoundException("Cookie not found")))
                    .domain(domain)
                    .estado(statistic.getEstado())
                    .fecha(LocalDate.parse(statistic.getFecha()))
                    .pais(statistic.getPais())
                    .plataforma(statistic.getPlataforma())
                    .build();
            cookieStatisticsRepository.save(cookieStatistics);
            saved.set("OK");
        });

        return saved.get();
    }
}
