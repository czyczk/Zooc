package com.zzzz.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
@PropertySource(value = { "classpath:redis.properties" })
@ComponentScan(basePackages = { "com.zzzz.repo" })
public class SpringRedisConfig {
    @Value("${redis.hostName}")
    private String hostName;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.database}")
    private int database;

    @Value("${redis.timeout}")
    private long timeout;

    @Value("${redis.maxTotal}")
    private int maxTotal;

    @Value("${redis.maxIdle}")
    private int maxIdle;

    @Value("${redis.maxWaitMillis}")
    private int maxWaitMillis;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    @Bean
    private static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    JedisPoolConfig poolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // Maximum number of connections
        poolConfig.setMaxTotal(maxTotal);
        // Maximum number of idle connections
        poolConfig.setMaxIdle(maxIdle);
        // Maximum waiting time when getting connections
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        // Check validity when getting connections
        poolConfig.setTestOnBorrow(testOnBorrow);
        return poolConfig;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        // Basic configuration
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(hostName);
        standaloneConfiguration.setPort(port);
        standaloneConfiguration.setDatabase(database);
        standaloneConfiguration.setPassword(RedisPassword.of(password));

        // Extended configuration
        JedisClientConfiguration.JedisClientConfigurationBuilder clientConfigBuilder = JedisClientConfiguration.builder();
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder poolingConfigBuilder = clientConfigBuilder.usePooling();

        // Pool config
        poolingConfigBuilder.poolConfig(poolConfig());
        // Keep connections for
        clientConfigBuilder.connectTimeout(Duration.ofSeconds(timeout));

        return new JedisConnectionFactory(standaloneConfiguration, clientConfigBuilder.build());
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSerializer.setObjectMapper(om);

        RedisTemplate<String, Object> result = new RedisTemplate<>();
        result.setKeySerializer(jacksonSerializer);
        result.setValueSerializer(jacksonSerializer);
        result.setHashKeySerializer(jacksonSerializer);
        result.setHashValueSerializer(jacksonSerializer);
        result.setConnectionFactory(jedisConnectionFactory());
        return result;
    }
}
