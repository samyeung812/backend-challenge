package com.synpulse8.samyeung812.backendchallenge.models.dto;

import com.synpulse8.samyeung812.backendchallenge.models.Transaction;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private List<Transaction> transactions;
    private Double balance;
}
