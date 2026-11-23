package pl.mateusz.projektinzynierski.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String EVENTS_TOPIC = "security-events";

    @Bean
    public NewTopic securityEventsTopic() {
        return TopicBuilder.name(EVENTS_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}