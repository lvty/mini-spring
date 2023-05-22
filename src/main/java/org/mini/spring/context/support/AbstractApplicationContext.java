package org.mini.spring.context.support;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.ConfigurableListenableBeanFactory;
import org.mini.spring.beans.factory.config.BeanFactoryPostProcessor;
import org.mini.spring.beans.factory.config.BeanPostProcessor;
import org.mini.spring.context.ConfigurableApplicationContext;
import org.mini.spring.core.io.DefaultResourceLoader;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *     继承DefaultResourceLoader，主要完成spring.xml配置资源的加载
 * </p>
 *
 * @author pp
 * @since 2023/5/18
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        // 1. 创建BeanFactory, 并加载BeanDefinition
        refreshBeanFactory();

        // 2. 获取BeanFactory
        ConfigurableListenableBeanFactory beanFactory = getBeanFactory();

        // 3. 在BeanFactory实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 4. 需要提前于其他Bean对象实例化之前执行注册BeanPostProcessor
        registerBeanPostProcessors(beanFactory);

        // 5. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 完成BeanFactory的创建， 和BeanDefinition的加载
     * @throws BeansException
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListenableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListenableBeanFactory beanFactory){
        // todo 完善此处的注册方法
        Object beanFactoryPostProcessor = beanFactory.getBean("BeanFactoryPostProcessor");
        if(Objects.nonNull(beanFactoryPostProcessor)){
            ((BeanFactoryPostProcessor) beanFactoryPostProcessor).postProcessBeanFactory(beanFactory);
        }
        /*Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap =
                beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);

        for(BeanFactoryPostProcessor beanFactoryPostProcessor: beanFactoryPostProcessorMap.values()){
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }*/
    }

    private void registerBeanPostProcessors(ConfigurableListenableBeanFactory beanFactory){
        // todo 完善此处的注册方法
        Object beanFactoryPostProcessor = beanFactory.getBean("BeanPostProcessor");
        if(Objects.nonNull(beanFactoryPostProcessor)){
            beanFactory.addBeanPostProcessor((BeanPostProcessor) beanFactoryPostProcessor);
        }
        /*Map<String, BeanPostProcessor> beanFactoryPostProcessorMap =
                beanFactory.getBeansOfType(BeanPostProcessor.class);

        for(BeanPostProcessor beanPostProcessor: beanFactoryPostProcessorMap.values()){
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }*/
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public Map getBeansOfType(Class type) {
        return null;
    }
}
