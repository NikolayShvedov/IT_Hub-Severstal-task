package ru.severstal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.severstal.entity.Note;
import ru.severstal.entity.User;
import ru.severstal.repository.NoteRepo;

import javax.validation.Valid;
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
            @Valid Note note,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        note.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("note", note);
        }
        else {
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
            model.addAttribute("note", null);
            noteRepo.save(note);
        }

        Iterable<Note> notes = noteRepo.findAllByUserId(user.getId());

        model.addAttribute("notes", notes);

        return "main";
    }
}
