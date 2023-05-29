package org.mini.spring.beans.factory;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/29
 */
public interface FactoryBean<T> {

    /**
     * 对象获取
     * @return
     */
    T getObject() throws Exception;

    /**
     * 对象类型
     */
    Class<?> getObjectType();

    /**
     * 是否单例对象
     * @return
     */
    boolean isSingleton();
}
