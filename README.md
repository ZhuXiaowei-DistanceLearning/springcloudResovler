# SpringCloud底层原理

## 1.eureka

### 1.1服务发现原理

- ![1562591095292](C:\Users\zxw\AppData\Roaming\Typora\typora-user-images\1562591095292.png)

- ```
  Application Service ：是一个Eureka Client ，扮演服务提供者的角色，提供业务服务，
  向Eureka Server 注册和更新自己的信息，同时能从Eureka Server 注册表中获取到
  其他服务的信息。
  Eureka Server ：扮演服务注册中心的角色，提供服务注册和发现的功能。每个
  Eureka Cient 向Eureka Server 注册自己的信息，也可以通过Eureka Server 获取到其
  他服务的信息达到发现和调用其他服务的目的。
  Application Client ：是一个Eureka Client ，扮演了服务消费者的角色，通过Eureka
  Sever 获取注册到其上其他服务的信息，从而根据信息找到所需的服务发起远程调用。
  Replicate: Eureka Server 之间注册表信息的同步复制，使Eureka Server 集群中不同
  注册表中服务实例信息保持一致。
  Make Remote Call ：服务之间的远程调用。
  Register ：注册服务实例， Client 端向Server 端注册自身的元数据以供服务发现。
  Renew ：续约，通过发送心跳到S巳rver 以维持和更新注册表中服务实例元数据的有
  效性。当在一定时长内， Server 没有收到C lient 的心跳信息，将默认服务下线，会
  把服务实例的信息从注册表中删除。
  Cancel ：服务下线， Client 在关闭时主动向Server 注销服务实例元数据，这时Client
  的服务实例数据将从Server 的注册表中删除。
  Get Registry ： 获取注册表， Client 向Server 请求注册表信息，用于服务发现，从而
  发起服务间远程调用。
  ```

### 1.2 服务发现原理

后台完成工作如图：![1562591620118](C:\Users\zxw\AppData\Roaming\Typora\typora-user-images\1562591620118.png)

- DiscoverClient 是springcloud中用来进行服务发现的顶级接口，在Netfix Eureka中都有相应的具体实现类
- ![1562592304591](C:\Users\zxw\AppData\Roaming\Typora\typora-user-images\1562592304591.png)

### 1.3服务发现客户端

DiscoveryClient 是Eureka Client的核心，包括与Eureka Server交互的关键逻辑，具备以下职能：

- 注册服务实例到Eureka Server 中；
- 发送心跳更新与Eureka Server 的租约；
- 在服务关闭时从Eureka Server 中取消租约，服务下线；
- 查询在Eureka Server 中注册的服务实例列表。

DiscoveryClient类结构

- ![1562594001860](C:\Users\zxw\AppData\Roaming\Typora\typora-user-images\1562594001860.png)

#### 1.3.1LookupService

- ```
  LookupService 作用是发现活跃的服务实例， 主要方法如下
  //LookupService.java
  public interface LookupService<T> { 
  ／／根据服务实例注册的appNarne来获取封装有相同appNarne 的服务实例信息容器
  Application getApplication(String appName);
  ／／返回当前注册表中所有的服务实例信息
  Applications getApplications();
  ／／根据服务实例的id获取服务实例信息
  List<Instanceinfo> getinstancesById (String id) ;
  ...
  }
  ```

#### 1.3.2 EurekaClient

EurekaCient 在LookupService 的基础上扩充了更多的接口，提供了更丰富的获取服务
实例的方式(方便版本过度)

- 提供了多种方式获取Instancelnfo ， 例如根据区域、Eureka Server 地址等获取。
- 提供了本地客户端（所处的区域、可用区等） 的数据，这部分与AWS 密切相关。
- 提供了为客户端注册和获取健康检查处理器的能力。

```java
／／为Eureka Client 注册健康检查处理器
public void registerHealthCheck(HealthCheckHandler healthCheckHandler) ;
／ ／为EurekaClient 注册一个Eureka Event Listener(事件监听器)
／／监听Clie口t服务实例信息的更新
public void registerEventListener(EurekaEventListe口er eventListener) ;
```

```java
Eureka Server 一般通过心跳（ heartbeats ）来识别一个实例的状态。Eureka Client 中存
在一个定时任务定时通过HealthCheckHandler 检测当前Client 的状态，如果Client 的状态
发生改变， 将会触发新的注册事件，更新Eureka Server 的注册表中该服务实例的相关信息。
HealthCheckHandler 的代码如下所示：
//HealthCheckHandler.java
publicc interface Heal thCheckHandler {
Instanceinfo.InstanceStatus getStatus (Instanceinfo.InstanceStatus currentStatus);
}
HealthCheckHandler 接口的代码如上所示，其在spring-cloud-netflix - eureka心lient 中的实
现类为EurekaHealthCheckHandler ， 主要组合了spring - boot-actuator 中的HealthAggregator
和Healthlndicator ，以实现对Spring Boot 应用的状态检测。
Eureka 中的事件模式属于观察者模式，事件监听器将监昕Client 的服务实例信息变化，
触发对应的处理事件
```

#### 1.3.3 DiscoveryClient

```
在DiscovryClient 构造函数中,Eureka Client 会执行从Eureka Server 中拉取注册表
信息、服务注册、初始化发送心跳、缓存刷新（ 重新拉取注册表信息、）和按需注册定时任
务等操作，可以说DiscoveryClient 的构造函数贯穿了Eureka Client 启动阶段的各项工作。
DiscoveryClient 的构造函数传人的参数如下所示：
```

![1562595731113](C:\Users\zxw\AppData\Roaming\Typora\typora-user-images\1562595731113.png)

> ApplicationlnfoManager 和EurekaClientConfig 在前面内容中已经做了介绍，一个是应
> 用信息管理器，另一个是封装了Client 与Server 交互配置信息的类。
>
> AbstractDiscoveryClientOptionalArgs 是用于注入一些可选参数，以及一些jerseyl 和
> jersey2 通用的过滤器。而BackupRegistry 充当了备份注册中心的职责，当Eureka Client 无
> 法从任何一个Eureka Server 中获取注册表信息时， BackupRegistry 将被调用以获取注册表
> 信息。默认的实现是Notlmp lement巳dRegistrylmpl ，即没有实现。