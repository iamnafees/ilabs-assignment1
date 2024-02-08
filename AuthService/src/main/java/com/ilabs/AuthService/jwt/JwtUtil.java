package com.ilabs.AuthService.jwt;

import com.ilabs.AuthService.entity.User;
import com.ilabs.AuthService.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {


    //private static final long EXPIRE_DURATION = 15 * 60 * 1000; // 15 minutes
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour


    private String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public String extractUsername(String token) {

        String[] parts = extractClaim(token, Claims::getSubject).split(",");
        return parts[1];
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
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

    public String generateToken(User user) {
        Map<User, Object> claims = new HashMap<>();
        return createToken(claims, user);
    }

    private String createToken(Map<User, Object> claims, User user) {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getEmailId()))
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(Keys.hmacShaKeyFor(keyBytes))
                .compact();
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}