package org.mini.spring.io.factory.support;

import org.mini.spring.io.BeansException;
import org.mini.spring.io.factory.config.BeanDefinition;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory {

    @Override
    protected BeanDefinition getBeanDefinition(String name) throws BeansException {
        return null;
    }
}
