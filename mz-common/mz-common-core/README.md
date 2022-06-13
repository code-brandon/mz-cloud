# 工程简介
MZ微服务系统核心模块

## 拓展阅读

### Jackson 字典翻译序列化

1. 在实体类上标记注解 `DictFormat`
1.1 注解属性说明
    dictType：默认为""，需手动指定，否则不进行字典翻译
    dictKey：默认为""，需手动指定，否则根据实体类字段值进行字典翻译
    defaultValue：默认为""，需手动指定，否则当没有找到字典项时 走原来实体类字段值
    
1.2 
```java
@DictFormat(dictType = "test")
private String sex;
```

2. 具体使用
```java
@GetMapping("/info")
public R<SysUserEntity> info(){
    // 构建字典缓存
    DictEntity dictEntity = new DictEntity("test", Arrays.asList(new DictData("男", "1")));
    DictCacheUtils.putCache("test", dictEntity);
    // 创建模拟数据
    SysUserEntity sysUserEntity = new SysUserEntity();
    // 要翻译的字段
    sysUserEntity.setSex("1");
    sysUserEntity.setPassword("q2131");
    return R.ok().data(sysUserEntity);
}
```
2.1 输出结果
```json
{
  "code": 0,
  "message": "操作成功！",
  "data": {
    "createBy": "",
    "createTime": "",
    "updateBy": "",
    "updateTime": "",
    "userId": 0,
    "deptId": 0,
    "userName": "",
    "nickName": "",
    "userType": "",
    "email": "",
    "phonenumber": "",
    "sex": "男",
    "avatar": "",
    "password": "q2131",
    "status": "",
    "delFlag": "",
    "loginIp": "",
    "loginDate": "",
    "remark": ""
  }
}
```