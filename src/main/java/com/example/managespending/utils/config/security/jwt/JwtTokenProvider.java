package com.example.managespending.utils.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider implements Serializable {

    private final String JWT_SECRET = "secret";

    public String generateToken(UserDetails userDetails) {

        Date now = new Date();
        long JWT_EXPIRATION = 604800000L;
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

    }

    public String getUsernameFromToken(String token){

        final Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        return claims.getSubject();

    }

    public Boolean validateToken(String token, UserDetails userDetails){

        String username = getUsernameFromToken(token);
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
        boolean isTokenExpired = claims.getExpiration().before(new Date());

        return (username.equals(userDetails.getUsername()) && !isTokenExpired);

    }

}
