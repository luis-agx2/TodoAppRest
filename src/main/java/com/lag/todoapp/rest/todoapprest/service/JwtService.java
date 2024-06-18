package com.lag.todoapp.rest.todoapprest.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    public String generateToken(UserDetails userDetails);

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    public  String getUserName(String token);

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver);

    public boolean validateToken(String token, UserDetails userDetails);
}
