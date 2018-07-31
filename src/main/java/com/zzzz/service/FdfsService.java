package com.zzzz.service;

import com.zzzz.vo.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FdfsService {
    UploadResult uploadFile(MultipartFile file) throws IOException, FdfsServiceException;
}
