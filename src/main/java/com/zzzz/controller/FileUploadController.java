package com.zzzz.controller;

import com.zzzz.service.FdfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class FileUploadController {
    private final FdfsService fdfsService;

    @Autowired
    public FileUploadController(FdfsService fdfsService) {
        this.fdfsService = fdfsService;
    }

    /**
     * Upload a file to the server.
     * @param file file: file
     * @return Success: File URL; Internal: 500
     */
    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file) throws IOException {
        String url = fdfsService.uploadFile(file);
        return ResponseEntity.ok(url);
    }
}
