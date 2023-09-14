package de.biela.migraine.service;
import de.biela.migraine.model.dto.MigraineDto;
import de.biela.migraine.model.entity.Migraine;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

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

}
