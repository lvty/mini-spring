package org.mini.spring.aop;

/**
 * <p>
 *     定义类匹配类，用于切点找到给定的接口和目标类
 * </p>
 *
 * @author pp
 * @since 2023/6/11
 */
public interface ClassFilter {

    /**
     * Should the pointcut apply to the given interface or target class ?
     * @param clazz
     * @return
     */
    boolean matches(Class<?> clazz);



}
