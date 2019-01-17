project using spring boot 2.0.3 release

redis
using lettuce 5.0, it's not jedis
redis 序列化使用fastjson,没有使用jackson是因为以前的项目使用fastjson,如果反序列化策略不同会异常
增加了系统复杂度

swagger
using swagger 2.8 version,删除了不必要的plugin,使用jackson的驼峰转下划线策略
需要在application-${profile}.properties 中配置 swagger.enable=on 开启,其他值为关闭
http://localhost:9892/paymentserver/swagger-ui.html 本地调试页面调,查看生成的swagger是否正确
解决了springboottest 因为plugin导致无法mock,解决了无法本地使用swagger-ui.html
启动时候会有debug级别的错误,貌似是jackson开启驼峰转下划线策略对最新的包有排斥,但是是debug级别的,不影响使用和项目运行


web 
response data serializer poly is jackson using JacksonConfiguration.java


mybatis 
使用tk.mybatis 代替 springboot默认的mybatis,注意@mapperScan需要使用tk.mapper的类而非mybatis的(如果使用自定义配置加载的话)
mybatis测试,使用test目录下的test.sql 导入测试的表和数据
运行mybatisTest进行测试.包括crud,分页,多数据源
多数据源目前做成显示调用,后期如果做成真正的读写分离,会配合aop再整改,使得使用者无感知.
事务使用也区分数据源,不同的数据源需要使用不同的事务,默认使用读写库的数据源,详细可以看MybatisTest测试类

httpclient
自定义默认的httpclient,使用最新版本的httpclient,详细见build.gradle
HttpClientConfiguration 定义了链接超时等参数和链接资源池大小,最大并发和每个host最大并发数.

restTemplate
restTemplate 使用 httpclient为客户端请求工具
restTemplateConfiguration 定义了 restTemplate的拦截器使用情况,打印request,response日志以及header的添加.

elasticsearch
spring boot2 + elasticsearch 整合出现netty冲突,主要是netty3和netty4.需要额外使用对应版本的elasticsearch_transport版本的jar
整合后可以使用elasticsearch,项目没有使用jpa,jpa提供简单的crud但是不具备聚合操作.
因为netty冲突,和spring boot 2 的redis中默认使用的lettuce也有些问题,所以修改了redisConfiguration.
使用:通过对entity使用spring boot2 + elasticsearch 标签@field 和 @document 标签进行映射.再通过define包下的
ElasticsearchMappings.java 中的@PostConstruct 进行springBean加载完成后,与elasticsearch初始化工作.
dev_elk 运维配置修改,注释network.host,配置network.publish_host为外网地址,network.bind_host:0.0.0.0 
这样开发人员可以再本地连接dev_elk和test_elk,通过访问 dev_elk:9302 访问kibana可视化组件

kafka
spring boot 2 + kafka 0.10.2.1版本 整合.
kafka listener 消费consumer,注意group id区分广播还是点对点.通过define包下定义Topic的分区,复制集数量以及topic的名字

apollo 配置中心
通过nexus私服,引入apollo相关jar
部署架构:通过1台portalserver 注册 metaserver,发现各个节点的configserver,adminserver.
configserver,adminserver部署在各个环境中,portalserver管理各个环境的config

使用手册:
1. 通知apollo维护人员增加admin权限账号,各个项目组一个master,由于apollo没有提供普通账号的添加,需要额外实现公司级别的sso,目前公司缺少单点登录的功能,所以只能暂时使用admin权限.
2. 添加对应的项目,在apollo服务端添加项目应用,选择对应的负责人和部门,并且指定appId,如果缺失目录则自行在本地项目中创建.
3. 登录地址:portal:8083[106.75.108.89:8083]
4. 项目引用apollo_client 1.0.0jar,这是升级后的好处,携程已经在中央仓库添加此jar,之前版本需要自己编译并且打包上去.
5. 集成apollo配置中心和spring-boot-devtools存在冲突,需要丧失调试热部署功能


