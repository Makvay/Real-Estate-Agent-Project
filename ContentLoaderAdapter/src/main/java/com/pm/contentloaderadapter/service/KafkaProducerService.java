package com.pm.contentloaderadapter.service;

import com.pm.contentloaderadapter.domain.dto.BuildingRawData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic:building.raw.data}")
    private String topic;

    @Value("${app.kafka.batch-size:50}")
    private int batchSize;

    public int sendBatch(List<BuildingRawData> records) {
        int sent = 0;
        for (int i = 0; i < records.size(); i += batchSize) {
            int end = Math.min(i + batchSize, records.size());

            List<BuildingRawData> batch = records.subList(i, end);

            kafkaTemplate.send(topic, batch);

            sent += batch.size();
        }
        log.info("Sent {} records to topic {} in batches of {}", sent, topic, batchSize);
        return sent;
    }
}