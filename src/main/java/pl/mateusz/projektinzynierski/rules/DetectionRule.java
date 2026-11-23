package pl.mateusz.projektinzynierski.rules;

import pl.mateusz.projektinzynierski.model.Alert;
import pl.mateusz.projektinzynierski.model.SecurityEvent;
import java.util.Optional;

public interface DetectionRule {
    Optional<Alert> evaluate(SecurityEvent event);
}