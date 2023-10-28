# 工程简介

### 项目介绍：

> 快速开发实现微服务系统的基础功能，现在没有前端对接，仅仅为微服务架子

### 项目架构：

* 后端：Spring Boot、Spring Cloud & Alibaba、Spring Security、Nacos、Sentinel、Mybatis-Plus、MySQL
* 前端：Vue、Element UI、Vuex、Js-Cookie、Axios
* 文件存储：Minio

### 主要实现功能：

* 实现Spring Cloud Gateway网关集成Swagger、拓展Spring Cloud Gateway实现路由信息保存至Redis
* Spring Security  OAuth2认证登录，用户信息存入Redis中，自定义权限注解
* 自定义注解 Ignore ，实现安全接口暴露
* 文件管理，管理上传至Minio中的文件
* ELK框架进行日志收集
* 拉取 sentinel-dashboard 源码，将原有的流量规则存入内存中，改为存放至Redis，以每个微服务的服务名作为Key
* 公共sentinel模块，以每个微服务的服务名作为Key，服务启动时通过Key拉取每个服务的流量规则
* 可视化路由规则，动态CURD路由规则
* 基于Jackson扩展实现字典翻译序列化给前端
* 通用后台管理所具备的功能 （待完成）

### 部分主要功能Api (密码：1911298402)
```http 
https://apifox.com/apidoc/shared-0a1cd98b-52cd-4a5d-baf6-efb4ed1f4fbc?pwd=1911298402
```

## 公共yml （Nacos）

> Data Id: application-dev.yml
>
> Group: DEFAULT_GROUP
>
> 归属应用: 所有应用

```yml
# 应用名称
spring:
  # redis 配置
  redis:
    host: ${REDIS_HOST:192.168.56.2}
    port: 6379
    password: 123456
    jedis:
      pool:
        max-active: 20 # 连接池最大连接数（使用负值表示没有限制）
        max-wait: -1  # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-idle: 10   # 连接池中的最大空闲连接
        min-idle: 3   # 连接池中的最小空闲连接
        time-between-eviction-runs: 100 # 连接超时时间（毫秒）
  swagger:
    author:
      name: 小政同学i丷
      email: xiaozheng666888@qq.com
    api-info:
      title: mz-cloud接口文档
      version: v1.0
# 可控点Api开启
management:
  endpoints:
    web:
      exposure:
        include: gateway
  # 网关可控点
  endpoint:
    health:
      show-details: always
```

# 延伸阅读

## 新模块遵守约束

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns = "http://maven.apache.org/POM/4.0.0" xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation = "http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <!--包名-->
    <groupId>com.mz.modules</groupId>
    <!--模块名-->
    <artifactId>mz-modules</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>mz-modules</name>
    <!--父模块为pom,子模块为jar-->
    <packaging>pom</packaging>

    <!--父依赖-->
    <parent>
        <artifactId>mz-cloud</artifactId>
        <groupId>com.mz.cloud</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <!--模块描述-->
    <description>MZ系统业务模块</description>

</project>
```

### 新模块下的子模块

```ini
mz-modules
├── mz-system -- system 模块 
├──   ├── mz-system-api -- system feign api 暴露
├──   ├── mz-system-model -- 实体对象抽离
├──   ├── mz-system-provider -- 服务能力提供
```

## 提交指南
Commit message 包括三个部分：Header，Body 和 Footer。可以用下方的格式表示它的结构。

```
<type>(<scope>): <subject>// 空一行<body>// 空一行<footer>
```

> 其中，Header 是必需的，Body 和 Footer 可以省略(默认忽略)，一般我们在 `git commit` 提交时指定的 `-m` 参数，就相当于默认指定 Header。

> 不管是哪一个部分，任何一行都不得超过72个字符（或100个字符）。这是为了避免自动换行影响美观。

### Header

Header部分只有一行，包括三个字段：type（必需）、scope（可选）和subject（必需）。

#### type

-   **feat**：新功能（feature）
-   **fix**：修补bug
-   **docs**：文档（documentation）
-   **style**： 格式（不影响代码运行的变动）
-   **refactor**：重构（即不是新增功能，也不是修改bug的代码变动）
-   **test**：增加测试
-   **chore**：构建过程或辅助工具的变动
-   **delete**：删除没用的文件和变动

如果 type 为 feat 和 fix ，则该 commit 将肯定出现在 Change log 之中。其他情况（docs、chore、style、refactor、test）由你决定，要不要放入 Change log，建议是不要。

#### scope

scope用于说明 commit 影响的范围，比如数据层、控制层、视图层等等，视仓库不同而不同。

#### subject

subject是 commit 目的的简短描述，不超过50个字符。

-   以动词开头，使用第一人称现在时，比如change，而不是changed或changes
-   第一个字母小写
-   结尾不加句号（.）

### Body

Body 部分是对本次 commit 的详细描述，可以分成多行。下面是一个范例。

> More detailed explanatory text, if necessary. Wrap it to about 72 characters or so. Further paragraphs come after blank lines.- Bullet points are okay, too- Use a hanging indent

有两个注意点。

-   使用第一人称现在时，比如使用change而不是changed或changes。
-   应该说明代码变动的动机，以及与以前行为的对比。

### Footer

Footer 部分只用于两种情况。

#### 1、不兼容变动

如果当前代码与上一个版本不兼容，则 Footer 部分以BREAKING CHANGE开头，后面是对变动的描述、以及变动理由和迁移方法。

```ini
BREAKING CHANGE: isolate scope bindings definition has changed.

    To migrate the code follow the example below:

    Before:

    scope: {
      myAttr: 'attribute',
    }

    After:

    scope: {
      myAttr: '@',
    }

    The removed `inject` wasn't generaly useful for directives so there should be no code using it.
```

#### 2、关闭 Issue

如果当前 commit 针对某个issue，那么可以在 Footer 部分关闭这个 issue 。

> Closes #234

也可以一次关闭多个 issue 。

> Closes #123, #245, #992

### Revert

还有一种特殊情况，如果当前 commit 用于撤销以前的 commit，则必须以revert:开头，后面跟着被撤销 Commit 的 Header。

> revert: feat(pencil): add 'graphiteWidth' option
>
> This reverts commit 667ecc1654a317a13331b17617d973392f415f02.

Body部分的格式是固定的，必须写成This reverts commit .，其中的hash是被撤销 commit 的 SHA 标识符。


