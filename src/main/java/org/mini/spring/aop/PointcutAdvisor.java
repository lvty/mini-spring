package org.mini.spring.aop;

/**
 * <p>
 *     Advisor 承担了 Pointcut 和 Advice 的组合， Pointcut 用于获取 JoinPoint，而
 *     Advice 决定于 JoinPoint 执行什么操作。
 * </p>
 *
 * @author Administrator
 * @since 2023/7/5
 */
public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
