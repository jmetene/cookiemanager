package com.metene.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="COOKIE", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Cookie implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private COOKIEType type;
    private String description;
    private String provider;
    private String duration;
    @Enumerated(EnumType.STRING)
    private SameSiteType sameSite;
    private boolean httpOnly;
    private boolean secure;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "cookie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CookieStatistics> statisticsList = new ArrayList<>();

    public void addStatistics(List<CookieStatistics> statistics) {
        this.statisticsList.addAll(statistics);
        statistics.forEach(cookieStatistics -> cookieStatistics.setCookie(this));
    }

    public void removeStatistics(CookieStatistics statistic) {
        statisticsList.remove(statistic);
    }
}
