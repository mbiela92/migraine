package de.biela.migraine.controller;

import de.biela.migraine.model.dto.MigraineDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MigraineControllerIT {
    private static MigraineDto migraineDto;
    private static UUID uuid;

    @BeforeAll
    public static void setUp(){
        uuid = UUID.randomUUID();
        baseURI = "http://localhost:8080/migraine";
        migraineDto = new MigraineDto(uuid, LocalDate.now(),"test", de.biela.migraine.model.entity.Migraine.PainSeverity.WEAK, LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0),null);
    }
    @Order(1)
    @Test
    public void TestCreateAndGetMigraine(){
        given()
                .contentType(ContentType.JSON)
                .body(migraineDto)
        .when()
                .put(uuid.toString())
        .then()
                .assertThat()
                .statusCode(200);
    }
    @Order(2)
    @Test
    public void TestUpdateAndGetMigraine(){
        //GIVEN
        MigraineDto updatedMigraineDto = new MigraineDto(migraineDto.id(), migraineDto.date(),"updated tesssssssssssssssssssssssssssst", migraineDto.painSeverity(), migraineDto.creationTimestamp(),migraineDto.modificationTimestamp(),null);
        given()
                .contentType(ContentType.JSON)
                .body(updatedMigraineDto)
        .when()
                .patch(uuid.toString())
        .then()
                .assertThat()
                .statusCode(200);
    }
    @Order(3)
    @Test
    public void TestDeleteAndGetMigraine(){
        given()
        .when()
                .delete(uuid.toString())
        .then()
                .assertThat()
                .statusCode(200);
    }



}
