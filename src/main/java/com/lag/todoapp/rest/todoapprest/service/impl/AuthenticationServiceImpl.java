package com.lag.todoapp.rest.todoapprest.service.impl;

import com.lag.todoapp.rest.todoapprest.dto.entrada.LoginEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.entrada.RegisterEntradaDto;
import com.lag.todoapp.rest.todoapprest.dto.LoginDto;
import com.lag.todoapp.rest.todoapprest.dto.RegisterDto;
import com.lag.todoapp.rest.todoapprest.entity.RoleEntity;
import com.lag.todoapp.rest.todoapprest.entity.UserEntity;
import com.lag.todoapp.rest.todoapprest.enums.RoleEnum;
import com.lag.todoapp.rest.todoapprest.exception.AccessNotGrantedException;
import com.lag.todoapp.rest.todoapprest.exception.RoleNotFoundException;
import com.lag.todoapp.rest.todoapprest.mapper.UserMapper;
import com.lag.todoapp.rest.todoapprest.repository.RoleRepository;
import com.lag.todoapp.rest.todoapprest.repository.UserRepository;
import com.lag.todoapp.rest.todoapprest.service.AuthenticationService;
import com.lag.todoapp.rest.todoapprest.service.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public AuthenticationServiceImpl(
            AuthenticationManager authenticationManager,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            UserRepository userRepository,
            UserMapper userMapper
    ) {
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public LoginDto authenticate(LoginEntradaDto userLogin) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLogin.getEmail(),
                userLogin.getPassword()
        ));

        UserEntity userEntity = userRepository.findUserByEmail(userLogin.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not fond"));

        UserDetails userDetails = buildUserDetails(userEntity);
        String jwtToken = jwtService.generateToken(userDetails);

        return LoginDto.builder()
                .jwt(jwtToken)
                .build();
    }

    @Override
    @Transactional
    public RegisterDto registerUser(RegisterEntradaDto userRegister) throws Exception {
        UserEntity user = buildUserToRegister(userRegister);
        UserEntity createdUser = userRepository.save(user);

        return userMapper.toRegisterSalidaDto(createdUser);
    }

    private UserEntity buildUserToRegister(RegisterEntradaDto userRegister) throws Exception {
        RoleEntity role = roleRepository.findById(userRegister.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role Not Found"));

        if (role.getRole() == RoleEnum.ADMIN) {
            throw new AccessNotGrantedException("You attempted to assign an unauthorized role");
        }

        return UserEntity.builder()
                .userName(userRegister.getUsername())
                .email(userRegister.getEmail())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(Set.of(role))
                .build();
    }

    private UserDetails buildUserDetails(UserEntity userEntity) {
        List<SimpleGrantedAuthority> authorities = buildAuthorities(userEntity);

        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorities
        );
    }

    private List<SimpleGrantedAuthority> buildAuthorities(UserEntity userEntity) {
        return userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .toList();
    }
}
