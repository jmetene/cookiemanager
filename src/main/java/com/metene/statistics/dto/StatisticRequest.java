package com.metene.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticRequest {
    private String estado;
    private String fechaDesde;
    private String fechaHasta;
    private String plataforma;
    private String pais;
}
