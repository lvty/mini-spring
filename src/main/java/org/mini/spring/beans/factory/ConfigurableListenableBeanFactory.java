package org.mini.spring.beans.factory;

import org.mini.spring.beans.factory.config.*;

/**
 * <p>
 * 提供分析和修改Bean以及预先实例化的操作接口
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public interface ConfigurableListenableBeanFactory extends ListableBeanFactory,
        ConfigurableBeanFactory, AutowireCapableBeanFactory {

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void preInstantiateSingletons();

    BeanDefinition getBeanDefinition(String beanName);

}
