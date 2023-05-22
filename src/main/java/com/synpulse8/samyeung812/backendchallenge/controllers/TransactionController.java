package com.synpulse8.samyeung812.backendchallenge.controllers;

import com.synpulse8.samyeung812.backendchallenge.models.Transaction;
import com.synpulse8.samyeung812.backendchallenge.models.User;
import com.synpulse8.samyeung812.backendchallenge.services.JWTService;
import com.synpulse8.samyeung812.backendchallenge.services.TransactionService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final JWTService jwtService;

    @GetMapping("/get")
    public ResponseEntity<?> get(
            @RequestParam @NotBlank String date,
            @RequestParam @NotBlank String currency,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String userID = jwtService.extractUserID(authHeader.substring(7));
            return ResponseEntity.ok(transactionService.get(date, currency, userID));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/publish")
    public ResponseEntity<?> publish(@RequestBody Transaction transaction, @RequestHeader("Authorization") String authHeader) {
        try {
            String userID = jwtService.extractUserID(authHeader.substring(7));
            return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.publish(transaction, userID));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
