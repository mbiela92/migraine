package de.biela.migraine.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import de.biela.migraine.model.dto.MigraineDto;
import de.biela.migraine.model.entity.Migraine;
import de.biela.migraine.service.DrugIntakeService;
import de.biela.migraine.service.MigraineService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MigraineControllerTest {

    @Test
    public void testGetMigraineById() {
        // GIVEN
        MigraineService migraineService = mock(MigraineService.class);
        DrugIntakeService drugIntakeService = mock(DrugIntakeService.class);
        UUID testId = UUID.randomUUID();
        MigraineDto testMigraineDto = new MigraineDto(testId, LocalDate.now(), "Beschreibung", Migraine.PainSeverity.WEAK, LocalDateTime.now(), LocalDateTime.now());
        MigraineController migraineController = new MigraineController(migraineService, drugIntakeService);

        // WHEN
        when(migraineService.getMigraineById(testId)).thenReturn(testMigraineDto);
        ResponseEntity<MigraineDto> responseEntity = migraineController.getMigraineById(testId.toString());

        // THEN
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testMigraineDto, responseEntity.getBody());
    }
}
