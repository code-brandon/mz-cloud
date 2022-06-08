# 工程简介
system 业务模块

## 随记踩坑


> ```java
> No serializer found for class org.apache.ibatis.executor.loader.javassist.JavassistProxyFactory$EnhancedResultObjectProxyImpl and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) (through reference chain: com.mz.common.core.entity.R["data"]->com.mz.system.model.dto.SysUserDto_$$_jvst4a6_0["handler"])
> ```
> **该问题原因是因为配置了Mybatis 懒加载导致的**
>
>**解决办法 :**
>
>在实体类前，即你返回需要jackson解析的类，前面添加注解，让Jackson序列化时忽略handler属性
>```java
>@JsonIgnoreProperties(value = "handler")
>```