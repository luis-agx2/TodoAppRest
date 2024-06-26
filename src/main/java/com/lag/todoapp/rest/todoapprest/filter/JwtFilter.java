package com.lag.todoapp.rest.todoapprest.filter;

import com.lag.todoapp.rest.todoapprest.dto.MyUserDetails;
import com.lag.todoapp.rest.todoapprest.entity.UserEntity;
import com.lag.todoapp.rest.todoapprest.repository.UserRepository;
import com.lag.todoapp.rest.todoapprest.service.JwtService;
import com.lag.todoapp.rest.todoapprest.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @Autowired
    public JwtFilter(MyUserDetailsService userDetailsService, UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.getUserName(jwt);

        if (userEmail == null && SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = getUserDetailsByEmail(userEmail);

        if (jwtService.validateToken(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    public MyUserDetails getUserDetailsByEmail(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority(role.getRole().name())));

        return new MyUserDetails(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList
        );
    }
}
