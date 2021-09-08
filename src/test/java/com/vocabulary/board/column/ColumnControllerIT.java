package com.vocabulary.board.column;

import com.vocabulary.board.builders.ColumnBuilder;
import com.vocabulary.board.builders.VocabularyBuilder;
import com.vocabulary.board.column.enums.StatusEnum;
import com.vocabulary.board.utils.JpaUtil;
import com.vocabulary.board.vocabulary.VocabularyRepository;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import io.restassured.http.ContentType;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles(value = "test")
public class ColumnControllerIT {

    private static final String TITLE = "This is the title";
    private static final Date NEXT_UPDATE = new Date();
    private static final StatusEnum  STATUS = StatusEnum.POOL;
    private static final Integer SPRINT_ORDER = 0;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private ColumnRepository columnRepository;

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @BeforeEach
    void baseSetup() {
        RestAssured.baseURI = "http://localhost:" + serverPort;
    }


    @Test
    void findById() {
        Column column = (Column) JpaUtil.save(columnRepository, new Column(UUID.randomUUID(), TITLE, NEXT_UPDATE, STATUS, SPRINT_ORDER, null));

        Column columnFound = given()
                .when()
                .get("column/"+column.getId())
                .then()
                .statusCode(200)
                .extract()
                .as(Column.class);

        assertEquals(column, columnFound);
    }

    @Test
    void findById_notFound() {
        given()
                .get("column/"+UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    void findAll() {
        Column column1 = (Column) JpaUtil.save(columnRepository, new Column());
        Column column2 = (Column) JpaUtil.save(columnRepository, new Column());
        Column column3 = (Column) JpaUtil.save(columnRepository, new Column());

        List<Column> columnsFound = Arrays.asList(
                given()
                .when()
                .get("columns/")
                .then()
                .statusCode(200)
                .extract()
                .as(Column[].class)
        );

        assertThat(columnsFound).hasSize(3).isEqualTo(List.of(column1, column2, column3));
    }

    @Test
    void findAllNotInBoard() {
        List<Column> columns = persistColumns();

        List<Column> columnsFound = Arrays.asList(
                given()
                        .when()
                        .get("columns/not-in-board")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Column[].class)
        );

        assertThat(columnsFound).hasSize(2).isEqualTo(List.of(columns.get(0), columns.get(1)));
    }

    @Test
    void findAllInBoard() {
        List<Column> columns = persistColumns();

        List<Column> columnsFound = Arrays.asList(
                given()
                        .when()
                        .get("columns/in-board")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Column[].class)
        );

        assertThat(columnsFound).hasSize(6).isEqualTo(List.of(columns.get(2), columns.get(3), columns.get(4), columns.get(5),
                columns.get(6), columns.get(7)));
    }

    @Test
    void findAllInProgress() {
        List<Column> columns = persistColumns();

        List<Column> columnsFound = Arrays.asList(
                given()
                        .get("columns/in-progress")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Column[].class)
        );

        assertThat(columnsFound).hasSize(3).isEqualTo(List.of(columns.get(2), columns.get(4), columns.get(6)));
    }

    @Test
    void findAllInProgressToPractice() {
        List<Column> columns = persistColumns();

        List<Column> columnsFound = Arrays.asList(
                given()
                        .get("columns/in-progress-practice")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Column[].class)
        );

        assertThat(columnsFound).hasSize(2).isEqualTo(List.of(columns.get(2), columns.get(4)));
    }


    @Test
    void saveColumn_success() {
        Column column = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new Column(UUID.randomUUID(), TITLE, NEXT_UPDATE, STATUS, SPRINT_ORDER, null))
                .post("/column")
                .then()
                .statusCode(200)
                .extract().as(Column.class);

        assertNotNull(column.getId());
        assertEquals(TITLE, column.getTitle());
        assertEquals(NEXT_UPDATE, column.getNextUpdate());
        assertEquals(STATUS, column.getStatus());
        assertEquals(SPRINT_ORDER, column.getSprintOrder());
        assertNull(column.getVocabularies());
    }

    private List<Column> persistColumns() {
        Column column0 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.POOL).build());
        Column column1 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.BACKLOG).build());
        Column column2 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build());
        Column column3 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.PAUSED).build());
        Column column4 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build());
        Column column5 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.PAUSED).build());
        Column column6 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.IN_PROGRESS).build());
        Column column7 = (Column) JpaUtil.save(columnRepository, ColumnBuilder.oneColumn().withStatus(StatusEnum.DONE).build());

        JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withColumn(column2).build());
        JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withColumn(column2).build());
        JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withColumn(column4).build());

        return List.of(column0, column1, column2, column3, column4, column5, column6, column7);
    }
}
