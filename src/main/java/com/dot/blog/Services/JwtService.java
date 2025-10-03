package com.dot.blog.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String SECRET_KEY = "ThisIsMySuperSecretKeyForJWTAuth123456";

    public String genrateToken(long id, String email) {

        Map<String,Object> claims=new HashMap<>();
        claims.put("id",id);

        return Jwts.
                builder().
                setClaims(claims).
                setSubject(email).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + 1000*60*60)).
                signWith(getKey(), SignatureAlgorithm.HS256).
                compact();
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Validate Token
    public  boolean validateToken(String token, UserDetails userDetails) {
        return (userDetails.getUsername().equals(extractEmail(token)) && !isTokenExpired(token));
    }

    // Extract Username
    public  String extractUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    // Extract bio
    public String extractBio(String token){
        return extractAllClaims(token).get("bio" , String.class);
    }

    // Extract createdAt
    public LocalDateTime createdAt(String token){
        return extractAllClaims(token).get("createdAt" , LocalDateTime.class);
    }

    //Extract Email
    public String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    // Extract Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    // Expired Check
    private  boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }


}
