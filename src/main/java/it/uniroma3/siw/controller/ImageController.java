package it.uniroma3.siw.controller;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class ImageController {
	
	 private final String uploadDir = "C:/Users/hp/Film-Workspace/siw-pet-1/images";
	 
	 @GetMapping("/{filename}")
	    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
	        File file = new File(Paths.get(uploadDir, filename).toString());
	        if (file.exists()) {
	            Resource resource = new FileSystemResource(file);
	            String contentType = Files.probeContentType(file.toPath());
	            if (contentType == null) {
	                contentType = "application/octet-stream";
	            }
	            return ResponseEntity.ok()
	                    .header(HttpHeaders.CONTENT_TYPE, contentType)
	                    .body(resource);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }

}
