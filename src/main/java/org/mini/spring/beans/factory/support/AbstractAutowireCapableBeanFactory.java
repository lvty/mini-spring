package org.mini.spring.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.PropertyValue;
import org.mini.spring.beans.PropertyValues;
import org.mini.spring.beans.factory.*;
import org.mini.spring.beans.factory.config.AutowireCapableBeanFactory;
import org.mini.spring.beans.factory.config.BeanDefinition;
import org.mini.spring.beans.factory.config.BeanPostProcessor;
import org.mini.spring.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 完成自动注入的BeanFactory， 自动注入等价于bean的创建且加入到容器中：
 * Bean的初始化按照构造函数区分
 * 当前类值完成Bean的创建和加入到单例容器中
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubClassingInstantiationStrategy();

    @Override
    protected Object createBean(String name, BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;
        try {
            // fixme: 此处只有无参构造才没有问题， 那么有参构造怎么实现?

            bean = beanDefinition.getBeanClass().newInstance();

            // 完成Bean的创建之后， 此处需要完成对象的属性填充，对象的属性包含基础类型和引用类型
            applyPropertyValues(name, bean, beanDefinition);

            // 执行Bean的初始化方法和BeanPostProcessor的前置和后置处理方法
            bean = initializeBean(name, bean, beanDefinition);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 注册：实现了DisposableBean接口的全部Bean对象
        registerDisposableBeanIfNecessary(name, bean, beanDefinition);

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

            // 执行Bean的初始化方法和BeanPostProcessor的前置和后置处理方法
            bean = initializeBean(name, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 注册：实现了DisposableBean接口的全部Bean对象
        registerDisposableBeanIfNecessary(name, bean, beanDefinition);

        // 完成单例bean的注入: 单例和原型的区别就在是否需要放置到内存中
        // add: 添加单例判断
        if (beanDefinition.isSingleton()) {
            addSingleton(name, bean);
        }

        return bean;
    }

    private void registerDisposableBeanIfNecessary(String name, Object bean, BeanDefinition beanDefinition) {
        // 非Singleton类型的Bean不执行销毁方法
        if (!beanDefinition.isSingleton()) return;


        if (bean instanceof DisposableBean || StrUtil.isNotBlank(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(name, new DisposableBeanAdapter(bean, name, beanDefinition));
        }
    }

    private Object initializeBean(String name, Object bean, BeanDefinition beanDefinition) {
        // 2023-05-29: 添加invokeAwareMethods
        // 感知到已实现Aware接口的类
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }

            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }

            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(name);
            }
        }


        // 1. 执行BeanPostProcessor Before 处理
        Object wrapped = applyBeanPostProcessorsBeforeInitialization(bean, name);

        try {
            invokeInitMethods(name, wrapped, beanDefinition);
        } catch (Exception ex) {
            throw new BeansException("Invocation of init method of bean[" + name + "] failed", ex);
        }
        // 3. 执行BeanPostProcessor after 处理
        wrapped = applyBeanPostProcessorsAfterInitialization(bean, name);
        return wrapped;
    }

    private ClassLoader getBeanClassLoader() {
        return null;
    }

    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition)
            throws Exception {
        // 1. 判断是否实现初始化接口来实现初始化扩展
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // 2. 或者通过配置信息的方式来完成扩展
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotBlank(initMethodName)) {
            Method method = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == initMethodName) {
                throw new BeansException("Could not find an init method names '"
                        + initMethodName + "' on bean with name '" + beanName + "'");
            }
            // fixme, 此处是有参初始化方法怎么处理
            method.invoke(bean);
        }
    }


    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException {

        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException {

        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
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
