package com.zzzz.service.impl;

import com.github.tobato.fastdfs.domain.*;
import com.github.tobato.fastdfs.exception.FdfsUnsupportImageTypeException;
import com.github.tobato.fastdfs.exception.FdfsUploadImageException;
import com.github.tobato.fastdfs.proto.storage.StorageSetMetadataCommand;
import com.github.tobato.fastdfs.proto.storage.StorageUploadFileCommand;
import com.github.tobato.fastdfs.proto.storage.StorageUploadSlaveFileCommand;
import com.github.tobato.fastdfs.proto.storage.enums.StorageMetdataSetType;
import com.github.tobato.fastdfs.service.DefaultGenerateStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CustomFdfsClient extends DefaultGenerateStorageClient implements FastFileStorageClient {
    /**
     * Default thumbnail image width
     */
    public static final int DEFAULT_THUMB_IMAGE_WIDTH = 150;
    /**
     * Default thumbnail image height
     */
    public static final int DEFAULT_THUMB_IMAGE_HEIGHT = 150;
    /**
     * The maximum thumbnail image width supported by the library used internally.
     */
    public static final int MAX_THUMB_IMAGE_WIDTH = 200;
    /**
     * The maximum thumbnail image height supported by the library used internally.
     */
    public static final int MAX_THUMB_IMAGE_HEIGHT = 200;
    private static final String[] SUPPORT_IMAGE_TYPE = { "JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP" };
    private static final List<String> SUPPORT_IMAGE_LIST = Arrays.asList(SUPPORT_IMAGE_TYPE);
    // ThumbImageConfig that can be tailored from the constructors (different from DefaultFastFileStorageClient)
    private ThumbImageConfig thumbImageConfig;
    // Open tracker access host to make generating a complete URL more conveniently
    private String trackerAccessHost;

    public String getTrackerAccessHost() {
        return trackerAccessHost;
    }

    /**
     * Use the default thumbnail image config (150 x 150) to generate a FDFS client.
     * This will construct an exactly same client as DefaultFastFileStorageClient provided in the library.
     * @param trackerAccessHost Tracker access host (e.g. 192.168.1.105:8000)
     */
    public CustomFdfsClient(String trackerAccessHost) {
        this(DEFAULT_THUMB_IMAGE_WIDTH, DEFAULT_THUMB_IMAGE_HEIGHT, trackerAccessHost);
    }

    /**
     * Generate a FDFS client using custom thumbnail image config.
     * The size of the thumbnail should be smaller than 200 x 200 due to the library used internally.
     * @param thumbImageWidth Thumbnail image width (<= 200)
     * @param thumbImageHeight Thumbnail image height (<= 200)
     * @param trackerAccessHost Tracker access host (e.g. 192.168.1.105:8000)
     */
    public CustomFdfsClient(int thumbImageWidth, int thumbImageHeight, String trackerAccessHost) {
        DefaultThumbImageConfig thumbImageConfig = new DefaultThumbImageConfig();
        if (thumbImageWidth > MAX_THUMB_IMAGE_WIDTH)
            throw new IllegalArgumentException("缩略图宽度大于支持的上限 " + MAX_THUMB_IMAGE_WIDTH);
        if (thumbImageHeight > MAX_THUMB_IMAGE_HEIGHT)
            throw new IllegalArgumentException("缩略图高度大于支持的上限 " + MAX_THUMB_IMAGE_HEIGHT);
        thumbImageConfig.setWidth(thumbImageWidth);
        thumbImageConfig.setHeight(thumbImageHeight);
        this.thumbImageConfig = thumbImageConfig;
        this.trackerAccessHost = trackerAccessHost;
    }


    /**
     * 上传文件
     */
    @Override
    public StorePath uploadFile(InputStream inputStream, long fileSize, String fileExtName, Set<MataData> metaDataSet) {
        Validate.notNull(inputStream, "上传文件流不能为空");
        Validate.notBlank(fileExtName, "文件扩展名不能为空");
        StorageNode client = trackerClient.getStoreStorage();
        return uploadFileAndMataData(client, inputStream, fileSize, fileExtName, metaDataSet);
    }

    /**
     * 上传图片并且生成缩略图
     */
    @Override
    public StorePath uploadImageAndCrtThumbImage(InputStream inputStream, long fileSize, String fileExtName,
                                                 Set<MataData> metaDataSet) {
        Validate.notNull(inputStream, "上传文件流不能为空");
        Validate.notBlank(fileExtName, "文件扩展名不能为空");
        // 检查是否能处理此类图片
        if (!isSupportImage(fileExtName)) {
            throw new FdfsUnsupportImageTypeException("不支持的图片格式" + fileExtName);
        }
        StorageNode client = trackerClient.getStoreStorage();
        byte[] bytes = inputStreamToByte(inputStream);

        // 上传文件和mataData
        StorePath path = uploadFileAndMataData(client, new ByteArrayInputStream(bytes), fileSize, fileExtName,
                metaDataSet);
        // 上传缩略图
        uploadThumbImage(client, new ByteArrayInputStream(bytes), path.getPath(), fileExtName);
        bytes = null;
        return path;
    }

    /**
     * 获取byte流
     *
     * @param inputStream
     * @return
     */
    private byte[] inputStreamToByte(InputStream inputStream) {
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            LOGGER.error("image inputStream to byte error", e);
            throw new FdfsUploadImageException("upload ThumbImage error", e.getCause());
        }
    }

    /**
     * 检查是否有MataData
     *
     * @param metaDataSet
     * @return
     */
    private boolean hasMataData(Set<MataData> metaDataSet) {
        return null != metaDataSet && !metaDataSet.isEmpty();
    }

    /**
     * 是否是支持的图片文件
     *
     * @param fileExtName
     * @return
     */
    private boolean isSupportImage(String fileExtName) {
        return SUPPORT_IMAGE_LIST.contains(fileExtName.toUpperCase());
    }

    /**
     * 上传文件和元数据
     *
     * @param client
     * @param inputStream
     * @param fileSize
     * @param fileExtName
     * @param metaDataSet
     * @return
     */
    private StorePath uploadFileAndMataData(StorageNode client, InputStream inputStream, long fileSize,
                                            String fileExtName, Set<MataData> metaDataSet) {
        // 上传文件
        StorageUploadFileCommand command = new StorageUploadFileCommand(client.getStoreIndex(), inputStream,
                fileExtName, fileSize, false);
        StorePath path = connectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);
        // 上传matadata
        if (hasMataData(metaDataSet)) {
            StorageSetMetadataCommand setMDCommand = new StorageSetMetadataCommand(path.getGroup(), path.getPath(),
                    metaDataSet, StorageMetdataSetType.STORAGE_SET_METADATA_FLAG_OVERWRITE);
            connectionManager.executeFdfsCmd(client.getInetSocketAddress(), setMDCommand);
        }
        return path;
    }

    /**
     * 上传缩略图
     *
     * @param client
     * @param inputStream
     * @param masterFilename
     * @param fileExtName
     */
    private void uploadThumbImage(StorageNode client, InputStream inputStream, String masterFilename,
                                  String fileExtName) {
        ByteArrayInputStream thumbImageStream = null;
        try {
            thumbImageStream = getThumbImageStream(inputStream);// getFileInputStream
            // 获取文件大小
            long fileSize = thumbImageStream.available();
            // 获取缩略图前缀
            String prefixName = thumbImageConfig.getPrefixName();
            StorageUploadSlaveFileCommand command = new StorageUploadSlaveFileCommand(thumbImageStream, fileSize,
                    masterFilename, prefixName, fileExtName);
            connectionManager.executeFdfsCmd(client.getInetSocketAddress(), command);

        } catch (IOException e) {
            LOGGER.error("upload ThumbImage error", e);
            throw new FdfsUploadImageException("upload ThumbImage error", e.getCause());
        } finally {
            IOUtils.closeQuietly(thumbImageStream);
        }
    }

    /**
     * 获取缩略图
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private ByteArrayInputStream getThumbImageStream(InputStream inputStream) throws IOException {
        // 在内存当中生成缩略图
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //@formatter:off
        Thumbnails
                .of(inputStream)
                .size(thumbImageConfig.getWidth(), thumbImageConfig.getHeight())
                .toOutputStream(out);
        //@formatter:on
        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * 删除文件
     */
    @Override
    public void deleteFile(String filePath) {
        StorePath storePath = StorePath.praseFromUrl(filePath);
        super.deleteFile(storePath.getGroup(), storePath.getPath());
    }
}

