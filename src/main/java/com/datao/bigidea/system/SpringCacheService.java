package com.datao.bigidea.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class SpringCacheService {
    Logger logger = LoggerFactory.getLogger(SpringCacheService.class);

    @Resource(name = "cacheManager")
    private CacheManager cacheManager;

    public Cache getCache(String cacheName) {
        checkArgument(cacheName != null);

        return cacheManager.getCache(cacheName);
    }

    public <T> T get(String cacheName, Object key) {
        checkArgument(key != null);

        Cache cache = getCache(cacheName);
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if (valueWrapper != null)
            return (T) valueWrapper.get();
        else
            return null;
    }

    public <T> T get(String cacheName, Object key, CacheValueLoader<T> cacheValueLoader) {
        T value = get(cacheName, key);
        if (value != null)
            return value;

        synchronized (SpringCacheService.class) {
            value = get(cacheName, key);
            if (value != null)
                return value;

            logger.info("load value for cache: " + cacheName + ", key: " + key);

            value = cacheValueLoader.load();
            Cache cache = getCache(cacheName);
            cache.put(key, value);
            return value;
        }
    }

    public void clear(String cacheName) {
        logger.info("clear cache: " + cacheName);

        Cache cache = getCache(cacheName);
        cache.clear();
    }

    public void evict(String cacheName, Object key) {
        checkArgument(key != null);

        logger.info("evict cache: " + cacheName + ", key: " + key);

        Cache cache = getCache(cacheName);
        cache.evict(key);
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
