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

3) 针对上面的步骤， 考虑到类的职责细分:
- AbstractAutowireCapableBeanFactory完成Bean的创建
内部根据Bean创建的不同策略， 比如Cglib/构造器形式创建没有属性具体值的Bean
创建Bean之后将Bean的属性信息赋值
Note: 以上两个步骤均依赖BeanDefinition!!

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

## 从扩展角度
哪些地方可以完成对Bean对象的扩展机制？
- BeanDefinition生成后是否可以支持动态的修改其定义， 而不是从原有的资源处定义后不可以再修改(不可变)
引出接口BeanFactoryPostProcess， 其主要是在Bean注册后，但是未初始化之前执行BeanDefinition的修改操作
- createBean前后是否可以支持一些动态修改
引出接口BeanPostProcess， 完成Bean的初始化前后修改操作







