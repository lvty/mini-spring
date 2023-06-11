package org.mini.spring.aop.aspectj;

import org.mini.spring.aop.ClassFilter;
import org.mini.spring.aop.MethodMatcher;
import org.mini.spring.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.HashSet;

/**
 * <p>
 *     只要依赖AspectJ组件并处理Pointcut/ClassFilter/MethodMatcher接口实现
 *     专门用于处理类和方法的匹配实现
 * </p>
 *
 * @author pp
 * @since 2023/6/11
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    private static final Set<PointcutPrimitive> SUPPORT_PRIMITIVES = new HashSet<PointcutPrimitive>();

    static {
        SUPPORT_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    private final PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParser = ;

        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
