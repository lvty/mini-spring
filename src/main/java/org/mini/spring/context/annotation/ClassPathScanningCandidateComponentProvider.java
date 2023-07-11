package org.mini.spring.context.annotation;

import cn.hutool.core.util.ClassUtil;
import org.mini.spring.beans.factory.config.BeanDefinition;
import org.mini.spring.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/11
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);

        for (Class<?> aClass : classes) {
            candidates.add(new BeanDefinition(aClass));

        }

        return candidates;
    }

}
