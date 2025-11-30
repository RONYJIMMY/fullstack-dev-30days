package com.dream.greetingservice.controller;

import com.dream.greetingservice.model.Note;
import com.dream.greetingservice.repository.NoteRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:5173")
public class NoteController {

    private final NoteRepository repo;

    public NoteController(NoteRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Note create(@Valid @RequestBody Note note) {
        return repo.save(note);
    }

    @GetMapping
    public List<Note> getAll() {
        return repo.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @Valid @RequestBody Note updatedNote) {
        Note note = repo.findById(id).orElseThrow(()-> new RuntimeException("Note not found"));

        note.setTitle(updatedNote.getTitle());
        note.setContent(updatedNote.getContent());

        Note savedNote = repo.save(note);
    
        return ResponseEntity.ok(savedNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public Page<Note> searchNotes(@RequestParam String keyword,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {
        Pageable peageable = PageRequest.of(page, size);
    if(keyword == null || keyword.isEmpty()){
        return repo.findAll(peageable);
    }else {
        return repo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, peageable);
    }
    }
}
