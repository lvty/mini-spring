package org.mini.spring.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.PropertyValue;
import org.mini.spring.beans.PropertyValues;
import org.mini.spring.beans.factory.BeanFactory;
import org.mini.spring.beans.factory.BeanFactoryAware;
import org.mini.spring.beans.factory.ConfigurableListenableBeanFactory;
import org.mini.spring.beans.factory.config.BeanPostProcessor;
import org.mini.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.mini.spring.utils.ClassUtils;

import java.lang.reflect.Field;

/**
 * <p>
 * Bean对象实例化完成之后，设置属性操作之前，进行属性信息的处理和操作。
 * 主要处理包含: @Value, @Autowired注解的属性， 进行属性信息的提取和设置
 * </p>
 *
 * @author Administrator
 * @since 2023/7/12
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListenableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListenableBeanFactory) beanFactory;
    }

    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) {
        // 处理注解 @Value
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (null != valueAnnotation) {
                String value = valueAnnotation.value();

                value = beanFactory.resolveEmbeddedValue(value);
                BeanUtil.setFieldValue(bean, field.getName(), value);
            }
        }

        // 处理注解 @Autowired
        for (Field field : fields) {
            Autowired fieldAnnotation = field.getAnnotation(Autowired.class);
            if (null != fieldAnnotation) {
                Class<?> fieldType = field.getType();

                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                String dependentBeanName = null;
                if (null != qualifier) {
                    dependentBeanName = qualifier.value();
                    // fixme: 此处没有给出Class
                    dependentBean = beanFactory.getBean(dependentBeanName, fieldType);
                } else {
                    dependentBean = beanFactory.getBean(StrUtil.lowerFirst(field.getName()));
                }

                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }

        return pvs;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
        return null;
    }


}
