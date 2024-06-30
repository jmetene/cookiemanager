package com.metene.service.impl;

import com.metene.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {
    private static final Long SECOND_IN_A_DAY = 1440000L;
    private static final String SECRET_KEY = "2862bed2ab0d2725282eda76732f32183ee007c2a44be4f1c3ac39e70af6a8b598645a6152bbd8627a544fb7dbe2cd8e0ccc98bb7cec52c9f9f979fbb8d4be247d66a9518ff24690b668597d2ab3a716ec52809816ac86d360bf2379ac595018e51c7570d0be6fdc9962735e342ed7fdf99c1f0e5bd1921553bd6801a8291f026ea2a3baa906df78ed2e4eccb0f61200f71817ae1c14b4df5ac69725e0d04be0179ad8bc2ad80f11fd70c61e435f3064bdb3511708a861b9e1b5aa00cbcc280d6d535abde0961059ed4c75eafbb3fad38549bcf0972aacec81736952f4fa0ed75cb67508e6ea4a1de7967effca5b18886907b25cd10edea8feab930b5333441e";
    @Override
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String getToken(HashMap<String, Object> extractClaims, UserDetails user) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SECOND_IN_A_DAY))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

}
