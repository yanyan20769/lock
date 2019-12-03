package com.ximalaya.per.yan.lock;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author yan.gao
 * @date 2019/11/1 10:25 上午
 */

@Import(LockSelector.class)
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableLock {

    /**
     * @see LockPolicy
     */
    LockPolicy policy() default LockPolicy.REDISSON;

}
