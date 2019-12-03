package per.yan.lock.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import per.yan.lock.LockProxy;
import per.yan.lock.enums.PunctuationEnum;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;

/**
 * @author yan.gao
 * @date 2019/8/30 7:55 下午
 */
@Slf4j
@ConditionalOnBean(RedisTemplate.class)
public class CustomLockProxy implements LockProxy {

    private static final Long SUCCESS = 1L;

    /**
     * 默认失效时间60秒
     */
    private static final long DEFAULT_EXPIRE_TIME = 60L;

    private static DefaultRedisScript<Long> lockScript;

    private static DefaultRedisScript<Long> releaseScript;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    static {
        lockScript = new DefaultRedisScript<>();
        lockScript.setResultType(Long.class);
        lockScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/lock.lua")));

        releaseScript = new DefaultRedisScript<>();
        releaseScript.setResultType(Long.class);
        releaseScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/release.lua")));
    }

    @Override
    public boolean lock(String key, long timeout, long expire) {
        return lock(key, generateUniqueValue(), expire / 1000L);
    }

    @Override
    public void release(String key) {
        release(key, generateUniqueValue());
    }

    public boolean lock(String key) {
        return lock(key, generateUniqueValue(), DEFAULT_EXPIRE_TIME);
    }

    public boolean lock(String key, String value, long expire) {
        return SUCCESS.equals(redisTemplate.execute(lockScript, Collections.singletonList(key), value, expire));
    }

    public boolean release(String key, String value) {
        return SUCCESS.equals(redisTemplate.execute(releaseScript, Collections.singletonList(key), value));
    }

    /**
     * 默认value为 本机ip - 当前线程ID
     */
    private static String generateUniqueValue() {
        String uniqueValue = String.valueOf(Thread.currentThread().getId());
        try {
            uniqueValue = InetAddress.getLocalHost() + PunctuationEnum.CONNECTOR.getValue() + uniqueValue;
        } catch (UnknownHostException e) {
            log.warn("获取本机IP地址异常，异常信息=", e);
        }
        return uniqueValue;
    }
}
