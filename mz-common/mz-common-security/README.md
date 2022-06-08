# 工程简介
安全模块


# 延伸阅读

## 模块权限注解

> 文章来源：https://juejin.cn/post/7012865988949442568

`Spring Security`支持方法级别的权限控制。在此机制上，我们可以在任意层的任意方法上加入权限注解，加入注解的方法将自动被`Spring Security`保护起来，仅仅允许特定的用户访问，从而还到权限控制的目的， 当然如果现有的权限注解不满足我们也可以自定义

### 快速开始

1.  首先加入security依赖如下

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

2.接着新建安全配置类

`Spring Security`默认是禁用注解的，要想开启注解，要在继承`WebSecurityConfigurerAdapter`的类加`@EnableMethodSecurity`注解，并在该类中将`AuthenticationManager`定义为`Bean。`

```java
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
  prePostEnabled = true, 
  securedEnabled = true, 
  jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

```

我们看到`@EnableGlobalMethodSecurity` 分别有`prePostEnabled 、securedEnabled、jsr250Enabled` 三个字段，其中每个字段代码一种注解支持，默认为`false，true为开启`。那么我们就一一来说一下这三总注解支持。

> prePostEnabled = true 的作用的是启用Spring Security的@PreAuthorize 以及@PostAuthorize 注解。

> securedEnabled = true 的作用是启用Spring Security的@Secured 注解。

> jsr250Enabled = true 的作用是启用@RoleAllowed 注解

