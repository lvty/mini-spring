package org.mini.spring.beans.factory;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/28
 */
public interface DisposableBean {

    void destroy() throws Exception;
}
