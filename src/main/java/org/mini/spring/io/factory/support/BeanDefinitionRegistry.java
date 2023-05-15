package org.mini.spring.io.factory.support;

import org.mini.spring.io.factory.config.BeanDefinition;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public interface BeanDefinitionRegistry {

    /**
     * 完成BeanDefinition的注册
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
