package com.synpulse8.samyeung812.backendchallenge.services;

import com.synpulse8.samyeung812.backendchallenge.exceptions.UserAlreadyExistException;
import com.synpulse8.samyeung812.backendchallenge.models.dto.JWT;
import com.synpulse8.samyeung812.backendchallenge.models.User;
import com.synpulse8.samyeung812.backendchallenge.models.dto.UserDTO;
import com.synpulse8.samyeung812.backendchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public User register(UserDTO newUser) throws Exception {
        if(userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            throw new UserAlreadyExistException(newUser.getUsername());
        }

        User user = User.builder()
                .username(newUser.getUsername())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .build();

        userRepository.save(user);
        return user;
    }

    public JWT authenticate(UserDTO userInfo) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userInfo.getUsername(),
                        userInfo.getPassword()
                )
        );

        User user = userRepository.findByUsername(userInfo.getUsername())
                .orElseThrow();

        String token = jwtService.generateTokenWithUserID(user.getUserID());
        return JWT.builder().token(token).build();
    }
}
