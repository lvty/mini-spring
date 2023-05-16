package org.mini.spring.beans.factory.support;

import org.mini.spring.beans.BeansException;
import org.mini.spring.core.io.Resource;
import org.mini.spring.core.io.ResourceLoader;

/**
 * <p>
 *     定义Bean读取的接口及其对应的实现类， 完成Bean对象的解析、注册到Spring容器中
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public interface BeanDefinitionReader {

    /**
     * 获取Bean定义注册器
     * @return
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取资源加载器
     * @return
     */
    ResourceLoader getResourceLoader();

    /**
     * 以上两个方法均是为了下面的接口服务的！！
     */

    /**
     * 根据现有Resource加载定义Bean
     * @param resource
     * @throws BeansException
     */
    void loadBeanDefinitions(Resource resource) throws BeansException;

    /**
     * 根据现有Resource加载定义Bean
     * @param resource
     * @throws BeansException
     */
    void loadBeanDefinitions(Resource... resource) throws BeansException;

    /**
     * 根据指定的位置加载定义Bean
     * @param location
     * @throws BeansException
     */
    void loadBeanDefinitions(String location) throws BeansException;
}
