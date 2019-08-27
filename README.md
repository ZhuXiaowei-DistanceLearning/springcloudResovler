# SpringCloud教程

## 1.Eureka

### 1.1 Client端

1. ![1566307391459](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566307391459.png)
2. ![1566307429515](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566307429515.png)
3. ![1566307437933](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566307437933.png)

### 1.2 Server端

1. ![1566307463470](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566307463470.png)
2. ![1566307479418](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566307479418.png)
3. ![1566307541474](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566307541474.png)

## 2.Zuul

1. 简介

   1. 基于JVM路由和服务端的负载均衡器
   2. zuul的核心是过滤器
      1. 动态路由
      2. 请求监控
      3. 认证授权
      4. 压力测试
      5. 灰度发布
   3. 过滤器类型
      1. pre:请求前被调用
      2. route:请求时被调用，适用灰度发布场景
      3. post:在route和error之后被调用，将请求路由到达具体的服务之后执行。适用于需要添加响应头，记录响应日志等场景
      4. error:处理请求时发生错误时被调用。在执行过程中发送错误时会进入error过滤器。
      5. ![1566386001738](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566386001738.png)

2. 典型配置

   1. 单实例

      ```yml
      zuul:
      	routes:
      		client-a:
      			path: /client/**
      			servicedId: client-a
      		client-a: /client/**
      		client-a: #默认的映射规则/client-a/**,相当于第一种方式
      ```

   2. 单实例url映射

      ```yml
      zuul:
      	routes:
      		client-a:
      			path: /client/**
      			url: http://localhost:7070
      ```

   3. 多实例路由

      1. 使用Eureka中继承的基本负载均衡功能，如果要使用Ribbon，需要指定一个serviceId，此操作需要禁止Ribbon使用eureka

      ```yml
      zuul:
      	routes:
      		client-a:
      			path: /client/**
      			servicedId: client-a
      ribbon:
      	eureka:
      		enable: false #禁止ribbon使用eureka
      ribbon-route:
      	ribbon:
      		xx:xxx
      ```

   4. forward本地跳转

      ```java
      @GetMapping("/client") // 跳转到该方法上
      public String add(Integer a,Integer b){
          return "本地跳转:"+(a+b);
      }
      ```

      ```yml
      zuul:
      	routes:
      		client-a:
      			path: /client/**
      			url: forward:/client
      ```

   5. 相同路径的加载规则

      ```yml
      zuul:
      	routes:
      		client-a:
      			path: /client/**
      			url: forward:/client-a
      		client-b: # 总是加载最末的服务
      			path: /client/**
      			url: forward:/client-b
      ```

   6. 路由通配符

      ```
      /**
      /*
      /?
      ```

      ![1566388557106](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566388557106.png)

3. 功能配置

   1. 路由前缀：

      ```yml
      zuul:
      	prefix: /pre
      	routes:
      		client-a:
      			path: /client/**
      			url: forward:/client-a
      			stripPrefix: false # 关闭路由前缀
      ```

   2. 服务屏蔽与路径屏蔽

      ```yml
      zuul:
      	prefix: /pre
      	ignored-services: client-b #忽略的服务，防服务侵入
      	ignored-patterns: /**/div/** #忽略的接口，屏蔽接口
      	routes:
      		client-a:
      			path: /client/**
      			url: forward:/client-a
      ```

   3. 敏感头信息

      ```yml
      zuul:
      	prefix: /pre
      	routes:
      		client-a:
      			path: /client/**
      			url: forward:/client-a
      			sensitiveHeaders: Cookie,Set-Cookie,Authorization
      ```

   4. 重定向问题

      ```yml
      zuul:
      	add-host-header: true #host为zuul的host
      ```

   5. 重试机制(默认继承Ribbon)

      ```yml
      zuul:
      	retryable: true #开启重试
      ribbon:
      	MaxAutoRetries: 1 # 同一个服务重试的次数(除去首次)
      	MaxAutoRetriesNextServer: 1 # 切换相同服务数量
      	ConnectTimeout: 250 # 连接超时时间(ms)
        	ReadTimeout: 2000 # 通信超时时间(ms)
        	OkToRetryOnAllOperations: true # 是否对所有操作重试
       spring:
       	cloud:
       		loadblancer:
       			retry:
       				enable: true #内部默认已开启，这里列出来说明这个参数比较重要
      ```

