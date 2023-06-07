package org.mini.spring.context.event;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.BeanFactory;
import org.mini.spring.beans.factory.BeanFactoryAware;
import org.mini.spring.context.ApplicationContext;
import org.mini.spring.context.ApplicationEvent;
import org.mini.spring.context.ApplicationListener;
import org.mini.spring.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * <p>
 *     完成对事件广播器的基础方法的抽取
 * </p>
 *
 * @author pp
 * @since 2023/6/7
 */
public abstract class AbstractApplicationEventMulticaster implements
        ApplicationEventMulticaster, BeanFactoryAware {

    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners =
            new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }


    /**
     * 获取对当前事件感兴趣的监听器，换言之：过滤并获取符合广播事件中的监听处理器
     *
     * @param applicationEvent
     * @return
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent applicationEvent) {
        LinkedList<ApplicationListener> allListeners = new LinkedList<>();
        for (ApplicationListener<ApplicationEvent> applicationListener : applicationListeners) {
            if (supportsEvent(applicationListener, applicationEvent)) {
                allListeners.add(applicationListener);
            }

        }
        return allListeners;
    }

    /**
     * 当前监听器是否对这个事件感兴趣：
     *  针对不同的实例化策略：
     *      - Cglib需要获取父类
     *      - 普通策略不需要获取目标Class
     * @param applicationListener
     * @param applicationEvent
     * @return
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent applicationEvent) {

        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

        // 获取监听器的目标Class类型
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass.getName()) ?
                listenerClass.getSuperclass() : listenerClass;

        Type genericInterface = targetClass.getGenericInterfaces()[0];

        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];

        String className = actualTypeArgument.getTypeName();

        Class<?> eventClassName;

        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name:" + className,e);
        }

        // 判断监听器的参数类型是否为当前事件的父Class类型或者类型两者是一致的
        return eventClassName.isAssignableFrom(applicationEvent.getClass());
    }



}
