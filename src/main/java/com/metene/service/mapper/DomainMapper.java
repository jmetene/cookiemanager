package com.metene.service.mapper;

import com.metene.domain.entity.Domain;
import com.metene.service.dto.DomainResponse;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DomainMapper {
    private static final int NO_COOKIES = 0;
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private DomainMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static DomainResponse toDto(Domain domain) {
        return DomainResponse
                .builder()
                .id(domain.getId())
                .name(domain.getName())
                .totalCookies(domain.getCookies().isEmpty() ? NO_COOKIES : domain.getCookies().size())
                .lastCookieScan(domain.getLastCookieScan().format(getFormatter()))
                .build();
    }

    private static DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(PATTERN, new Locale("es"));
    }
}
