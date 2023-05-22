package com.synpulse8.samyeung812.backendchallenge.models.dto;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
public class JWT {
    private final String token;
}
