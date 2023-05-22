package org.mini.spring.beans.factory.config;

import org.mini.spring.beans.BeansException;

/**
 * <p>
 *     定制化修改Bean对象
 *     Factory hook that allows for custom modification of new bean instances, e.g.
 * </p>
 *
 * @author pp
 * @since 2023/5/18
 */
public interface BeanPostProcessor {

    /**
     * 在Bean对象初始化之前完成处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;


    /**
     * 在Bean对象初始化之后完成处理
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
