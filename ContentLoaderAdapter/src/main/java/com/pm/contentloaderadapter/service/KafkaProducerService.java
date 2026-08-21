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


    private String rosreestrTopic;

    private String domclickTopic;



    public int sendBatch(List<BuildingRawData> records, String topic) {
        int sent = 0;
        for (BuildingRawData record : records) {
            kafkaTemplate.send(topic, record);
            sent++;
        }
        log.info("Sent {} records to topic {}", sent, topic);
        return sent;
    }
}