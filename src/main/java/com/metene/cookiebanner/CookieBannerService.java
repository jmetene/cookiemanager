package com.metene.cookiebanner;

import com.metene.cookiebanner.dto.CookieBannerMapper;
import com.metene.cookiebanner.dto.CookieBannerRequest;
import com.metene.cookiebanner.dto.CookieBannerResponse;
import com.metene.domain.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieBannerService {

    private static final String NOT_FOUN_MESSAGE = "Cookie Banner not found";
    private final CookieBannerRepository repository;
    private final DomainService domainService;

    public CookieBannerResponse getCookieBanner(Long id) {
         CookieBanner banner = repository.findById(id)
                 .orElseThrow(() -> new CookieBannerNotFoundException(NOT_FOUN_MESSAGE));
        return CookieBannerMapper.toDto(banner);
    }

    public void deleteCookieBanner(Long id) {
        CookieBanner banner = repository.findById(id)
                .orElseThrow(() -> new CookieBannerNotFoundException(NOT_FOUN_MESSAGE));
        domainService.deleteBanner(banner);
    }

    public void updateCookieBanner(Long id, CookieBannerRequest bannerRequest) {
        CookieBanner banner = repository.findById(id)
                .orElseThrow(() -> new CookieBannerNotFoundException(NOT_FOUN_MESSAGE));

        CookieBanner bannerToUpdate = CookieBannerMapper.toEntity(bannerRequest);
        bannerToUpdate.setId(banner.getId());
        repository.save(bannerToUpdate);
    }
}
