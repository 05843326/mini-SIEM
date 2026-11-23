package pl.mateusz.projektinzynierski.processing;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pl.mateusz.projektinzynierski.alerts.AlertRepository;
import pl.mateusz.projektinzynierski.config.KafkaTopicConfig;
import pl.mateusz.projektinzynierski.model.SecurityEvent;
import pl.mateusz.projektinzynierski.rules.DetectionRule;

import java.util.List;

@Component
public class EventConsumer {

    private final EventRepository eventRepository;
    private final AlertRepository alertRepository;
    private final List<DetectionRule> rules;

    public EventConsumer(EventRepository eventRepository,
                         AlertRepository alertRepository,
                         List<DetectionRule> rules) {
        this.eventRepository = eventRepository;
        this.alertRepository = alertRepository;
        this.rules = rules;
    }

    @KafkaListener(topics = KafkaTopicConfig.EVENTS_TOPIC, groupId = "siem-group")
    public void consume(SecurityEvent event) {
        eventRepository.save(EventEntity.from(event));
        rules.forEach(rule -> rule.evaluate(event).ifPresent(alert -> {
            alertRepository.save(alert);
            System.out.println("ALERT: " + alert.getDescription());
        }));
    }
}