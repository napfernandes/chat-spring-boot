package com.napfernandes.chat.cache;

import java.util.List;

public interface CacheService {
    void setItem(String cacheKey, Object item);

    <T> T getItem(String cacheKey, Class<T> clazz);

    <T> List<T> getItemAsList(String cacheKey, Class<T> clazz);
}
