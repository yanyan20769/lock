package per.yan.lock.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author yan.gao
 * @date 2020/6/22 7:27 下午
 */
public class BeanUtil {

    public static <T> T getBean(String beanName) {
        return (T) SpringContext.getContext().getBean(beanName);
    }

    public static <T> T getBean(Class<?> clazz) {
        return (T) SpringContext.getContext().getBean(clazz);
    }

    @Component
    public static class SpringContext implements ApplicationContextAware {

        protected static ApplicationContext context;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            context = applicationContext;
        }

        public static ApplicationContext getContext() {
            return context;
        }
    }
}
