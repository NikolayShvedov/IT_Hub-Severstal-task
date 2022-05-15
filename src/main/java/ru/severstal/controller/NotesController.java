package ru.severstal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.severstal.entity.Note;
import ru.severstal.entity.User;
import ru.severstal.repository.NoteRepo;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class NotesController {

    @Autowired
    private NoteRepo noteRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String getStartPage(Map<String, Object> model) {
        return "home";
    }

    @GetMapping("/main")
    public String getAllNotes(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String filterTag,
            Map<String, Object> model
    ){
        Iterable<Note> notes;

        if (filterTag != null && !filterTag.isEmpty()) {
            notes = noteRepo.findByTagAndUserId(filterTag, user.getId());
        } else {
            notes = noteRepo.findAllByUserId(user.getId());
        }

        model.put("notes", notes);
        model.put("filterTag", filterTag);

        return "main";
    }

    @PostMapping("/main")
    public String addNote(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Note note = new Note(text, tag, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            note.setFilename(resultFilename);
        }

        noteRepo.save(note);

        Iterable<Note> notes = noteRepo.findAll();

        model.put("notes", notes);

        return "main";
    }
}
