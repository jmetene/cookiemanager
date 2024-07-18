package com.metene.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DomainResponse {
    private Long id;
    private String name;
    private int totalCookies;
    private String lastCookieScan;
}
