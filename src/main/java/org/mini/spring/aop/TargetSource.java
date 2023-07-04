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

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetClass() {
        return target.getClass().getInterfaces();
    }


    public Object getTarget() {
        return target;
    }
}

