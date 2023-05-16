package org.mini.spring.beans.factory.config;

/**
 * <p>
 *     单例注册接口
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public interface SingletonBeanRegistry {

    /**
     * 获取单例对象
     * @param beanName
     */
    Object getSingleton(String beanName);
}
