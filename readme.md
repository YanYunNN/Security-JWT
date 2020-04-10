# SpringSecurity整合JWT实践


## 一、为什么使用JWT

CSRF 攻击：
适合移动应用：
Cookie+Session的鉴权方式中，鉴权数据（cookie 
中的 session_id）是由浏览器自动携带发送到服务移动端上不支持 cookie，而 token 只
端的，借助这个特性，攻击者就可以通过让用户误点要客户端能够进行存储就能够使用，因
攻击链接，比如偷梁换柱地将转账对象B改成黑客C，
此 token 在移动端上也具有优势。
这样用户A就会发现自己的钱进了C的口袋。
token是开发者为了防范csrf而特别设计的令牌，浏
览器不会自动添加到headers里，攻击者也无法访问
用户的token，所以提交的表单无法通过服务器验证

## 二、什么是JWT
JWT(JSON Web Token) 是一个开放标准(RFC 7519)，它定义了一种紧凑的、自包含的方式，用于作为
JSON对象在各方之间安全地传输信息。该信息可以被验证和信任，可直接被用于认证，也可被加密

## 三、怎么用JWT
auth0的Jwt签名方法
Bearer 
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
.eyJleHAiOjE1ODY0Mjg2NjMsInVzZXJuYW1lIjoiMTAwMyJ9
.q-C2nOHpF5u3ah20mT2YGalHC6lt2-dw5umJvZi-2vI

## 四、JWT其他用法
1.单端登录
2.Remember Me
