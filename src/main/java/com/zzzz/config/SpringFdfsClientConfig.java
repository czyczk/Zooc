package com.zzzz.config;

import com.github.tobato.fastdfs.conn.ConnectionPoolConfig;
import com.github.tobato.fastdfs.conn.FdfsConnectionPool;
import com.github.tobato.fastdfs.conn.PooledConnectionFactory;
import com.github.tobato.fastdfs.conn.TrackerConnectionManager;
import com.zzzz.service.impl.CustomFdfsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource(value = { "classpath:fdfs.properties" })
@ComponentScan(basePackages = { "com.github.tobato.fastdfs.service", "com.github.tobato.fastdfs.domain" })
public class SpringFdfsClientConfig {
    @Value("${fdfs.soTimeout}")
    private int soTimeout;

    @Value("${fdfs.connectTimeout}")
    private int connectTimeout;

    @Value("${fdfs.thumbImage.width}")
    private int thumbImageWidth;

    @Value("${fdfs.thumbImage.height}")
    private int thumbImageHeight;

    @Value("${fdfs.trackerList}")
    private List<String> trackerList;

    @Value("${fdfs.trackerAccessHost}")
    private String trackerAccessHost;

    // Connection pool
    @Bean
    FdfsConnectionPool fdfsConnectionPool() {
        PooledConnectionFactory factory = new PooledConnectionFactory();
        factory.setSoTimeout(soTimeout);
        factory.setConnectTimeout(connectTimeout);
        return new FdfsConnectionPool(factory, new ConnectionPoolConfig());
    }

    // Connection manager
    @Bean
    TrackerConnectionManager trackerConnectionManager() {
        TrackerConnectionManager result = new TrackerConnectionManager(fdfsConnectionPool());
        result.setTrackerList(trackerList);
        return result;
    }

    // Custom FdfsClient
    @Bean
    CustomFdfsClient customFdfsClient() {
        return new CustomFdfsClient(thumbImageWidth, thumbImageHeight, trackerAccessHost);
    }
}

