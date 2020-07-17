# CCR

### 主要项目组织结构

```
bs-ccr-springcloud
├── bs-ccr-aliCloud-gateway -- 阿里云api管理模块，就是管理本项目consumer中的所有接口
├── bs-ccr-api -- 系统一些共同封装模块，业务类以及数据库映射基础类
├── bs-ccr-basic-deal -- 封装货币交易逻辑以及公式模块
├── bs-ccr-consumer -- 系统对外暴露的统一接口服务
├── bs-ccr-deal -- 火币交易平台的货币交易服务
├── bs-ccr-deal-okex -- okex交易平台的货币交易服务
├── bs-ccr-exception -- 系统全局异常处理
├── bs-ccr-flyway -- 数据库sql管理工具模块
├── bs-ccr-message-rocketMQ -- rocketMQ模块
├── bs-ccr-mybatis-generator -- MyBatisGenerator生成的数据库操作代码
├── bs-ccr-order -- 交易订单记录服务
├── bs-ccr-provider-coin -- 货币模块提供者服务
├── bs-ccr-provider-combo -- 系统套餐提供者服务
├── bs-ccr-provider-product -- 系统产品提供者服务
├── bs-ccr-provider-product-combo -- 系统产品套餐提供者服务
├── bs-ccr-provider-reason -- 事由提供者服务
├── bs-ccr-provider-strategy -- 策略模块提供者服务
├── bs-ccr-provider-trade-basic-data -- 提供者汇总服务
├── bs-ccr-provider-trade-platform -- 交易平台提供者服务
├── bs-ccr-provider-transaction -- 自选货币对交易提供者服务
├── bs-ccr-provider-user -- 用户提供者服务
├── bs-ccr-provider-user-boss -- 后台管理用户提供者服务
├── bs-ccr-provider-user-front -- 前台用户提供者服务
├── bs-ccr-redis -- 对接redis模块
├── bs-ccr-security-jwt-auth-server -- spring security Oauth2.0的认证服务器
├── bs-ccr-security-jwt-client -- spring security Oauth2.0的资源服务器
├── bs-ccr-service -- 系统的Service层
├── bs-ccr-swagger -- 对接接口文档swagger
└── bs-ccr-validator -- 封装验证模块
```

##### 

#### SpringCloud SpringBoot版本

| SpringCloud Version | SpringBoot Version |
| ------------------- | ------------------ |
| Greenwich           | 2.1.6              |

#### 数据库一些配置

##### redis: password = 123zxc

##### mysql : username=root; password=123zxc;

数据库(bs-ccr)结构：

![image-20200717155743553](/Users/admin/Library/Application Support/typora-user-images/image-20200717155743553.png)

#### 项目启动

##### 1.下载docker，用docker启动ccr所需环境

在bs-ccr-springcloud项目下，有一个docker的文件夹，下面有启动系统所需环境的docker-compose.yaml，用docker compose命令去启动。

##### 2.启动哪几个服务

按顺序启动以下必要服务：

1. bs-ccr-provider-trade-basic-data
2. bs-ccr-consumer
3. bs-ccr-security-jwt-auth-server

(注意：boostrap.yml中的启动环境建议用dev)

以下服务按功能需求

- bs-ccr-deal
- bs-ccr-deal-okex
- bs-ccr-order
- bs-ccr-provider-product-combo