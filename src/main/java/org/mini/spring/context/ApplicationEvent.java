package org.mini.spring.context;

/**
 * <p>
 *     事件抽象类， 所有的具体事件类都需要继承这个类
 * </p>
 *
 * @author pp
 * @since 2023/6/7
 */
public abstract class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object source) {
        super(source);
    }
}
