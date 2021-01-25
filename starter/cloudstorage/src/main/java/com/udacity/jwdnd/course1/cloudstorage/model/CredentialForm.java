package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.stereotype.Component;

@Component
public class CredentialForm {

    private Integer userId;
    private String url;
    private String username;
    private String password;
    private Integer credentialId;


    public CredentialForm(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
        userId = null;
        credentialId = null;
    }

    public CredentialForm() {
        this.username = null;
        this.password = null;
        this.url = null;
        this.userId = null;
        credentialId = null;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
