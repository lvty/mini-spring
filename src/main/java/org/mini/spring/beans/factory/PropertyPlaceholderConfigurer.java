package org.mini.spring.beans.factory;

import cn.hutool.core.util.ArrayUtil;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.PropertyValue;
import org.mini.spring.beans.PropertyValues;
import org.mini.spring.beans.factory.config.BeanDefinition;
import org.mini.spring.beans.factory.config.BeanFactoryPostProcessor;
import org.mini.spring.beans.factory.support.DefaultListableBeanFactory;
import org.mini.spring.core.io.DefaultResourceLoader;
import org.mini.spring.core.io.Resource;
import org.mini.spring.utils.StringValueResolver;

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

            // 向容器中添加字符串解析器， 动态解析@value注解使用
            ((DefaultListableBeanFactory)beanFactory)
                    .addEmbeddedValueResolver(new PlaceholderResolvingStringValueResolver(properties));

        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }


    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String replacePlaceholders(String strValue){
        StringBuilder buffer = new StringBuilder(strValue);
        int startIdx = buffer.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int stopIdx = buffer.lastIndexOf(DEFAULT_PLACEHOLDER_SUFFIX);

        if (stopIdx != -1 && startIdx < stopIdx) {
            return strValue.substring(startIdx + 2, stopIdx);
        }
        return strValue;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        @Override
        public String resolveStringValue(String strVal) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(replacePlaceholders(strVal), properties);
        }
    }




    /** Never check system properties. */
    public static final int SYSTEM_PROPERTIES_MODE_NEVER = 0;

    /**
     * Check system properties if not resolvable in the specified properties.
     * This is the default.
     */
    public static final int SYSTEM_PROPERTIES_MODE_FALLBACK = 1;

    /**
     * Check system properties first, before trying the specified properties.
     * This allows system properties to override any other property source.
     */
    public static final int SYSTEM_PROPERTIES_MODE_OVERRIDE = 2;


    protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
        String propVal = null;
        if (systemPropertiesMode == SYSTEM_PROPERTIES_MODE_OVERRIDE) {
            propVal = resolveSystemProperty(placeholder);
        }
        if (propVal == null) {
            propVal = resolvePlaceholder(placeholder, props);
        }
        if (propVal == null && systemPropertiesMode == SYSTEM_PROPERTIES_MODE_FALLBACK) {
            propVal = resolveSystemProperty(placeholder);
        }
        return propVal;
    }

    protected String resolveSystemProperty(String key) {
        try {
            String value = System.getProperty(key);
            if (value == null /*&& this.searchSystemEnvironment*/) {
                value = System.getenv(key);
            }
            return value;
        }
        catch (Throwable ex) {
            return null;
        }
    }

    protected String resolvePlaceholder(String placeholder, Properties props) {
        return props.getProperty(placeholder);
    }
}
