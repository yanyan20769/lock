package per.yan.lock.redisson;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import per.yan.lock.LockAspect;

/**
 * @author yan.gao
 * @date 2019/11/1 2:03 下午
 */
@EnableConfigurationProperties(RedissonConfig.class)
public class RedissonLockProperties {

    @Bean
    public LockAspect lockAspect() {
        return new LockAspect();
    }

    @Bean
    public RedissonLockProxy redissonLockProxy() {
        return new RedissonLockProxy();
    }
}
