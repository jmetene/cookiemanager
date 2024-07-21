package com.metene.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Domain implements Serializable {
    /// Identificador único del dominio
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , unique = true )
    private String nombre;
    private String descripcion;
    /// ENUM: Activo, Inactivo
    private String estado;
    private String propietario;
    private String contactoEmail;
    private String paisOrigen;
    /// Sincronización de cookies.
    private LocalDateTime lastCookieScan;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @JsonIgnore
    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    /// Cookies asociadas al dominio
    private List<Cookie> cookies = new ArrayList<>();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "banner_id")
    /// Banner asociado al dominio
    private CookieBanner banner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
        cookie.setDomain(this);
    }

    public void addAllCookies(List<Cookie> cookies) {
        this.cookies.addAll(cookies);
        cookies.forEach(cookie -> cookie.setDomain(this));
    }

    public void removeCookie(Cookie cookie) {
        cookies.remove(cookie);
    }
}
