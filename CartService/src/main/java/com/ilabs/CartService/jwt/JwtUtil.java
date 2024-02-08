package com.ilabs.CartService.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {



    //private static final long EXPIRE_DURATION = 15 * 60 * 1000; // 15 minutes
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    public static int userId;

    private String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    public String extractUsername(String token) {
        //return extractClaim(token, Claims::getSubject);
        String[] parts = extractClaim(token, Claims::getSubject).split(",");
        userId= Integer.parseInt(parts[0]);
        return parts[1];
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}