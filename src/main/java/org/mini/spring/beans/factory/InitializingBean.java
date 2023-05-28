package org.mini.spring.beans.factory;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/28
 */
public interface InitializingBean {

    /**
     * 处理了属性填充后的调用
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;
}
