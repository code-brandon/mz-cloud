# 工程简介



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
├──   ├── system-api -- system feign api 暴露
├──   ├── system-model -- 实体对象抽离
├──   ├── system-provider -- 服务能力提供
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


