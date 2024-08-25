package com.metene.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.metene.domain.Domain;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})})
public class User implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String company;
    /// Starter, Basic, Business, Enterprise
    private String suscriptionPlan;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Domain> domains = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    public void addDomain(Domain domain) {
        domains.add(domain);
        domain.setUser(this);
    }

    public void removeDomain(Domain domain) {
        domains.remove(domain);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
