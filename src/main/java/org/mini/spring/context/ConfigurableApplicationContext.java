package org.mini.spring.context;

import org.mini.spring.beans.BeansException;

/**
 * <p>
 *     继承自ApplicationContext， 比提供refresh核心方法， 需要子类完成刷新容器的操作；
 * </p>
 *
 * @author pp
 * @since 2023/5/18
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 完成容器的刷新动作
     * @throws BeansException
     */
    void refresh() throws BeansException;

    void registerShutdownHook();

    /**
     * 执行手动关闭的钩子方法
     */
    void close();
}
