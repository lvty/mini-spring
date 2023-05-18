package org.mini.spring.beans.factory.config;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.ConfigurableListenableBeanFactory;

/**
 * <p>
 *     提供修改BeanDefinition属性的机制
 * </p>
 *
 * @author pp
 * @since 2023/5/18
 */
public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(ConfigurableListenableBeanFactory beanFactory) throws BeansException;
}
