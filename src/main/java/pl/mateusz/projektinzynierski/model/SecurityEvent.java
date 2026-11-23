package pl.mateusz.projektinzynierski.model;

import java.time.Instant;

public record SecurityEvent(
        Instant timestamp,
        String sourceIp,
        String username,
        EventType eventType,
        String endpoint,
        int statusCode,
        String source) {
}
