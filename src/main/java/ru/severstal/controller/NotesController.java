package ru.severstal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.severstal.entity.Note;
import ru.severstal.entity.User;
import ru.severstal.repository.NoteRepo;

import java.util.Map;

@Controller
public class NotesController {

    @Autowired
    private NoteRepo noteRepo;

    @GetMapping("/")
    public String getStartPage(Map<String, Object> model) {
        return "home";
    }

    @GetMapping("/main")
    public String getAllNotes(
            @RequestParam(required = false) String filterTag,
            Map<String, Object> model
    ){
        Iterable<Note> notes = noteRepo.findAll();

        if (filterTag != null && !filterTag.isEmpty()) {
            notes = noteRepo.findByTag(filterTag);
        } else {
            notes = noteRepo.findAll();
        }

        model.put("notes", notes);
        model.put("filterTag", filterTag);

        return "main";
    }

    @PostMapping("/main")
    public String addNote(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model
    ) {
        Note note = new Note(text, tag, user);

        noteRepo.save(note);

        Iterable<Note> notes = noteRepo.findAll();

        model.put("notes", notes);

        return "main";
    }
}
