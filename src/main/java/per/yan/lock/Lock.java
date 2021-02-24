package per.yan.lock;

import org.aspectj.lang.JoinPoint;

import java.lang.annotation.*;

/**
 * redis分布式锁切面注解
 *
 * @author yan.gao
 * @date 2019/8/30 4:52 下午
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {

    /**
     * <strong>
     * key可以使用spring el表达式指定
     * <p>
     * 对于spring el类型的key会使用
     * {@link per.yan.lock.el.SpringELUtil#getKeyValue(JoinPoint, String)}
     * 进行解析
     * </p>
     * </strong>
     */
    String key();

    /**
     * key是否是spring el表达式
     */
    boolean isSpringEL() default false;

    /**
     * 锁失效时间 默认60秒
     */
    long expire() default 60000L;

    /**
     * 获取锁异常时的错误描述
     */
    String errorMessage() default "获取分布式锁失败!";

}
