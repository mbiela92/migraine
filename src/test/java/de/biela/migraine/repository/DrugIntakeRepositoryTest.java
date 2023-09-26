package de.biela.migraine.repository;

import de.biela.migraine.model.entity.DrugIntake;
import de.biela.migraine.model.entity.Migraine;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
public class DrugIntakeRepositoryTest {
    private static DrugIntake drugIntake;
    private static Migraine migraine;
    @Autowired
    private DrugIntakeRepository drugIntakeRepository;
    @Autowired()
    private MigraineRepository migraineRepository;
    private void assertDrugIntakeProperties(DrugIntake expected, DrugIntake actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDrug(), actual.getDrug());
        assertEquals(expected.getAmountEntity(), actual.getAmountEntity());
        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getTakeTimestamp(), actual.getTakeTimestamp());
        //assertEquals(expected.getMigraineId(), actual.getMigraineId());
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
                LocalDateTime.now().withNano(0),
                LocalDateTime.now().withNano(0),
                migraine
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
        migraineRepository.save(migraine);
        drugIntakeRepository.save(drugIntake);
        //WHEN
        DrugIntake getDrugIntake = drugIntakeRepository.getReferenceById(drugIntake.getId());

        //THEN
        assertAll("migraine",
                () -> assertDrugIntakeProperties(drugIntake,getDrugIntake)
        );
    }

    @Test
    @Rollback(value = true)
    public void TestUpdateAndGetMigraine() {
        //GIVEN
        migraineRepository.save(migraine);
        drugIntakeRepository.save(drugIntake);
        DrugIntake customizedDrugIntake = drugIntake;
        customizedDrugIntake.setDrug(DrugIntake.Drug.IBU);


        //WHEN
        DrugIntake getDrugIntake = drugIntakeRepository.getReferenceById(drugIntake.getId());
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
        migraineRepository.save(migraine);
        drugIntakeRepository.save(drugIntake);

        //WHEN
        drugIntakeRepository.deleteById(drugIntake.getId());

        //THEN
        assertFalse(drugIntakeRepository.existsById(drugIntake.getId()));
    }

}
