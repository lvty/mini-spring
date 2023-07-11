package org.mini.spring.context.support;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.ConfigurableListenableBeanFactory;
import org.mini.spring.beans.factory.config.BeanFactoryPostProcessor;
import org.mini.spring.beans.factory.config.BeanPostProcessor;
import org.mini.spring.beans.factory.support.DefaultListableBeanFactory;
import org.mini.spring.context.ApplicationEvent;
import org.mini.spring.context.ApplicationEventPublisher;
import org.mini.spring.context.ApplicationListener;
import org.mini.spring.context.ConfigurableApplicationContext;
import org.mini.spring.context.event.ApplicationEventMulticaster;
import org.mini.spring.context.event.ContextClosedEvent;
import org.mini.spring.context.event.SimpleApplicationEventMulticaster;
import org.mini.spring.core.io.DefaultResourceLoader;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 继承DefaultResourceLoader，主要完成spring.xml配置资源的加载
 * </p>
 *
 * @author pp
 * @since 2023/5/18
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        // 1. 创建BeanFactory, 并加载BeanDefinition
        refreshBeanFactory();

        // 2. 获取BeanFactory
        ConfigurableListenableBeanFactory beanFactory = getBeanFactory();

        // 2.1 添加ApplicationContextAwareProcessor, 让继承到ApplicationContextAware的Bean 对象都能
        // 感知到所属的ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));


        // 3. 在BeanFactory实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 4. 需要提前于其他Bean对象实例化之前执行注册BeanPostProcessor
        registerBeanPostProcessors(beanFactory);

        // 5. 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

        // 6. 初始化事件发布者
        initApplicationEventMulticaster();

        // 7. 注册事件监听器
        registerListeners();

        // 8. 发布容器刷新完成事件
        finishRefresh();
    }

    /**
     * 完成事件发布， 其实质还是依赖广播器完成事件的分发
     *
     * @param applicationEvent
     */
    @Override
    public void publishEvent(ApplicationEvent applicationEvent) {
        applicationEventMulticaster.multicastEvent(applicationEvent);
    }

    private void finishRefresh() {
        publishEvent(new ContextClosedEvent(this));
    }

    /**
     * 获取全部的监听器类型，并加入到广播器中
     */
    private void registerListeners() {
        /*Map beansOfType = getBeansOfType(ApplicationListener.class);
        if (Objects.nonNull(beansOfType)) {
            for (Object value : beansOfType.values()) {
                applicationEventMulticaster.addApplicationListener((ApplicationListener) value);
            }
        }*/

        // fixme: 此处要查找全部的Listener Bean
        Object customEventListener = getBean("customEventListener");
        applicationEventMulticaster.addApplicationListener((ApplicationListener) customEventListener);

    }

    /**
     * 初始化一个简单的事件广播器
     */
    private void initApplicationEventMulticaster() {
        ConfigurableListenableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);

        ((DefaultListableBeanFactory) beanFactory).
                registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    /**
     * 完成BeanFactory的创建， 和BeanDefinition的加载
     *
     * @throws BeansException
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListenableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListenableBeanFactory beanFactory) {
        // todo 完善此处的注册方法
        try {
            Object beanFactoryPostProcessor = beanFactory.getBean("BeanFactoryPostProcessor");
            if (Objects.nonNull(beanFactoryPostProcessor)) {
                ((BeanFactoryPostProcessor) beanFactoryPostProcessor).postProcessBeanFactory(beanFactory);
            }
        /*Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap =
                beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);

        for(BeanFactoryPostProcessor beanFactoryPostProcessor: beanFactoryPostProcessorMap.values()){
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }*/
        } catch (Exception ex) {

        }

    }

    private void registerBeanPostProcessors(ConfigurableListenableBeanFactory beanFactory) {
        // todo 完善此处的注册方法
        try {
            Object beanFactoryPostProcessor = beanFactory.getBean("BeanPostProcessor");
            if (Objects.nonNull(beanFactoryPostProcessor)) {
                beanFactory.addBeanPostProcessor((BeanPostProcessor) beanFactoryPostProcessor);
            }
        /*Map<String, BeanPostProcessor> beanFactoryPostProcessorMap =
                beanFactory.getBeansOfType(BeanPostProcessor.class);

        for(BeanPostProcessor beanPostProcessor: beanFactoryPostProcessorMap.values()){
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }*/
        } catch (Exception ex) {

        }

    }

    @Override
    public void close() {
        try {
            // 发布容器关闭事件
            publishEvent(new ContextClosedEvent(this));

            // 执行销毁Bean的方法
            getBeanFactory().destroySingletons();
        } catch (Exception ex) {
            throw new BeansException("ApplicationContext close error.", ex);
        }
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
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
