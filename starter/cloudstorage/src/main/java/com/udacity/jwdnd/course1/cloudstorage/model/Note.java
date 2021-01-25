package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

    public Note(NoteForm noteForm) {
        this.noteId = noteForm.getNoteId();
        this.noteTitle = noteForm.getNoteTitle();
        this.noteDescription = noteForm.getNoteDescription();
        this.userId = noteForm.getUserId();
    }

    public Note() {
        this.noteId = null;
        this.noteTitle = null;
        this.noteDescription = null;
        this.userId = null;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
}
