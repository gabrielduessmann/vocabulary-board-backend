package com.vocabulary.board.column;

import com.vocabulary.board.column.enums.StatusEnum;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")

public class ColumnControllerIT {

    private static final String TITLE = "This is the title";
    private static final Date NEXT_UPDATE = new Date();
    private static final StatusEnum  STATUS = StatusEnum.POOL;
    private static final Integer SPRINT_ORDER = 0;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void baseSetup() {
        RestAssured.baseURI = "http://localhost:" + serverPort;
    }

    @Test
    void saveColumn_success() {
        Column column = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new Column(TITLE, NEXT_UPDATE, STATUS, SPRINT_ORDER))
                .post("/column")
                .then()
                .statusCode(200)
                .extract().as(Column.class);

        assertEquals(TITLE, column.getTitle());
        assertEquals(NEXT_UPDATE, column.getNextUpdate());
        assertEquals(STATUS, column.getStatus());
        assertEquals(SPRINT_ORDER, column.getSprintOrder());
    }

    @Test
    void findColumnById_success() {
        UUID id = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new Column(TITLE, NEXT_UPDATE, STATUS, SPRINT_ORDER))
                .post("column")
                .then()
                .statusCode(200)
                .extract().as(Column.class).getId();

        Column column = given()
                .when()
                .get("column/"+id)
                .then()
                .statusCode(200)
                .extract().as(Column.class);

        assertEquals(TITLE, column.getTitle());
        assertEquals(NEXT_UPDATE, column.getNextUpdate());
        assertEquals(STATUS, column.getStatus());
        assertEquals(SPRINT_ORDER, column.getSprintOrder());
    }
}
