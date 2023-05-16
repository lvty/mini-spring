package org.mini.spring.io.factory.support;

import org.mini.spring.io.BeansException;
import org.mini.spring.io.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     BeanDefinitionRegistry： 定义了BeanDefinition注册
 *     AbstractAutowireCapableBeanFactory 定义了Bean的创建和注入，创建依赖BeanDefinition，
 *          所以抽象类定义了模板方法： getBeanDefinition
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    protected BeanDefinition getBeanDefinition(String name) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(null == beanDefinition){
            throw new BeansException("No bean named " + name + " is defined.");
        }
        return beanDefinition;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }
}