4. 容错机制

   1. ![1566386680753](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566386680753.png)

5. 回退机制

   1. zuul默认整合了 Hystrix
   2. 实现ZuulFallbackProvider接口

6. 高可用

   1. ![1566386825225](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566386825225.png)

7. zuul+OAuth2.0+JWT

   1. OAuth2.0面向资源授权协议
   2. ![1566390145458](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566390145458.png)
   3. ![1566390189521](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566390189521.png)
   4. @EnableOAuth2Sso

8. zuul限流

   1. 限流算法
      1. 漏桶(Leaky Bucket)
      2. 令牌桶(Token Bucket)

9. 动态路由

   1. ![1566391286396](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566391286396.png)

10. 灰度发布

    1. 系统迭代新功能时的一种平滑过渡的上线方式。
    2. 在原有系统的基础上，额外增加一个新版本，这个新版本包含我们需要待验证的新功能，随后用负载均衡器引入一小部分流量到这个新版本应用，如果整个过程没有出现任何差错，在平滑地把线上系统或服务一步步替换成新版本，至此完成一次灰度发布。

11. 饥饿加载

    1. 默认使用Ribbon来调用远程服务，所以由于Ribbon的原因，第一次经过Zuul的调用往往会去注册中心读取服务注册表，初始化Ribbon负载信息，这是一种懒加载策略。

       ```yml
       zuul: # 开启饥饿加载
       	ribbon:
       		eager-load:
       			enable: true
       ```

12. 请求体修改

    1. ![1566391877126](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566391877126.png)
    2. ![1566391917221](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566391917221.png)

13. 使用okhttp替换HttpClient

    1. <dependency>

       ```yml
       ribbon:
       	httpclient:
       		enable: false
       	okhttp:
       		enable: true
       ```

14. Header传递

    1. Zuul中对请求做了一些处理，需要把处理结果发给下游服务，但是又不能影响请求体的原始特性

       ```java
       RequestContext context = RequestContext.getCurrentContext();
       context.addZuulRequestHeader("result","to next service")
       ```

15. Swagger2整合

    1. ```java
       @Configuration
       @EnableSwagger2
       public class SwaggerConfig {
           @Autowired
           ZuulProperties zuulProperties;
       
           @Primary
           @Bean
           public SwaggerResourcesProvider swaggerResourcesProvider() {
               return () -> {
                   List<SwaggerResource> resources = new ArrayList<>();
                   zuulProperties.getRoutes().values().stream()
                           .forEach(route -> resources
                                   .add(createResource(route.getServiceId(), route.getServiceId(), "2.0")));
                   return resources;
               };
           }
       
           private SwaggerResource createResource(String name, String location, String version) {
               SwaggerResource swaggerResource = new SwaggerResource();
               swaggerResource.setName(name);
               swaggerResource.setLocation("/" + location + "/v2/api-docs");
               swaggerResource.setSwaggerVersion(version);
               return swaggerResource;
           }
       }
       ```

### 2.1 多层负载

1. ![1566392851162](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566392851162.png)
2. ![1566392870314](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566392870314.png)
3. ![1566392878747](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566392878747.png)

### 2.2 应用优化

1. 容器优化:内置容器Tomcat与Undertow的比较与参数设置
2. 组件优化:内部集成的组件优化，如Hystrix线程隔离、Ribbon
3. JVM参数优化:适用于网关应用的JVM参数建议
4. 内部优化:

## 3.Gateway

1. 简介
   1. 提供简单、有效且统一的API路由管理方式
   2. 路由:路由是网关最基础的部分
   3. 断言:java8中的断言函数。
   4. 过滤器:一个标准的Spring webFilter。springcloudgateway中的filter分为两种Gateway Filter和Global Filter
2. 工作流程图
   1. ![1566393822831](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566393822831.png)

### 3.1 路由断言

1. After路由断言工厂

   1. ![1566564519911](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566564519911.png)

   2. ```yml
      spring:
        cloud:
          gateway:
            routes:
              - id: jd_route
                uri: http://jd.com:80/
                predicates:
                  - Path=/jd
                  - After=2019-07-21T22:30:15.854+08:00[Asia/Shanghai]
      ```

