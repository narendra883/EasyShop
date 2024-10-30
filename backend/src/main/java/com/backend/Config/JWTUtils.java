//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.backend.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils {
    private SecretKey Key;
    private static final long EXPIRATION_TIME = 86400000L;

    public JWTUtils() {
        String secretString = "thisisaverysecuresecretkey123456";
        this.Key = new SecretKeySpec(secretString.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails, String email) {
        return ((JwtBuilder) ((JwtBuilder) ((JwtBuilder) Jwts.builder().setSubject(email)).claim("email", email).setIssuedAt(new Date(System.currentTimeMillis()))).setExpiration(new Date(System.currentTimeMillis() + 86400000L))).signWith(this.Key).compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return ((JwtBuilder) ((JwtBuilder) ((JwtBuilder) Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())).setIssuedAt(new Date(System.currentTimeMillis()))).setExpiration(new Date(System.currentTimeMillis() + 86400000L))).signWith(this.Key).compact();
    }

    public String extractEmail(String token) {
        return (String) this.extractClaims(token, (claims) -> {
            return (String) claims.get("email", String.class);
        });
    }

    public String extractUsername(String token) {
        return (String) this.extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply((Claims) Jwts.parser().setSigningKey(this.Key).build().parseClaimsJws(token).getBody());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = this.extractUsername(token);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return ((Date) this.extractClaims(token, Claims::getExpiration)).before(new Date());
    }
}
