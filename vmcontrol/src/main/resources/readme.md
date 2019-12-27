**VMControl System ReadMe**

后端模块项目地址；https://github.com/Threadalive/vm-control.git

项目技术架构：
后端基础框架：SpringBoot
持久化框架：Spring-data-JPA
数据库：Mysql
缓存中间件：Redis
安全框架：Shiro
JSON组件：alibaba-fastjson
依赖管理：Maven
Libvirt组件：java-libvirt
API管理：SWAGGER

本项目使用以上框架与组件技术搭建，基于前后端分离的设计，前端模块使用Vue进行搭建，地址如下

前端模块：https://github.com/kongfu-cat/vm-front-end/

旨在搭建一个基于KVM的虚拟机管理的Web操作平台，实现了包括虚拟机的创建销毁，启动挂起关闭等等一系列操作，以及虚拟机和相关主机、集群的数据记录，数据查询展示等等。

启动项目时，可先在Navicat上执行以下resources下的sql文件，用户记录中目前仅有admin用户，密码123456，新添加的用户密码使用md5加密，可对用户添加角色以及角色对应的权限细粒度地控制各用户的访问接口。

在Vue项目中使用admin用户即可登录。

主页中展示总体概览信息，集群管理中可选择树形的菜单选项，包括集群，主机，虚拟机，由后台动态生成。

选择虚拟机获取具体虚拟机信息，以及实时信息监控（cpu、内存占用率等）

虚拟机信息中可进行虚拟机的启停，增加删除虚拟机，修改信息等操作。

集群以及主机也可进行相应curd操作。

日志直接输出本地路径下文件夹，级别为info，可根据自己文件路径进行设置。

##