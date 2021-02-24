package per.yan.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import per.yan.lock.util.BeanUtil;

import java.util.List;

/**
 * @author yan.gao
 * @date 2020/10/30 3:53 下午
 */
@Slf4j
public class PipelineLockProxy implements LockProxy {

    @Override
    public boolean lock(String key, long expire) {
        return tryLock(key, expire);
    }

    @Override
    public void release(String key) {
        RedisTemplate redisTemplate = getRedisTemplateBean();
        redisTemplate.delete(key);
    }

    private boolean tryLock(String key, Long expire) {
        try {
            RedisTemplate redisTemplate = getRedisTemplateBean();
            List list = redisTemplate.executePipelined((RedisCallback<?>) connection -> {
                byte[] keyBytes = keySerializer(key, redisTemplate);
                byte[] valueBytes = valueSerializer(System.currentTimeMillis(), redisTemplate);
                connection.setNX(keyBytes, valueBytes);
                connection.expire(keyBytes, expire);
                return null;
            });
            Boolean o = (Boolean) list.get(0);
            return o != null && o;
        } catch (Exception e) {
            log.error("get distributed lock error! key=[{}], e=[{}]", key, e);
        }
        return false;
    }

    private static byte[] keySerializer(String key, RedisTemplate redisTemplate) {
        RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        return keySerializer.serialize(key);
    }

    private static byte[] valueSerializer(Object value, RedisTemplate redisTemplate) {
        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
        return valueSerializer.serialize(value);
    }

    private static RedisTemplate getRedisTemplateBean() {
        RedisTemplate redisTemplate = BeanUtil.getBean("redisTemplate");
        redisTemplate = redisTemplate == null ? BeanUtil.getBean(RedisTemplate.class) : redisTemplate;
        if (redisTemplate == null) {
            throw new LockException("no redisTemplate found!");
        }
        return redisTemplate;
    }
}
