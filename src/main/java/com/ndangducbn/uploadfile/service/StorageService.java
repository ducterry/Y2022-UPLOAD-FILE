package com.ndangducbn.uploadfile.service;

import com.ndangducbn.uploadfile.constants.FileConstants;
import com.ndangducbn.uploadfile.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {
    private final Environment env;

    public StorageService(Environment env) {
        this.env = env;
    }

    public void uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }
        String fileName = file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, Paths.get(env.getProperty(FileConstants.PATH)+fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            var msg = String.format("Failed to store file %s", fileName);
            throw new StorageException(msg, e);
        }
    }
}
