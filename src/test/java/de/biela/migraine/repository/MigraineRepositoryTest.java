package de.biela.migraine.repository;


import de.biela.migraine.model.entity.Migraine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MigraineRepositoryTest {
    private static Migraine migraine;
    @Autowired
    private MigraineRepository migraineRepository;

    private void assertMigraineProperties(Migraine expected, Migraine actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPainSeverity(), actual.getPainSeverity());
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
    }

    @Test
    @Rollback(value = true)
    public void TestCreateAndGetMigraine() {
        //GIVEN
        migraineRepository.save(migraine);

        //WHEN
        Migraine getMigraine = migraineRepository.getReferenceById(migraine.getId());
        System.out.println(getMigraine);

        //THEN
        assertAll("migraine",
                () -> assertMigraineProperties(migraine,getMigraine)
        );
    }

    @Test
    @Rollback(value = true)
    public void TestUpdateAndGetMigraine() {
        //GIVEN
        migraineRepository.save(migraine);
        Migraine customizedMigraine = migraine;
        customizedMigraine.setDescription("Update Test Migraine");


        //WHEN
        Migraine getMigraine = migraineRepository.getReferenceById(migraine.getId());
        getMigraine.setDescription("Update Test Migraine");

        //THEN
        assertAll("migraine",
                () -> assertMigraineProperties(customizedMigraine,getMigraine)
        );
    }

    @Test
    @Rollback(value = true)
    public void TestDeleteAndGetMigraine() {
        //GIVEN
        migraineRepository.save(migraine);

        //WHEN
        migraineRepository.deleteById(migraine.getId());

        //THEN
        assertFalse(migraineRepository.existsById(migraine.getId()));
    }


}