2. Before路由断言工厂

   1. ![1566565079121](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565079121.png)

   2. ```yml
      spring:
        cloud:
          gateway:
            routes:
              - id: jd_route
                uri: http://jd.com:80/
                predicates:
                  - Before=2022-03-13T00:54:30.877+08:00[Asia/Shanghai]
      ```

3. Between路由断言工厂

   1. ![1566565157340](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565157340.png)

   2. ```yml
      spring:
        cloud:
          gateway:
            routes:
              - id: jd_route
                uri: http://jd.com:80/
      		  predicates:
                - name: Between
                    args:
                        datetime1: 2019-07-21T22:30:15.854+08:00[Asia/Shanghai]
                        datetim2: 2019-08-21T22:30:15.854+08:00[Asia/Shanghai]
      ```

4. Cokkie路由断言工厂

   1. ![1566565298822](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565298822.png)

   2. ```yml
      spring:
        cloud:
          gateway:
            routes:
              - id: jd_route
                uri: http://jd.com:80/
                predicates:
                  - Cookie=chocolate, ch.p
      ```

5. Header路由断言工厂

   1. ![1566565365066](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565365066.png)
   2. ![1566565378454](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565378454.png)

6. Host路由断言工厂

   1. ![1566565394425](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565394425.png)
   2. ![1566565403194](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565403194.png)

7. method路由断言工厂

   1. ![1566565412346](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565412346.png)

8. Query路由断言工厂

   1. ![1566565444227](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565444227.png)
   2. ![1566565453674](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565453674.png)

9. RemoteAddr路由断言工厂

   1. ![1566565473935](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565473935.png)

### 3.2 Gateway的内置Filter

1. addRequestHeader过滤器工厂
   1. 使用java流式API配置路由的配置
   2. ![1566565544288](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565544288.png)
2. addRequestParameter过滤器
   1. 对匹配上的请求路由添加请求参数
   2. ![1566565607768](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565607768.png)![1566565611837](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565611837.png)
3. RewritePath过滤器
   1. ![1566565635329](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565635329.png)
   2. ![1566565677790](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565677790.png)
4. AddResponseHeader过滤器
   1. ![1566565695457](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565695457.png)
5. StripPrefix过滤器
   1. ![1566565712912](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565712912.png)
6. Retry过滤器
   1. ![1566565776530](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565776530.png)
7. Hystrix过滤器
   1. ![1566565818827](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566565818827.png)

### 3.3 权重路由

1. ```yml
   spring:
     application:
       name: sc-gateway-server
     cloud:
       gateway:
         routes:
         - id: service1_v1
           uri: http://localhost:8081/v1
           predicates:
             - Path=/test
             - Weight=service1, 95
         - id: service1_v2
           uri: http://localhost:8081/v2
           predicates:
             - Path=/test
             - Weight=service1, 5
   ```

2. Https使用技巧

   1. ```yml
      server:
      ssl:
        key-alias: spring
        enabled: true
        key-password: spring
        key-store: classpath:selfsigned.jks
        key-store-type: JKS
        key-store-provider: SUN
        key-store-passowrd: spring
      ```

3. 集成Swagger

   ```
   
   ```

### 3.4 限流

1. 概述
   1. 高并发系统三把利器保护系统：缓存、降级和限流
   2. 缓存：提升系统访问速度和增大系统处理的容量
   3. 降级：当服务出现问题或影响到核心流程时，需要暂时将其屏蔽掉
   4. 限流：秒杀、抢购、写服务(如评论、下单)频繁的复杂查询,通过对并发访问/请求进行限速或者对一个时间窗口内的请求进行限速来保护系统，一旦达到限制速率可以拒绝服务、排队或等待、降级
   5. 两种限流模式：控制速率和控制并发
2. 自定义过滤器实现限流
   1. ![1566575500589](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566575500589.png)
3. 内置过滤器工厂限流
   1. ![1566575577204](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566575577204.png)
4. 基于CPU的使用率进行限流
   1. ![1566575601256](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566575601256.png)
5. 动态路由

## 4.Ribbon

1. 定义

   1. 集中式负载均衡
   2. 进程式负载均衡:从实例库选取一个实例进行流量导入，客户端负载均衡
   3. ![1566473904315](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566473904315.png)

