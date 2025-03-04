package com.poc.product.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class CacheConfig {
	
	 @Bean
	    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
	        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
	                .entryTtl(Duration.ofMinutes(5)) // Cache expiry time
	                .disableCachingNullValues(); // Prevents caching null values

	        return RedisCacheManager.builder(redisConnectionFactory)
	                .cacheDefaults(cacheConfig)
	                .build();
	    }

}
