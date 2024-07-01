package com.metene.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CookieBanner implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 1000)
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
    @Column(length = 1000)
    private String style;
    private String lang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
