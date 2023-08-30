package de.biela.migraine.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity(name = "Migraine")
@Table(name = "migraine"
)
public class Migraine {


    public enum PainSeverity {
        WEAK, MEDIUM, STRONG
    }
    @Id
    @Column(name = "id",
            updatable = false
    )
    private UUID id;
    @Column(name = "date"
    )
    private LocalDate date;
    @Column(name = "description"
    )
    private String description;
    @Column(name = "pain_severity"
    )
    public PainSeverity painSeverity;
    @Column(name = "creation_timestamp",
            nullable = false
    )
    private LocalDateTime creationTimestamp;
    @Column(name = "modification_timestamp"

    )
    private LocalDateTime modificationTimestamp;

    public Migraine(){

    }

    public Migraine(UUID id, LocalDate date, String description, PainSeverity painSeverity,
                    LocalDateTime creationTimestamp, LocalDateTime modificationTimestamp) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.painSeverity = painSeverity;
        this.creationTimestamp = creationTimestamp;
        this.modificationTimestamp = modificationTimestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PainSeverity getPainSeverity() {
        return painSeverity;
    }

    public void setPainSeverity(PainSeverity painSeverity) {
        this.painSeverity = painSeverity;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public LocalDateTime getModificationTimestamp() {
        return modificationTimestamp;
    }

    public void setModificationTimestamp(LocalDateTime modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }

    @Override
    public String toString() {
        return "\nMigraine{\n" +
                "id=" + id +
                ",\ndate=" + date +
                ",\ndescription=" + description +
                ",\npainSeverity=" + painSeverity +
                ",\ncreationTimestamp=" + creationTimestamp +
                ",\nmodificationTimestamp=" + modificationTimestamp +
                '}';
    }
}
