package per.yan.lock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author yan.gao
 * @date 2020/11/23 4:24 下午
 */
@ConditionalOnBean({RedisTemplate.class, StringRedisTemplate.class})
public class LockAutoConfiguration {

    @Bean
    public LockProxy lockProxy() {
        return new PipelineLockProxy();
    }

}
