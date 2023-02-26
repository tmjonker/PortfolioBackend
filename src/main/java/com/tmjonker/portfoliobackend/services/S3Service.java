package com.tmjonker.portfoliobackend.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Instant;
import java.util.Date;

@Service
public class S3Service {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;
    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;

    public String getURL() {
        Regions clientRegion = Regions.US_EAST_1;
        String bucketName = "tmjonker-resume1";
        String key = "Timothy_Jonker_Resume_1.pdf";
        long signedUrlExpireSeconds = Instant.now().toEpochMilli();
        Date expiration = new Date();
        signedUrlExpireSeconds += 1000 * 60 * 5;
        expiration.setTime(signedUrlExpireSeconds);

        try {
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKey);

            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .build();

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, key)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return url.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
