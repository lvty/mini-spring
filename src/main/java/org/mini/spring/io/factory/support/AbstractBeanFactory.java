package org.mini.spring.io.factory.support;

import org.mini.spring.io.BeansException;
import org.mini.spring.io.factory.BeanFactory;
import org.mini.spring.io.factory.config.BeanDefinition;

/**
 * <p>
 * 抽象beanFactory， 定义模板方法
 * DefaultSingletonBeanRegistry: 具备使用单例注册类的方法
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    /**
     * 定义获取Bean的基础步骤
     *
     * @param name
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String name) throws BeansException {

        // 获取单例实现
        Object singleton = getSingleton(name);
        if (singleton != null) {
            return singleton;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }

    /**
     * 根据name和bean的定义创建bean
     * @param name
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition) throws BeansException;

    /**
     * 获取bean的定义信息
     * @param name
     * @return
     * @throws BeansException
     */
    protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;



}
