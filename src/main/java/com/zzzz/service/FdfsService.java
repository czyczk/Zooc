package com.zzzz.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FdfsService {
    String uploadFile(MultipartFile file) throws IOException;
}
