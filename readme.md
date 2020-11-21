**项目说明** 
- 本项目是基于springboot的长链转短链项目、即开即用

<br>

**具有如下特点** 
- 友好的代码结构及注释，便于阅读及二次开发
- 引入mybatis-plus 具备完善的代码生成机制，可在线生成entity、dao、service、controller、sql代码，减少70%以上的开发任务
<br> 

**项目结构** 
```
short-url
├─doc  接口文档及时序图
│
├─short-api api接口模块
│  ├─db 初始化数据库
│  ├─src
│    ├─aspect  日志切片
│    ├─common  公共组件
│    ├─config  配置信息
│    └─controller 
│  ├─ShrotUrlApplication 项目启动类

```

**技术选型：** 
- 核心框架：Spring Boot 2.4.0
- 持久层框架：MyBatis 2.1.3
- 数据库连接池：Druid 1.1.10
<br> 


 **后端部署**
- 通过git下载源码
- idea、eclipse需安装lombok插件，不然会提示找不到entity的get set方法
- 创建数据库short_url，数据库编码为UTF-8
- 执行db/init.sql文件，初始化数据
- 修改application-dev.yml，更新MySQL账号和密码
- Eclipse、IDEA运行ShortUrlApplication.java，则可启动项目

<br> 