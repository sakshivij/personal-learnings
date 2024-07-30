package com.example.demo.services;

import java.net.URI;
import java.net.URL;
import java.time.Duration;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

@Service
public class FileUploadService {
    
    private final S3Presigner s3Presigner;

    public FileUploadService() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create("test", "test");

        this.s3Presigner = S3Presigner.builder()
                .region(Region.US_EAST_1) // Use your desired region
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create("http://localhost:4566")) // Ensure this matches LocalStack's endpoint
                .build();
    }

    public URL generatePresignedUrl(String objectKey) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("my-bucket")
                .key(objectKey)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(r -> r.signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(putObjectRequest));

        return presignedRequest.url();
    }
}
