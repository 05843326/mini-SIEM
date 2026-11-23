package pl.mateusz.projektinzynierski.alerts;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.projektinzynierski.model.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}