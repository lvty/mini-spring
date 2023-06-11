package org.mini.spring.aop;

/**
 * <p>
 *     实现方法的匹配, 切点表达式提供的内容
 * </p>
 *
 * @author pp
 * @since 2023/6/11
 */
public interface Pointcut {

    /**
     * return the {@link ClassFilter} for this pointcut.
     * @return
     */
    ClassFilter getClassFilter();

    /**
     * Return the {@link MethodMatcher} for this pointcut.
     */
    MethodMatcher getMethodMatcher();
}
