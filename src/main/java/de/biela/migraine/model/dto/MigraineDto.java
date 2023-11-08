package de.biela.migraine.model.dto;


import de.biela.migraine.model.entity.Migraine;
import org.hibernate.mapping.List;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record MigraineDto(UUID id,
                          LocalDate date,
                          String description,
                          Migraine.PainSeverity painSeverity,
                          LocalDateTime creationTimestamp,
                          LocalDateTime modificationTimestamp,
                          List drugIntakeList
) {



}