2. 负载均衡策略

   1. ![1566475901062](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566475901062.png)

   2. ![1566475912745](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566475912745.png)

   3. 饥饿加载

      1. ```yml
         ribbon:  
         	eager-load:    
         	enabled: true    
         	clients: client-a
         ```

   4. 利用配置文件自定义Ribbon

      1. ![1566476918325](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566476918325.png)

### 4.1 核心工作原理

1. ![1566476969959](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566476969959.png)

## 5.Hystrix

1. 定义
   1. ![1566477524140](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566477524140.png)
   2. 通过客户端库对延迟和故障进行保护和控制
   3. 在一个复杂的分布式系统中停止级联故障
   4. 快速失败和迅速恢复
   5. 在合理的情况下回退和优雅地降级
   6. 开启近实时监控、告警和操作控制
2. ![1566561508615](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566561508615.png)
3. ![1566561528586](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566561528586.png)
4. ![1566561556478](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566561556478.png)

### 5.1 Hystrix配置说明

1. ![1566561585032](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566561585032.png)
2. ![1566561594736](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566561594736.png)

### 5.2 Hystrix线程调整和计算

1. ![1566561650295](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566561650295.png)

### 5.3 请求缓存

1. ![1566561674399](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566561674399.png)
2. ![1566561688246](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566561688246.png)
3. 使用类开启缓存
   1. ![1566561706618](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566561706618.png)
4. 使用注解开启缓存
   1. ![1566561727494](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566561727494.png)
5. 使用注解清除缓存
   1. ![1566561743755](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566561743755.png)
6. ![1566561752072](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566561752072.png)

### 5.4 Hystrix Request Collapser

![1566562526785](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566562526785.png)

1. 使用注解进行请求合并
   1. ![1566562621066](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566562621066.png)
   2. ![1566562628294](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566562628294.png)
   3. ![1566562711795](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566562711795.png)
   4. ![1566562722170](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566562722170.png)
   5. ![1566562731512](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566562731512.png)
   6. ![1566562856184](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566562856184.png)
2. Hystrix线程传递及并发策略
   1. ![1566563412102](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566563412102.png)
   2. ![1566563439223](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566563439223.png)

### 5.5 Hystrix命令注解

1. ![1566563615458](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566563615458.png)
2. ![1566563629198](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566563629198.png)
3. ![1566563637874](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566563637874.png)
4. ![1566563655809](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566563655809.png)
5. ![1566563669046](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566563669046.png)

## 6.Feign

1. 简介

   1. 声明式的Web Service客户端
   2. 声明式、模板化的HTTP客户端
   3. 可插拔的注解主持，包括Feign注解和JAX-RS注解
   4. 支持可插拔的HTTP编码器和解码器
   5. 支持Hystrix和它的Fallback
   6. 支持Ribbon的负载均衡
   7. 支持HTTP请求和响应的压缩,整合了Ribbon和Hystrix

2. 开启GZIP压缩

   1. ```yml
      feign:
      	compression:
      		request:
      			enable: true
      		mime-types: text/xml.application/xml,application/json #配置压缩支持的MIME TYPE
      		min-request-size: 2048 #配置压缩数据大小的下限
      		response:
      			enable: true # 配置响应GZIP压缩
      ```

      ![1566481604560](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566481604560.png)

3. 属性文件配置

   1. ![1566481624772](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566481624772.png)
   2. ![1566481650941](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566481650941.png)

4. 开启日志

   1. ![1566481699420](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566481699420.png)

5. 超时设置

   1. Feign的调用分两层，即Ribbono的调用和Hystrix的调用，高版本的Hystrix默认是关闭的![1566481748576](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566481748576.png)
   2. ![1566481762013](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566481762013.png)

### 6.1 Feign默认Client的替换

1. 默认使用URLConnection发送HTTP请求，没有连接池，但是对每一个地址会保持长连接
2. 使用httpClient替换
   1. 导入![1566481918333](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566481918333.png)
   2. ![1566481926090](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566481926090.png)
3. 使用okhttp替换
   1. ![1566481942427](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566481942427.png)
   2. ![1566481951817](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566481951817.png)
   3. ![1566481970679](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566481970679.png)

### 6.2 Feign的Post和Get的多参数传递

