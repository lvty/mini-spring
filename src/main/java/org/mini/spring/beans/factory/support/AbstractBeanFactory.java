package org.mini.spring.beans.factory.support;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.BeanFactory;
import org.mini.spring.beans.factory.FactoryBean;
import org.mini.spring.beans.factory.config.BeanDefinition;
import org.mini.spring.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 抽象beanFactory， 定义模板方法
 * DefaultSingletonBeanRegistry: 具备获取/注册 单例类的方法
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements BeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.add(beanPostProcessor);
    }

    protected <T> T doGetBean(final String name, final Object[] args){
        Object singleton = getSingleton(name);
        if(null != singleton){
            // 如果是FactoryBean， 则需要调用FactoryBean.getObject
            return (T) getObjectForBeanInstance(singleton, name);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, name);
    }

    /**
     * 针对FactoryBean的操作:
     *  获取到Bean之后， 都会判断一下是否是FactoryBean， 如果是， 那么会从FactoryBean中获取
     *      先从缓存中获取， 如果能获取到， 那么直接返回；
     *      否则， 如果是单例Bean， 那么创建完之后要放到缓存中去；
     *              如果不是单例Bean， 直接调用FactoryBean去创建。
     * @param bean
     * @param name
     * @return
     */
    private Object getObjectForBeanInstance(Object bean, String name) {
        if(!(bean instanceof FactoryBean)){
            return bean;
        }

        Object obj = getCachedObjectForFactoryBean(name);
        if(obj == null){
            FactoryBean<?> factoryBean = (FactoryBean<?>) bean;
            obj = getObjectFromFactoryBean(factoryBean, name);
        }
        return obj;
    }


    /**
     * 定义获取Bean的基础步骤
     *
     * @param name
     * @return
     * @throws BeansException
     */
    @Override
    public Object getBean(String name) throws BeansException {

        /*// 获取单例实现
        Object singleton = getSingleton(name);
        if (singleton != null) {
            return singleton;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);*/
        return doGetBean(name, null);
    }


    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        /*// 获取单例实现
        Object singleton = getSingleton(name);
        if (singleton != null) {
            return singleton;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition, args);*/

        return doGetBean(name, args);
    }

    /**
     * 根据name和bean的定义创建bean
     * @param name
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    /**
     * 根据name和bean的定义创建bean
     * @param name
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    protected abstract Object createBean(String name, BeanDefinition beanDefinition) throws BeansException;

    /**
     * 获取bean的定义信息
     * @param name
     * @return
     * @throws BeansException
     */
    protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;



}
