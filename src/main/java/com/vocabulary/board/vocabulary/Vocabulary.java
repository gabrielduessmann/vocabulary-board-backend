package com.vocabulary.board.vocabulary;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vocabulary.board.comment.Comment;

import javax.persistence.*;

@Entity
public class Vocabulary {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String word;

    private String description;

    @ManyToOne
    private com.vocabulary.board.column.Column column;

    @JsonIgnore
    @OneToMany(mappedBy = "vocabulary")
    private List<Comment> comment;

    public Vocabulary() {
    }

    public Vocabulary(UUID id, String word, String description) {
        this.id = id;
        this.word = word;
        this.description = description;
    }

    public Vocabulary(String word, String description) {
        this.word = word;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public com.vocabulary.board.column.Column getColumn() {
        return column;
    }

    public void setColumn(com.vocabulary.board.column.Column column) {
        this.column = column;
    }
}
