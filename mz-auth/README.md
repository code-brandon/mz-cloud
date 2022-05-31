# 工程简介



## 授权模式

### 授权码
```http request
GET http://localhost:9000/oauth/authorize?response_type=code&client_id=mz_cloud&redirect_uri=https://www.baidu.com&scope=all
```
跳转结果：https://www.baidu.com/?code=mAUbIP

获取token
```http request
POST http://localhost:9000/oauth/token
content-type:application/x-www-form-urlencoded
authorization:Basic YWRtaW46MTEyMjMz
{
    code:mAUbIP
    grant_type:authorization_code
    client_id:mz_cloud
    redirect_uri:https://www.baidu.com
    scope:all
}
```
返回结果：
```json
{
	"access_token": "3b22cb69-4f43-4703-8b7e-b0f392d04a8f",
	"token_type": "bearer",
	"expires_in": 1799,
	"scope": "all"
}
```
### 密码

获取token
```http request
POST http://localhost:9000/oauth/token
content-type:application/x-www-form-urlencoded
authorization:Basic YWRtaW46MTEyMjMz
{
    grant_type:password
    scope:all
    username:admin
    password:123456
}
```
返回结果：
```json
{
	"access_token": "4ea3de37-12a5-4ed6-9622-620b5bed601c",
	"token_type": "bearer",
	"refresh_token": "d92c3f97-f362-4e98-98ed-74040cab1bd1",
	"expires_in": 17999,
	"scope": "all"
}
```