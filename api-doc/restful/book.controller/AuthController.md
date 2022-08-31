# 用户登录

## 请求信息

### 请求地址
```
http://192.168.254.109:8080/api/auth/login
```

### 请求方法
```
POST
```

### 请求体类型
```
applicatin/json
```

## 入参
### 入参示例 (RequestBody)
```json
{
  "username": "用户名8",
  "password": "密码88"
}
```


### 入参字段说明

| **字段** | **类型** | **必填** | **含义** | **其他参考信息** |
| -------- | -------- | -------- | -------- | -------- |
| username     | **String**     | 否  |  用户名 |   |
| password     | **String**     | 否  |  密码 |   |

## 出参
### 出参示例
```json
{
  "code": 672,
  "message": "饶淆猴夫涌取号儡酿洋启",
  "data": {}
}
```


### 出参字段说明

| **字段** | **类型**  | **含义** | **其他参考信息** |
| -------- | -------- | -------- | -------- |
| code     | **long**    |   |   |
| message     | **String**    |   |   |
| data     | **Object**    |   |   |



# 用户注册

## 请求信息

### 请求地址
```
http://192.168.254.109:8080/api/auth/register
```

### 请求方法
```
POST
```

### 请求体类型
```
applicatin/json
```

## 入参
### 入参示例 (RequestBody)
```json
{
  "username": "用户名106",
  "password": "密码35",
  "secondPassword": "确认密码107"
}
```


### 入参字段说明

| **字段** | **类型** | **必填** | **含义** | **其他参考信息** |
| -------- | -------- | -------- | -------- | -------- |
| username     | **String**     | 否  |  用户名 |   |
| password     | **String**     | 否  |  密码 |   |
| secondPassword     | **String**     | 否  |  确认密码 |   |

## 出参
### 出参示例
```json
{
  "code": 631,
  "message": "躁尾篮滇悔嗡粒",
  "data": {
    "id": 401,
    "username": "送容但钝梧帽许郊骋妊晾其履税冰",
    "password": "画歼贯能瓜匠守讨悉巾",
    "icon": "头像37",
    "email": "邮箱90",
    "nickName": "昵称22",
    "note": "备注信息18",
    "createTime": "2022-08-31T04:58:32.645+0000",
    "loginTime": "2022-08-31T02:17:04.825+0000",
    "status": 773
  }
}
```


### 出参字段说明

| **字段** | **类型**  | **含义** | **其他参考信息** |
| -------- | -------- | -------- | -------- |
| code     | **long**    |   |   |
| message     | **String**    |   |   |
| data     | **UmsAdmin**    |   |   |
|└─ id     | **Long**    |   |   |
|└─ username     | **String**    |   |   |
|└─ password     | **String**    |   |   |
|└─ icon     | **String**    |  头像 |   |
|└─ email     | **String**    |  邮箱 |   |
|└─ nickName     | **String**    |  昵称 |   |
|└─ note     | **String**    |  备注信息 |   |
|└─ createTime     | **Date**    |  创建时间 |   |
|└─ loginTime     | **Date**    |  最后登录时间 |   |
|└─ status     | **Integer**    |  帐号启用状态：0-\>禁用；1-\>启用 |   |



