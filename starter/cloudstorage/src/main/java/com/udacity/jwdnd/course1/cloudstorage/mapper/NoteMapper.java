package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid)" +
            " VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    public void addNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    public List<Note> getAllNotes(Integer userId);

    @Update("UPDATE NOTES SET " +
                "notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}"
    )
    public void updateNote(NoteForm noteForm);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId} AND userid = #{userId}")
    public int deleteNote(int noteId, int userId);

    @Select("SELECT userid FROM NOTES where noteid = #{noteId}")
    int getUserId(int noteId);
}
