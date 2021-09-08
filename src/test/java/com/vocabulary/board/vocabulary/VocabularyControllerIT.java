package com.vocabulary.board.vocabulary;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.vocabulary.board.builders.ColumnBuilder;
import com.vocabulary.board.builders.VocabularyBuilder;
import com.vocabulary.board.column.Column;
import com.vocabulary.board.column.ColumnRepository;
import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.utils.JpaUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

  @Autowired
  private VocabularyRepository vocabularyRepository;

  @Autowired
  private ColumnRepository columnRepository;

  @Test
  void getVocabularyById_success() {
    Vocabulary vocabulary = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withWord(WORD)
            .withDescription(DESCRIPTION).withDate(DATE).build());

    Vocabulary vocabularyFound = given()
            .when()
            .get("/vocabulary/" + vocabulary.getId())
            .then()
            .statusCode(200)
            .extract().as(Vocabulary.class);

    assertEquals(vocabulary, vocabularyFound);
  }

  @Test
  void getVocabularyById_notFound() {
    given()
            .when()
            .get("/vocabulary/" + UUID.randomUUID())
            .then()
            .statusCode(404);
  }

  @Test
  void getAllVocabularies() {
    Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withWord(WORD)
            .withDescription(DESCRIPTION).withDate(DATE).build());
    Vocabulary vocabulary2 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withWord(WORD)
            .withDescription(DESCRIPTION).withDate(DATE).build());
    Vocabulary vocabulary3 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withWord(WORD)
            .withDescription(DESCRIPTION).withDate(DATE).build());

    List<Vocabulary> vocabulariesFound = Arrays.asList(
            given()
            .get("/vocabularies")
            .then()
            .statusCode(200)
            .extract()
            .as(Vocabulary[].class)
    );

    assertThat(vocabulariesFound).hasSize(3).isEqualTo(List.of(vocabulary1, vocabulary2, vocabulary3));
  }

  @Test
  void getAllVocabularies_empty() {
    List<Vocabulary> vocabulariesFound = Arrays.asList(
            given()
            .get("/vocabularies")
            .then()
            .statusCode(200)
            .extract()
            .as(Vocabulary[].class)
    );

    assertThat(vocabulariesFound).isEmpty();
  }

  @Test
  void findAllVocabulariesByColumnId() {
    Column column1 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().build());
    Column column2 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().build());
    Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withColumn(column1).build());
    Vocabulary vocabulary2 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withColumn(column1).build());
    Vocabulary vocabulary3 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withColumn(column2).build());

    List<Vocabulary> vocabulariesFound = Arrays.asList(
            given()
                    .when()
                    .pathParam("columnId", column1.getId())
                    .get("/vocabularies/column/{columnId}")
                    .then()
                    .statusCode(200)
                    .extract()
                    .as(Vocabulary[].class)
    );

    assertThat(vocabulariesFound).hasSize(2).isEqualTo(List.of(vocabulary1, vocabulary2));
  }

  @Test
  void saveNewVocabulary() {
    Column column = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.POOL).build());
    Vocabulary vocabulary = VocabularyBuilder.oneVocabulary().withId(null).withWord(WORD)
            .withDescription(DESCRIPTION).withDate(DATE).build();

    Vocabulary vocabularySaved = given()
        .when()
        .contentType(ContentType.JSON)
        .body(vocabulary)
        .post("/vocabulary")
        .then()
        .statusCode(200)
        .extract().as(Vocabulary.class);

    assertEquals(WORD, vocabularySaved.getWord());
    assertEquals(DESCRIPTION, vocabularySaved.getDescription());
    assertEquals(DATE, vocabularySaved.getCreationDate());
    assertEquals(column, vocabularySaved.getColumn());
    assertNotNull(vocabularySaved.getId());
  }

  @Test
  void saveNewVocabulary_exception_noColumnInPool() {
    Vocabulary vocabulary = VocabularyBuilder.oneVocabulary().withId(null).withWord(WORD)
            .withDescription(DESCRIPTION).withDate(DATE).build();

    given()
      .when()
      .contentType(ContentType.JSON)
      .body(vocabulary)
      .post("/vocabulary")
      .then()
      .statusCode(500);
  }

  @Test
  void saveNewVocabulary_exception_multipleColumnsInPool() {
    JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.POOL).build());
    JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.POOL).build());
    Vocabulary vocabulary = VocabularyBuilder.oneVocabulary().withId(null).withWord(WORD)
            .withDescription(DESCRIPTION).withDate(DATE).build();

    given()
      .when()
      .contentType(ContentType.JSON)
      .body(vocabulary)
      .post("/vocabulary")
      .then()
      .statusCode(500);
  }

  @Test
  void updateVocabulary() {
    Vocabulary vocabularyPersisted = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withWord(WORD)
            .withDescription(DESCRIPTION).withDate(DATE).build());
    final String newWord = "amazing";
    final String newDescription = "the view is amazing";
    Vocabulary vocabularyToUpdate = VocabularyBuilder.oneVocabulary().withId(vocabularyPersisted.getId())
            .withWord(newWord).withDescription(newDescription).build();

    Vocabulary vocabularyUpdated = given()
            .when()
            .contentType(ContentType.JSON)
            .body(vocabularyToUpdate)
            .post("/vocabulary")
            .then()
            .statusCode(200)
            .extract()
            .as(Vocabulary.class);

    assertEquals(vocabularyPersisted.getId(), vocabularyUpdated.getId());
    assertEquals(newWord, vocabularyUpdated.getWord());
    assertEquals(newDescription, vocabularyUpdated.getDescription());
    assertEquals(DATE, vocabularyUpdated.getCreationDate());
  }

  @Test
  void moveVocabularyToNextColumn() {
    Column column1 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).withSprintOrder(1).build());
    Column column2 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.PAUSED).withSprintOrder(2).build());
    Column column3 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).withSprintOrder(3).build());
    Vocabulary vocabulary = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withColumn(column1).build());

    Vocabulary vocabularyMoved = given()
            .when()
            .contentType(ContentType.JSON)
            .body(vocabulary)
            .put("/vocabulary/moveToNextColumn")
            .then()
            .statusCode(200)
            .extract()
            .as(Vocabulary.class);

    assertEquals(column2, vocabularyMoved.getColumn());
  }

}
