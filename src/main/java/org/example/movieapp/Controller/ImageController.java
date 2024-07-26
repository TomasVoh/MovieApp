package org.example.movieapp.Controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/img")
public class ImageController {

    @GetMapping(value = "/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> findImage(@PathVariable("name") String name) throws MalformedURLException {
        Path path = Paths.get("/home/tomasv/img/%s".formatted(name));
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok(resource);
    }
}
