package org.mini.spring.io.factory.support;

import org.mini.spring.io.BeansException;
import org.mini.spring.io.factory.config.BeanDefinition;

/**
 * <p>
 * 完成自动注入的BeanFactory
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;
        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e){
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 完成单例bean的注入
        addSingleton(name, bean);

        return bean;
    }
}
