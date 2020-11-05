> kxmall 针对中小商户、企业和个人学习者开发。使用Java编码，采用SpringBoot、Mybatis-Plus等易用框架，适合个人学习研究。同时支持单机部署、集群部署，用户与店铺范围动态定位，中小商户企业可根据业务动态扩容。kxmall使用uniapp前端框架，可同时编译到 微信小程序、H5、Android App、iOS App等几个平台，可为中小商户企业节约大量维护成本。也可支撑中小商户企业前期平台横扩需求。

---
QQ讨论群：865607763 (进群前，请在网页右上角点star)
---

#### 数据库初始化sql文件，请进入讨论交流群，群文件自行下载，欢迎讨论与交流
---
#### 优先更新地址

[01-kxmall用户端源码地址](https://gitee.com/shoujing1001/kxmall/)

[02-kxmall服务端源码地址](https://gitee.com/ybyt123/kxmall)

[03-kxmall后台管理端源码地址](https://gitee.com/zhengkaixing/kxmall-ui.git)

[04-kxmall骑手端源码地址](https://gitee.com/shoujing1001/kxmall-hourseman)

---

#### 视频教程

[01-kxmall微信B2C商城搭建-搭建开发环境](https://www.bilibili.com/video/av85227442/)

[02-kxmall微信B2C商城搭建-本地运行前后端代码](https://www.bilibili.com/video/av85237640/)

[03-kxmall微信B2C商城搭建-打包上传到服务器运行（前后端）](https://www.bilibili.com/video/av85244186/)

#### 用户端系统演示

下面分别是微信小程序包和Android安装Apk。因为苹果开发者ID太贵，所以这里没有打包，实际也可同时支持苹果的。
在这基础上，还增加了H5。可内置到微信公众号上，变成公众号商城！尽情体验！


---
| 河禾生鲜 | 河禾生鲜 | 河禾生鲜 |
| :----: | :----: | :----: |
| ![河禾生鲜](https://images.gitee.com/uploads/images/2020/0422/222247_2cfd32fc_3044973.jpeg)  | ![河禾生鲜](https://images.gitee.com/uploads/images/2020/0422/222248_99d41ffb_3044973.jpeg) | ![河禾生鲜](https://images.gitee.com/uploads/images/2020/0422/222248_4d595984_3044973.jpeg) |
| ![河禾生鲜](https://images.gitee.com/uploads/images/2020/0422/222248_d7ab2977_3044973.jpeg)  | ![河禾生鲜](https://images.gitee.com/uploads/images/2020/0422/222248_f0f1fc0c_3044973.jpeg) | ![河禾生鲜](https://images.gitee.com/uploads/images/2020/0422/222249_7e26e581_3044973.jpeg) |

#### 后台端系统演示

- [后台演示地址链接<--点击](http://121.196.31.198/)
- 登录名:guest   密码:123456   验证码:666666 (guest仅有只读权限)


#### 骑手端系统演示


#### 插件中心

kxmall主程序功能均项目中，包括其基本的订单、商品、广告、优惠券、运费模板、VIP等等功能。后续功能将以插件的形式陆续提供。插件使用主程序模板方法的形式实现。具有自动安装的功能，尽量减少对主代码入侵。插件并无太多学习价值，所以仅对授权用户开放，并且每个插件单独收费。

**1.点餐列表**   部分开发者也许需要点餐列表，用于外卖、社区超市、生鲜线下配送等场景。

**2.自动发货**  部分商家售卖虚拟物品，例如话费、网游充值卡。自创作视频教程等。使用自动发货插件可自动将卡密发送到用户微信上。

更多插件，敬请期待！

#### kxmall 基本框架

kxmall使用前后分离的设计、后端采用Java编写，前端均适用vuejs编写。

![架构图](snapshoot/framework.png)

#### 项目部署方式

>项目部署

##### ⓪ 服务器推荐
服务器可根据自身业务来选购，单机环境推荐2C4G
[阿里云新用户服务器优惠链接](https://www.aliyun.com/minisite/goods?userCode=3prh4omm)

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

项目部署分为 Register Server、Rider Server、Admin Server 、 App 、Rider App  五个部分

[部署Step1:Java后台编译&部署](doc/server.md)    

1.服务器安装必备软件[JDK | mysql | Redis | RabbitMq | Nginx]  
2.先启注册中心： market-register  
3.然后启动其它服务，其它服务不分先后  

[部署Step2:App编译打包](doc/app.md)

[部署Step3:Admin编译打包](doc/admin.md)

##### ④ 二次开发文档

[二次开发文档](doc/2develop.md)

#### 版权声明

本项目后端由云伴工作室开发，禁止未经授权用于商业用途。个人学习可免费使用。如需商业授权，进QQ讨论群（865607763）联系群主。


前端代码使用的 mix.R 的开源模板，在此模板的基础上对接了kxmall后端Api，作者说不做商用限制，保留原地址，所以授权仅针对后端代码商用授权。

mix模板地址：https://ext.dcloud.net.cn/plugin?id=200

### 更多服务

### 项目定制开发服务

俗称外包，请加文档顶部的群，联系群主或者管理员（外包不仅限于线上商城）。

### SaaS服务

正在研发中...
