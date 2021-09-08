package com.vocabulary.board.comment;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.vocabulary.board.builders.CommentBuilder;
import com.vocabulary.board.builders.VocabularyBuilder;
import com.vocabulary.board.utils.JpaUtil;
import com.vocabulary.board.vocabulary.Vocabulary;
import com.vocabulary.board.vocabulary.VocabularyRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.inject.Inject;

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

    @Inject
    private VocabularyRepository vocabularyRepository;

    @Inject
    private CommentRepository commentRepository;

    @Test
    void saveComment_success() {
        Vocabulary vocabulary = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withWord("house").build());
        Comment commentExpected = CommentBuilder.oneComment().withComment("The house is beautiful").withCommentedDate(new Date()).withVocabulary(vocabulary).build();

        Comment commentSaved = given()
                .when()
                .contentType(ContentType.JSON)
                .body(commentExpected)
                .post("/comment")
                .then()
                .statusCode(200)
                .extract()
                .as(Comment.class);

        assertEquals(commentExpected.getComment(), commentSaved.getComment());
        assertEquals(commentExpected.getCommentedDate(), commentSaved.getCommentedDate());
        assertEquals(commentExpected.getVocabulary(), commentSaved.getVocabulary());
    }

    @Test
    void findAllVocabularyComments() {
        Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withWord("car")
                .build());
        Vocabulary vocabulary2 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withWord("job")
                .build());
        Comment comment1 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withComment("I bought a red car")
                .withCommentedDate(new Date()).withVocabulary(vocabulary1).build());
        Comment comment2 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withComment("My friend has a better car")
                .withCommentedDate(new Date()).withVocabulary(vocabulary1).build());
        Comment comment3 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withComment("I got a new job to work in Berlin")
                .withCommentedDate(new Date()).withVocabulary(vocabulary2).build());

        List<Comment> commentsFound = Arrays.asList(
                given()
                .when()
                .contentType(ContentType.JSON)
                .pathParam("vocabularyId", vocabulary1.getId())
                .get("/vocabulary/{vocabularyId}/comments")
                .then()
                .statusCode(200)
                .extract()
                .as(Comment[].class)
            );

        assertThat(commentsFound).hasSize(2).isEqualTo(List.of(comment2, comment1));
    }

    @Test
    void findAllVocabularyComments_empty() {
        Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withWord("car")
                .build());
        Vocabulary vocabulary2 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().withWord("job")
                .build());
        Comment comment1 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withComment("I bought a red car")
                .withCommentedDate(new Date()).withVocabulary(vocabulary1).build());
        Comment comment2 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withComment("My friend has a better car")
                .withCommentedDate(new Date()).withVocabulary(vocabulary1).build());
        Comment comment3 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withComment("I got a new job to work in Berlin")
                .withCommentedDate(new Date()).withVocabulary(vocabulary1).build());

        List<Comment> commentsFound = Arrays.asList(
                given()
                .when()
                .contentType(ContentType.JSON)
                .pathParam("vocabularyId", vocabulary2.getId())
                .get("/vocabulary/{vocabularyId}/comments")
                .then()
                .statusCode(200)
                .extract()
                .as(Comment[].class)
            );

        assertThat(commentsFound).isEmpty();
    }
}
