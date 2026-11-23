package pl.mateusz.projektinzynierski.collector;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz.projektinzynierski.model.SecurityEvent;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventProducer producer;

    public EventController(EventProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<Void> receive(@RequestBody SecurityEvent event) {
        producer.send(event);
        return ResponseEntity.accepted().build();
    }
}