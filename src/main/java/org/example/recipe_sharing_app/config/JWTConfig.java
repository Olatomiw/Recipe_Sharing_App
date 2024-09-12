package org.example.recipe_sharing_app.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTConfig {
    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.secret.expiry}")
    private int expiry;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date createdDate = new Date();
        Date expiryDate = new Date(createdDate.getTime() + expiry);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expiryDate)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public String extractUsername(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Date extractExpiration(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = userDetails.getUsername();
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    public Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
