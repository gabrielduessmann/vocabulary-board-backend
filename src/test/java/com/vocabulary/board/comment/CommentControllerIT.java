package com.vocabulary.board.comment;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.vocabulary.board.vocabulary.Vocabulary;
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

public class CommentControllerIT {

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void baseSetup() {
        RestAssured.baseURI = "http://localhost:" + serverPort;
    }

    private static final String COMMENT = "The house is beautiful";
    private static final Date DATE = new Date();

    @Test
    void saveComment_success() {
        Comment comment = given()
                .when()
                .contentType(ContentType.JSON)
                .body(new Comment(COMMENT, DATE))
                .post("/comment")
                .then()
                .statusCode(200)
                .extract().as(Comment.class);

        assertEquals(COMMENT, comment.getComment());
        assertEquals(DATE, comment.getCommentedDate());
    }
}
