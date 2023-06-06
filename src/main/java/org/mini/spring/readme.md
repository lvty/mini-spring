# 流程梳理
## 从资源角度
1) **资源定义**
所有的资源全部定义为"流"的形式；
2) 完成资源的加载ResourceLoader
3) **资源加载**：BeanDefinitionReader
完成资源定义和资源加载的同时， 对加载后的资源进行注册， 注册的容器为BeanDefinitionRegistry
此处针对XML文件进行解析的实现类为：XMLBeanDefinitionReader
4) **BeanDefinition注册**：BeanDefinitionRegistry
资源注册中心的核心实现类为DefaultListableBeanFactory, 其核心目标是为了从注册中心获取的Bean定义给到父类接口或者抽象类使用
5) **BeanDefinition获取**：DefaultListenableBeanFactory


## 从BeanFactory角度
1) BeanFactory完成的核心功能就是从容器中拿出指定的对象
2) AbstractBeanFactory完成的是， 对Bean的创建流程进一步拆分：
- 从SingletonBeanRegistry中获取现存的Bean，获取不到则进行下一步
- 定义抽象方法， 获取到BeanDefinition信息
- 定义抽象方法， 根据BeanDefinition的信息创建具体的Bean
- 根据获取到的Bean， 判断是否为FactoryBean， 如果是的话， 则调用FactoryBean的方法完成Bean的创建

3) 针对上面的步骤， 考虑到类的职责细分:
- AbstractAutowireCapableBeanFactory完成Bean的创建
内部根据Bean创建的不同策略， 比如Cglib/构造器形式创建没有属性具体值的Bean
创建Bean之后将Bean的属性信息赋值
Note: 以上两个步骤均依赖BeanDefinition!!
完成Bean的属性赋值之后， 判断是否是Aware接口， 如果是的话， 完成Aware信息的绑定；
完成以上步骤之后， 执行BeanPostProcessorsBeforeInitialization和BeanPostProcessorsAfterInitialization
在此期间，完成invokeInitMethod方法的处理(InitializingBean接口或者属性配置init-method)；

- DefaultListenableBeanFactory完成BeanDefinition的获取
实现了BeanDefinitionRegistry接口从注册中心获取， 来源可以是外部用户输入的， 也可以是后续的读取XML资源信息

## 从Bean定义角度
1) Bean的核心是一个Class和对应的属性
根据Class可以获取对应的构造器和参数
2) 属性的值可以是普通的对象或者引用对象， 此处的区分表现为：Object的类型为BeanReference
根据创建好的Bean可以完成对属性值的绑定

## 从使用角度
1) DefaultListenableBeanFactory/XMLBeanDefinitionReader
两者虽然已经很方便进行配置化、灵活支持Bean的注册和获取，但是这两个步骤是否可以进一步合并？ 
作为BeanFactory的Bean初始化的步骤，只是支持了基本的功能，还不具备一定的扩展性；
2) 是否存在一个统一的入口 
方便使用方完成Bean的定义、加载、注册、初始化、获取、使用、销毁等一系列过程呢
且支持对中间的过程进行动态的扩展的机制呢

3) 初始化和销毁方法
- 在xml配置文件中增加init-method和destroy-method, 对应到BeanDefinition的属性中
- 当initialzeBean初始化操作过程中，就可以通过反射的方法来调用属性配置的方法信息
- 如果是接口实现的方式，可以直接通过Bean对象调用对应接口定义的方法接口(InitialingBean(Bean)).afterPropertiesSet()
- 当Bean对象初始化完成，会注册销毁方法到DefaultSingletonBeanRegistry中的disposableBeans属性中，统一进行处理；

3.1) 用途
接口暴露，数据库数据读取，配置文件加载等

### 统一使用
1) 定义ApplicationContext暴露统一的入口给用户使用
而非先创建DefaultListableBeanFactory，配合XMLBeanDefinitionReader完成Bean的定义、加载和初始化

3) ConfigurableApplicationContext
定义refresh() 
4) AbstractApplicationContext
对refresh方法的骨架定义， 主要包括四个步骤：
- 定义了BeanFactory的创建：refreshBeanFactory()
- BeanFactoryPostProcessor的获取和执行
- BeanPostProcessor的注册
- 提前初始化单例Bean
5) AbstractRefreshableApplicationContext
执行refreshBeanFactory方法， 主要目的是创建工厂: DefaultListableBeanFactory；
完成BeanDefinition的加载方法的定义
6) AbstractXmlApplicationContext
完成XMLBeanDefinitionReader的定义， 根据其方法完成BeanDefinition的加载
核心代码
```java
XMLBeanDefinitionReader xmlBeanDefinitionReader = new XMLBeanDefinitionReader(beanFactory, this);
```
7) ClassPathXMLApplicationContext
对外暴露的接口， 主要给出资源的路径即可





## 从扩展角度
哪些地方可以完成对Bean对象的扩展机制？
- BeanDefinition生成后是否可以支持动态的修改其定义， 而不是从原有的资源处定义后不可以再修改(不可变)
引出接口BeanFactoryPostProcess， 其主要是在Bean注册后，但是未初始化之前执行BeanDefinition的修改操作
- createBean前后是否可以支持一些动态修改
引出接口BeanPostProcess， 完成Bean的初始化前后修改操作

### Aware感知容器对象
如果想要获取到Spring框架提供的BeanFactory、ApplicationContext、BeanClassLoader等，进一步做一些扩展框架的使用
该怎么设计？
- Spring提供的这些资源， 其获取方式怎么定义？
通过定义标记接口， 类似InitialingBean， 这个标记接口不需要有任何接口方法， 只起到标记作用；
具体的功能由继承此接口的其他功能性接口来完成， 在代码里面通过InstanceOf来进行判断和调用；
定义Aware接口，其实现有：BeanFactoryAware/BeanClassLoaderAware/BeanNameAware/ApplicationContextAware
其实现能感知到容器中的相关对象
- 这些获取方式怎么和Spring框架无缝衔接？

### FactoryBean
交给Spring管理的Bean对象一定是用户类创建出来的么？有没有可能通过定义接口， 然后具体的实现类是由Spring来管理的呢？
- 在ORM框架中， 我们一般只会定义一个接口， 也并没有创建任何操作数据库的Bean对象， 但是这就可以完成对数据库的读取了。
- 问题简化为： 如何完成把复杂且以代理方式将动态变化的对象注入到Spring容器呢。
- 另外， 创建好的对象一定是单例的么？ 有木有可能是非单例的？

以上问题就都可以通过FactoryBean来解决， 让使用者定义复杂的Bean对象， 且根据对象的类型(是否单例)来创建。
- 对外提供一个可以从FactoryBean的getObject方法中获取对象的功能即可， 这样所有实现此接口的对象类， 就可以扩充自己的对象
功能了。
- MyBatis就实现了一个MapperFactoryBean类， 在getObject方法提供SqlSession执行CRUD的操作。

思路：
createBean完成对象的创建、属性填充、依赖加载、前置处理、初始化方法处理、后置处理；最后可以加上一个判断当前对象是否是一个
FactoryBean对象， 如果是的话， 就需要继续执行获取FactoryBean具体对象的getObject对象了， 整个getBean过程新增一个创建
类型的判断， 用于确定是否使用内存来存放对象， 如果要存放到内存，就是单例；否则每次都要重新创建；

