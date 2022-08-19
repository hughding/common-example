package pers.hugh.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author dingxiuzheng
 */
@Slf4j
@Component
public class RedisLock {
    private static final String LOCK_PREFIX = "CONSOLE_OPENSTACK_VOLUME_";

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 尝试获取锁 立即返回
     *
     * @param key
     * @param value
     * @param timeoutSeconds
     * @return
     */
    public boolean tryLock(String key, String value, long timeoutSeconds) {
        String lockKey = LOCK_PREFIX + key;
        String lua = "return redis.call('SET', KEYS[1], ARGV[1], 'NX', 'EX', ARGV[2])";
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>(lua, String.class);
        String result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value, String.valueOf(timeoutSeconds));
        log.info("#RedisLock.tryLock result:{}", result);
        return "OK".equals(result);
    }

    /**
     * 释放锁
     *
     * @param key
     * @param value
     * @return
     */
    public boolean unlock(String key, String value) {
        String lockKey = LOCK_PREFIX + key;
        String lua = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(lua, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value);
        log.info("#RedisLock.unlock result:{}", result);
        return 1L == result;
    }
}
