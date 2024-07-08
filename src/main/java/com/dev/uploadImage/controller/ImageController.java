package com.dev.uploadImage.controller;

import com.dev.uploadImage.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.dev.uploadImage.helper.ImageConstants.*;

@RestController
@RequestMapping(MAIN_URL)
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping(value = UPLOAD_IMAGE)
    public ResponseEntity<?> uploadImage(@RequestBody MultipartFile imageToUpload) {
        return ResponseEntity.ok().body(imageService.uploadImage(imageToUpload));
    }

    @GetMapping(value = GET_IMAGES)
    public ResponseEntity<?> images() {
        return ResponseEntity.ok().body(imageService.getImages());
    }

    @GetMapping(value = GET_IMAGES_BY_NAME)
    public ResponseEntity<?> imageByName(@RequestParam String fileName) {
        return ResponseEntity.ok().body(imageService.getImageByName(fileName));
    }
}
