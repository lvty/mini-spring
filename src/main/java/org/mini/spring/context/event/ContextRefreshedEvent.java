package org.mini.spring.context.event;

/**
 * <p>
 *     监听ApplicationContext刷新
 * </p>
 *
 * @author pp
 * @since 2023/6/7
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {

    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
