package com.zzzz.config;

import org.springframework.context.annotation.*;

@Configuration
@Import({ SpringRedisConfig.class, SpringFdfsClientConfig.class})
public class SpringConfig {

}
