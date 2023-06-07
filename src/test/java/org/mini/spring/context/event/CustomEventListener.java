package org.mini.spring.context.event;

import org.mini.spring.context.ApplicationListener;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/6/8
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("get msg: " + event);
    }
}
