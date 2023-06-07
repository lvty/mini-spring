package org.mini.spring.context.event;

import org.mini.spring.context.ApplicationContext;
import org.mini.spring.context.ApplicationEvent;

/**
 * <p>
 *     ApplicationContext事件类， 所有的事件包括: 关闭、刷新以及用户自定义实现的事件都需要继承这个类
 * </p>
 *
 * @author pp
 * @since 2023/6/7
 */
public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
