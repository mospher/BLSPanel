# BSL v2.0.3

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE) [![加入QQ群①](https://img.shields.io/badge/QQ%E7%BE%A4%E2%91%A0-941209502(%E5%B7%B2%E6%BB%A1)-red)](https://jq.qq.com/?_wv=1027&k=5RCnDCO) [![加入QQ群②](https://img.shields.io/badge/QQ%E7%BE%A4②-545633945-brightgreen.svg)](https://jq.qq.com/?_wv=1027&k=5AIEf1E) [![star](https://gitee.com/aun/timo/badge/star.svg?theme=dark)](https://gitee.com/aun/BSL/stargazers) [![fork](https://gitee.com/aun/BSL/badge/fork.svg?theme=dark)](https://gitee.com/aun/BSL/members)

#### 项目介绍

BSL后台管理系统，基于SpringBoot2.0 + Spring Data Jpa + Thymeleaf + Shiro 开发的后台管理系统，采用分模块的方式便于开发和维护，支持前后台模块分别部署，目前支持的功能有：权限管理、项目管理、字典管理、日志记录、文件上传、代码生成等，为快速开发后台系统而生的脚手架！

#### 技术选型

- 后端技术：SpringBoot + Spring Data Jpa + Thymeleaf + Shiro + Jwt + EhCache

- 前端技术：Layui + Jquery  + zTree + Font-awesome

#### 全新的项目结构

![项目结构图](https://oscimg.oschina.net/oscnet/4e8e47b3801ba98767dc25a1a6efbb522fe.jpg)

#### 功能列表

- 用户管理：用于管理后台系统的用户，可进行增删改查等操作。
- 角色管理：分配权限的最小单元，通过角色给用户分配权限。
- 菜单管理：用于配置系统菜单，同时也作为权限资源。
- 项目管理：通过不同的项目来管理和区分用户。
- 字典管理：对一些需要转换的数据进行统一管理，如：男、女等。
- 行为日志：用于记录用户对系统的操作，同时监视系统运行时发生的错误。
- 文件上传：内置了文件上传接口，方便开发者使用文件上传功能。
- 代码生成：可以帮助开发者快速开发项目，减少不必要的重复操作，花更多精力注重业务实现。
- 表单构建：通过拖拽的方式快速构建一个表单模块。
- 数据接口：根据业务代码自动生成相关的api接口文档

#### 安装教程

- ##### 环境及插件要求

   - Jdk8+
   - Mysql5.5+
   - Maven
   - Lombok<font color="red">（重要）</font>

- ##### 导入项目

   - IntelliJ IDEA：Import Project -> Import Project from external model -> Maven
   - Eclipse：Import -> Exising Mavne Project


- ##### 运行项目

  - 通过Java应用方式运行admin模块下的com.linln.admin.BootApplication文件
  - 数据库配置：数据库名称timo   用户root    密码root
  - 访问地址：http://localhost:8080/
  - 默认帐号密码：admin/123456

#### 使用说明

1. 使用文档：sdoc/使用文档.docx
2. 开发手册：[BSL开发文档](http://42.194.205.137/docs)、[看云文档](https://www.kancloud.cn/BSL/BSL-doc)
3. SQL文件：sdoc/timo.sql

#### 演示地址
演示地址： [http://42.194.205.137](http://42.194.205.137)

#### 预览图

![项目结构图](https://oscimg.oschina.net/oscnet/584b70844227ad813eb8f10bd452fad015c.jpg)

![登录页面](https://oscimg.oschina.net/oscnet/55b1a88090da20735b67ec91a9bcbafc48a.jpg)

![用户管理](https://oscimg.oschina.net/oscnet/91d2f63ac18b34773ddb7f20b25d0c9c539.jpg)

![菜单管理](https://oscimg.oschina.net/oscnet/ac6c1a0521acb2c22c76130057bd97dfd3f.jpg)

![字典管理](https://oscimg.oschina.net/oscnet/ce428dc1a62c6d591ac6bb5ed10e32caf39.jpg)

![行为日志](https://oscimg.oschina.net/oscnet/8b41f93fad654f81349d9572c1630f6fe1f.jpg)

![代码生成](https://oscimg.oschina.net/oscnet/f355fa74868080440299fa4453e9b7ea399.jpg)