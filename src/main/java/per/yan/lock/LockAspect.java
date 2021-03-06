package per.yan.lock;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import per.yan.lock.el.SpringELUtil;

import javax.annotation.Resource;

/**
 * @author yan.gao
 * @date 2019/11/4 10:16 上午
 */
@Slf4j
@Aspect
@Component
public class LockAspect {

    @Value("${spring.application.name}")
    private String appName;

    @Resource
    private LockProxy lockProxy;

    @Around("@annotation(per.yan.lock.Lock)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Lock lock = methodSignature.getMethod().getAnnotation(Lock.class);

        String key = lock.key();

        String finalKey = null;

        boolean locked = false;
        try {
            if (!StringUtils.hasText(key)) {
                return joinPoint.proceed();
            }

            finalKey = parseKey(joinPoint, lock);

            long expire = lock.expire();

            locked = lockProxy.lock(finalKey, expire);

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
        return appName + ":" + key;
    }
}
