package com.metene.srciptbanner;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "SCRIPT_BANNER")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScriptBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String codigoCss;
    private String codigoJs;
    private String dominio;
    private String apiKey;
}
