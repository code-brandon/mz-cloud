# 工程简介
MZ网关模块
## 接口文档
http://localhost:88/doc.html
http://localhost:88/swagger-ui
## 动态路由Api

### 网关远程端点-获取所有路由

GET 127.0.0.1:88/actuator/gateway/routes

#### 返回结果
```json
[
	{
		"route_id": "123456",
		"route_definition": {
			"id": "123456",
			"predicates": [
				{
					"name": "Path",
					"args": {
						"_genkey_0": "/api/system/**"
					}
				}
			],
			"filters": [
				{
					"name": "RewritePath",
					"args": {
						"_genkey_0": "/api/(?<segment>.*)",
						"_genkey_1": "/$\\{segment}"
					}
				}
			],
			"uri": "lb://mz-system-provider",
			"metadata": {},
			"order": 0
		},
		"order": 0
	}
]
```

### 网关远程端点-获添加路由

POST 127.0.0.1:88/actuator/gateway/routes/{路由ID}

```json
{
	"id": "mz-system-provider",
	"predicates": [
		{
			"name": "Path",
			"args": {
				"_genkey_0": "/api/system/**"
			}
		}
	],
	"filters": [
		{
			"name": "RewritePath",
			"args": {
				"_genkey_0": "/api/(?<segment>.*)",
				"_genkey_1": "/$\\{segment}"
			}
		}
	],
	"uri": "lb://mz-system-provider",
	"metadata": {},
	"order": 0
}
```

#### 返回结果
```json
{
	"id": "123456",
	"predicates": [
		{
			"name": "Path",
			"args": {
				"_genkey_0": "/api/system/**"
			}
		}
	],
	"filters": [
		{
			"name": "RewritePath",
			"args": {
				"_genkey_0": "/api/(?<segment>.*)",
				"_genkey_1": "/$\\{segment}"
			}
		}
	],
	"uri": "lb://mz-system-provider",
	"metadata": {},
	"order": 0
}
```

### 网关远程端点-获取所有路由过滤器

GET 127.0.0.1:88/actuator/gateway/routefilters

#### 返回结果
```json
{
	"[RemoveRequestHeaderGatewayFilterFactory@2abbd0d9 configClass = AbstractGatewayFilterFactory.NameConfig]": null,
	"[RewriteResponseHeaderGatewayFilterFactory@343e225a configClass = RewriteResponseHeaderGatewayFilterFactory.Config]": null,
	"[RequestHeaderSizeGatewayFilterFactory@f29353f configClass = RequestHeaderSizeGatewayFilterFactory.Config]": null,
	"[DedupeResponseHeaderGatewayFilterFactory@71cea1b8 configClass = DedupeResponseHeaderGatewayFilterFactory.Config]": null,
	"[AddResponseHeaderGatewayFilterFactory@5a97b17c configClass = AbstractNameValueGatewayFilterFactory.NameValueConfig]": null,
	"[RedirectToGatewayFilterFactory@36ab3814 configClass = RedirectToGatewayFilterFactory.Config]": null,
	"[ModifyRequestBodyGatewayFilterFactory@9c73fff configClass = ModifyRequestBodyGatewayFilterFactory.Config]": null,
	"[RequestSizeGatewayFilterFactory@889a8a8 configClass = RequestSizeGatewayFilterFactory.RequestSizeConfig]": null,
	"[AddRequestHeaderGatewayFilterFactory@7af52ec7 configClass = AbstractNameValueGatewayFilterFactory.NameValueConfig]": null,
	"[AddRequestParameterGatewayFilterFactory@12c0c0b3 configClass = AbstractNameValueGatewayFilterFactory.NameValueConfig]": null,
	"[ModifyResponseBodyGatewayFilterFactory@659f226a configClass = ModifyResponseBodyGatewayFilterFactory.Config]": null,
	"[SetRequestHostHeaderGatewayFilterFactory@4fb04a72 configClass = SetRequestHostHeaderGatewayFilterFactory.Config]": null,
	"[SetRequestHeaderGatewayFilterFactory@5dcd0cdf configClass = AbstractNameValueGatewayFilterFactory.NameValueConfig]": null,
	"[StripPrefixGatewayFilterFactory@34d713a2 configClass = StripPrefixGatewayFilterFactory.Config]": null,
	"[SetResponseHeaderGatewayFilterFactory@1e79d43 configClass = AbstractNameValueGatewayFilterFactory.NameValueConfig]": null,
	"[MapRequestHeaderGatewayFilterFactory@77c1e611 configClass = MapRequestHeaderGatewayFilterFactory.Config]": null,
	"[RemoveResponseHeaderGatewayFilterFactory@5d3ff859 configClass = AbstractGatewayFilterFactory.NameConfig]": null,
	"[RequestRateLimiterGatewayFilterFactory@32bb0072 configClass = RequestRateLimiterGatewayFilterFactory.Config]": null,
	"[RewritePathGatewayFilterFactory@467233e4 configClass = RewritePathGatewayFilterFactory.Config]": null,
	"[RemoveRequestParameterGatewayFilterFactory@b61edb9 configClass = AbstractGatewayFilterFactory.NameConfig]": null,
	"[PreserveHostHeaderGatewayFilterFactory@32ec9c90 configClass = Object]": null,
	"[SetStatusGatewayFilterFactory@4c0e426a configClass = SetStatusGatewayFilterFactory.Config]": null,
	"[PrefixPathGatewayFilterFactory@2e463f4 configClass = PrefixPathGatewayFilterFactory.Config]": null,
	"[SetPathGatewayFilterFactory@6025d790 configClass = SetPathGatewayFilterFactory.Config]": null,
	"[SaveSessionGatewayFilterFactory@713ec32d configClass = Object]": null,
	"[RetryGatewayFilterFactory@427a12b6 configClass = RetryGatewayFilterFactory.RetryConfig]": null,
	"[RewriteLocationResponseHeaderGatewayFilterFactory@1a07bf6 configClass = RewriteLocationResponseHeaderGatewayFilterFactory.Config]": null,
	"[SecureHeadersGatewayFilterFactory@af7e376 configClass = SecureHeadersGatewayFilterFactory.Config]": null,
	"[RequestHeaderToRequestUriGatewayFilterFactory@36aab105 configClass = AbstractGatewayFilterFactory.NameConfig]": null
}
```