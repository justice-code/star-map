# star-map 星图

* 在分布式web服务中启用定时任务，往往需要对定时任务进行额外的处理。星图的思路是使分布式web服务的每个节点都能作为任务执行节点去执行定时任务，但又不需担心任务多次执行的问题。
* 星图是基于Dubbo设计思路的分布式任务分发执行服务，控制中心用于将定时任务分发到多个任务执行节点去执行，任务执行成功之后会将执行结果返回给控制中心，以扩展任务执行能力。

## 模块介绍

