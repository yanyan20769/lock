package com.ximalaya.yan.lock.custom;

import com.ximalaya.yan.lock.LockAspect;
import com.ximalaya.yan.lock.LockProxy;
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