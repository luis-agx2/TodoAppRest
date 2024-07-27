package com.lag.todoapp.rest.todoapprest.service.impl;

import com.lag.todoapp.rest.todoapprest.entity.UserDetailEntity;
import com.lag.todoapp.rest.todoapprest.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    @Override
    public String generateToken(UserDetails userDetails, UserDetailEntity userDetailEntity) {
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("authorities", userDetails.getAuthorities());
        extraClaims.put("username", userDetailEntity.getFirstName() + " " + userDetailEntity.getLastNames());

        return generateToken(extraClaims, userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 24)))
                .signWith(SECRET_KEY)
                .compact();
    }

    @Override
    public String getUserName(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = getUserName(token);

        return userName.equals(userDetails.getUsername()) && !isTokenEpired(token);
    }

    private boolean isTokenEpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    private Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
