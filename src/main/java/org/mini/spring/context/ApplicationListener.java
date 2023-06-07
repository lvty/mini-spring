package org.mini.spring.context;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/6/7
 */
public interface ApplicationListener<E extends ApplicationEvent> {

    /**
     * Handle an application event.
     * @param event the event to respond to
     */
    void onApplicationEvent(E event);
}
