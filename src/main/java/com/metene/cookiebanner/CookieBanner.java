package com.metene.cookiebanner;

import com.metene.domain.Domain;
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
@Table(name="BANNER")
public class CookieBanner implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 1000)
    private String cookieDeclaration;
    // Campo opcional
    private String companyLogo;
    // GDPR
    private String legislation;
    private String legislationDesc;
    private String cookiePolicy;
    private String privacyPolicy;
    @Enumerated(EnumType.STRING)
    private ConsentType consentType;
    private String lang;

    @OneToOne(mappedBy = "banner", fetch = FetchType.LAZY)
    private Domain domain;
}
