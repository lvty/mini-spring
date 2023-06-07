package org.mini.spring.context.event;

import cn.hutool.core.collection.CollectionUtil;
import org.mini.spring.beans.factory.config.ConfigurableBeanFactory;
import org.mini.spring.context.ApplicationEvent;
import org.mini.spring.context.ApplicationListener;

import java.util.Collection;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/6/7
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(ConfigurableBeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent applicationEvent) {
        Collection<ApplicationListener> allListeners = getApplicationListeners(applicationEvent);
        if (CollectionUtil.isNotEmpty(allListeners)) {
            for (ApplicationListener listener : allListeners) {
                listener.onApplicationEvent(applicationEvent);
            }
        }
    }
}
