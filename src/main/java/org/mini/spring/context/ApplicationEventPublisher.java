package org.mini.spring.context;

/**
 * <p>
 *     所有的事件都需要通过这个接口发布出去
 * </p>
 *
 * @author pp
 * @since 2023/6/7
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent applicationEvent);
}