1. ![1566482085435](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566482085435.png)

### 6.3 文件上传

1. ![1566482112576](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566482112576.png)

### 6.4 首次请求失败问题

1. ![1566482163677](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566482163677.png)

### 6.5 返回图片流处理方式

1. ![1566482214765](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566482214765.png)

### 6.5 Feign调用传递Toekn

1. ![1566482231428](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566482231428.png)
2. ![1566482241541](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566482241541.png)

### 6.6 venus-cloud-feign 设计与使用

1. ![1566559916378](C:\Users\zxw\Desktop\个人项目笔记\SpringCloud教程.assets\1566559916378.png)

## 7. 分布式事务解决方案

### 7.1 2PC

1. 两阶段提交(two-phase Commit):分布式事务解决数据的一致性问题，引入一个作为协调者的组件来统一掌控所有节点的操作结果并最终指示这些节点是否要把操作结果进行真正的提交.参与者将操作成功通知协调者，再由协调者根据所有参与者的反馈情报决定各参与者是要提交还是中止操作

### 7.2 TCC补偿性

1. Try、Confirm、Cancel：Try预留业务资源，Confirm确认执行业务操作、Cancel取消执行业务操作
2. 首先通过Try锁住服务中的业务资源进行资源预留，只有资源预留成功了，后续操作才能正常进行，Cancel进行回滚

### 7.3 最终一致性



### 7.4 最大努力通知型事务

1. 尽自己最大的努力通知对方，但是不保证一定能通知到

## 8. 分布式任务调度

1. Elastic-Job

## 9. 分布分表解决方案

1. sharding

## 10. Swagger

1. 注解

   1. @Api：作用在类上

      1. value ：接口说明
      2.  tags ： 接口说明，可以在页面中显示。可
         以配置多个，当配置多个的时候， 在页面
         中会显示多个接口的信息

      ```java
      @Api(value = "功能测试1", tags = {"功能测试"})
      public class HelloController{
      }
      ```

   2. @ApiMode:用在类上

      1. 对类进行说明，用于实体类的接受参数说明

      ```java
      @ApiModel(value =”com.fangjia.fsh.user.query .LoginQuery",description ＝” 登录参数”）
      public class LoginQuery {
      }
      ```

   3. @ApiModelProperty:用在字段上

      1. 对Model属性的说明

      ```java
      @ApiModel(value = "com.zxw.swagger.controller",description = "登录参数")
      public class HelloController {
          
          @ApiModelProperty(value = "企业编号", required = true)
          private Long eid;
          }
      ```

   4. @ApiParam:对Controller中方法的参数说明

      ```java
      @GetMapping("/hello/{hello}")
          public String hello(@ApiParam(value = "登录参数",required = true) @PathVariable("hello") String hello) {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
              String format = sdf.format(new Date());
              return format + ":" + hello;
          }
      ```

   5. ```java
       @GetMapping("/hello/{hello}")
          @ApiOperation(value = "用户登录",notes = "企业用户认证接口，参数为必填项")
          public String hello(@ApiParam(value = "登录参数",required = true) @PathVariable("hello") String hello) {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
              String format = sdf.format(new Date());
              return format + ":" + hello;
          }
      ```

   6. @ApiResponse、@ApiResponses

      1. code ：响应状态码
      2. message ：状态码对应的说明
      3. 用于方法上，通过ApiResponses封装ApiResponse

      ```java
       @ApiResponses({
                  @ApiResponse(code = 200,message = "欢迎使用")
          })
          @ApiOperation(value = "用户登录",notes = "企业用户认证接口，参数为必填项")
          public String hello(@ApiParam(value = "登录参数",required = true) @PathVariable("hello") String hello) {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
              String format = sdf.format(new Date());
              return format + ":" + hello;
          }
      ```

   7. @ApilmplicitParam和ApilimplicitParams

      1. 为请求参数单独说明
      2. name ：参数名，对应方法中单独的参数名称
      3. value ： 参数中文说明
      4. required ：是否必填
      5. paramType ：参数类型，取值为path 、query 、body 、header 、form
      6. dataType ：参数数据类型
      7. defaultValue ：默认值

      ```java
      @ApiOperation(value = "用户登录",notes = "企业用户认证接口，参数为必填项")
      @ApiImplicitParams({@ApiImplicitParam(name="hello",value = "用户ID",required = false,paramType = "query",dataType = "String",defaultValue = "1")})
          public String hello(@ApiParam(value = "登录参数",required = true) @PathVariable("hello") String hello) {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
              String format = sdf.format(new Date());
              return format + ":" + hello;
          }
      ```

