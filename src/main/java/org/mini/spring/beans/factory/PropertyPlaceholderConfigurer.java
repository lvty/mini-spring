package org.mini.spring.beans.factory;

import cn.hutool.core.util.ArrayUtil;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.PropertyValue;
import org.mini.spring.beans.PropertyValues;
import org.mini.spring.beans.factory.config.BeanDefinition;
import org.mini.spring.beans.factory.config.BeanFactoryPostProcessor;
import org.mini.spring.core.io.DefaultResourceLoader;
import org.mini.spring.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * <p>
 * 完成对配置文件的加载以及摘取占位符中的在属性文件里的配置， 这样就可以把提取到的配置信息放置到属性配置中.
 * </p>
 *
 * @author Administrator
 * @since 2023/7/11
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    // 前缀
    private static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    // 后缀
    private static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;


    @Override
    public void postProcessBeanFactory(ConfigurableListenableBeanFactory beanFactory) throws BeansException {
        // 加载属性文件
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource(location);

        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            if (ArrayUtil.isNotEmpty(beanDefinitionNames)) {
                for (String beanName : beanDefinitionNames) {
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                    PropertyValues propertyValues = beanDefinition.getPropertyValues();

                    for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {

                        Object propertyValueValue = propertyValue.getValue();

                        // fixme  此处只能处理String吗
                        if (!(propertyValueValue instanceof String)) continue;

                        String strValue = (String) propertyValueValue;

                        StringBuilder buffer = new StringBuilder(strValue);
                        int startIdx = buffer.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                        int stopIdx = buffer.lastIndexOf(DEFAULT_PLACEHOLDER_SUFFIX);

                        if (stopIdx != -1 && startIdx < stopIdx) {
                            String propKey = strValue.substring(startIdx + 2, stopIdx);

                            // fixme: 此处是否可以支持默认值
                            String property = properties.getProperty(propKey);

                            buffer.replace(startIdx, stopIdx + 1, property);

                            propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), buffer.toString()));
                        }

                    }

                }
            }

        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }


    }

    public void setLocation(String location) {
        this.location = location;
    }
}
