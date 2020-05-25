# springboot_v1


# 结构

```
Springboot
│
├─ares-ui vue前端 参考若依vue前端http://www.ruoyi.vip
│
├─doc  项目SQL语句以及文档
│
├─common 公共模块
│  ├─autocode 自动生成代码
│  ├─config springBoot所有配置
│  ├─interceptor 拦截器
│  ├─kafka kafka配置
│  ├─log 日志记录AOP
│  ├─model 通用实体
│  ├─quartz spring计划任务
│  ├─rabbitmq rabbitmq配置
│  ├─redis 缓存配置
│  ├─shiro shiro权限模块
│  ├─spring Spring工具
│  └─templates 模版
│
├─project 系统模块
│  ├─controller 请求访问模块
│  ├─dao Dao模块
│  ├─model 实体类模块
│  └─service 服务层模块
│
├─utils 工具模块
│
├─restful 前后分离请求访问模块
│
├─jobs 定时任务
│
├─ServletInitializer 启动类
│ 
├─SpringbootV1Application tomcat启动类
│
├─test 测试类
│
├─resources 配置文件夹
│  ├─mapping mybatis Mapper.xml文件夹
│  │   ├─project 
│  │   └─generator 代码生成
│  ├─static 静态文件存放文件夹
│  │   ├─css css目录存放
│  │   ├─js js存放
│  │   └─system 系统
│  │
│  ├─templates 前台HTML存放文件夹
│  │
│  ├─application-dev.yml 开发环境配置
│  ├─application-prod.yml 生产环境配置
│  ├─application.yml springboot配置
│  ├─banner.txt springboot 启动动画
│  ├─logback-spring.xml log4j配置文件
│  └─mybatis-generator.xml mybates自动生成 xml、dao、model
│  
└─pom.xml   maven.xml
```
