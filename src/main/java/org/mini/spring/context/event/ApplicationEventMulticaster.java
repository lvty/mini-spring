package org.mini.spring.context.event;

import org.mini.spring.context.ApplicationEvent;
import org.mini.spring.context.ApplicationListener;

/**
 * <p>
 *     完成注册监听器和发布事件的广播器，提供添加、移除监听器和发布事件的方法
 * </p>
 *
 * @author pp
 * @since 2023/6/7
 */
public interface ApplicationEventMulticaster {

    /**
     * 注册监听器
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 移除监听器
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件
     * @param applicationEvent
     */
    void multicastEvent(ApplicationEvent applicationEvent);
}
