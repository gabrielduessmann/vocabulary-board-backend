package com.vocabulary.board.vocabulary;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vocabulary.board.comment.Comment;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vocabulary {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String word;

    private String description;

    @Column(nullable = false)
    private Date creationDate = new Date();

    @ManyToOne
    private com.vocabulary.board.column.Column column;

    @JsonIgnore
    @OneToMany(mappedBy = "vocabulary")
    private List<Comment> comment;

    public Vocabulary(UUID id, String word, String description, Date creationDate) {
        this.id = id;
        this.word = word;
        this.description = description;
        this.creationDate = creationDate;
    }

    public Vocabulary(String word, String description, Date creationDate) {
        this.word = word;
        this.description = description;
        this.creationDate = creationDate;
    }

}
