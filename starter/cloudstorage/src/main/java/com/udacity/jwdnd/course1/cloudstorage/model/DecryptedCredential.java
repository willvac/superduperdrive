package com.udacity.jwdnd.course1.cloudstorage.model;

public class DecryptedCredential {
    private Integer credentialId;
    private String url;
    private String username;
    private String password;
    private String decryptedPassword;

    public DecryptedCredential(Credential credential, String password) {
        credentialId = credential.getCredentialId();
        url = credential.getUrl();
        username = credential.getUsername();
        this.decryptedPassword = password;
        this.password = credential.getPassword();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDecryptedPassword() {
        return decryptedPassword;
    }
}
