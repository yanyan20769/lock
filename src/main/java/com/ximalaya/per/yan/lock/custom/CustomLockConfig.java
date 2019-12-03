package com.ximalaya.per.yan.lock.custom;

import com.ximalaya.per.yan.lock.LockAspect;
import com.ximalaya.per.yan.lock.LockProxy;
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