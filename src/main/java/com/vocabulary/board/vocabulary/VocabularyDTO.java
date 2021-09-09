package com.vocabulary.board.vocabulary;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Data
public class VocabularyDTO {
    private UUID id;
    private String word;
    private String description;
    private Date creationDate;

    public Vocabulary toEntity() {
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setWord(word);
        vocabulary.setDescription(description);
        vocabulary.setCreationDate(creationDate);
        return vocabulary;
    }
}
