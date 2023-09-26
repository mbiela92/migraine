package de.biela.migraine.controller;

import de.biela.migraine.model.dto.DrugIntakeDto;
import de.biela.migraine.model.entity.DrugIntake;
import de.biela.migraine.model.entity.Migraine;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DrugIntakeControllerIT {
    private static DrugIntakeDto drugIntakeDto;
    private static UUID uuid;
    @BeforeAll
    public static void setUp(){
        uuid = UUID.randomUUID();
        baseURI = "http://localhost:8080/drugIntake";
        Migraine migraine = new Migraine(UUID.fromString("0074f035-90a0-4749-8f40-0a4a7d3731e0"), LocalDate.now(),"test", Migraine.PainSeverity.WEAK, LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0));
        drugIntakeDto = new DrugIntakeDto(uuid, DrugIntake.Drug.PARACETAMOL, DrugIntake.AmountEntity.PIECE, BigDecimal.ONE, LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0),LocalDateTime.now().withNano(0),migraine);
    }
    @Order(1)
    @Test
    public void TestCreateAndGetDrugIntake(){
        given()
                .contentType(ContentType.JSON)
                .body(drugIntakeDto)
        .when()
                .put(uuid.toString())
        .then()
                .assertThat()
                .statusCode(200);
    }
    @Order(2)
    @Test
    public void TestUpdateAndGetDrugIntake(){
        //GIVEN
        DrugIntakeDto updatedDrugIntakeDto = new DrugIntakeDto(drugIntakeDto.id(), drugIntakeDto.drug(),drugIntakeDto.amountEntity(), BigDecimal.TWO, drugIntakeDto.takeTimestamp(),drugIntakeDto.creationTimestamp(),drugIntakeDto.modificationTimestamp(),drugIntakeDto.migraineId());
        given()
                .contentType(ContentType.JSON)
                .body(updatedDrugIntakeDto)
        .when()
                .patch(uuid.toString())
        .then()
                .assertThat()
                .statusCode(200);
    }
    @Order(3)
    @Test
    public void TestDeleteAndGetDrugIntake(){
        given()
        .when()
                .delete(uuid.toString())
        .then()
                .assertThat()
                .statusCode(200);
    }
}
