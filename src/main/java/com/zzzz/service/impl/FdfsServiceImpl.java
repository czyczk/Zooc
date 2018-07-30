package com.zzzz.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.zzzz.service.FdfsService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FdfsServiceImpl implements FdfsService {
    private final CustomFdfsClient fdfsClient;

    @Autowired
    public FdfsServiceImpl(CustomFdfsClient customFdfsClient) {
        this.fdfsClient = customFdfsClient;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = fdfsClient.uploadFile(
                file.getInputStream(),
                file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return toCompleteUrl(storePath);
    }

    private String toCompleteUrl(StorePath storePath) {
        return "http://" + fdfsClient.getTrackerAccessHost() + "/" + storePath.getFullPath();
    }
}
