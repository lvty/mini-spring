package org.mini.spring.aop.framework;

/**
 * <p>
 *     实现关于方法的代理操作， 其子类实现主要包含JDK和Cglib代理。
 * </p>
 *
 * @author pp
 * @since 2023/6/11
 */
public interface AopProxy {

    /**
     * 获取代理类
     * @return
     */
    Object getProxy();
}
