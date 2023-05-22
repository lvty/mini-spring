package org.mini.spring.context.support;

import org.mini.spring.beans.factory.support.DefaultListableBeanFactory;
import org.mini.spring.beans.factory.support.XMLBeanDefinitionReader;
import org.mini.spring.core.io.Resource;

/**
 * <p>
 *     上下文中对配置信息的加载
 * </p>
 *
 * @author pp
 * @since 2023/5/18
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    /**
     * 初始化XMLBeanDefinitionReader，完成XML文件配置信息的操作
     * @param beanFactory
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XMLBeanDefinitionReader xmlBeanDefinitionReader =
                new XMLBeanDefinitionReader(beanFactory, this);

        String[] configLocations = getConfigLocations();
        if(null != configLocations){
            xmlBeanDefinitionReader.loadBeanDefinitions(configLocations);
        }

    }

    /**
     * 从入口上下文类， 获取配置信息的地址描述
     * @return
     */
    protected abstract String[] getConfigLocations();
}
