package org.mini.spring.beans.factory.annotation;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.config.BeanPostProcessor;
import org.mini.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/12
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
        return null;
    }
}
