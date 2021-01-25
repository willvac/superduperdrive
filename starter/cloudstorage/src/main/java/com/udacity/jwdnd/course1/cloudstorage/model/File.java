package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class File {

    public File() {

        fileId = null;
        fileName = null;
        contentType = null;
        fileSize =null;
        userId = null;
        fileData = null;
    }

    public File(MultipartFile file, Integer userId) {
        fileName = file.getOriginalFilename();
        contentType = file.getContentType();
        fileSize = file.getSize() + "";
        this.userId = userId;


        try {
            fileData =  file.getInputStream();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        this.fileId = null;

    }

    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private InputStream fileData;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public InputStream getFileData() {
        return fileData;
    }

    public void setFileData(InputStream fileData) {
        this.fileData = fileData;
    }
}
