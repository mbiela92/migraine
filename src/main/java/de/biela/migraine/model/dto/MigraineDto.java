package de.biela.migraine.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record MigraineDto(UUID id,
                          LocalDate date,
                          String description,
                          de.biela.migraine.model.entity.Migraine.PainSeverity painSeverity,
                          LocalDateTime creationTimestamp,
                          LocalDateTime modificationTimestamp
) {



}
