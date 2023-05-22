package com.synpulse8.samyeung812.backendchallenge.controllers;

import com.synpulse8.samyeung812.backendchallenge.models.dto.UserDTO;
import com.synpulse8.samyeung812.backendchallenge.services.AuthService;
import com.synpulse8.samyeung812.backendchallenge.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(user));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody UserDTO user) {
        try {
            return ResponseEntity.ok(authService.authenticate(user));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
