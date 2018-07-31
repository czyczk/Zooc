package com.zzzz.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.zzzz.service.FdfsService;
import com.zzzz.service.FdfsServiceException;
import com.zzzz.vo.UploadResult;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.zzzz.service.FdfsServiceException.ExceptionTypeEnum.FILE_NOT_SPECIFIED;

@Service
public class FdfsServiceImpl implements FdfsService {
    private final CustomFdfsClient fdfsClient;

    @Autowired
    public FdfsServiceImpl(CustomFdfsClient customFdfsClient) {
        this.fdfsClient = customFdfsClient;
    }

    @Override
    public UploadResult uploadFile(MultipartFile file) throws IOException, FdfsServiceException {
        if (file == null)
            throw new FdfsServiceException(FILE_NOT_SPECIFIED);

        StorePath storePath = fdfsClient.uploadFile(
                file.getInputStream(),
                file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        UploadResult result = new UploadResult();
        result.setUrl(toCompleteUrl(storePath, file.getOriginalFilename()));
        return result;
    }

    private String toCompleteUrl(StorePath storePath, String originalFilename) {
        return "http://" + fdfsClient.getTrackerAccessHost() + "/" + storePath.getFullPath() + "?attname=" + originalFilename;
    }
}
