package de.biela.migraine.repository;

import de.biela.migraine.model.entity.DrugIntake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DrugIntakeRepository extends JpaRepository<DrugIntake, UUID> {
}
