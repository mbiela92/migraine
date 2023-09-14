package de.biela.migraine.service.impl;

import de.biela.migraine.model.dto.DrugIntakeDto;
import de.biela.migraine.model.dto.MigraineDto;
import de.biela.migraine.model.entity.DrugIntake;
import de.biela.migraine.model.entity.Migraine;
import de.biela.migraine.repository.DrugIntakeRepository;
import de.biela.migraine.service.DrugIntakeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Service
public class DrugIntakeServiceImpl implements DrugIntakeService {
    private final DrugIntakeRepository drugIntakeRepository;

    public DrugIntakeServiceImpl(DrugIntakeRepository drugIntakeRepository) {
        this.drugIntakeRepository = drugIntakeRepository;
    }

    @Override
    public DrugIntakeDto getDrugIntakeById(UUID id) {
        try {
            Optional<DrugIntake> drugIntake = drugIntakeRepository.findById(id);
            return drugIntake.map(drug -> new DrugIntakeDto(drug.getId(),drug.getDrug(),drug.getAmountEntity(),drug.getAmount(),drug.getTakeTimestamp(),drug.getMigraineId(),drug.getCreationTimestamp(),drug.getModificationTimestamp())).orElse(null);
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Ungültige Argumente für Aufruf des DrugIntake.");
        }
    }

    @Override
    public String updateDrugIntakeById(UUID id, DrugIntakeDto updatedDrugIntake) {
        try {
            Optional<DrugIntake> existingDrugIntake = drugIntakeRepository.findById(id);
            if (existingDrugIntake.isPresent()) {
                DrugIntake tempDrugIntake = existingDrugIntake.get();
                if (updatedDrugIntake.drug() != null) {
                    tempDrugIntake.setDrug(updatedDrugIntake.drug());
                    tempDrugIntake.setModificationTimestamp(LocalDateTime.now());
                }
                if (updatedDrugIntake.amountEntity() != null) {
                    tempDrugIntake.setAmountEntity(updatedDrugIntake.amountEntity());
                    tempDrugIntake.setModificationTimestamp(LocalDateTime.now());
                }
                if (updatedDrugIntake.amount() != null) {
                    tempDrugIntake.setAmount(updatedDrugIntake.amount());
                    tempDrugIntake.setModificationTimestamp(LocalDateTime.now());
                }
                if (updatedDrugIntake.takeTimestamp() != null) {
                    tempDrugIntake.setTakeTimestamp(updatedDrugIntake.takeTimestamp());
                    tempDrugIntake.setModificationTimestamp(LocalDateTime.now());
                }
                if (updatedDrugIntake.migraineId() != null) {
                    tempDrugIntake.setMigraineId(updatedDrugIntake.migraineId());
                    tempDrugIntake.setModificationTimestamp(LocalDateTime.now());
                }
                drugIntakeRepository.save(tempDrugIntake);
                return "DrugIntake wurde aktualisiert";
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Ungültige Argumente für DrugIntake update.");
        }
        return "Update ist fehlgeschlagen";
    }
    @Override
    public String createDrugIntakeById(UUID id, DrugIntakeDto createDrugIntake) {
        try {
            DrugIntake drugIntake = new DrugIntake(createDrugIntake.id(),createDrugIntake.drug(),createDrugIntake.amountEntity(),createDrugIntake.amount(), createDrugIntake.takeTimestamp(),createDrugIntake.migraineId(),createDrugIntake.creationTimestamp(),createDrugIntake.modificationTimestamp());
            drugIntakeRepository.save(drugIntake);
            return "DrugIntake wurde erstellt";
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new IllegalArgumentException("Ungültige Argumente für die Erstellung von DrugIntake.");
        }

    }

    @Override
    public String deleteDrugIntakeById(UUID id) {
        try {
            drugIntakeRepository.deleteById(id);
            return "DrugIntake wurde gelöscht";
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            throw new IllegalArgumentException("Ungültige Argumente für die Löschung von DrugIntake.");
        }
    }
}
