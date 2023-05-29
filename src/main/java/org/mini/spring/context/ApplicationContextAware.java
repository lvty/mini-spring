package org.mini.spring.context;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/29
 */
public interface ApplicationContextAware {

    /**
     * 感知到所属的applicationContext
     * @param applicationContext
     */
    void setApplicationContext(ApplicationContext applicationContext);
}
