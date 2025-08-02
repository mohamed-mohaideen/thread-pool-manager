package com.msoft.threadmanager.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.msoft.threadmanager.util.ThreadPoolManager;

//@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "my-topic", groupId = "pool-group")
    public void listen(String message) {
        var pool = ThreadPoolManager.createPool("kafka-pool", 3, 6, 100);
        pool.execute(() -> {
            System.out.println("Kafka message: " + message);
            // You can add RetryUtil.executeWithRetry(...) here
        });
    }
}
