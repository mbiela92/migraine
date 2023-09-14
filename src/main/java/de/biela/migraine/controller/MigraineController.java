package de.biela.migraine.controller;

import de.biela.migraine.model.dto.DrugIntakeDto;
import de.biela.migraine.model.dto.MigraineDto;
import de.biela.migraine.service.DrugIntakeService;
import de.biela.migraine.service.MigraineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class MigraineController {
    private final MigraineService migraineService;
    private final DrugIntakeService drugIntakeService;

    public MigraineController(MigraineService migraineService, DrugIntakeService drugIntakeService) {
        this.migraineService = migraineService;
        this.drugIntakeService = drugIntakeService;
    }

    @GetMapping(value = "/migraine/{id}")
    public ResponseEntity<MigraineDto> getMigraineById(@PathVariable(value = "id") final String id) {
        return ResponseEntity.ok(migraineService.getMigraineById(UUID.fromString(id)));
    }

    @PatchMapping(value = "/migraine/{id}")
    public String updateMigraineById(@PathVariable(value = "id") final String id, @RequestBody MigraineDto updatedMigraine) {
        return migraineService.updateMigraineById(UUID.fromString(id), updatedMigraine);
    }

    @PutMapping(value = "/migraine/{id}")
    public String createMigraineById(@PathVariable(value = "id") final String id, @RequestBody MigraineDto createMigraine) {
        return migraineService.createMigraineById(UUID.fromString(id), createMigraine);
    }

    @DeleteMapping(value = "/migraine/{id}")
    public String deleteMigraineById(@PathVariable(value = "id") final String id) {
        return migraineService.deleteMigraineById(UUID.fromString(id));
    }

    @GetMapping(value = "/drugIntake/{id}")
    public ResponseEntity<DrugIntakeDto> getDrugIntakeById(@PathVariable(value = "id") final String id) {
        return ResponseEntity.ok(drugIntakeService.getDrugIntakeById(UUID.fromString(id)));
    }

    @PatchMapping(value = "/drugIntake/{id}")
    public String updateDrugIntakeById(@PathVariable(value = "id") final String id, @RequestBody DrugIntakeDto updatedDrugintake) {
        return drugIntakeService.updateDrugIntakeById(UUID.fromString(id), updatedDrugintake);
    }

    @PutMapping(value = "/drugIntake/{id}")
    public String createDrugIntakeById(@PathVariable(value = "id") final String id, @RequestBody DrugIntakeDto createDrugintake) {
        return drugIntakeService.createDrugIntakeById(UUID.fromString(id), createDrugintake);
    }

    @DeleteMapping(value = "/drugIntake/{id}")
    public String deleteDrugIntakeById(@PathVariable(value = "id") final String id) {
        return drugIntakeService.deleteDrugIntakeById(UUID.fromString(id));
    }
}
