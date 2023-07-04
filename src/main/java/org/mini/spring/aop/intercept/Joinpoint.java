package org.mini.spring.aop.intercept;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/4
 */
public interface Joinpoint {

    /**
     * Proceed to the next interceptor in the chain.
     * <p>The implementation and the semantics of this method depends
     * on the actual joinpoint type (see the children interfaces).
     * @return see the children interfaces' proceed definition
     * @throws Throwable if the joinpoint throws an exception
     */
    Object proceed() throws Throwable;
}
