package per.yan.lock;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import per.yan.lock.constant.RedisConstant;
import per.yan.lock.el.SpringELUtil;

/**
 * @author yan.gao
 * @date 2019/11/4 10:16 上午
 */
@Slf4j
@Aspect
public class LockAspect {

    @Autowired
    private LockProxy lockProxy;

    @Around("@annotation(per.yan.lock.Lock)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Lock lock = methodSignature.getMethod().getAnnotation(Lock.class);

        String key = lock.key();

        String finalKey = null;

        boolean locked = false;
        try {
            if (StringUtils.isBlank(key)) {
                return joinPoint.proceed();
            }

            finalKey = parseKey(joinPoint, lock);

            long expire = lock.expire();

            long timeout = lock.timeout();

            locked = lockProxy.lock(finalKey, timeout, expire);

            if (locked) {
                return joinPoint.proceed();
            } else {
                log.warn("类 [{}] - 方法 [{}] - 线程 [{}] 获取分布式锁失败！", methodSignature.getDeclaringType().getSimpleName(), methodSignature.getMethod().getName(), Thread.currentThread().getId());
                String errorMessage = lock.errorMessage();
                throw new LockException(errorMessage);
            }
        } catch (Throwable throwable) {
            log.error("lock 执行异常，异常信息=", throwable);
            throw throwable;
        } finally {
            if (locked) {
                lockProxy.release(finalKey);
            }
        }
    }

    private String parseKey(JoinPoint joinPoint, Lock lock) {
        String key = lock.key();

        if (lock.isSpringEL()) {

            Object keyValue = SpringELUtil.getKeyValue(joinPoint, key);
            key = keyValue == null ? "" : keyValue.toString();
        }

        return RedisConstant.appendLockPrefix(key);
    }

}
