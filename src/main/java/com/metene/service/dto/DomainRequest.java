package com.metene.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DomainRequest {
    private Long id;
    private String name;
}
