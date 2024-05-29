package com.example.todolist.Service;

import com.example.todolist.model.Notes;
import com.example.todolist.model.People;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.todolist.Repository.noteRepository;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class noteServices {

    @Autowired
    noteRepository NoteRepository;

    public void updateNotes(Notes notes) {
        NoteRepository.updateNotes(notes.getId(),notes.getNotes(),notes.getDescription(),LocalDateTime.now());
    }

    public Notes notesId(int id) {
        return NoteRepository.findById(id).orElse(null);
    }


    public  void addNote(Notes notes, Optional<People> people) {
        NoteRepository.addNotes(notes.getNotes(),notes.getDescription(),LocalDateTime.now(),people.get().getId());
    }

    public void deleteNote(int id) {

        NoteRepository.deleteById(id);
    }
}
