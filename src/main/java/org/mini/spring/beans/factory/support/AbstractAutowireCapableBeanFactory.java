package org.mini.spring.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.PropertyValue;
import org.mini.spring.beans.PropertyValues;
import org.mini.spring.beans.factory.config.BeanDefinition;
import org.mini.spring.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 * <p>
 * 完成自动注入的BeanFactory， 自动注入等价于bean的创建且加入到容器中：
 * Bean的初始化按照构造函数区分
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

            // 完成Bean的创建之后， 此处需要完成对象的属性填充，对象的属性包含基础类型和引用类型
            applyPropertyValues(name, bean, beanDefinition);
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

            // 完成Bean的创建之后， 此处需要完成对象的属性填充，对象的属性包含基础类型和引用类型
            applyPropertyValues(name, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 完成单例bean的注入
        addSingleton(name, bean);

        return bean;
    }

    /**
     * 完成属性的填充
     *
     * @param name
     * @param bean
     * @param beanDefinition
     */
    private void applyPropertyValues(String name, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {

                String pName = propertyValue.getName();
                Object pValue = propertyValue.getValue();

                if (pValue instanceof BeanReference) {
                    // A 依赖B， 获取B的实例化
                    BeanReference beanReference = (BeanReference) pValue;

                    // fixme: 此处的Bean获取如果是有参形式怎么处理， 对应的Bean应该调用哪个方法
                    pValue = getBean(beanReference.getBeanName());
                }

                // 属性填充
                // fixme: 循环依赖怎么处理？
                BeanUtil.setFieldValue(bean, pName, pValue);
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values: " + name);
        }
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

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
