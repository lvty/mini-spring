package org.mini.spring.io.factory.config;

import org.mini.spring.io.PropertyValues;

/**
 * <p>
 * 完成Bean的定义：
 * 1) 由初版的直接set bean修改为class定义的形式, 这样方便Bean的实例化操作放到容器中处理
 *
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public class BeanDefinition {

    private Class beanClass;

    /**
     * 当前BeanClass对应的全量属性信息集合
     */
    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
