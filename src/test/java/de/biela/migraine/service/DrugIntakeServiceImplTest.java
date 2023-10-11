package de.biela.migraine.service;

import de.biela.migraine.model.dto.DrugIntakeDto;
import de.biela.migraine.model.entity.DrugIntake;
import de.biela.migraine.model.entity.Migraine;
import de.biela.migraine.repository.DrugIntakeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DrugIntakeServiceImplTest {
    private static DrugIntakeDto drugIntakeDto;
    private static UUID uuid;
    @Autowired
    private DrugIntakeService drugIntakeService;
    private void assertDrugIntakeProperties(DrugIntakeDto expected, DrugIntakeDto actual) {
        assertEquals(expected.id(), actual.id());
        assertEquals(expected.drug(), actual.drug());
        assertEquals(expected.amountEntity(), actual.amountEntity());
        assertEquals(expected.amount(), actual.amount());
        assertEquals(expected.takeTimestamp(), actual.takeTimestamp());
        assertEquals(expected.creationTimestamp(), actual.creationTimestamp());
    }
    @BeforeAll
    public static void setUp() {
        uuid = UUID.randomUUID();
        Migraine migraine = new Migraine(UUID.fromString("1f76bfe2-5c82-4575-b0b5-a5804c86f704"), LocalDate.now(),"test", Migraine.PainSeverity.WEAK, LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0));
        drugIntakeDto = new DrugIntakeDto(uuid, DrugIntake.Drug.PARACETAMOL, DrugIntake.AmountEntity.PIECE, BigDecimal.ONE, LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0),migraine);
    }
    @Test
    @Order(1)
    public void TestCreateAndGetDrugIntake() {
        //GIVEN
        drugIntakeService.createDrugIntakeById(uuid, drugIntakeDto);

        //WHEN
        DrugIntakeDto getDrugIntakeById = drugIntakeService.getDrugIntakeById(uuid);

        //THEN
        assertAll("migraine",
                () -> assertDrugIntakeProperties(drugIntakeDto,getDrugIntakeById)
        );
        assertEquals(drugIntakeDto.modificationTimestamp(), getDrugIntakeById.modificationTimestamp());
    }
    @Test
    @Order(2)
    public void TestUpdateAndGetDrugIntake() {
        //GIVEN
        DrugIntakeDto updatedDrugIntakeDto = new DrugIntakeDto(drugIntakeDto.id(), drugIntakeDto.drug(),drugIntakeDto.amountEntity(), BigDecimal.TWO, drugIntakeDto.takeTimestamp(),drugIntakeDto.creationTimestamp(),drugIntakeDto.modificationTimestamp(),drugIntakeDto.migraineId());

        //WHEN
        drugIntakeService.updateDrugIntakeById(uuid,updatedDrugIntakeDto);
        DrugIntakeDto getDrugIntakeDto = drugIntakeService.getDrugIntakeById(uuid);
        //THEN
        assertAll("migraine",
                () -> assertDrugIntakeProperties(updatedDrugIntakeDto,getDrugIntakeDto)
        );
    }
    @Test
    @Order(3)
    public void TestDeleteAndGetDrugIntake() {
        //GIVEN

        //WHEN
        drugIntakeService.deleteDrugIntakeById(drugIntakeDto.id());
        DrugIntakeDto deletedDrugIntakeDto = drugIntakeService.getDrugIntakeById(drugIntakeDto.id());

        //THEN
        assertNull(deletedDrugIntakeDto);
    }
    @Order(4)
    @Test
    public void TestCreateDrugIntakeById_InvalidForeignKey_ThrownIllegalArgument() {
        //GIVEN
        Migraine migraine = new Migraine(UUID.fromString("11111111-1111-1111-1111-111111111111"), LocalDate.now(),"test", Migraine.PainSeverity.WEAK, LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0));
        DrugIntakeDto invalidDto = new DrugIntakeDto(uuid, DrugIntake.Drug.PARACETAMOL, DrugIntake.AmountEntity.PIECE, BigDecimal.ONE, LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0),migraine);
        //WHEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            drugIntakeService.createDrugIntakeById(UUID.randomUUID(), invalidDto);
        });
        //THEN
        assertEquals("Ungültige Argumente für die Erstellung von DrugIntake.", exception.getMessage());
    }
    @Order(5)
    @Test
    public void TestGetDrugIntakeById_InvalidForeignKey_ThrownIllegalArgument() {
        //GIVEN
        DrugIntakeRepository drugIntakeMock = mock(DrugIntakeRepository.class);
        //WHEN
        when(drugIntakeMock.findById(any(UUID.class))).thenThrow(new IllegalArgumentException());
        //THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            drugIntakeService.getDrugIntakeById(UUID.fromString("31ea9ad9-082a-4dd5-b918-d18a79efb036"));
        });
        assertEquals("Ungültige Argumente für Aufruf des DrugIntake.", exception.getMessage());
    }
    @Order(6)
    @Test
    public void TestGetDrugIntakeById_InvalidForeignKey_ThrownIllegalArgument1(){
        //WHEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            drugIntakeService.getDrugIntakeById(UUID.fromString("4412622b-782c-4f02-b0a1-4575bce4cc10"));
        });
        //THEN
        assertEquals("Ungültige Argumente für Aufruf des DrugIntake.", exception.getMessage());
    }
    @Order(7)
    @Test
    public void TestUpdateDrugIntakeById_InvalidDrugIntakeDto_ThrownIllegalArgument(){
        //GIVEN
        Migraine migraine = new Migraine(UUID.fromString("11111111-1111-1111-1111-111111111111"), LocalDate.now(),"test", Migraine.PainSeverity.WEAK, LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0));
        DrugIntakeDto invalidDto = new DrugIntakeDto(uuid, DrugIntake.Drug.PARACETAMOL, DrugIntake.AmountEntity.PIECE, BigDecimal.ONE, LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0),migraine);
        //WHEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            drugIntakeService.updateDrugIntakeById(UUID.fromString("4412622b-782c-4f02-b0a1-4575bce4cc10"), invalidDto);
        });
        //THEN
        assertEquals("Ungültige Argumente für DrugIntake update.", exception.getMessage());
    }
    @Order(8)
    @Test
    public void TestUpdateDrugIntakeById_InvalidUUID_FailureMessage(){
        //GIVEN
        // WHEN
        String failureMessage = drugIntakeService.updateDrugIntakeById(UUID.fromString("11111111-1111-1111-1111-111111111111"),drugIntakeDto);
        //THEN
        assertEquals("Update ist fehlgeschlagen", failureMessage);
    }








}
