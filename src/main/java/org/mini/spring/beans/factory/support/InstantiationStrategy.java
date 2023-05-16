package org.mini.spring.beans.factory.support;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public interface InstantiationStrategy {

    /**
     * 实例化的具体策略
     * @param beanDefinition
     * @param beanName
     * @param constructor
     * @param args
     * @return
     * @throws BeansException
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName,
                       Constructor constructor, Object[] args) throws BeansException;
}
