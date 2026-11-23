package pl.mateusz.projektinzynierski.processing;

import jakarta.persistence.*;
import pl.mateusz.projektinzynierski.model.EventType;
import pl.mateusz.projektinzynierski.model.SecurityEvent;
import java.time.Instant;

@Entity
@Table(name = "events", indexes = {
        @Index(name = "idx_events_ip_time", columnList = "sourceIp, timestamp")
})
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant timestamp;
    private String sourceIp;
    private String username;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String endpoint;
    private int statusCode;
    private String source;

    protected EventEntity() {}

    public static EventEntity from(SecurityEvent e) {
        EventEntity x = new EventEntity();
        x.timestamp = e.timestamp();
        x.sourceIp = e.sourceIp();
        x.username = e.username();
        x.eventType = e.eventType();
        x.endpoint = e.endpoint();
        x.statusCode = e.statusCode();
        x.source = e.source();
        return x;
    }

    public Long getId() { return id; }
    public Instant getTimestamp() { return timestamp; }
    public String getSourceIp() { return sourceIp; }
    public String getUsername() { return username; }
    public EventType getEventType() { return eventType; }
    public String getEndpoint() { return endpoint; }
    public int getStatusCode() { return statusCode; }
    public String getSource() { return source; }
}