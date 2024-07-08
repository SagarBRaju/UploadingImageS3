package com.dev.uploadImage.controller;

import com.dev.uploadImage.exception.ImageUploadException;
import com.dev.uploadImage.utils.BasicResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ImageControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleImageUploadException(ImageUploadException imageUploadException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BasicResponse().errorResponse(null, imageUploadException.getMessage(), null));
    }
}
