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
     * Scope identifier for the standard singleton scope: {@value}.
     * <p>Custom scopes can be added via {@code registerScope}.
     */
    String SCOPE_SINGLETON = "singleton";

    /**
     * Scope identifier for the standard prototype scope: {@value}.
     * <p>Custom scopes can be added via {@code registerScope}.
     */
    String SCOPE_PROTOTYPE = "prototype";


    /**
     * 销毁方法
     */
    void destroySingletons() throws Exception;
}
