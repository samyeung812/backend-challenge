package com.synpulse8.samyeung812.backendchallenge.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    @Value("${jwt_secret:5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437}")
    private String SECRET_KEY;

    public String extractUserID(String token) {
        return extractClaim(token, "userID", String.class);
    }

    public <T> T extractClaim(String token, String claim, Class<T> returnType) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claim, returnType);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(Map<String,Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateTokenWithUserID(String userID) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("userID", userID);
        return generateToken(claims);
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
