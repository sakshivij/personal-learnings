package com.example.demo.controller;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.FileUpload;
import com.example.demo.services.FileUploadService;

@RestController
public class FileUploadController{

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/presigned-url")
    public ResponseEntity<URL> saveBlogPost(@RequestBody FileUpload fileUpload){
        try{
            URL url = fileUploadService.generatePresignedUrl(fileUpload.getFileName());
            return new ResponseEntity<>(url, HttpStatus.OK);
        }
        catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " 
            + ex.getMessage());
        }

        
    }

}