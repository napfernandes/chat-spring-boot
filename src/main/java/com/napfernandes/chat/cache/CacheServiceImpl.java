package com.napfernandes.chat.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.napfernandes.chat.gson.GsonConverter;

@Service
public class CacheServiceImpl implements CacheService {
    private GsonConverter gsonConverter;
    private RedissonClient redissonClient;

    public CacheServiceImpl(RedissonClient redissonClient, GsonConverter gsonConverter) {
        this.gsonConverter = gsonConverter;
        this.redissonClient = redissonClient;
    }

    @Override
    public void setItem(String cacheKey, Object item) {
        if (item == null)
            return;

        RBucket<String> bucket = redissonClient.getBucket(cacheKey);

        bucket.set(gsonConverter.toJson(item), 10, TimeUnit.MINUTES);
    }

    @Override
    public <T> T getItem(String cacheKey, Class<T> clazz) {
        RBucket<String> bucket = redissonClient.getBucket(cacheKey);
        String bucketValue = bucket.get();

        if (bucketValue == null)
            return null;

        return gsonConverter.fromJson(bucket.get(), clazz);
    }

    @Override
    public <T> List<T> getItemAsList(String cacheKey, Class<T> clazz) {
        RBucket<String> bucket = redissonClient.getBucket(cacheKey);
        String bucketValue = bucket.get();

        if (bucketValue == null)
            return null;

        return gsonConverter.fromJsonList(bucket.get(), clazz);
    }
}