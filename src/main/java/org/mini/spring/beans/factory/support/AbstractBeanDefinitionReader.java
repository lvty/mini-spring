package org.mini.spring.beans.factory.support;

import org.mini.spring.core.io.DefaultResourceLoader;
import org.mini.spring.core.io.ResourceLoader;

/**
 * <p>
 *     抽象类处理非接口功能外的注册Bean组件填充：
 *      外部可以完成bean注入工具类和资源加载工具类
 *     这样在具体的实现类中， 就可以把解析后的XML文件中的Bean信息注入到Spring容器中，
 *      而不再需要代码的硬编码完成对象配置信息的注入, 比如单元测试中， 定义的一系列BeanDefinition信息都可以挪到XML配置文件中去；
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
