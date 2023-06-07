package org.mini.spring.context;

/**
 * <p>
 *     事件对象
 * </p>
 *
 * @author pp
 * @since 2023/6/7
 */
public abstract class EventObject {

    private final Object source;

    public EventObject(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }
}
