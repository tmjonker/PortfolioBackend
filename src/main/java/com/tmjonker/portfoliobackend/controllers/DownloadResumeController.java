package com.tmjonker.portfoliobackend.controllers;

import com.tmjonker.portfoliobackend.services.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class DownloadResumeController {

    private S3Service s3Service;

    public DownloadResumeController(S3Service s3Service) {

        this.s3Service = s3Service;
    }

    @GetMapping("/download")
    public ResponseEntity<?> getPresignedURL() {

        String url = s3Service.getURL();

        if (url != null)
            return new ResponseEntity<>(url, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
