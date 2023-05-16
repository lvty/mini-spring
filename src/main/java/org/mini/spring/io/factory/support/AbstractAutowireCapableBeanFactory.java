package org.mini.spring.io.factory.support;

import org.mini.spring.io.BeansException;
import org.mini.spring.io.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * <p>
 * 完成自动注入的BeanFactory
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubClassingInstantiationStrategy();

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;
        try {
            // fixme: 此处只有无参构造才没有问题， 那么有参构造怎么实现?

            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 完成单例bean的注入
        addSingleton(name, bean);

        return bean;
    }

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            bean = createBeanInstance(beanDefinition, name, args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 完成单例bean的注入
        addSingleton(name, bean);

        return bean;
    }

    /**
     * 创建具体的Bean
     *
     * @param beanDefinition
     * @param name
     * @param args
     * @return
     */
    private Object createBeanInstance(BeanDefinition beanDefinition, String name, Object[] args) {
        Constructor constructor = null;
        // 根据BeanDefinition获取目标Constructor

        Class beanClass = beanDefinition.getBeanClass();
        // 全量的构造器
        Constructor[] declaredConstructors = beanClass.getDeclaredConstructors();
        for (Constructor declaredConstructor : declaredConstructors) {
            if (null != args && declaredConstructor.getParameterTypes().length == args.length) {
                constructor = declaredConstructor;
                break;
            }
        }
        return getInstantiationStrategy().instantiate(beanDefinition, name, constructor, args);
    }


    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }
}
