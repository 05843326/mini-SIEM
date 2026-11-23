package pl.mateusz.projektinzynierski.rules;

import org.springframework.stereotype.Component;
import pl.mateusz.projektinzynierski.model.Alert;
import pl.mateusz.projektinzynierski.model.EventType;
import pl.mateusz.projektinzynierski.model.SecurityEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BruteForceRule implements DetectionRule {

    private static final int THRESHOLD = 5;
    private static final Duration WINDOW = Duration.ofSeconds(60);

    private final Map<String, Deque<Instant>> failures = new ConcurrentHashMap<>();

    @Override
    public Optional<Alert> evaluate(SecurityEvent event) {
        if (event.eventType() != EventType.LOGIN_FAILED || event.sourceIp() == null) {
            return Optional.empty();
        }

        Deque<Instant> times = failures.computeIfAbsent(event.sourceIp(), k -> new ArrayDeque<>());

        synchronized (times) {
            Instant now = event.timestamp();
            times.addLast(now);

            while (!times.isEmpty() && times.peekFirst().isBefore(now.minus(WINDOW))) {
                times.pollFirst();
            }

            if (times.size() >= THRESHOLD) {
                int count = times.size();
                times.clear();
                return Optional.of(new Alert(
                        "BruteForceRule",
                        "T1110",
                        event.sourceIp(),
                        "HIGH",
                        count + " nieudanych logowan z IP " + event.sourceIp()
                                + " w ciagu " + WINDOW.getSeconds() + " s"));
            }
        }
        return Optional.empty();
    }
}