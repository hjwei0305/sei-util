# JWT
JSON WEB Token（JWT），是一种基于JSON的、用于在网络上的令牌（token）。
JWT通常由三部分组成: 头信息（header）, 消息体（payload）和签名（signature）。

头信息指定了该JWT使用的签名算法:
```js
header = '{"alg":"HS256","typ":"JWT"}'
```
***HS256*** 表示使用了 **HMAC-SHA256** 来生成签名。

消息体包含了JWT的信息： 
```js
payload = '{"account":"admin","iat":1422779638}'//iat表示令牌生成的时间
```
未签名的令牌由base64url编码的头信息和消息体拼接而成（使用"."分隔），签名则通过私有的key计算而成：
```js
key = 'secretkey'  
unsignedToken = encodeBase64(header) + '.' + encodeBase64(payload)  
signature = HMAC-SHA256(key, unsignedToken) 
```
最后在未签名的令牌尾部拼接上base64url编码的签名（同样使用"."分隔）就是JWT了：
```js
token = encodeBase64(header) + '.' + encodeBase64(payload) + '.' + encodeBase64(signature) 

# token看起来像这样: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2Mzh9.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI
```
JWT常常被用作保护服务端的资源（resource），客户端通常将JWT通过HTTP的Authorization header发送给服务端，服务端使用自己保存的key计算、验证签名以判断该JWT是否可信：
```js
Authorization: Bearer eyJhbGci*...<snip>...*yu5CSpyHI
```

# ECMP中JWT规则

1. 头信息(header)
```js
header = '{"alg":"HS512"}'
```
2. 消息体(payload)
```js
payload = '{"sub":"pcp001","ip":"192.168.68.1","userName":"智能制造","userId":"B6C458A5-4765-11E8-B08D-0A580A82011D","randomKey":"A270623C-A618-11E8-BF93-0242C0A84407","authorityPolicy":"TenantAdmin","appId":"95E74E9C-7A59-697E-DDD1-F6ED954650C8","userType":"Employee","exp":1534984536,"iat":1534948536,"tenant":"10049","account":"pcp001","email":null}'

//随机值(会话ID)     randomKey : "A270623C-A618-11E8-BF93-0242C0A84407"
//应用标识           appId : "95E74E9C-7A59-697E-DDD1-F6ED954650C8"
//登录账号           account : "pcp001"
//登录用户ID         userId : "B6C458A5-4765-11E8-B08D-0A580A82011D"
//登录用户名         userName : "智能制造"
//登录用户权限策略   authorityPolicy : "TenantAdmin"
//登录用户类型       userType : "Employee"
//登录用户租户代码   tenant : "10049"
//登录用户email      email : "null"
//客户端IP           ip : "192.168.68.1"

//登录账号           sub : "pcp001"
//token过期时间      exp : "1534984536"
//token生成时间      iat : "1534948536"
```

3. 签名(signature)
未签名的令牌由base64url编码的头信息和消息体拼接而成（使用"."分隔），签名则通过私有的key计算而成：
```js
key = 'SecretKey_ECMP' 
unsignedToken = encodeBase64(header) + '.' + encodeBase64(payload) 
signature = HmacSHA512(AES(encodeBase64(key)), unsignedToken) 
```
最后在未签名的令牌尾部拼接上base64url编码的签名（同样使用"."分隔）就是JWT了：
```js
token = encodeBase64(header) + '.' + encodeBase64(payload) + '.' + encodeBase64(signature) 
```

4. 传递
将JWT和随机产生的randomKey通过HTTP的Authorization header发送给服务端
```js
_s: ${randomKey} 
Authorization: Bearer ${token}
```

5. 验证 
- [1]解析token。若成功，则是合法token
- [2]校验token是否过期
- [3]分别从header和token中取出randomKey，比较是否一致


