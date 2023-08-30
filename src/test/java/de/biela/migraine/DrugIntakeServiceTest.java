package de.biela.migraine;

import de.biela.migraine.model.DrugIntake;
import de.biela.migraine.model.Migraine;
import de.biela.migraine.service.DrugIntakeService;
import de.biela.migraine.service.MigraineService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DrugIntakeServiceTest {
    private static DrugIntake drugIntake;
    private static Migraine migraine;
    @Autowired
    private DrugIntakeService drugIntakeService;
    @Autowired()
    private MigraineService migraineService;
    private void assertDrugIntakeProperties(DrugIntake expected, DrugIntake actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDrug(), actual.getDrug());
        assertEquals(expected.getAmountEntity(), actual.getAmountEntity());
        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getTakeTimestamp(), actual.getTakeTimestamp());
        assertEquals(expected.getMigraineId(), actual.getMigraineId());
        assertEquals(expected.getCreationTimestamp(), actual.getCreationTimestamp());
        assertEquals(expected.getModificationTimestamp(), actual.getModificationTimestamp());
    }
    @BeforeAll
    public static void setUp() {

        migraine = new Migraine(
                UUID.randomUUID(),
                LocalDate.now(),
                "Test Migräneeee",
                Migraine.PainSeverity.STRONG,
                LocalDateTime.now().withNano(0),
                LocalDateTime.now().withNano(0)
        );
        drugIntake = new DrugIntake(
                UUID.randomUUID(),
                DrugIntake.Drug.PARACETAMOL,
                DrugIntake.AmountEntity.PIECE,
                new BigDecimal(2),
                LocalDateTime.now().withNano(0),
                migraine.getId(),
                LocalDateTime.now().withNano(0),
                LocalDateTime.now().withNano(0)
        );
    }

    @AfterAll
    public static void tearDown() {
        migraine = null;
        drugIntake = null;
    }

    @Test
    @Rollback(value = true)
    public void TestCreateAndGetMigraine() {
        //GIVEN
        migraineService.save(migraine);
        drugIntakeService.save(drugIntake);
        //WHEN
        DrugIntake getDrugIntake = drugIntakeService.getReferenceById(drugIntake.getId());

        //THEN
        assertAll("migraine",
                () -> assertDrugIntakeProperties(drugIntake,getDrugIntake)
        );
    }

    @Test
    @Rollback(value = true)
    public void TestUpdateAndGetMigraine() {
        //GIVEN
        migraineService.save(migraine);
        drugIntakeService.save(drugIntake);
        DrugIntake customizedDrugIntake = drugIntake;
        customizedDrugIntake.setDrug(DrugIntake.Drug.IBU);


        //WHEN
        DrugIntake getDrugIntake = drugIntakeService.getReferenceById(drugIntake.getId());
        getDrugIntake.setDrug(DrugIntake.Drug.IBU);

        //THEN
        assertAll("migraine",
                () -> assertDrugIntakeProperties(customizedDrugIntake,getDrugIntake)
        );
    }
    @Test
    @Rollback(value = true)
    public void TestDeleteAndGetMigraine() {
        //GIVEN
        migraineService.save(migraine);
        drugIntakeService.save(drugIntake);

        //WHEN
        drugIntakeService.deleteById(drugIntake.getId());
        Boolean available = drugIntakeService.existsById(drugIntake.getId());

        //THEN
        assertFalse(available);
    }

}
