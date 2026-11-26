package pl.mateusz.projektinzynierski.generator;

import org.springframework.stereotype.Service;
import pl.mateusz.projektinzynierski.collector.EventProducer;
import pl.mateusz.projektinzynierski.model.EventType;
import pl.mateusz.projektinzynierski.model.SecurityEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TrafficGenerator {

    private static final String[] USERS = {"anna", "piotr", "marek", "ewa", "admin"};
    private static final String[] PATHS = {"/", "/products", "/cart", "/orders", "/profile"};

    private final EventProducer producer;
    private final Random random = new Random();

    public TrafficGenerator(EventProducer producer) {
        this.producer = producer;
    }

    public void sendNormalTraffic(int count) {
        for (int i = 0; i < count; i++) {
            String ip = "10.0.0." + (1 + random.nextInt(50));
            String user = USERS[random.nextInt(USERS.length)];
            Instant ts = Instant.now();

            if (random.nextInt(100) < 5) {
                producer.send(new SecurityEvent(ts, ip, user, EventType.LOGIN_FAILED, "/login", 401, "generator"));
            } else if (random.nextInt(100) < 15) {
                producer.send(new SecurityEvent(ts, ip, user, EventType.LOGIN_SUCCESS, "/login", 200, "generator"));
            } else {
                producer.send(new SecurityEvent(ts, ip, user, EventType.HTTP_REQUEST,
                        PATHS[random.nextInt(PATHS.length)], 200, "generator"));
            }
        }
    }

    public String sendBruteForce(int attemptNumber, int attempts) {
        String ip = "203.0.113." + (1 + attemptNumber);
        Instant start = Instant.now().minusSeconds(attempts * 3L);
        for (int i = 0; i < attempts; i++) {
            producer.send(new SecurityEvent(start.plusSeconds(i * 3L), ip, "admin",
                    EventType.LOGIN_FAILED, "/login", 401, "generator"));
        }
        return ip;
    }

    public List<String> run(int normalEvents, int bruteForceAttacks, int attemptsPerAttack) {
        List<String> attackIps = new ArrayList<>();
        sendNormalTraffic(normalEvents / 2);
        for (int i = 0; i < bruteForceAttacks; i++) {
            attackIps.add(sendBruteForce(i, attemptsPerAttack));
        }
        sendNormalTraffic(normalEvents / 2);
        return attackIps;
    }
}