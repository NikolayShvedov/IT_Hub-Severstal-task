package ru.severstal.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "notes")
@NoArgsConstructor
@Setter
@Getter
public class Note {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please fill the note")
    @Length(max = 2048, message = "Note too long (more than 2kB)")
    private String text;

    @NotBlank(message = "Please fill the note")
    @Length(max = 255, message = "Tag too long (more than 255 symbols)")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public Note(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }
}
