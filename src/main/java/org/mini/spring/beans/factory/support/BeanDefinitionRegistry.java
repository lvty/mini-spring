package org.mini.spring.beans.factory.support;

import org.mini.spring.beans.factory.config.BeanDefinition;

/**
 * <p>
 *     通过预定义的BeanDefinition， 完成Bean的注册
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

    /**
     * 判断BeanDefinition 注册中心是否包含指定的BeanName
     * @param name
     * @return
     */
    boolean containsBeanDefinition(String name);
}
