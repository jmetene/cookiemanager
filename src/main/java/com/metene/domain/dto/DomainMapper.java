package com.metene.domain.dto;

import com.metene.domain.Domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DomainMapper {
    private static final int ZERO_COOKIES = 0;
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private DomainMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static DomainResponse toDto(Domain domain) {
        return DomainResponse
                .builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .descripcion(domain.getDescripcion())
                .estado(domain.getEstado())
                .propietario(domain.getPropietario())
                .contactoEmail(domain.getContactoEmail())
                .paisOrigen(domain.getPaisOrigen())
                .totalCookies(domain.getCookies() == null || domain.getCookies().isEmpty() ? ZERO_COOKIES :
                        domain.getCookies().size())
                .lastCookieScan(domain.getLastCookieScan() != null ? domain.getLastCookieScan().format(getFormatter()) : null)
                .fechaCreacion(domain.getFechaCreacion().format(getFormatter()))
                .fechaModificacion(domain.getFechaModificacion().format(getFormatter()))
                .build();
    }

    public static Domain toEntity(DomainRequest request) {
        return Domain
                .builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .estado(request.getEstado())
                .propietario(request.getPropietario())
                .contactoEmail(request.getContactoEmail())
                .paisOrigen(request.getPaisOrigen())
                .fechaCreacion(LocalDateTime.now())
                .fechaModificacion(LocalDateTime.now())
                .build();
    }

    private static DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(PATTERN, new Locale("es"));
    }
}
