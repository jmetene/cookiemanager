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
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Domain implements Serializable {
    /// Identificado único del dominio
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /// Nombre del dominio
    private String name;
    /// Almacena la fecha en la que se realizó el último escaneo de cookies (Carga de las cookies)
    private LocalDateTime lastCookieScan;
    @JsonIgnore
    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    /// Cookies asociadas al dominio
    private List<Cookie> cookies = new ArrayList<>();
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "banner_id", referencedColumnName = "id")
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
        cookies.addAll(cookies);
        cookies.forEach(cookie -> cookie.setDomain(this));
    }

    public void removeCookie(Cookie cookie) {
        cookies.remove(cookie);
    }
}
