package org.mini.spring.aop;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/5
 */
public interface Advisor {

    /**
     * Return the advice part of this aspect. An advice may be an interceptor, a before advice, a throws advice, etc.
     *
     * @return the advice that should apply if the pointcut matches
     */
    Advice getAdvice();

}
