package com.vocabulary.board.vocabulary;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.vocabulary.board.builders.VocabularyBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
class VocabularyControllerIT {

  private static final String WORD = "beautiful";
  private static final String DESCRIPTION = "adjective";
  private static final Date DATE = new Date();

  @LocalServerPort
  private int serverPort;

  @BeforeEach
  void baseSetup() {
    RestAssured.baseURI = "http://localhost:" + serverPort;
  }

  @Test
  void addVocabulary_success() {
    Vocabulary vocabulary = given()
        .when()
        .contentType(ContentType.JSON)
        .body(VocabularyBuilder.oneVocabulary().withId(null).withWord(WORD).withDescription(DESCRIPTION).withDate(DATE).build())
        .post("/vocabulary")
        .then()
        .statusCode(200)
        .extract().as(Vocabulary.class);

    assertEquals(WORD, vocabulary.getWord());
    assertEquals(DESCRIPTION, vocabulary.getDescription());
    assertEquals(DATE, vocabulary.getCreationDate());
    assertNotNull(vocabulary.getId());
  }

  @Test
  void getVocabularyById_success() {
    UUID vocabularyId = given()
        .when()
        .contentType(ContentType.JSON)
        .body(new Vocabulary(WORD, DESCRIPTION, DATE))
        .post("/vocabulary")
        .then()
        .statusCode(200)
        .extract().as(Vocabulary.class).getId();

    Vocabulary vocabulary = given()
        .when()
        .get("/vocabulary/" + vocabularyId)
        .then()
        .statusCode(200)
        .extract().as(Vocabulary.class);

    assertEquals(WORD, vocabulary.getWord());
    assertEquals(DESCRIPTION, vocabulary.getDescription());
    assertEquals(DATE, vocabulary.getCreationDate());
    assertNotNull(vocabulary.getId());
  }

  @Test
  void getVocabularyById_notFound() {
    given()
        .when()
        .get("/vocabulary/" + UUID.randomUUID())
        .then()
        .statusCode(404);
  }
}
