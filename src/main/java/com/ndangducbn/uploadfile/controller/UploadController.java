package com.ndangducbn.uploadfile.controller;

import com.ndangducbn.uploadfile.entity.Person;
import com.ndangducbn.uploadfile.exception.StorageException;
import com.ndangducbn.uploadfile.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class UploadController {
    private final StorageService storageService;

    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("person", new Person());
        return "upload";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/failure")
    public String failure() {
        return "failure";
    }

    @PostMapping(value = "/", consumes = { "multipart/form-data" })
    public String upload(@Valid @ModelAttribute Person person, BindingResult result, Model model) {
        if (Objects.requireNonNull(person.getPhoto().getOriginalFilename()).isEmpty()) {
            result.addError(new FieldError("person", "photo", "Photo is required"));
        }
        if (result.hasErrors()) {
            return "upload";
        }

        storageService.uploadFile(person.getPhoto());
        model.addAttribute("name", person.getName());
        model.addAttribute("photo", person.getPhoto().getOriginalFilename());
        return "success";
    }
}