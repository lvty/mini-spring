package org.mini.spring.beans.factory;

/**
 * <p>
 *     interface to be implemented by means that wish to be aware if their owning {@link BeanFactory}
 * </p>
 *
 * @author pp
 * @since 2023/5/29
 */
public interface BeanFactoryAware extends Aware{

    /**
     * 实现此方法， 感知到所属的BeanFactory
     * @param beanFactory
     */
    void setBeanFactory(BeanFactory beanFactory);
}
