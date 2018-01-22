# star-map 星图

* 在web集群中启用定时任务，往往需要对定时任务进行额外的处理。星图的思路是使web集群的每个节点都能作为任务执行节点去执行定时任务，但又不需担心任务多次执行的问题。
* 星图是基于Dubbo设计思路的分布式任务分发执行服务，控制中心用于将定时任务分发到多个任务执行节点去执行，任务执行成功之后会将执行结果返回给控制中心，以扩展任务执行能力。
* 需求：JAVA8，maven3

## 模块介绍

* console：控制中心，用来分发任务，获取任务结果。
* selector: 选择器，利用zookeeper实现console主节点选举，用来执行定时任务。
* task：任务执行节点，可单独部署，也可集成作为web服务的模块。
* registry：注册中心，用来管理任务执行节点地址。
* loadBalance：负载均衡，用来获取当前可执行节点。

![模块](https://raw.githubusercontent.com/justice-code/star-map/master/wiki/resources/static/star-map.png)

## 控制中心

目前使用spring scheduled，采用变成方式来增加定时任务，具体的任务执行逻辑则存储在脚本中。触发时由```org.eddy.schedule.ScheduleSender```来推送执行脚本到具体的执行节点。通过异步的future```org.eddy.future.StarFuture```来获取任务执行结果。

## 节点选择器
默认利用zookeeper实现`控制中心`选举，选出的leader节点用来执行执行任务，租约时常为`5秒钟`。

## 任务执行

使用```org.eddy.engine.Engine```作为任务执行处理器来执行任务。默认使用groovy engine作为任务脚本执行器来执行任务。

## 通信协议

默认使用基于netty4的TCP长链接通信。

## 注册中心

默认使用zookeeper作为注册中心。

## 模块扩展

每个被添加了```org.eddy.extension.Extension```注解的接口均可自定义扩展，扩展实现需要使用和星图相同的spring context来管理。```org.eddy.extension.ExtensionLoader```会根据注解的value作为key来从配置文件中获取匹配的spring bean name。

## sandbox

为了防止任务脚本越权调用spring context中的其他模块，sandbox用来为任务脚本构建沙箱环境，使得任务执行与其他服务能够隔离开来。是否开启sandbox功能需要主动增加```-Dstar.policy.path=```JVM变量。sandbox边界需要在方法上增加```org.eddy.permission.annotation.Permission```注解。

## demo

所有demo都是用过spring boot来构建，启动也是通过执行Main函数。

* task启动类：```org.eddy.TaskApplicationStart```
* console启动类：```org.eddy.ConsoleApplicationStart```

如果使用单机同时启动task和console，可以通过指定web服务监听端口```-Dserver.port=8082```来防止端口被占用导致的spring boot启动报错。

## 存在的问题

1. 仅支持编程式地增加执行任务。