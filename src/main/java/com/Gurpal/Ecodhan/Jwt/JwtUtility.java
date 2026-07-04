package com.Gurpal.Ecodhan.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtility { //it will create tokens

    private final String SECRET = "my-super-secret-key-that-is-long-enough-1234567890!@#";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final long EXPIRATION_TIME = 1000*60*60; //1hour

    public String createToken(String userName)
    {
        String token = Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public Claims extractClaims(String token)
    {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims;
    }


    public String extractUserName(String token)
    {
        Claims claims = extractClaims(token);
        return claims.getSubject(); //subject which is username is extracted from claim which is basically data
    }

    public boolean isTokenExpired(String token)
    {
        return extractClaims(token).getExpiration().before(new Date());
    }


    public boolean validateToken(String userName, UserDetails userDetails, String token)
    {
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }




}

