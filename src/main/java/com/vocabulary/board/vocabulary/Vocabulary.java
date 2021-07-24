package com.vocabulary.board.vocabulary;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Vocabulary {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String word;

    @Column
    private String description;

    public Vocabulary() {
    }

    public Vocabulary(String id, String word, String description) {
        this.id = id;
        this.word = word;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
