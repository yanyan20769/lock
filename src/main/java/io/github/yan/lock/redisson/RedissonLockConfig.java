package io.github.yan.lock.redisson;

import io.github.yan.lock.LockAspect;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author yan.gao
 * @date 2019/11/1 2:03 下午
 */
@EnableConfigurationProperties(RedissonClientConfig.class)
public class RedissonLockConfig {

    @Bean
    public LockAspect lockAspect() {
        return new LockAspect();
    }

    @Bean
    public RedissonLockProxy redissonLockProxy() {
        return new RedissonLockProxy();
    }
}
