package per.yan.lock.custom;

import per.yan.lock.LockAspect;
import per.yan.lock.LockProxy;
import org.springframework.context.annotation.Bean;

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