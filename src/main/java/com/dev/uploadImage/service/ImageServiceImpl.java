package com.dev.uploadImage.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.dev.uploadImage.exception.ImageUploadException;
import com.dev.uploadImage.utils.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    String bucketName;

    @Override
    public Map<String, Object> uploadImage(MultipartFile fileToUpload) {
        String actualFileName = fileToUpload.getOriginalFilename();
        if (Objects.requireNonNull(actualFileName).length() == 0) {
            throw new ImageUploadException("Invalid Image");
        }

        String fileName = UUID.randomUUID() + actualFileName.substring(actualFileName.lastIndexOf("."));

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileToUpload.getSize());
        try {
            PutObjectResult result = amazonS3.putObject(new PutObjectRequest(bucketName, fileName, fileToUpload.getInputStream(), metadata));
            return new BasicResponse().successResponse(null, null, preSignedUrl(fileName));
        } catch (IOException e) {
            throw new ImageUploadException("Error while uploading image");
        }
    }

    @Override
    public Map<String, Object> getImages() {
        ListObjectsV2Request listObjectV2Request = new ListObjectsV2Request().withBucketName(bucketName);

        ListObjectsV2Result result = amazonS3.listObjectsV2(listObjectV2Request);
        List<S3ObjectSummary> objectSummaries = result.getObjectSummaries();

        List<String> filesList = objectSummaries.stream().map(item -> preSignedUrl(item.getKey())).toList();
        return new BasicResponse().successResponse(null, null, filesList);
    }

    @Override
    public String preSignedUrl(String fileName) {
        Date expirationDate = new Date();
        long time = expirationDate.getTime();
        int hour = 2;
        time = time + hour + 60 * 60 * 1000;
        expirationDate.setTime(time);

        GeneratePresignedUrlRequest preSignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET).withExpiration(expirationDate);

        URL url = amazonS3.generatePresignedUrl(preSignedUrlRequest);
        return url.toString();
    }

    @Override
    public Map<String, Object> getImageByName(String name) {
        try {
            S3Object s3Object = amazonS3.getObject(bucketName, name);
            return new BasicResponse().successResponse(null, null, preSignedUrl(s3Object.getKey()));
        }catch (AmazonS3Exception exception){
            throw new ImageUploadException("NO IMAGE FOUND");
        }
    }
}
