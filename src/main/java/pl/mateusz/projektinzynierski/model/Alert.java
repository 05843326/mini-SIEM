package pl.mateusz.projektinzynierski.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant createdAt;
    private String ruleName;
    private String mitreTechnique;
    private String sourceIp;
    private String severity;

    @Column(length = 500)
    private String description;

    protected Alert() {}

    public Alert(String ruleName, String mitreTechnique, String sourceIp,
                 String severity, String description) {
        this.createdAt = Instant.now();
        this.ruleName = ruleName;
        this.mitreTechnique = mitreTechnique;
        this.sourceIp = sourceIp;
        this.severity = severity;
        this.description = description;
    }

    public Long getId() { return id; }
    public Instant getCreatedAt() { return createdAt; }
    public String getRuleName() { return ruleName; }
    public String getMitreTechnique() { return mitreTechnique; }
    public String getSourceIp() { return sourceIp; }
    public String getSeverity() { return severity; }
    public String getDescription() { return description; }
}