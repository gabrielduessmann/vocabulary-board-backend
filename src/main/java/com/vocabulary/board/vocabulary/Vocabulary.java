package com.vocabulary.board.vocabulary;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vocabulary.board.comment.Comment;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
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

    private String word;

    private String description;

    private Date creationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private com.vocabulary.board.column.Column column;

    @JsonIgnore
    @OneToMany(mappedBy = "vocabulary")
    private List<Comment> comment;  

}
