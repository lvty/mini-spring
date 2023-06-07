package org.mini.spring.context.event;

/**
 * <p>
 *     监听ApplicationContext关闭
 * </p>
 *
 * @author pp
 * @since 2023/6/7
 */
public class ContextClosedEvent extends ApplicationContextEvent {

    public ContextClosedEvent(Object source) {
        super(source);
    }
}
