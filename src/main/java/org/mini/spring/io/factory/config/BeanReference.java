package org.mini.spring.io.factory.config;

/**
 * <p>
 *     根据Bean对应的名称， 方便在BeanFactory中递归创建和填充
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public class BeanReference {

    /**
     * 当前引用对应的Bean名称
     */
    private String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
