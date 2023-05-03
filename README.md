# Logistics-backend

使用 Java 撰写的物资管理系统后端

## 开发技术栈

> 注意：本项目强制使用 JDK17,低版本 JDK 无法编译(SpringBoot 3 强制要求)
>
> 开发环境为 JDK 19,实测可以编译通过,更高版本的 JDK 未测试

- JDK 17 (LTS)
- SpringBoot 3.0.6
    - Spring Data JPA
    - Spring Security
- MySQL 8.0
- Redis 7.0

## 项目结构

开发中需要关注的目录结构如下

- `controller` : 交互层
    - `request` : 入参类型
    - `response` : 响应类型
- `repository` : 数据仓库层,用于和数据库交互
    - `entity` : 与数据库字段对应的类型
- `service` : 业务处理层,复杂或者公用逻辑放在这里
    - `model` : 应用内传输用类型放这里

> 您无需为每个类型都提供对应接口,只有当接口在可见未来有多个实现的时候才考虑建立接口

其余目录结构如下,您无需过多关注

- `config` : 存放 spring 配置
- `common` : 存放通用的类型
- `security` : 存放安全相关的配置
- `util` : 存放工具类

## 安装开发环境

### JDK 选择

**下载安装 jdk 17 或者以上版本的 jdk**

可以考虑从 [Azul Zulu Builds of OpenJDK](https://www.azul.com/downloads/?version=java-17-lts&package=jdk)
或者 [Liberica JDK](https://bell-sw.com/pages/downloads/#/java-17-lts) 下载安装

### 数据库与中间件

本地测试使用的 MySQL 版本为 8.0.32,Redis 版本为 7.0.11

> 如果您是 Windows 用户,可以从 https://github.com/tporadowski/redis 中下载版本较旧的 redis 使用

## 启动环境

> 已知问题：当项目存放的路径中存在中文时,可能会导致项目无法启动,请将项目放在英文路径下
>
> 具体报错为`java.nio.charset.MalformedInputException: Input length = 1`

### 修改配置文件

使用你喜欢的 IDE 导入此项目,复制`/src/main/resources/application-template.yml`
文件,重命名为`application-dev.yml`,按照`application-template.yml`中的配置,符合你自己配置的环境

### 导入依赖

使用 Gradle 导入依赖,如果您使用的是 IDEA,可以直接在 IDEA 中导入,也可以运行
`./gradlew idea` 或者 `./gradlew eclipse` 生成 IDE 配置文件

最后运行 MainApplication 类里的 main 方法

## 测试

若不想为每次测试携带 token,可以在`application-dev.yml`中将`jwt.enabled`设置为`false`

## Git 提交规范

本项目使用 [AngularJS Git Commit Message Conventions](https://docs.google.com/document/d/1QrDFcIiPjSLDn3EL15IJygNPiHORgU1_OOAqWjiDU5Y/edit#heading=h.greljkmo14y0)
规范,请在提交代码前阅读,简易说明参照[这里](/docs/commit.md)

> 可以在 IDEA 中安装插件`Git Commit Template`来自动化提交规范

Angular 规范中定义的格式有 3 个内容:`Header`,`Body`,`Footer`，其中`Header`是必需的，`Body`和`Footer`可以省略
