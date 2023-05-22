package org.mini.spring.beans.factory.config;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.PropertyValue;
import org.mini.spring.beans.PropertyValues;
import org.mini.spring.beans.factory.ConfigurableListenableBeanFactory;
import org.mini.spring.beans.factory.support.DefaultListableBeanFactory;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/28
 */
public class MyBeanFactoryPostProcessorTest implements BeanFactoryPostProcessor{

    @Override
    public void postProcessBeanFactory(ConfigurableListenableBeanFactory beanFactory) throws BeansException {
        if(beanFactory instanceof DefaultListableBeanFactory){
            DefaultListableBeanFactory beanFactory1 = (DefaultListableBeanFactory) beanFactory;
            BeanDefinition userService = beanFactory1.getBeanDefinition("userService");

            PropertyValues propertyValues = userService.getPropertyValues();
            propertyValues.addPropertyValue(new PropertyValue("company", "alibaba"));
        }
    }
}