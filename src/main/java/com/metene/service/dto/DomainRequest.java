package com.metene.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DomainRequest {
    private String nombre;
    private String descripcion;
    private String estado;
    private String propietario;
    private String contactoEmail;
    private String paisOrigen;
}
