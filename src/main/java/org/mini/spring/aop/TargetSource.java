package org.mini.spring.aop;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/6/11
 */
public class TargetSource {


    private Object target;

    public Class<?>[] getTargetClass() {
        return new Class[]{target.getClass()};
    }


    public Object getTarget() {
        return target;
    }
}

