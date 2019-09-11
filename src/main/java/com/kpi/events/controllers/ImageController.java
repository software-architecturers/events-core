package com.kpi.events.controllers;

import com.kpi.events.model.Image;
import com.kpi.events.services.AmazonClient;
import com.kpi.events.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api")
public class ImageController {

    @Autowired
    private AmazonClient amazonClient;

    @Autowired
    private ImageService service;

    @PostMapping("/uploadFile")
    public Image uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return service.uploadImage(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestParam(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}
