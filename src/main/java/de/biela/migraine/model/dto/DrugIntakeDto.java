package de.biela.migraine.model.dto;

import de.biela.migraine.model.entity.DrugIntake;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DrugIntakeDto(
        UUID id,
        DrugIntake.Drug drug,
        DrugIntake.AmountEntity amountEntity,
        BigDecimal amount,
        LocalDateTime takeTimestamp,
        UUID migraineId,
        LocalDateTime creationTimestamp,
        LocalDateTime modificationTimestamp) {}
