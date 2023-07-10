package org.mini.spring.aop;

import org.junit.Test;
import org.mini.spring.aop.aspectj.AspectJExpressionPointcut;
import org.mini.spring.aop.framework.Cglib2AopProxy;
import org.mini.spring.aop.framework.JdkDynamicAopProxy;
import org.mini.spring.aop.intercept.MethodInterceptor;
import org.mini.spring.aop.intercept.MethodInvocation;
import org.mini.spring.context.support.ClassPathXmlApplicationContext;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/4
 */
public class UserServiceInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTs = System.currentTimeMillis();

        try {
            // 包装之后， 放行
            return invocation.proceed();
        } finally {
            System.out.println("监控 - Begin By Aop");
            System.out.println("方法名称：" + invocation.getMethod().getName());
            System.out.println("方法耗时：" + (System.currentTimeMillis() - startTs) + " ms");
            System.out.println("监控 - End\n");
        }
    }


    @Test
    public void testDynamic(){
        // 目标对象
        UService uService = new UService();

        // 代理支持
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(uService));
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* org.mini.spring.aop.IUserService.*(..))"));

        // 代理对象
        IUserService proxy = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        System.out.println(proxy.queryUserInfo());

        IUserService proxy1 = (IUserService) new Cglib2AopProxy(advisedSupport).getProxy();
        System.out.println(proxy1.queryUserInfo());
    }

    @Test
    public void test_aop(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springv10.xml");

        IUserService service = (IUserService) applicationContext.getBean("userService");

        System.out.println(service.queryUserInfo());
    }
}
