package com.synpulse8.samyeung812.backendchallenge.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synpulse8.samyeung812.backendchallenge.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ConsumerFactory<String, String> consumerFactory;

    public void produce(String topic, Transaction transaction) throws Exception {
        String transactionJson = new ObjectMapper().writeValueAsString(transaction);
        kafkaTemplate.send(topic, 0, transaction.getTransactionID(), transactionJson);
    }

    public List<Transaction> consume(String topic) throws Exception {
        Consumer<String, String> consumer = consumerFactory.createConsumer();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Transaction> result = new ArrayList<>();

        TopicPartition topicPartition = new TopicPartition(topic, 0);
        consumer.assign(List.of(topicPartition));
        consumer.seek(topicPartition, 0);

        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
            if(records.isEmpty()) break;

            for (ConsumerRecord<String, String> record : records) {
                Transaction transaction = objectMapper.readValue(record.value(), Transaction.class);
                result.add(transaction);
            }
        }

        consumer.unsubscribe();
        return result;
    }
}