[Spring Security核心接口用户权限获取，鉴权流程执行原理](https://juejin.cn/post/6999952004990631949 "https://juejin.cn/post/6999952004990631949")

### 在方法上设置权限认证

### JSR-250注解

遵守了JSR-250标准注解 主要注解

1.  `@DenyAll`
2.  `@RolesAllowed`
3.  `@PermitAll`

这里面`@DenyAll` 和 `@PermitAll` 相信就不用多说了 代表拒绝和通过。

`@RolesAllowed` 使用示例

```java
@RolesAllowed("ROLE_VIEWER")
public String getUsername() {
    //...
}
     
@RolesAllowed({ "USER", "ADMIN" })
public boolean isValidUsername(String username) {
    //...
}

```

代表标注的方法只要具有USER, ADMIN任意一种权限就可以访问。这里可以省略前缀ROLE\_，实际的权限可能是ROLE\_ADMIN

在功能及使用方法上与 `@Secured` 完全相同

### securedEnabled注解

主要注解

`@Secured`

1.  Spring Security的`@Secured`注解。注解规定了访问访方法的角色列表，在列表中最少指定一种角色

2.  `@Secured`在方法上指定安全性，要求 角色/权限等 只有对应 角色/权限 的用户才可以调用这些方法。 如果有人试图调用一个方法，但是不拥有所需的 角色/权限，那会将会拒绝访问将引发异常。

比如：

```java
@Secured("ROLE_VIEWER")
public String getUsername() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return securityContext.getAuthentication().getName();
}

@Secured({ "ROLE_DBA", "ROLE_ADMIN" })
public String getUsername2() {
    //...
}
```

`@Secured("ROLE_VIEWER")` 表示只有拥有`ROLE_VIEWER`角色的用户，才能够访问`getUsername()`方法。

`@Secured({ "ROLE_DBA", "ROLE_ADMIN" })` 表示用户拥有"`ROLE_DBA", "ROLE_ADMIN"` 两个角色中的任意一个角色，均可访问 `getUsername2` 方法。

> 还有一点就是@Secured,不支持Spring EL表达式

### prePostEnabled注解

> 这个开启后支持Spring EL表达式 算是蛮厉害的。如果没有访问方法的权限，会抛出AccessDeniedException。

主要注解

1.  `@PreAuthorize` --适合进入方法之前验证授权

2.  `@PostAuthorize` --检查授权方法之后才被执行并且可以影响执行方法的返回值

3.  `@PostFilter` --在方法执行之后执行，而且这里可以调用方法的返回值，然后对返回值进行过滤或处理或修改并返回

4.  `@PreFilter` --在方法执行之前执行，而且这里可以调用方法的参数，然后对参数值进行过滤或处理或修改

#### @PreAuthorize注解使用

```java
@PreAuthorize("hasRole('ROLE_VIEWER')")
public String getUsernameInUpperCase() {
    return getUsername().toUpperCase();
}
```

@PreAuthorize("hasRole('ROLE\_VIEWER')") 相当于@Secured(“ROLE\_VIEWER”)。

同样的 `@Secured({“ROLE_VIEWER”,”ROLE_EDITOR”})` 也可以替换为：`@PreAuthorize(“hasRole(‘ROLE_VIEWER') or hasRole(‘ROLE_EDITOR')”)`。

> 除此以外，我们还可以在方法的参数上使用表达式：

在方法执行之前执行，这里可以调用方法的参数，也可以得到参数值，这里利用JAVA8的参数名反射特性，如果没有JAVA8，那么也可以利用Spring Secuirty的@P标注参数，或利用Spring Data的@Param标注参数。

```java
//无java8
@PreAuthorize("#userId == authentication.principal.userId or hasAuthority(‘ADMIN’)")
void changePassword(@P("userId") long userId ){}
//有java8
@PreAuthorize("#userId == authentication.principal.userId or hasAuthority(‘ADMIN’)")
void changePassword(long userId ){}
```

这里表示在`changePassword`方法执行之前，判断方法参数userId的值是否等于principal中保存的当前用户的userId，或者当前用户是否具有ROLE\_ADMIN权限，两种符合其一，就可以访问该 方法。

#### @PostAuthorize注解使用

在方法执行之后执行可,以获取到方法的返回值，并且可以根据该方法来决定最终的授权结果（是允许访问还是不允许访问):

```java
@PostAuthorize("returnObject.username == authentication.principal.nickName")
public CustomUser loadUserDetail(String username) {
    return userRoleRepository.loadUserByUserName(username);
}
```

上述代码中，仅当`loadUserDetail`方法的返回值中的username与当前登录用户的username相同时才被允许访问

> 注意如果EL为false，那么该方法也已经执行完了，可能会回滚。EL变量returnObject表示返回的对象。

#### @PreFilter以及@PostFilter注解使用

Spring Security提供了一个`@PreFilter` 注解来对传入的参数进行过滤：

```java
@PreFilter("filterObject != authentication.principal.username")
public String joinUsernames(List<String> usernames) {
    return usernames.stream().collect(Collectors.joining(";"));
}
```

当usernames中的子项与当前登录用户的用户名不同时，则保留；当usernames中的子项与当前登录用户的用户名相同时，则移除。比如当前使用用户的用户名为zhangsan，此时usernames的值为`{"zhangsan", "lisi", "wangwu"}`，则经@PreFilter过滤后，实际传入的usernames的值为`{"lisi", "wangwu"}`

如果执行方法中包含有多个类型为Collection的参数，filterObject 就不太清楚是对哪个Collection参数进行过滤了。此时，便需要加入 filterTarget 属性来指定具体的参数名称：

```java
@PreFilter(value = "filterObject != authentication.principal.username",filterTarget = "usernames")
public String joinUsernamesAndRoles(List<String> usernames, List<String> roles) {
  
    return usernames.stream().collect(Collectors.joining(";")) + ":" + roles.stream().collect(Collectors.joining(";"));
}
```

同样的我们还可以使用`@PostFilter` 注解来过`返回`的Collection进行过滤：

```java
@PostFilter("filterObject != authentication.principal.username")
public List<String> getAllUsernamesExceptCurrent() {
    return userRoleRepository.getAllUsernames();
}
```

此时 filterObject 代表返回值。如果按照上述代码则实现了：移除掉返回值中与当前登录用户的用户名相同的子项。

### 自定义元注解

如果我们需要在多个方法中使用相同的安全注解，则可以通过创建元注解的方式来提升项目的可维护性。

比如创建以下元注解：

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ROLE_VIEWER')")
public @interface IsViewer {
}
```

然后可以直接将该注解添加到对应的方法上：

```java
@IsViewer
public String getUsername4() {
    //...
}
```

在生产项目中，由于元注解分离了业务逻辑与安全框架，所以使用元注解是一个非常不错的选择。

### 类上使用安全注解

如果一个类中的所有的方法我们全部都是应用的同一个安全注解，那么此时则应该把安全注解提升到类的级别上：

```java
@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class SystemService {
  
    public String getSystemYear(){
        //...
    }
  
    public String getSystemDate(){
        //...
    }
}
```

上述代码实现了：访问getSystemYear 以及getSystemDate 方法均需要ROLE\_ADMIN权限。

### 方法上应用多个安全注解

在一个安全注解无法满足我们的需求时，还可以应用多个安全注解:

```java
@PreAuthorize("#username == authentication.principal.username")
@PostAuthorize("returnObject.username == authentication.principal.nickName")
public CustomUser securedLoadUserDetail(String username) {
    return userRoleRepository.loadUserByUserName(username);
}
```

此时Spring Security将在`执行方法前`执行`@PreAuthorize`的安全策略，在`执行方法后`执行`@PostAuthorize`的安全策略。

### 总结

在此结合我们的使用经验，给出以下两点提示：

1.  默认情况下，在方法中使用安全注解是由Spring AOP代理实现的，这意味着：如果我们在方法1中去调用同类中的使用安全注解的方法2，则方法2上的安全注解将失效。

2.  Spring Security上下文是线程绑定的，这意味着：安全上下文将不会传递给子线程。

```java
public boolean isValidUsername4(String username) {
    // 以下的方法将会跳过安全认证
    this.getUsername();
    return true;
}
```