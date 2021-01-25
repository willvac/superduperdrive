package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ResultController {

    // returns outcome
    // can be coded to give special outcome message;
    @GetMapping("/result/{outcome}")
    public String getResult(Model model, @PathVariable("outcome") String outcome) {
        switch(outcome) {
            case "fileAlreadyExists":
                outcome = "Operation not successful: A file with the same name already exists!";
                break;
            case "invalidDeleteRequest":
                outcome = "Operation not successful: An item with that ID does not exist!";
                break;
            case "success":
            case "failure":
                break;
            default:
                outcome = "failure";
        }

        model.addAttribute("outcome",outcome);
        return "result";
    }

}
