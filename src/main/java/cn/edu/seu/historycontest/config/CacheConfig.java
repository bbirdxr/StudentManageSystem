package cn.edu.seu.historycontest.config;

import cn.edu.seu.historycontest.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@SuppressWarnings("unchecked")
public class CacheConfig implements Cache {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private RedisTemplate<String, Object> redisTemplate;

    private final String id;

    public CacheConfig(final String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate<String, Object>) SpringUtils.getBean("redisTemplate");
        }
        if (value != null) {
            redisTemplate.opsForValue().set(key.toString(), value);
        }
    }

    @Override
    public Object getObject(Object key) {
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate<String, Object>) SpringUtils.getBean("redisTemplate");
        }
        try {
            if (key != null) {
                return redisTemplate.opsForValue().get(key.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("缓存出错 ");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate<String, Object>) SpringUtils.getBean("redisTemplate");
        }
        if (key != null) {
            redisTemplate.delete(key.toString());
        }
        return null;
    }

    @Override
    public void clear() {
        log.debug("清空缓存");
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate<String, Object>) SpringUtils.getBean("redisTemplate");
        }
        Set<String> keys = redisTemplate.keys("*:" + this.id + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public int getSize() {
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate<String, Object>) SpringUtils.getBean("redisTemplate");
        }
        Long size = redisTemplate.execute(RedisServerCommands::dbSize);
        if (size == null)
            return 0;
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

}
