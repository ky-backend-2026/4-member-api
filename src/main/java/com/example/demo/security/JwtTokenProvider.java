package com.example.demo.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    public String secret;

    @Value("${jwt.access-token-expire}")
    private long accessTokenValidTime;

    @Value("${jwt.refresh-token-expire}")
    private long refreshTokenValidTime;

    private SecretKey key;


//    init

    @jakarta.annotation.PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    //createAccessToken
    public String createAccessToken(Long memberId, String role) {
        Date now = new Date();

        Date expiredAt = new Date(now.getTime() + accessTokenValidTime);

        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //createRefreshToken
    public String createRefreshToken(Long memberId) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + refreshTokenValidTime);


        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    //    getMemberId
    public Long getMemberId(String token) {
        Claims claims = parseClaims(token);

        return Long.valueOf(claims.getSubject());
    }

    //    getRole
    public String getRole(String token) {
        Claims claims = parseClaims(token);
        return claims.get("role", String.class);
    }

    //validateToken
    public boolean validateToken(String token) {

        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }


    //parseClaims
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
