package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (userid, filename, contenttype, filesize, filedata) VALUES (#{userId}, #{fileName}, #{contentType}, #{fileSize}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int addFile(File file);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userid = #{userId}")
    public File getFile(int fileId, int userId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    public List<File> getAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    public File getFileByName(String fileName, int userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userId = #{userId}")
    int deleteFile(int fileId, int userId);

}
