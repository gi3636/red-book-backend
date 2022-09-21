# 技术方案

## **项目技术架构**

前端：react-native

后端：Springboot + MySQL + redis

访问Swagger-UI接口文档：http://localhost:8080/doc.html

接口文档在api-doc 目录下

### JWT-Token验证 用处以及使用场景

Token 主要的用处是用在安全的方面。除了登录注册之类的接口，前端每发一次请求都需要携带token在header里。然后后端每一次都去验证这token是否是合法的。如果token
超时或者token是假的，将无法访问接口。需要重新登录来获取新的token，这样就能防止在无登录的情况去修改或者查看某些资料。同时因为token的时效性，在token无效前，用户都能保持着登录的状态，大大提升了用户的体验。

### 数据库

+ tbl_user 用户表

  ```sql
  DROP TABLE IF EXISTS `tbl_user`;
  CREATE TABLE `tbl_user`  (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户',
    `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
    `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
    `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
    `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称;昵称',
    `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
    `sex` int NULL DEFAULT 0 COMMENT '性别 0是保密 1是男 2是女',
    `birthday` datetime NULL DEFAULT NULL COMMENT '生日',
    `country` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国家',
    `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '城市',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简介',
    `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人介绍的背景图',
    `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间;创建时间',
    `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间;更新时间',
    `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除;1是删除，0是不删除',
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB AUTO_INCREMENT = 125878278 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;
  ```

