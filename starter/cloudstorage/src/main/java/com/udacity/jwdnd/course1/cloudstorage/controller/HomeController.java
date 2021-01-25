package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController {
    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;
    private FileService fileService;

    public HomeController(UserService userService, NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }



    @GetMapping("/home")
    public String getHome(Model model, @ModelAttribute("noteForm") NoteForm noteForm, Authentication authentication, @ModelAttribute("credentialForm") CredentialForm credentialForm) {

        // Get current user from Authentication object
        String username = authentication.getName();
        User user = userService.getUser(username);

        // Add current user's notes, credentials, and files to the model
        model.addAttribute("notes", noteService.getAllNotes(user.getUserId()));
        model.addAttribute("credentials", credentialService.getAllCredentials(user.getUserId()));
        model.addAttribute("files", fileService.getAllFiles(user.getUserId()));

        return "home";
    }

    // Call handlers for file upload
    @PostMapping("/home/file/upload")
    public String uploadFile(@RequestParam ("fileUpload") MultipartFile file, Authentication authentication) {

        // Get current user from Authentication object
        String username = authentication.getName();
        User user = userService.getUser(username);

        // First checks if the current user has already uploaded a file of the same name
        // Checks by both userId and file name as files from different users can share the same file name
        try {
            // If file does not exist, file will be null. Add file to the user's file system
            if(fileService.getFile(file.getOriginalFilename(), user.getUserId()) == null) {
                fileService.addFile(file, user.getUserId());
            } else {
                return "redirect:/result/fileAlreadyExists";
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Other unaccounted failures
            return "redirect:/result/failure";
        }

        return "redirect:/home";
    }

    // Call handlers to add credentials
    @PostMapping("/home/credential/add")
    public String addCredential(@ModelAttribute("credentialForm") CredentialForm credentialForm, Authentication authentication) {

        // Get current user from Authentication object
        String username = authentication.getName();
        User user = userService.getUser(username);

        // Fill in the userId from the submitted credential form
        credentialForm.setUserId(user.getUserId());

        // Pass the form to credentialService to create the credential
        credentialService.createCredential(credentialForm);

        return "redirect:/result/success";
    }

    @PostMapping("/home/note/add")
    public String addNote(@ModelAttribute("noteForm") NoteForm noteForm, Authentication authentication) {

        // Get current user from Authentication object
        String username = authentication.getName();
        User user = userService.getUser(username);

        // Fill in the userId in the noteForm
        noteForm.setUserId(user.getUserId());

        // Pass to noteService to add note
        noteService.addNote(noteForm);

        return "redirect:/result/success";
    }

    // Call handlers to delete file, note, or credential
    @GetMapping("/home/{service}/delete/{IdAsString}")
    public String delete(Model model, @PathVariable("service") String serviceType, @PathVariable("IdAsString") String IdAsString, Authentication authentication) {

        // ID of item to delete
        int serviceId;

        // vet the id passed in
        // if link is sent via delete button should be a valid integer
        // handle values that may not be integers
        try {
            serviceId = Integer.parseInt(IdAsString);
        } catch (java.lang.NumberFormatException e) {
            return "redirect:/result/invalidDeleteRequest";
        }

        // Get current userId from Authentication object
        String username = authentication.getName();
        User user = userService.getUser(username);
        int userId = user.getUserId();

        // Store result of delete operation from service.
        int numberOfItemsDeleted = 0;

        // Have to pass both the userId (which is provided by the controller) and the serviceId (Id of item to be deleted)
        // This prevents people who do not own the item at a particular ID from deleting it
        switch(serviceType) {
            case "note":
                numberOfItemsDeleted = noteService.deleteNote(serviceId, userId);
                break;
            case "file":
                numberOfItemsDeleted = fileService.deleteFile(serviceId, userId);
                break;
            case "credential":
                numberOfItemsDeleted = credentialService.deleteCredential(serviceId, userId);
                break;
            default: // If service does not exist
                return "redirect:/result/failure";
        }


        return numberOfItemsDeleted > 0 ? "redirect:/result/success":"redirect:/result/failure";
    }

    // Handler to return the file as a download
    @GetMapping("/home/file/view/{fileId}")
    public String view(@PathVariable("fileId") Integer fileId, HttpServletResponse response, Authentication authentication) {

        // Get current user's ID
        String username = authentication.getName();
        User user = userService.getUser(username);
        int userId = user.getUserId();

        // Retrieve fileId from UserId
        // Both fileId and userId have to a file in order for it to be returned
        // This prevents inappropriate access to files that a user may not be the owner of
        File returnFile = fileService.getFile(fileId,userId);
        if (returnFile != null) {
            try {
                response.setContentType(returnFile.getContentType());
                response.getOutputStream().write(returnFile.getFileData().readAllBytes());

            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/result/failure";
            }
            return null; // File is obtained and written to response; cannot return an HTML and file, so return null;
        } else {
            return "redirect:/result/failure"; // File not found;
        }
    }
}
