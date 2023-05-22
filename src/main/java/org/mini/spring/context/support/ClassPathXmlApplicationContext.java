package org.mini.spring.context.support;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.config.BeanDefinition;

import java.util.Map;

/**
 * <p>
 *     具体对外给用户提供的应用上下文: 提供配置文件地址信息
 *
 * </p>
 *
 * @author pp
 * @since 2023/5/18
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    /**
     * 资源定义
     */
    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    /**
     * 从XML中加载BeanDefinition, 并刷新上下文
     * @param configLocation
     */
    public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
        this(new String[]{configLocation});
    }


    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }



    @Override
    public void getBeanDefinitionNames() {

    }
}
