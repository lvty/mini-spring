package org.mini.spring.beans.factory.config;

import org.mini.spring.beans.PropertyValues;

/**
 * <p>
 * 完成Bean的定义：
 * 1) 由初版的直接set bean修改为class定义的形式, 这样方便Bean的实例化操作放到容器中处理
 * 2) initMethodName/destroyMethodName
 *  均可以在spring.xml配置的Bean对象中，配置init-method="xx" destroy-method="xx"操作， 其实现思路和接口是一样的
 *      只不过接口是直接调用， 而配置是需要通过反射的方法来实现的
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

    /**
     * 初始化方法名称
     */
    private String initMethodName;

    /**
     * 销毁方法名称
     */
    private String destroyMethodName;
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

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
