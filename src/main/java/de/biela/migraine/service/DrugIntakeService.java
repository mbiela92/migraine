package de.biela.migraine.service;

import de.biela.migraine.model.DrugIntake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DrugIntakeService extends JpaRepository<DrugIntake, UUID> {
}
