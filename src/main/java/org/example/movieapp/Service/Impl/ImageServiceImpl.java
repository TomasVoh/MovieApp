package org.example.movieapp.Service.Impl;

import jdk.dynalink.StandardOperation;
import org.example.movieapp.Service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public String saveImage(MultipartFile file) {
        String filePath = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get("img");
        Path newPath = path.resolve(filePath);
        try {
            Files.copy(file.getInputStream(), newPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return newPath.toString();
    }
}
