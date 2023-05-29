package org.mini.spring.beans.factory;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/29
 */
public interface BeanNameAware  extends Aware{

    /**
     * 感知到所属的BeanName
     * @param name
     */
    void setBeanName(String name);
}
