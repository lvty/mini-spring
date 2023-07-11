package org.mini.spring.beans.factory;

import java.util.Map;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public interface ListableBeanFactory<T> extends BeanFactory {

    Map<String, T> getBeansOfType(Class<T> type);

    String[] getBeanDefinitionNames();
}
