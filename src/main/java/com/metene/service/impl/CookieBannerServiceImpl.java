package com.metene.service.impl;

import com.metene.domain.entity.CookieBanner;
import com.metene.domain.repository.CookieBannerRepository;
import com.metene.service.ICookieBannerService;
import com.metene.service.IDomainService;
import com.metene.service.dto.CookieBannerRequest;
import com.metene.service.dto.CookieBannerResponse;
import com.metene.service.mapper.CookieBannerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CookieBannerServiceImpl implements ICookieBannerService {

    private final CookieBannerRepository repository;
    private final IDomainService domainService;

    @Override
    public CookieBannerResponse getCookieBanner(Long id) {
         CookieBanner banner = repository.findById(id).orElseThrow(NoSuchElementException::new);
        return CookieBannerMapper.toDto(banner);
    }

    @Override
    public void deleteCookieBanner(Long id) {
        CookieBanner banner = repository.findById(id).orElseThrow();
        domainService.deleteBanner(banner);
    }

    @Override
    public void updateCookieBanner(Long id, CookieBannerRequest bannerRequest) {
        CookieBanner bannerToUpdate = repository.findById(id).orElseThrow();

        domainService.updateBanner(bannerToUpdate, bannerRequest);
    }
}