## 11. 微服务之缓存

### 11.1 Guava Cache本地缓存

1. 优点
   1. 本地缓存，读取效率高，不受网络因素影响
   2. 拥有丰富的功能，操作简单
   3. 线程安全
2. 缺点
   1. 缓存为本地缓存，不能持久化数据
   2. 单机缓存，受机器内存限制，应用重启数据会丢失
   3. 分布式部署时无法保证数据的一致性
3. API
   1. 回收策略
      1. expireAfterAccess(long, TimeUnit)
         当缓存项在指定的时间段内没有被读或写就会被回收。这种回收策略类似于基于容量回收策略。
      2. expireAfterWrite(long, TimeUnit)
         当缓存项在指定的时间段内没有更新就会被回收。如果我们认为缓存数据在一段时间后不再可用，那么可以使用该种策略。
      3. CacheBuilder 提供了显示移除的三种方式：
         CacheBuilder.invalidate(key）单个移除
         CacheBuilder.invalidteAll(keys）批量移除
         CacheBuilder.invalidateAll （） 移除全部

### 11.2 Redis缓存

1. stringRedisTemplate.opsForValue().set("key ”，” 猿天地”， 1,TimeUnit.HOURS);

2. Repository操作Redis

   1. ```java
      package com.zxw.pojo;
      
      import org.springframework.data.annotation.Id;
      import org.springframework.data.redis.core.RedisHash;
      
      /**
       * @author zxw
       * @date 2019/8/27 20:29
       */
      @RedisHash("person")
      public class Person {
          @Id
          String id;
          String firstName;
          String lastName;
      
          public Person(String firstName, String lastName) {
              this.firstName = firstName;
              this.lastName = lastName;
          }
      
          public String getId() {
              return id;
          }
      
          public void setId(String id) {
              this.id = id;
          }
      
          public String getFirstName() {
              return firstName;
          }
      
          public void setFirstName(String firstName) {
              this.firstName = firstName;
          }
      
          public String getLastName() {
              return lastName;
          }
      
          public void setLastName(String lastName) {
              this.lastName = lastName;
          }
      }
      ```

### 11.3 Spring Cache缓存数据

1. @Cacheable ：用于查询的时候缓存数据
   @CachePut ：用于对数据修改的时候修改缓存中的数据
   @CacheEvict ：用于对数据删除的时候清除缓存中的数据
2. 缓存穿透
   1. 每次对数据库的操作都没有缓存，增加了缓存查询的时间
   2. 解决方案
      1. mongoDB中对ID进行规范查询
      2. 利用布隆过滤器来实现缓存key的检查
3. 防止缓存雪崩
   1. 大量缓存同时失效导致所有请求都去查询数据库，导致数据库压力过大
   2. 解决方案
      1. 缓存存储高可用， 比如Red is 集群，这样就能防止某台Re dis 挂掉之后所有缓存丢失导致的雪崩问题。
      2. 缓存失效时间要设计好，不同的数据有不同的有效期，尽量保证不要在同一时间失效，统一去规划有效期，让失效时间分布均匀即可。
      3. 对于一些热门数据的持续读取，这种缓存数据也可以采取定时更新的方式来刷新缓存，避免自动失效。
      4. 服务限流和接口限流，如果服务和接口都有限流机制，就算缓存全部失效了，但是请求的总量是有限制的，可以在承受范围之内，这样短时间内系统响应慢点，但不至于挂掉，影响整个系统。
      5. 从数据库获取缓存需要的数据时加锁控制，本地锁或者分布式锁都可以。当所有请求都不能命中缓存，这就是我们之前讲的缓存穿透，这时候要去数据库中查询，如果同时并发的量大，也是会导致雪崩的发生，我们可以在对数据库查询的地方进行加锁控制，不要让所有请求都过去，这样可以保证存储服务不挂掉。
4. Zookeeper+redis
   1. redisson

## 12. 微服务之存储