package de.biela.migraine.service;

import de.biela.migraine.model.dto.DrugIntakeDto;
import de.biela.migraine.model.entity.DrugIntake;
import de.biela.migraine.model.entity.Migraine;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
        Boolean available = true;

        //WHEN
        drugIntakeService.deleteDrugIntakeById(drugIntakeDto.id());
        DrugIntakeDto deletedDrugIntakeDto = drugIntakeService.getDrugIntakeById(drugIntakeDto.id());

        //THEN
        assertTrue(deletedDrugIntakeDto == null);
    }

}
