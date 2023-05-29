package org.mini.spring.beans.factory;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/29
 */
public interface BeanClassLoaderAware extends Aware {

    void setBeanClassLoader(ClassLoader classLoader);
}
