package org.mini.spring.io.factory.config;

/**
 * <p>
 *     完成Bean的定义：
 *     1) 由初版的直接set bean修改为class定义的形式, 这样方便Bean的实例化操作放到容器中处理
 *
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public class BeanDefinition {

    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
