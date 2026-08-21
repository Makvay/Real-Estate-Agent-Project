package com.pm.contentloaderadapter.config;


import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public NewTopic RosReestrTopic() {
        return new NewTopic("rosreestr-event", 1, (short) 1);
    }

    @Bean
    public NewTopic DomClickTopic() {
        return new NewTopic("domclick-event", 1, (short) 1);
    }
    //  Можно строить топики
    public KafkaAdmin getAdminClient() {
        HashMap<String, Object> mapAdmin = new HashMap<>();
        mapAdmin.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(mapAdmin);
    }

}
