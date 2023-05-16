package org.mini.spring.beans.factory;

import org.mini.spring.beans.BeansException;

/**
 * <p>
 *     bean 工厂:
 *     完成主要功能是： Bean的注册和获取，此处注册的是BeanDefinition
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public interface BeanFactory {

    /**
     * 定义bean的缓存数据
     *//*
    private Map<String, BeanDefinition> beanDefinitionMap
            = new ConcurrentHashMap<>();

    public Object getBean(String name){
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(Objects.nonNull(beanDefinition)){
            return beanDefinition.getBean();
        }
        throw new UnsupportedOperationException(name + " is not support.");
    }

    public void registerBean(String name, BeanDefinition beanDefinition){
        beanDefinitionMap.putIfAbsent(name, beanDefinition);
    }*/

    /**
     * 获取Bean: 无参构造形式
     * @param name
     * @return
     */
    Object getBean(String name) throws BeansException;

    /**
     * 获取Bean： 有参构造形式
     * @param name
     * @param args
     * @return
     * @throws BeansException
     */
    Object getBean(String name, Object...args) throws BeansException;


    /**
     * 按照类型获取Bean
     * @param name
     * @param requiredType
     * @return
     * @param <T>
     */
    <T> T getBean(String name, Class<T> requiredType);

}
