package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int deleteFile(int fileId, int userId) {
        return fileMapper.deleteFile(fileId, userId);
    }

    public void addFile(MultipartFile file, Integer userId) throws IOException {
        File fileToAdd = new File(file,userId);
        fileMapper.addFile(fileToAdd);
    }

    public List<File> getAllFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }

    // Get file by fileId
    public File getFile(int fileId, int userId) {
        return fileMapper.getFile(fileId, userId);
    }

    // Get file by fileName
    public File getFile(String fileName, int userId) {
        return fileMapper.getFileByName(fileName, userId);
    }

}
