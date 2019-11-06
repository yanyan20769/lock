package io.github.yan.lock.custom;

import io.github.yan.lock.LockAspect;
import io.github.yan.lock.LockProxy;
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