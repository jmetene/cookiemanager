package com.metene.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statistic {
    private String estado;
    private String fecha;
    private String plataforma;
    private String pais;
    private Long domainId;
    private Long cookieId;
}
