package pl.mateusz.projektinzynierski.processing;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.mateusz.projektinzynierski.config.KafkaTopicConfig;
import pl.mateusz.projektinzynierski.model.SecurityEvent;

@Component
public class EventConsumer {

    @KafkaListener(topics = KafkaTopicConfig.EVENTS_TOPIC, groupId = "siem-group")
    public void consume(SecurityEvent event) {
        System.out.println("Odebrano: " + event);
    }
}