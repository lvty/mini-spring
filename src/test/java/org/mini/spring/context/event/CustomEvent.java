package org.mini.spring.context.event;

import org.mini.spring.context.ApplicationEvent;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/6/8
 */
public class CustomEvent extends ApplicationEvent {

    private Long id;
    private String msg;

    public CustomEvent(Object source, Long id, String msg) {
        super(source);
        this.id = id;
        this.msg = msg;
    }

    public CustomEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return "CustomEvent{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                '}';
    }
}
