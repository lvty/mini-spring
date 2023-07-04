package org.mini.spring.aop.intercept;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/4
 */
public interface Invocation extends Joinpoint {

    Object[] getArguments();
}
