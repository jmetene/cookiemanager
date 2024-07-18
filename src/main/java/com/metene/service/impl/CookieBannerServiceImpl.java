package com.metene.service.impl;

import com.metene.domain.entity.CookieBanner;
import com.metene.domain.repository.CookieBannerRepository;
import com.metene.service.ICookieBannerService;
import com.metene.service.dto.CookieBannerRequest;
import com.metene.service.dto.CookieBannerResponse;
import com.metene.service.mapper.CookieBannerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CookieBannerServiceImpl implements ICookieBannerService {

    private final CookieBannerRepository repository;

    @Override
    public CookieBannerResponse getCookieBanner(Long id) {
         CookieBanner banner = repository.getReferenceById(id);
         if (Objects.isNull(banner)) throw new NoSuchElementException();

        return CookieBannerMapper.toDto(banner);
    }

    @Override
    public void deleteCookieBanner(Long id) {
        CookieBanner banner = repository.getReferenceById(id);
        if (Objects.isNull(banner)) throw new NoSuchElementException();

        repository.delete(banner);
    }

    @Override
    public void updateCookieBanner(Long domain, CookieBannerRequest bannerRequest) {
        CookieBanner banner = repository.getByDomain(domain).orElseThrow();

        CookieBanner toUpdate = CookieBannerMapper.toEntity(bannerRequest);
        toUpdate.setId(banner.getId());

        repository.save(banner);
    }
}
