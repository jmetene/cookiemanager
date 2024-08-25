package com.metene.domain.dto;

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
    private String nombre;
    private String descripcion;
    private String estado;
    private String propietario;
    private String contactoEmail;
    private String paisOrigen;
    private int totalCookies;
    private String lastCookieScan;
    private String fechaCreacion;
    private String fechaModificacion;
}
