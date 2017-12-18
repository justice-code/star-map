# star-map 星图

* 在web集群中启用定时任务，往往需要对定时任务进行额外的处理。星图的思路是使web集群的每个节点都能作为任务执行节点去执行定时任务，但又不需担心任务多次执行的问题。
* 星图是基于Dubbo设计思路的分布式任务分发执行服务，控制中心用于将定时任务分发到多个任务执行节点去执行，任务执行成功之后会将执行结果返回给控制中心，以扩展任务执行能力。

## 模块介绍

* console：控制中心，用来分发任务，获取任务结果。
* task：任务执行节点，可单独部署，也可集成作为web服务的模块。
* registry：注册中心，用来管理任务执行节点地址。
* loadBalance：负载均衡，用来获取当前可执行节点。

![模块](https://raw.githubusercontent.com/justice-code/star-map/master/wiki/resources/static/star-map.png)
