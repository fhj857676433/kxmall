> kxmall 针对中小商户、企业和个人学习者开发。使用Java编码，采用SpringBoot、Mybatis-Plus等易用框架，适合个人学习研究。同时支持单机部署、集群部署，用户与店铺范围动态定位，中小商户企业可根据业务动态扩容。kxmall使用uniapp前端框架，可同时编译到 微信小程序、H5、Android App、iOS App等几个平台，可为中小商户企业节约大量维护成本。也可支撑中小商户企业前期平台横扩需求。

---
QQ讨论群：865607763 (进群前，请在网页右上角点star)
---

#### 数据库初始化sql文件，请进入讨论交流群，群文件自行下载，欢迎讨论与交流
---
#### 优先更新地址

kxmall项目结构:

- Java 后端服务
    - kxmall-admin: 启动器（打包打这个就行）
    - kxmall-admin-api: 提供管理员管理系统的WebApi
    - kxmall-app-api: 提供APP、小程序、H5用户请求的WebApi
    - kxmall-biz: 提供通用业务代码
    - kxmall-data: 提供数据模型以及数据访问层封装
    - kxmall-core: 提供注解、工具类等
    
- Vue 前端页面
    - kxmall-admin-ui: 基于element-ui的后台管理页面
    - kxmall-app-ui: 基于uniapp的小程序、H5、APP前端代码

- sql: 数据库初始化SQL脚本

#### 数据库初始化sql文件，请进入讨论交流群，群文件自行下载，欢迎讨论与交流
---
#### 优先更新地址

[01-kxmall源码地址](https://github.com/zhengkaixing/kxmall) https://github.com/zhengkaixing/kxmall

---

#### 用户端系统演示

下面是微信小程序真机模式调试的界面，可Android安装Apk,也可同时支持苹果。
在这基础上，还增加了H5。可内置到微信公众号上，变成公众号商城！尽情体验！


---
| 河禾生鲜 | 河禾生鲜 | 河禾生鲜 |
| :----: | :----: | :----: |
| ![河禾生鲜](doc/kxmall-app-1.jpeg)  | ![河禾生鲜](doc/kxmall-app-2.jpeg) | ![河禾生鲜](doc/kxmall-app-3.jpeg) |
| ![河禾生鲜](doc/kxmall-app-4.jpeg)  | ![河禾生鲜](doc/kxmall-app-5.jpeg) | ![河禾生鲜](doc/kxmall-app-6.jpeg) |

#### 后台端系统演示

使用免费开源框架vue-element-admin，基于element-ui的后台管理页面！尽情体验！


---
 
![河禾生鲜](doc/kxmall-admin-1.png)  
![河禾生鲜](doc/kxmall-admin-2.png)  
![河禾生鲜](doc/kxmall-admin-3.png)   

- 登录名:guest   密码:123456   验证码:666666 (guest仅有只读权限)


#### 项目部署方式

>项目部署

##### ⓪ 服务器推荐
服务器可根据自身业务来选购，单机环境推荐2C4G

##### ① 基础运行环境

| 运行环境 | 版本号 |
|:--------|:--------|
|  MySQL   |  5.7（推荐）   |
|  JDK   |  1.8（推荐）   |
|  Redis   |  4.0.1（其他也可以）   |
|  RabbitMq  | 3.7.15（其他也可以）   |
|  Nginx  |  只要Web容器就可以了  |

请参考 [CentOS7.4 安装 MySQL5.7](https://github.com/iotechn/document-basic/blob/master/CentOS7.4_Install_MySQL5.7.md)

请参考 [CentOS 安装 JDK8](https://github.com/iotechn/document-basic/blob/master/CentOS_Install_JDK8.md)

请参照CentOS [安装 NodeJs 8.15.0](https://github.com/iotechn/document-basic/blob/master/CentOS_Install_NodeJS_8.15.0.md)

Redis安装可直接使用yum安装 
	
	yum install redis

安装完成后使用 redis-cli 命令，若能进入，则表示redis安装完成

##### ② 后台管理系统编译运行指南

[编译运行指南](doc/run.md)

##### ③ 编译部署前后端代码

项目部署分为 Admin/App Server 、 App 、 Admin  三个部分

[部署Step1:Java后台编译&部署](doc/server.md)    

1.服务器安装必备软件[JDK | mysql | Redis | Nginx]   
2.启动服务 

[部署Step2:App编译打包](doc/app.md)

[部署Step3:Admin编译打包](doc/admin.md)


#### 版权声明

本项目后端由云伴工作室开发，禁止未经授权用于商业用途。个人学习可免费使用。如需商业授权-授权针对前段代码和后端代码商用授权，进QQ讨论群（865607763）联系群主。


### 更多服务

### 项目定制开发服务

俗称外包，请加文档顶部的群，联系群主或者管理员（外包不仅限于线上商城）。

### SaaS服务

正在研发中...
