package com.synpulse8.samyeung812.backendchallenge.services;

import com.synpulse8.samyeung812.backendchallenge.exceptions.InvalidDateException;
import com.synpulse8.samyeung812.backendchallenge.models.Transaction;
import com.synpulse8.samyeung812.backendchallenge.models.dto.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final KafkaService kafkaService;
    private final ExchangeRateService exchangeRateService;

    public Transaction publish(Transaction transaction, String userID) throws Exception {
        String topic = getTopic(userID, transaction.getDate(), "dd-MM-yyyy");
        transaction.setTransactionID(UUID.randomUUID().toString());
        kafkaService.produce(topic, transaction);
        return transaction;
    }

    public TransactionResponse get(String date, String target, String userID) throws Exception {
        String topic = getTopic(userID, date, "MM-yyyy");
        List<Transaction> transactions = kafkaService.consume(topic);

        Map<String, Double> currenciesAmount = new HashMap<>();
        for(Transaction transaction : transactions) {
            String[] currency = transaction.getCurrency().split(" ");
            String symbol = currency[0];
            Double amount = Double.parseDouble(currency[1]);
            currenciesAmount.put(symbol, currenciesAmount.getOrDefault(symbol, 0.0) + amount);
        }

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
        double balance = 0.0;
        for(String symbol : currenciesAmount.keySet()) {
            balance += currenciesAmount.get(symbol) * exchangeRateService.getRate(symbol, target, today);
        }

        return TransactionResponse.builder()
                .transactions(transactions)
                .balance(balance)
                .build();
    }

    public String getTopic(String userID, String date, String dateFormat) throws Exception {
        Calendar calendar = getCalendar(date, dateFormat);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        return String.format("transaction-%s-%d-%d", userID, year, month);
    }

    public Calendar getCalendar(String dateString, String dateFormat) throws Exception {
        Calendar calendar;
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            format.setLenient(false);
            Date date = format.parse(dateString);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        }
        catch (Exception e) {
            throw new InvalidDateException(dateFormat);
        }
        return calendar;
    }
}
