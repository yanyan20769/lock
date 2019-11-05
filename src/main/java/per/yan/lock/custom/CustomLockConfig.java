package per.yan.lock.custom;

import org.springframework.context.annotation.Bean;
import per.yan.lock.LockAspect;
import per.yan.lock.LockProxy;

public class CustomLockConfig {

    @Bean
    public LockProxy lockProxy() {
        return new CustomLockProxy();
    }

    @Bean
    public LockAspect lockAspect() {
        return new LockAspect();
    }
}