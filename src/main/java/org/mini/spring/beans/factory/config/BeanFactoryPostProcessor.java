package org.mini.spring.beans.factory.config;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.ConfigurableListenableBeanFactory;

/**
 * <p>
 *     提供修改BeanDefinition属性的机制：
 *     Allows for custom modification of an application context's bean definitions,
 *      adapting the bean property values of the context's underlying bean factory
 * </p>
 *
 * @author pp
 * @since 2023/5/18
 */
public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(ConfigurableListenableBeanFactory beanFactory) throws BeansException;
}
