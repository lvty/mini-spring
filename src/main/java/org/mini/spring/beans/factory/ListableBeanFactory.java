package org.mini.spring.beans.factory;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public interface ListableBeanFactory<T> extends BeanFactory {

    void getBeansOfType(Class<T> type);

    void getBeanDefinitionNames();

}
