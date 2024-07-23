package com.metene.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticResponse {
    private String nombreCookie;
    private String tipoCookie;
    private String estado;
    private String fecha;
    private String plataforma;
    private String pais;
}
