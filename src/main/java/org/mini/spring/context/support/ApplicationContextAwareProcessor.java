package org.mini.spring.context.support;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.config.BeanPostProcessor;
import org.mini.spring.context.ApplicationContext;
import org.mini.spring.context.ApplicationContextAware;

/**
 * <p>
 *     由于ApplicationContext并不能在createBean的时候获取到， 所以可以继承BeanPostProcessor
 *     直接由Spring的扩展来完成ApplicationContext的绑定
 * </p>
 *
 * @author pp
 * @since 2023/5/29
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
