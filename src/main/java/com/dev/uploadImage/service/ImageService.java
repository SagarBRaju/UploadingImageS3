package com.dev.uploadImage.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageService {

    Map<String,Object> uploadImage(MultipartFile fileToUpload);

    Map<String,Object> getImages();

    String preSignedUrl(String fileName);

    Map<String,Object> getImageByName(String name);
}
