package org.mini.spring.aop.aspectj;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.mini.spring.aop.*;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *     把切面 pointcut、拦截方法 advice 和具体的拦截表达式包装在一起。这样就可以在 xml
 * 的配置中定义一个 pointcutAdvisor 切面拦截器了。
 * </p>
 *
 * @author pp
 * @since 2023/6/11
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    /**
     * 切面
     */
    private AspectJExpressionPointcut pointcut;

    /**
     * 具体的拦截方法
     */
    private Advice advice;

    /**
     * 表达式
     */
    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if(null == pointcut){
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
