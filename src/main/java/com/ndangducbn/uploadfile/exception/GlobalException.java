package com.ndangducbn.uploadfile.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler({StorageException.class, Exception.class})
    public String handleStorageFileNotFound(StorageException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "failure";
    }
}
