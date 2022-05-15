package ru.severstal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.severstal.entity.Note;

import java.util.List;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {

    @Query(value="SELECT note FROM Note note " +
            " WHERE note.tag = :tag AND note.author.id = :userId")
    List<Note> findByTagAndUserId(String tag, Long userId);

    @Query(value="SELECT note FROM Note note " +
            " WHERE note.author.id = :userId")
    List<Note> findAllByUserId(Long userId);
}
