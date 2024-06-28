package com.metene.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String company;
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cookie> cookies = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CookieBanner> cookieBanners = new ArrayList<>();

    public void remove(Object object) {
        if (object instanceof Cookie) removeCookie((Cookie) object);
        if (object instanceof CookieBanner) removeCookieBanner((CookieBanner) object);
    }

    public void add(Object object) {
        if (object instanceof Cookie) addCookie((Cookie) object);
        if (object instanceof CookieBanner) addCookieBanner((CookieBanner) object);
    }

    private void addCookieBanner(CookieBanner banner) {
        cookieBanners.add(banner);
        banner.setUser(this);
    }

    private void addCookie(Cookie cookie) {
        cookies.add(cookie);
        cookie.setUser(this);
    }

    private void removeCookie(Cookie cookie) {
        cookies.remove(cookie);
    }

    private void removeCookieBanner(CookieBanner banner) {
        cookieBanners.remove(banner);
    }
}
