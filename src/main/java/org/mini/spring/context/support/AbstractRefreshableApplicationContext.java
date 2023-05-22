package org.mini.spring.context.support;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.ConfigurableListenableBeanFactory;
import org.mini.spring.beans.factory.support.DefaultListableBeanFactory;

/**
 * <p>
 *      获取Bean工厂和加载资源
 * </p>
 *
 * @author pp
 * @since 2023/5/18
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 主要完成DefaultListableBeanFactory的实例化以及对资源配置的加载操作
     *              loadBeanDefinitions， 加载完成后即可完成对spring.xml配置文件中的Bean
     *              对象的定义和注册，同时也包括实现了接口BeanFactoryPostProcessor、BeanPostProcessor的配置Bean信息
     * @throws BeansException
     */
    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 完成Bean的定义
     * @param beanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);


    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }


    @Override
    public ConfigurableListenableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
