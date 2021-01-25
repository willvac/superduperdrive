package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {

    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userId;

    public Credential(CredentialForm credentialForm) {
        this.url = credentialForm.getUrl();
        this.username = credentialForm.getUsername();
        this.userId = credentialForm.getUserId();
        this.credentialId = null;
        this.password = null;
        this.key = null;
    }

    public Credential(CredentialForm credentialForm, String password, String key) {
        this.url = credentialForm.getUrl();
        this.username = credentialForm.getUsername();
        this.userId = credentialForm.getUserId();
        this.password = password;
        this.key = key;
        this.credentialId = credentialForm.getCredentialId();
    }

    public Credential() {
        this.url = null;
        this.username = null;
        this.userId = null;
        this.credentialId = null;
        this.password = null;
        this.key = null;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
