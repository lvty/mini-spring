package org.mini.spring.aop;

import java.lang.reflect.Method;

/**
 * <p>
 *     方法匹配，找到表达式范围内匹配下的目标类和方法
 * </p>
 *
 * @author pp
 * @since 2023/6/11
 */
public interface MethodMatcher {

    /**
     * 方法匹配，找到表达式范围内匹配下的目标类和方法
     * @param method
     * @param targetClass
     * @return
     */
    boolean matches(Method method, Class<?> targetClass);
}
