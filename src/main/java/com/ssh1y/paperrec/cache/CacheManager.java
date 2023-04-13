package com.ssh1y.paperrec.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssh1y.paperrec.entity.Papers;

/*
 * 缓存管理器
 * @author chenweihong
 */
public class CacheManager {
    private static CacheManager instance;
    private Map<String, List<Papers>> cache;
    // 定义缓存的最大容量
    private static final int MAX_CACHE_SIZE = 100;

    /*
     * 私有构造函数
     */
    private CacheManager() {
        cache = new HashMap<>();
    }

    /*
     * 获取单例
     * @return 单例
     */
    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    /*
     * 获取缓存
     * @param key 缓存的key
     * @return 缓存的value
     */
    public List<Papers> get(String key) {
        return cache.get(key);
    }

    /*
     * 添加缓存
     * @param key 缓存的key
     * @param value 缓存的value
     * @return 缓存的value
     */
    public void put(String key, List<Papers> value) {
        // 如果缓存已满，则清空缓存
        if (cache.size() >= MAX_CACHE_SIZE) {
            cache.clear();
        }
        cache.put(key, value);
    }

    /*
     * 判断是否包含某个key
     * @param key 缓存的key
     * @return 是否包含
     */
    public boolean contains(String key) {
        return cache.containsKey(key);
    }

    /*
     * 删除缓存
     * @param key 缓存的key
     */
    public void remove(String key) {
        cache.remove(key);
    }

    /*
     * 清空缓存
     */
    public void clear() {
        cache.clear();
    }
}
