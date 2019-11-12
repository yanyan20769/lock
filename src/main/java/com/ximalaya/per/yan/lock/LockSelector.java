package com.ximalaya.per.yan.lock;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import com.ximalaya.per.yan.lock.custom.CustomLockConfig;
import com.ximalaya.per.yan.lock.redisson.RedissonLockConfig;

/**
 * @author yan.gao
 * @date 2019/11/1 1:53 下午
 */
public class LockSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        Class<EnableLock> enableLockClass = EnableLock.class;

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(
                enableLockClass.getName(), false));

        Enum<LockPolicy> policy = attributes.getEnum("policy");

        return LockPolicy.CUSTOM.equals(policy)
                ? new String[]{CustomLockConfig.class.getName()}
                : new String[]{RedissonLockConfig.class.getName()};
    }
}
