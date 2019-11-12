package com.ximalaya.per.yan.lock.redisson;

import com.ximalaya.per.yan.lock.LockProxy;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import com.ximalaya.per.yan.lock.constant.RedisConstant;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author yan.gao
 * @date 2019/10/31 12:43 下午
 */
@Slf4j
@ConditionalOnBean(RedissonClient.class)
public class RedissonLockProxy implements LockProxy {

    @Resource
    private RedissonClient redissonClient;

    @Override
    public boolean lock(String key, long timeout, long expire) {
        try {
            return redissonClient.getLock(RedisConstant.appendLockPrefix(key)).tryLock(timeout, expire, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.warn("获取分布式锁错误 key=[{}]，e=[{}]", key, e);
        }
        return false;
    }

    @Override
    public void release(String key) {
        redissonClient.getLock(RedisConstant.appendLockPrefix(key)).unlock();
    }
}
