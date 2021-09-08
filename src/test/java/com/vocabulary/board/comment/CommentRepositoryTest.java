package com.vocabulary.board.comment;

import com.vocabulary.board.builders.CommentBuilder;
import com.vocabulary.board.builders.VocabularyBuilder;
import com.vocabulary.board.utils.JpaUtil;
import com.vocabulary.board.vocabulary.Vocabulary;
import com.vocabulary.board.vocabulary.VocabularyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VocabularyRepository vocabularyRepository;

    @Test
    public void findAllByVocabularyId() {
        Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().build());
        Vocabulary vocabulary2 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().build());
        Comment comment1 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary1).build());
        Comment comment2 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary2).build());
        Comment comment3 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary1).build());
        Comment comment4 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary2).build());
        Comment comment5 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary2).build());

        List<Comment> commentsFound = commentRepository.findAllByVocabularyId(vocabulary2.getId());

        assertThat(commentsFound).hasSize(3).isEqualTo(List.of(comment2, comment4, comment5));
    }

    @Test
    public void findAllByVocabularyId_orderByDateDesc() {
        Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().build());
        Vocabulary vocabulary2 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().build());
        Comment comment1 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary1).withCommentedDate(new Date()).build());
        Comment comment2 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary2).withCommentedDate(new Date()).build());
        Comment comment3 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary1).withCommentedDate(new Date()).build());
        Comment comment4 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary2).withCommentedDate(new Date()).build());
        Comment comment5 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary2).withCommentedDate(new Date()).build());

        List<Comment> commentsFound = commentRepository.findAllByVocabularyId(vocabulary2.getId());

        assertThat(commentsFound).hasSize(3).isEqualTo(List.of(comment5, comment4, comment2));
    }

    @Test
    public void findAllByVocabularyId_empty() {
        Vocabulary vocabulary1 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().build());
        Vocabulary vocabulary2 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().build());
        Vocabulary vocabulary3 = (Vocabulary) JpaUtil.save(vocabularyRepository, VocabularyBuilder.oneVocabulary().build());
        Comment comment1 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary1).build());
        Comment comment2 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary2).build());
        Comment comment3 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary1).build());
        Comment comment4 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary2).build());
        Comment comment5 = (Comment) JpaUtil.save(commentRepository, CommentBuilder.oneComment().withVocabulary(vocabulary2).build());

        List<Comment> commentsFound = commentRepository.findAllByVocabularyId(vocabulary3.getId());

        assertThat(commentsFound).isEmpty();
    }
}
