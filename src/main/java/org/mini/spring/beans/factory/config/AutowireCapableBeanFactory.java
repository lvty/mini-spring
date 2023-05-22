package org.mini.spring.beans.factory.config;

import org.mini.spring.beans.factory.BeanFactory;

/**
 * <p>
 *     自动化处理Bean工厂配置的接口
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 前置操作
     * @param existingBean
     * @param beanName
     * @return
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName);

    /**
     * 后置操作
     * @param existingBean
     * @param beanName
     * @return
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName);
}
