package de.biela.migraine.controller;

import de.biela.migraine.model.dto.DrugIntakeDto;
import de.biela.migraine.service.DrugIntakeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/drugIntake")
public class DrugIntakeController {
    private final DrugIntakeService drugIntakeService;

    public DrugIntakeController(DrugIntakeService drugIntakeService) {
        this.drugIntakeService = drugIntakeService;
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<DrugIntakeDto> getDrugIntakeById(@PathVariable(value = "id") final String id) {
        return ResponseEntity.ok(drugIntakeService.getDrugIntakeById(UUID.fromString(id)));
    }
    @PatchMapping(value = "/{id}")
    public ResponseEntity<DrugIntakeDto> updateDrugIntakeById(@PathVariable(value = "id") final String id, @RequestBody DrugIntakeDto updatedDrugIntake) {
        return ResponseEntity.ok(drugIntakeService.updateDrugIntakeById(UUID.fromString(id), updatedDrugIntake));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DrugIntakeDto> createDrugIntakeById(@PathVariable(value = "id") final String id, @RequestBody DrugIntakeDto createDrugIntake) {
        return ResponseEntity.ok(drugIntakeService.createDrugIntakeById(UUID.fromString(id), createDrugIntake));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteDrugIntakeById(@PathVariable(value = "id") final String id) {
        return ResponseEntity.ok(drugIntakeService.deleteDrugIntakeById(UUID.fromString(id)));
    }
}
