package org.mini.spring.context.event;

import org.mini.spring.beans.factory.BeanFactory;
import org.mini.spring.beans.factory.BeanFactoryAware;
import org.mini.spring.context.ApplicationContext;
import org.mini.spring.context.ApplicationEvent;
import org.mini.spring.context.ApplicationListener;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>
 *     TODO
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


    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent applicationEvent) {

    }


    @Override
    public void multicastEvent(ApplicationEvent applicationEvent) {

    }
}
