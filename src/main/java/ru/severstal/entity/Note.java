package ru.severstal.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Note {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String text;
    private String tag;

    public Note(String text, String tag) {
        this.text = text;
        this.tag = tag;
    }
}