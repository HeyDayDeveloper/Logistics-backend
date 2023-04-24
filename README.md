# Logistics-backend

使用 Java 撰写的物资管理系统后端

## 开发技术栈

> 注意：本项目强制使用JDK17，低版本JDK无法编译（SpringBoot 3要求）

- JDK 17 (LTS)
- SpringBoot 3.0.6
    - spring-jpa
    - spring-security
    - springdoc-openapi
- MySQL 8.0
- Redis

## 项目结构

- `config`存放spring配置
- `controller`交互层
    - `request`入参类型
    - `response`响应类型
- `repository`数据仓库层，用于和数据库交互
    - `entity`与数据库字段对应的类型
- `service`业务处理层，复杂或者公用逻辑放在这里（注：您无需为每个类型都提供对应接口，只有当接口在可见未来有多个实现的时候才考虑建立接口）
    - `model`应用内传输用类型放这里

## 安装开发环境

### JDK 选择

**下载安装 jdk 17 或者以上版本的 jdk**

可以考虑从 [Azul Zulu Builds of OpenJDK](https://www.azul.com/downloads/?version=java-17-lts&package=jdk)
或者 [Liberica JDK](https://bell-sw.com/pages/downloads/#/java-17-lts) 下载安装

### 数据库与中间件

本地测试使用的 MySQL 版本为 8.0.32，Redis 版本为 7.0.11

> 如果您是Windows用户，可以从 https://github.com/tporadowski/redis 中下载版本较旧的 redis 使用

## 启动环境

### 修改配置文件

使用你喜欢的 IDE 导入此项目，复制`/src/main/resources/application-template.yml`
文件，重命名为`application-dev.yml`,按照`application-template.yml`中的配置，符合你自己配置的环境

### 导入依赖

使用 Gradle 导入依赖，如果您使用的是 IDEA，可以直接在 IDEA 中导入，也可以运行
`./gradlew idea` 或者 `./gradlew eclipse` 生成 IDE 配置文件

最后运行 MainApplication 类里的 main 方法

