package org.mini.spring.beans.factory.config;

import org.mini.spring.beans.factory.HierarchicalBeanFactory;

/**
 * <p>
 *     可以获取BeanPostProcessor/BeanClassLoader等配置化接口
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory {

    /**
     * 销毁方法
     */
    void destroySingletons() throws Exception;
}
