package pl.mateusz.projektinzynierski.collector;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.mateusz.projektinzynierski.config.KafkaTopicConfig;
import pl.mateusz.projektinzynierski.model.SecurityEvent;

@Service
public class EventProducer {

    private final KafkaTemplate<String, SecurityEvent> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, SecurityEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(SecurityEvent event) {
        kafkaTemplate.send(KafkaTopicConfig.EVENTS_TOPIC, event.sourceIp(), event);
    }
}