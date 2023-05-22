package org.mini.spring.beans.factory.support;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.BeanFactory;
import org.mini.spring.beans.factory.config.BeanDefinition;
import org.mini.spring.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 抽象beanFactory， 定义模板方法
 * DefaultSingletonBeanRegistry: 具备获取/注册 单例类的方法
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }


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


    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        // 获取单例实现
        Object singleton = getSingleton(name);
        if (singleton != null) {
            return singleton;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition, args);
    }

    /**
     * 根据name和bean的定义创建bean
     * @param name
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object[] args) throws BeansException;

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
