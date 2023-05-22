package org.mini.spring.beans.factory.config;

import org.junit.Test;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.service.UserService;

import static org.junit.Assert.*;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/28
 */
public class MyBeanPostProcessorTest implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 对属性值进行修改
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("Modify to shanghai.");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}