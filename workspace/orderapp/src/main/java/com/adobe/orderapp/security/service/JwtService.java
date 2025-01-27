package com.adobe.orderapp.security.service;


import com.adobe.orderapp.security.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.security.KeyRep.Type.SECRET;

/**
    To generate and validate token
 */

@Service
public class JwtService {
    // from application.properties
    @Value("${jwt.secret}")
    private String jwtSigningKey;

    // on successful login generate a Token and send to client
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Collection<String> authorities = userDetails
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        User user = (User) userDetails;

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *24))
                .claim("authorities", authorities)
                .claim("roles", authorities)
                .claim("iss", "https://autserver.adobe.com")
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSigningKey)
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("Claims " + claims );
        } catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }
        return claims;
    }

    private <T>  T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUserName(String token) {
        String sub =  extractClaim(token, Claims::getSubject);
        System.out.println("Subject " + sub);
        return sub;
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // next time when client passes token, need to validate it
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String userName = extractUserName(token);
        System.out.println("Extracted userNAme " + userName);
        User user = (User) userDetails;
        return (userName.equals(user.getEmail()) && !isTokenExpired(token));
    }
}
