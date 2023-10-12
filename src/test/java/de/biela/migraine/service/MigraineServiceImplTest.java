package de.biela.migraine.service;

import de.biela.migraine.model.dto.MigraineDto;
import de.biela.migraine.model.entity.Migraine;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MigraineServiceImplTest {
    private static MigraineDto migraineDto;
    private static UUID uuid;
    @Autowired
    private MigraineService migraineService;
    private void assertMigraineProperties(MigraineDto expected, MigraineDto actual) {
        assertEquals(expected.id(), actual.id());
        assertEquals(expected.date(), actual.date());
        assertEquals(expected.description(), actual.description());
        assertEquals(expected.painSeverity(), actual.painSeverity());
        assertEquals(expected.creationTimestamp(), actual.creationTimestamp());
    }
    @BeforeAll
    public static void setUp() {
        uuid = UUID.randomUUID();
        migraineDto = new MigraineDto(uuid, LocalDate.now(),"test", Migraine.PainSeverity.WEAK, LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0));
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void TestCreateAndGetMigraine() {
        //GIVEN
        migraineService.createMigraineById(uuid, migraineDto);

        //WHEN
        MigraineDto getMigraineDto = migraineService.getMigraineById(uuid);

        //THEN
        assertAll("migraine",
                () -> assertMigraineProperties(migraineDto,getMigraineDto)
        );
        assertEquals(migraineDto.modificationTimestamp(), getMigraineDto.modificationTimestamp());
    }
    @Test
    @Order(2)
    public void TestUpdateAndGetMigraine() {
        //GIVEN
        MigraineDto updatedMigraineDto = new MigraineDto(migraineDto.id(), migraineDto.date(),"updatedMigraine", migraineDto.painSeverity(), migraineDto.creationTimestamp(),migraineDto.modificationTimestamp());

        //WHEN
        migraineService.updateMigraineById(uuid,updatedMigraineDto);
        MigraineDto getMigraineDto = migraineService.getMigraineById(uuid);
        //THEN
        assertAll("migraine",
                () -> assertMigraineProperties(updatedMigraineDto,getMigraineDto)
        );
    }
    @Test
    @Order(3)
    public void TestDeleteAndGetMigraine() {
        //GIVEN
        Boolean available = true;

        //WHEN
        migraineService.deleteMigraineById(migraineDto.id());
        MigraineDto deletedMigraineDto = migraineService.getMigraineById(migraineDto.id());

        //THEN
        assertTrue(deletedMigraineDto == null);
    }
    @Order(4)
    @Test
    public void TestCreateMigraineById_InvalidMockArgument_ThrownIllegalArgument() {
        //GIVEN
        MigraineDto mockMigraine = Mockito.mock(MigraineDto.class);
        when(mockMigraine.id()).thenThrow(new IllegalArgumentException());
        //WHEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            migraineService.createMigraineById(UUID.randomUUID(), mockMigraine);
        });
        //THEN
        assertEquals("Ungültige Argumente für die Erstellung von Migräne.", exception.getMessage());
    }
    @Order(5)
    @Test
    public void TestGetMigraineById_InvalidArgumentInDb_ThrownIllegalArgument() {
        //GIVEN
        //WHEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            migraineService.getMigraineById(UUID.fromString("a054f847-7667-4f56-91e9-95c031555e97"));
        });
        //THEN
        assertEquals("Ungültige Argumente für Aufruf der Migräne.", exception.getMessage());
    }
    @Order(6)
    @Test
    public void TestUpdateMigraineById_InvalidMigraineDto_ThrownIllegalArgument(){
        //GIVEN
        MigraineDto migraineDtoMock = Mockito.mock(MigraineDto.class);
        when(migraineDtoMock.date()).thenThrow(new IllegalArgumentException());
        //WHEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            migraineService.updateMigraineById(UUID.fromString("c3cd356c-2dc0-44d2-9c2a-3f40b985e245"), migraineDtoMock);
        });
        //THEN
        assertEquals("Ungültige Argumente für die Migräne update.", exception.getMessage());
    }
    @Order(7)
    @Test
    public void TestUpdateMigraineById_InvalidUUID_FailureMessage(){
        //GIVEN
        // WHEN
        String failureMessage = migraineService.updateMigraineById(UUID.fromString("11111111-1111-1111-1111-111111111111"),migraineDto);
        //THEN
        assertEquals("Update ist fehlgeschlagen", failureMessage);
    }

}
