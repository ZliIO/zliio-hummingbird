# Core Model Support

1. 加载指定包下的所有需要注册成Bean的Class
2. 读取Resource目录下的配置文件,注册ConfigurationManager到Bean的IOC容器中
3. 加载所有的ResourceConfigurationPostProcess处理器对Bean执行相关的产生调整和后处理
4. 完成BeanClass的实例化,创建出相关的Bean实例
5. 加载所有的拦截器、完成对应的实例化
6. 对Bean进行依赖注入，完成Bean相关的加载和初始化