package org.mini.spring.beans.factory.support;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.ConfigurableListenableBeanFactory;
import org.mini.spring.beans.factory.config.BeanDefinition;
import org.mini.spring.beans.factory.config.BeanPostProcessor;

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
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListenableBeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String name) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(null == beanDefinition){
            throw new BeansException("No bean named " + name + " is defined.");
        }
        return beanDefinition;
    }

    @Override
    public void preInstantiateSingletons() {

    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return beanDefinitionMap.containsKey(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return null;
    }

    @Override
    public Map getBeansOfType(Class type) {
        return null;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[0]);
    }
}
