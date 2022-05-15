package ru.severstal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.severstal.entity.Note;
import ru.severstal.repository.NoteRepo;

import java.util.Map;

@Controller
public class NotesController {

    @Autowired
    private NoteRepo noteRepo;

    @GetMapping("/start_page")
    public String getStartPage(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "start_page";
    }

    @GetMapping
    public String getAllNotes(Map<String, Object> model) {
        Iterable<Note> notes = noteRepo.findAll();

        model.put("notes", notes);

        return "main";
    }

    @PostMapping
    public String createNote(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Note note = new Note(text, tag);

        noteRepo.save(note);

        Iterable<Note> notes = noteRepo.findAll();

        model.put("notes", notes);

        return "main";
    }

    @PostMapping("filter")
    public String filterNotes(@RequestParam String filterTag, Map<String, Object> model) {
        Iterable<Note> notes;

        if (filterTag != null && !filterTag.isEmpty()) {
            notes = noteRepo.findByTag(filterTag);
        } else {
            notes = noteRepo.findAll();
        }

        model.put("notes", notes);

        return "main";
    }
}
