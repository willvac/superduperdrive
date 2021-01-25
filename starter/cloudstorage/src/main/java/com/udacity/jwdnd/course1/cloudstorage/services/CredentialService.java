package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.DecryptedCredential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    EncryptionService encryptionService;
    CredentialMapper credentialMapper;

    public CredentialService(EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }


    public int createCredential(CredentialForm credentialForm) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String key = Base64.getEncoder().encodeToString(salt);

        String inputPassword = credentialForm.getPassword();
        String encryptedPassword = encryptionService.encryptValue(inputPassword, key);

        // Will check if form has a credentialId associated with it
        if(credentialForm.getCredentialId() == null) {
            // create new credential
            return credentialMapper.addCredential(new Credential(credentialForm, encryptedPassword, key));
        } else {
            // update existing credential
            credentialMapper.updateCredential(new Credential(credentialForm, encryptedPassword, key));
            return credentialForm.getCredentialId();
        }

    }

    public int deleteCredential(int credentialId, int userId) {
        return credentialMapper.deleteCredential(credentialId, userId);
    }

    public Integer getOwnerOf(Integer credentialId) {
        return credentialMapper.getUserIdOfCredential(credentialId);
    }

    public List<DecryptedCredential> getAllCredentials(Integer userId) {
        // Store decrypted credentials to return
        List<DecryptedCredential> decryptedCredentials = new ArrayList<>();

        // Credentials that have not been decrypted
        List<Credential> credentials = credentialMapper.getAllCredentials(userId);


        // Decrypt all of the password from credentials list
        for(Credential credential : credentials) {
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            decryptedCredentials.add(new DecryptedCredential(credential, decryptedPassword));
        }

        return decryptedCredentials;
    }

}
