package org.mini.spring.context.annotation;

import cn.hutool.core.util.StrUtil;
import org.mini.spring.beans.factory.config.BeanDefinition;
import org.mini.spring.beans.factory.support.BeanDefinitionRegistry;
import org.mini.spring.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * <p>
 *     除了获取到扫描的类信息以后，还需要获取 Bean 的作用域和类名，如果不配置类名基本都是把首字母缩写。
 * </p>
 *
 * @author Administrator
 * @since 2023/7/11
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> definitions = findCandidateComponents(basePackage);
            for (BeanDefinition definition : definitions) {

                String scope = resolveBeanScope(definition);

                if (StrUtil.isNotBlank(scope)) {
                    definition.setScope(scope);
                }

                //fixme: 不支持属性注入
//                definition.setPropertyValues();

                beanDefinitionRegistry.registerBeanDefinition(determineBeanName(definition), definition);
            }
        }
    }

    private String determineBeanName(BeanDefinition definition) {
        Class beanClass = definition.getBeanClass();
        Component component = (Component) beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }

    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class beanClass = beanDefinition.getBeanClass();
        Scope scope = (Scope) beanClass.getAnnotation(Scope.class);

        if (null != scope) return scope.value();

        return StrUtil.EMPTY;
    }


}
