package com.leonpahole.workoutapp.security;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtProvider implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-time}")
    private Long jwtExpirationInMillis;

    public String generateToken(String email) {
        return Jwts.builder().setSubject(email).setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                // todo: expire jwts
                .signWith(signatureAlgorithm, secret).compact();
    }

    private Claims decodeToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(token).getBody();
    }

    public String getEmailFromToken(String token) {
        return decodeToken(token).getSubject();
    }

    private Instant getExpirationDateFromToken(String token) {
        return decodeToken(token).getExpiration().toInstant();
    }

    private Boolean isTokenExpired(String token) {
        final Instant expiration = getExpirationDateFromToken(token);
        return expiration.isBefore(Instant.now());
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}