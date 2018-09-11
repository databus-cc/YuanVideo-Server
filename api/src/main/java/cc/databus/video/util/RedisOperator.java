package cc.databus.video.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
@Component
public class RedisOperator {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * return the remaining TTL (time to live) in seconds for given key.
     * @param key given key
     * @return remaining TTL
     */
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * set the expire time for given key. The expiration time is in seconds.
     * @param key key
     * @param timeout timeout to live
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * Increase key with given delta
     * @param key key to increase
     * @param delta delta of the increase op
     * @return result val
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * search all keys with given pattern
     * @param pattern given pattern
     * @return keys match the patter
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * Delete the given key
     * @param key the given key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Set a key with given value
     * @param key given key
     * @param value given value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Set a key with given value. And key-value will expire in given timeout
     * @param key given key
     * @param value given value
     * @param timeout given ttl in seconds
     */
    public void set(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * get value for given key
     * @param key given key
     * @return value for given key
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * HSET with given key/field and value
     * @param key key
     * @param field field
     * @param value value
     */
    public void hset(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * HDEL key field [field...] operation. Delete multi fields from hash
     * @param key key
     * @param fields fields
     */
    public void hdel(String key, Object ... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * HGETALL key: return all fields and values with given key
     * @param key given key
     * @return field to value map
     */
    public Map<Object, Object> hgetall(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
    // List operations

    /**
     * LPUSH command. Push a value to the head of list.
     * @param key key
     * @param value value
     * @return length after pusj
     */
    public Long lpush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * LPOP [key]: remove and return the element for key
     * @param key name
     * @return removed element
     */
    public String lpop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * RPUSH key value: push a value to the tail of the key list
     * @param key key
     * @param value value
     * @return length after RPUSH
     */
    public Long rpush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

}
