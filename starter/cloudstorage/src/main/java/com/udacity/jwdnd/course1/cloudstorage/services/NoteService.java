package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper notemapper) {
        this.noteMapper = notemapper;
    }

    public void addNote(NoteForm noteForm) {

        if(noteForm.getNoteId() == null) { // New note does not have noteId assigned yet
            noteMapper.addNote(new Note(noteForm));
        }
        else{ //Note has a noteId = Updating a note
            noteMapper.updateNote(noteForm);
        }
    }

    public List<Note> getAllNotes(Integer userId) {
        return noteMapper.getAllNotes(userId);
    }

    /**
     * deleteNote is a method that deletes the note from Notes with noteid = noteId iff userid = userId
     * This prevents unauthorized delete requests through HTTP Get Spam Request to the note delete controller
     * @param noteId = Id of the note to delete.
     * @param userId = Id of the user requesting the delete
     * @return + = success or number of notes deleted; - = failure
     *
     */
    public int deleteNote(int noteId, int userId) {
        return noteMapper.deleteNote(noteId, userId);
    }
}
