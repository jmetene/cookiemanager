package com.metene.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CookieBanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String companyLogo;
    @Enumerated(EnumType.STRING)
    private Legislation legislation;
    private String legislationDescription;
    @Enumerated(EnumType.STRING)
    private ComplienceMethod method;
    @Enumerated(EnumType.STRING)
    private ComplienceType type;
    @Enumerated(EnumType.STRING)
    private Visitor territoryForVisitor;
    private String style;
    private String lang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