+ tbl_comment 评论表

  ```sql
  DROP TABLE IF EXISTS `tbl_comment`;
  CREATE TABLE `tbl_comment`  (
    `id` bigint NOT NULL COMMENT '评论id',
    `note_id` bigint NOT NULL COMMENT '笔记id',
    `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
    `parent_id` bigint NULL DEFAULT NULL COMMENT '父id',
    `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
    `like_count` int NULL DEFAULT NULL COMMENT '评论点赞数',
    `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间;创建时间',
    `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间;更新时间',
    `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除;1是删除，0是不删除',
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;
  ```

+ tbl_note 笔记表

  ```sql
  DROP TABLE IF EXISTS `tbl_note`;
  CREATE TABLE `tbl_note`  (
    `id` bigint NOT NULL COMMENT '笔记Id',
    `user_id` bigint NOT NULL COMMENT '用户Id',
    `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '笔记标题',
    `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '笔记内容',
    `follow_count` int(10) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '笔记收藏数',
    `like_count` int(10) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '笔记点赞数',
    `view_count` int NULL DEFAULT NULL COMMENT '浏览数',
    `images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '笔记图片,多个图片用逗号分隔',
    `is_public` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '是否公开 1是公开，0是个人可见',
    `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间;创建时间',
    `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间;更新时间',
    `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除;1是删除，0是不删除',
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '笔记表' ROW_FORMAT = Dynamic;
  
  ```

+ tbl_user_note_view 存储笔记观看数

  ```sql
  DROP TABLE IF EXISTS `tbl_user_note_view`;
  CREATE TABLE `tbl_user_note_view`  (
    `id` bigint NOT NULL,
    `user_id` bigint NOT NULL COMMENT '用户id',
    `note_id` bigint NOT NULL COMMENT '笔记id',
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '笔记观看数表' ROW_FORMAT = Dynamic;
  
  SET FOREIGN_KEY_CHECKS = 1;
  ```

  

+ tbl_user_comment_like 存储用户对评论的点赞

  ```sql
  DROP TABLE IF EXISTS `tbl_user_comment_like`;
  CREATE TABLE `tbl_user_comment_like`  (
    `id` bigint NOT NULL COMMENT 'id',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `comment_id` bigint NOT NULL COMMENT '评论id',
    `note_id` bigint NOT NULL COMMENT '笔记id',
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论点赞数表' ROW_FORMAT = Dynamic;
  ```

+ tbl_user_note_follow 存储用户对笔记的收藏

  ```sql
  DROP TABLE IF EXISTS `tbl_user_note_follow`;
  CREATE TABLE `tbl_user_note_follow`  (
    `id` bigint NOT NULL,
    `user_id` bigint NOT NULL,
    `note_id` bigint NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '笔记收藏数表' ROW_FORMAT = Dynamic;
  ```

+ tbl_user_note_like 存储用户对笔记的点赞

  ```sql
  DROP TABLE IF EXISTS `tbl_user_note_like`;
  CREATE TABLE `tbl_user_note_like`  (
    `id` bigint NOT NULL,
    `user_id` bigint NOT NULL,
    `note_id` bigint NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
  ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '笔记点赞数表' ROW_FORMAT = Dynamic;
  
  ```

​	



## 注册模块

### 应用场景

* 用户未注册

  <Img src="https://raw.githubusercontent.com/gi3636/red-book-backend/main/images/%E7%99%BB%E5%BD%95%E9%A1%B5.jpg"  style="width:340px;height:600px;" >

### 接口说明

1. 传入用户名，密码，确认密码以用来验证
2. 密码和确认密码需要在前端加一次密，安全考量，避免出现明文密码
3. 查询数据库，没有数据表示此用户还未存在，可以注册。
4. 对密码再次加密，然后存入数据库，安全考量。
5. 返回注册成功的消息，表示已经存入数据库。

### 流程图

![注册模块.png](https://franky123.oss-cn-hangzhou.aliyuncs.com/%E6%B3%A8%E5%86%8C%E6%A8%A1%E5%9D%97.png)

## 登录模块

### 应用场景

* 用户登录

  <Img src="https://raw.githubusercontent.com/gi3636/red-book-backend/main/images/%E7%99%BB%E5%BD%95%E9%A1%B5.jpg"  style="width:350px;height:600px;" >

### 接口说明

1. 传入用户名和密码以用来验证
2. 密码需要在前端加一次密，安全考量，避免出现明文密码
3. 登录时先取redis缓存，为了性能考量，减少sql查询。查询到的用户数据能存储在redis,以方便取相同的资料
4. 返回token 是为了让用户保持登录状态，提升用户体验
5. 密码需要加密后在验证，因为存入数据库的密码已经有再加密一次
6. 返回用户数据是避免多发一个请求，同时也能减少sql查询，因为一般上请求登录接口后就会直接跳转首页，这时候会需要个人用户资料，所以直接登录接口直接返回用户资料

### 流程图

![登录模块.png](https://franky123.oss-cn-hangzhou.aliyuncs.com/%E7%99%BB%E5%85%A5%E6%A8%A1%E5%9D%97.png)

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
  "username": "用户名88",
  "password": "密码104"
}
```

### 入参字段说明

| **字段** | **类型**   | **必填** | **含义** | **其他参考信息** |
| -------- | ---------- | -------- | -------- | ---------------- |
| username | **String** | 否       | 用户名   |                  |
| password | **String** | 否       | 密码     |                  |

## 出参

### 出参示例

```json
{
  "code": 639,
  "message": "返回信息125",
  "data": {
    "id": 487,
    "username": "用户名15",
    "mobile": "手机号62",
    "nickname": "昵称;媒体号95",
    "no": "媒体号，唯一标识;类似头条号，抖音号，公众号，唯一标识，需要限制修改次数，比如终生1次，每年1次，每半年1次等，可以用于付费修改。44",
    "avatar": "头像123",
    "sex": 7,
    "birthday": "生日65",
    "country": "国家64",
    "city": "城市99",
    "description": "简介43",
    "cover": "个人介绍的背景图62",
    "token": "JwtToken1"
  }
}
```

### 出参字段说明

| **字段**       | **类型**    | **含义**                                                     | **其他参考信息** |
| -------------- | ----------- | ------------------------------------------------------------ | ---------------- |
| code           | **long**    | 返回码                                                       |                  |
| message        | **String**  | 返回信息                                                     |                  |
| data           | **UserVo**  | 返回数据                                                     |                  |
| └─ id          | **Integer** | 用户id                                                       |                  |
| └─ username    | **String**  | 用户名                                                       |                  |
| └─ mobile      | **String**  | 手机号                                                       |                  |
| └─ nickname    | **String**  | 昵称;媒体号                                                  |                  |
| └─ no          | **
String**  | 媒体号，唯一标识;类似头条号，抖音号，公众号，唯一标识，需要限制修改次数，比如终生1次，每年1次，每半年1次等，可以用于付费修改。 |                  |
| └─ avatar      | **String**  | 头像                                                         |                  |
| └─ sex         | **Integer** | 性别;1:男 0:女 2:保密                                      |                  |
| └─ birthday    | **String**  | 生日                                                         |                  |
| └─ country     | **String**  | 国家                                                         |                  |
| └─ city        | **String**  | 城市                                                         |                  |
| └─ description | **String**  | 简介                                                         |                  |
| └─ cover       | **String**  | 个人介绍的背景图                                             |                  |
| └─ token       | **String**  | JwtToken                                                     |                  |

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
  "username": "用户名76",
  "password": "密码118",
  "confirmPassword": "确认密码1"
}
```

### 入参字段说明

| **字段**        | **类型**   | **必填** | **含义** | **其他参考信息** |
| --------------- | ---------- | -------- | -------- | ---------------- |
| username        | **String** | 否       | 用户名   |                  |
| password        | **String** | 否       | 密码     |                  |
| confirmPassword | **String** | 否       | 确认密码 |                  |

## 出参

### 出参示例

```json
{
  "code": 558,
  "message": "返回信息126",
  "data": {}
}
```

### 出参字段说明

| **字段** | **类型**   | **含义** | **其他参考信息** |
| -------- | ---------- | -------- | ---------------- |
| code     | **long**   | 返回码   |                  |
| message  | **String** | 返回信息 |                  |
| data     | **Null**   | 返回数据 |                  |

## 小红书功能点

![小红书功能点.png](https://raw.githubusercontent.com/gi3636/red-book-backend/main/images/%E5%B0%8F%E7%BA%A2%E4%B9%A6%E5%8A%9F%E8%83%BD%E7%82%B9.png)

