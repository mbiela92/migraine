package de.biela.migraine.service;

import de.biela.migraine.model.dto.DrugIntakeDto;
import de.biela.migraine.model.entity.DrugIntake;

import java.util.UUID;

public interface DrugIntakeService {
    DrugIntakeDto getDrugIntakeById (final UUID id);
    String updateDrugIntakeById(final UUID id, final DrugIntakeDto updatedDrugIntake);
    DrugIntake createDrugIntakeById(final UUID id, final DrugIntakeDto createDrugIntake);
    String deleteDrugIntakeById(final UUID id);
}
