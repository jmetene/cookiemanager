package com.metene.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cookie_statistics")
public class CookieStatistics implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String estado;
    private LocalDateTime fecha;
    private String plataforma;
    private String pais;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cookie_id", nullable = false)
    private Cookie cookie;
}
