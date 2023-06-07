package org.mini.spring.beans;

import org.junit.Test;
import org.mini.spring.beans.factory.config.*;
import org.mini.spring.beans.factory.support.DefaultListableBeanFactory;
import org.mini.spring.beans.factory.support.XMLBeanDefinitionReader;
import org.mini.spring.beans.service.UserDao;
import org.mini.spring.beans.service.UserService;
import org.mini.spring.context.event.CustomEvent;
import org.mini.spring.context.support.ClassPathXmlApplicationContext;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public class BeanFactoryTest {
    // 初始版的缺点是：
    // 1. bean的创建全部是业务侧自己手动创建出来的， 而不是容器生产出来的。
    // 2.
    @Test
    public void testBeanFactory() {
        /*
        // 1. 完成BeanFactory的初始化
        BeanFactory beanFactory = new BeanFactory();

        // 2. 注册Bean
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBean(new UserService());
        beanFactory.registerBean("userService", beanDefinition);

        // 3. 获取Bean
        Object userService = beanFactory.getBean("userService");
        if (userService instanceof UserService) {
            ((UserService) userService).queryByUserInfo();
        }*/
    }

    @Test
    public void testBeanFactoryV2() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        String beanName = "userService";

        // 注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        // 首次获取
        Object bean = beanFactory.getBean(beanName);
        if(bean instanceof UserService){
            ((UserService) bean).queryByUserInfo();
        }

        Object bean1 = beanFactory.getBean(beanName);
        if(bean1 instanceof UserService){
            ((UserService) bean1).queryByUserInfo();
        }

        System.out.println(bean == bean1);

    }

    @Test
    public void testBeanFactoryV3() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        String beanName = "userService";

        // 注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        // 首次获取
        Object bean = beanFactory.getBean(beanName, "haha");
        if(bean instanceof UserService){
            ((UserService) bean).queryByUserInfo();
        }

        Object bean1 = beanFactory.getBean(beanName);
        if(bean1 instanceof UserService){
            ((UserService) bean1).queryByUserInfo();
        }

        System.out.println(bean == bean1);

        System.out.println(bean);

    }

    @Test
    public void testBeanFactoryV4() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 注册UserDao
        beanFactory.registerBeanDefinition("uDao", new BeanDefinition(UserDao.class,
                new PropertyValues()
                        .addPropertyValue(new PropertyValue("uid", "pp"))
                        .addPropertyValue(new PropertyValue("uName", "pp"))
        ));

        // 注册UserService
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "haha"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("uDao")));

        // 注入
        String beanName = "userService";
        beanFactory.registerBeanDefinition(beanName, new BeanDefinition(UserService.class, propertyValues));

        Object bean = beanFactory.getBean(beanName);
        if(bean instanceof UserService){
            ((UserService) bean).queryByUserInfo();
        }

        System.out.println(bean);

    }

    @Test
    public void testBeanFactoryV5() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 读取配置文件并注册
        XMLBeanDefinitionReader xmlBeanDefinitionReader = new XMLBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        // 获取对象
        Object bean = beanFactory.getBean("userService");
        if(bean instanceof UserService){
            ((UserService) bean).queryByUserInfo();
        }

        System.out.println(bean);

    }

    /**
     * 不使用增强配置
     */
    @Test
    public void testBeanFactoryV61() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 读取配置文件并注册
        XMLBeanDefinitionReader xmlBeanDefinitionReader = new XMLBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        // BeanDefinition加载完成 & Bean实例化之前， 修改BeanDefinition的属性值
        BeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessorTest();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // 实例化之前修改Bean的属性信息
        BeanPostProcessor beanPostProcessor = new MyBeanPostProcessorTest();
        beanFactory.addBeanPostProcessor(beanPostProcessor);


        // 获取Bean
        Object bean = beanFactory.getBean("userService");
        if(bean instanceof UserService){
            ((UserService) bean).queryByUserInfo();
        }

        System.out.println(bean);

        /*user info: pp->>774dd51f-d98e-429a-a20a-723c8a4d6901
        UserService{name='xx', company='alibaba',
        location='Modify to shanghai.', userDao=org.mini.spring.beans.service.UserDao@46f5f779}*/
    }

    /**
     * 使用增强配置
     */
    @Test
    public void testBeanFactoryV62() {
        // 初始化BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springv2.xml");

        // 获取Bean对象
        Object bean = applicationContext.getBean("userService");
        if(bean instanceof UserService){
            ((UserService) bean).queryByUserInfo();
        }

        System.out.println(bean);

        /*user info: pp->>774dd51f-d98e-429a-a20a-723c8a4d6901
        UserService{name='xx', company='alibaba',
        location='Modify to shanghai.', userDao=org.mini.spring.beans.service.UserDao@46f5f779}*/
    }

    /**
     * 使用增强配置
     */
    @Test
    public void testBeanFactoryV7() {
        // 初始化BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springv3.xml");

        applicationContext.registerShutdownHook();

        // 获取Bean对象
        Object bean = applicationContext.getBean("userService");
        if(bean instanceof UserService){
            ((UserService) bean).queryByUserInfo();
        }

        System.out.println(bean);

        /*userDao: this is a new init method.
        execute UserService afterPropertiesSet.
        user info: pp->>485fdd82-d12f-4a8a-b8fe-5e3fb24d3fb1
        UserService{name='xx', company='alibaba', location='Modify to shanghai.', userDao=org.mini.spring.beans.service.UserDao@270421f5}
        userDao: execute destroy method.
        execute userService destroy.*/
    }

    /**
     * 使用增强配置
     */
    @Test
    public void testBeanFactoryV8() {
        // 初始化BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springv3.xml");

        applicationContext.registerShutdownHook();

        // 获取Bean对象
        Object bean = applicationContext.getBean("userService");
        if(bean instanceof UserService){
            ((UserService) bean).queryByUserInfo();
        }

        System.out.println(bean);

        /*
        * userDao: this is a new init method.
            Aware beanFactory: org.mini.spring.beans.factory.support.DefaultListableBeanFactory@6fdb1f78
            Aware classLoader: null
            Aware beanName: userService
            Aware applicationContext: org.mini.spring.context.support.ClassPathXmlApplicationContext@51016012
            execute UserService afterPropertiesSet.
            user info: pp->>c88e1a0b-9eb3-4dd3-9ddc-129f694c63cb
            UserService{name='xx', company='alibaba', location='Modify to shanghai.', userDao=org.mini.spring.beans.service.UserDao@4f4a7090}
            userDao: execute destroy method.
            execute userService destroy.

        * */
    }


    /**
     * 测试Prototype
     */
    @Test
    public void testBeanFactoryV91() {
        // 初始化BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springv4.xml");

        applicationContext.registerShutdownHook();

        // 获取Bean对象
        Object bean1 = applicationContext.getBean("userService");
        Object bean2 = applicationContext.getBean("userService");
        System.out.println(bean1.hashCode());
        System.out.println(bean2.hashCode());

        /*
        * 1635546341
        1698156408
        * */

        // 证明: prototype是生效的!!
    }

    /**
     * 测试爱丽对象
     */
    @Test
    public void testBeanFactoryV92() {
        // 初始化BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springv4.xml");

        applicationContext.registerShutdownHook();

        // 获取Bean对象
        Object bean = applicationContext.getBean("userService");
        System.out.println(((UserService) bean).queryUserInfo());

        /*
        被代理了,alibaba,Modify to shanghai.
        * */

        // 证明: ProxyBeanFactory取代了原来的UserDao， 通过动态代理完成对UserDao的封装
        // 将接口的定义注册到Bean的属性中， 完成对复杂对象的代理
    }

    /**
     * 测试爱丽对象
     */
    @Test
    public void testBeanFactoryV10() {
        // 初始化BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springv5.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1L, "hahahahh"));

//        get msg: CustomEvent{id=1, msg='hahahahh'}

    }

}
