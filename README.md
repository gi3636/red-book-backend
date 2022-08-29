# 技术方案

## **项目技术架构**

前端：react-native

后端：Springboot + MySQL + redis

## **各模块方案**

### **登录模块**

1. 用户在登录界面输入账号密码，点击登录按钮时发起请求。
2. 请求前验证账号和密码字符是否大于6位，没超过则跳出错误提示。
3. 发送请求前，密码先md5加密。
4. 后端取得账号密码并对账号和密码进行查询，若无此账号或密码错误则返回错误消息。
5. 后端验证成功则返回用户基本信息以及JWT Token，同时把数据都存入 Redis。
6. 登出的时候会把redis的数据清空，如果redis的token没有或过期，前端就要重新登入。
7. 登录成功后端返回JWT Token，前端本地存储，每次登入则带token。

![](https://gulimall-franky.oss-cn-hangzhou.aliyuncs.com/%E6%B3%A8%E5%86%8C%E6%B5%81%E7%A8%8B%E5%9B%BE.png)

### **注册模块**

1. 用户在注册界面输入账号，密码以及确认密码。点击注册按钮发起请求。
2. 请求前验证确认密码是否跟密码一致，一致才发起请求。
3. 后端判断用户是否存在，存在则返回错误。
4. 注册成功则返回成功消息，前端跳转到登录界面。

![Untitled Diagram (3).png](https://gulimall-franky.oss-cn-hangzhou.aliyuncs.com/%E6%B3%A8%E5%86%8C%E6%B5%81%E7%A8%8B%E5%9B%BE.png)