# 技术方案

## **项目技术架构**

前端：react-native

后端：Springboot + MySQL + redis

访问Swagger-UI接口文档：http://localhost:8080/swagger-ui/



### 数据库

+ 用户表

```sql
DROP TABLE IF EXISTS tbl_user;
CREATE TABLE tbl_user(
    id INT NOT NULL AUTO_INCREMENT  COMMENT '用户' ,
    username VARCHAR(255) NOT NULL   COMMENT '用户名' ,
    password VARCHAR(255) NOT NULL   COMMENT '密码' ,
    mobile VARCHAR(255)    COMMENT '手机号' ,
    nickname VARCHAR(255)    COMMENT '昵称;昵称' ,
    no VARCHAR(255)    COMMENT '媒体号，唯一标识;类似头条号，抖音号，公众号，唯一标识，需要限制修改次数，比如终生1次，每年1次，每半年1次等，可以用于付费修改。' ,
    avatar VARCHAR(255)    COMMENT '头像' ,
    sex INT NOT NULL  DEFAULT 0 COMMENT '性别;1:男  0:女  2:保密' ,
    birthday VARCHAR(255)    COMMENT '生日' ,
    country VARCHAR(255)    COMMENT '国家' ,
    city VARCHAR(255)    COMMENT '城市' ,
    description VARCHAR(900)    COMMENT '简介' ,
    cover VARCHAR(255)    COMMENT '个人介绍的背景图' ,
    created_time DATETIME    COMMENT '创建时间;创建时间' ,
    updated_time DATETIME    COMMENT '更新时间;更新时间' ,
    deleted INT   DEFAULT 0 COMMENT '是否删除;1是删除，0是不删除' ,
    PRIMARY KEY (id)
)  COMMENT = '用户表';

```





### 小红书功能点



![小红书功能点.png](https://raw.githubusercontent.com/gi3636/red-book-backend/main/images/%E5%B0%8F%E7%BA%A2%E4%B9%A6%E5%8A%9F%E8%83%BD%E7%82%B9.png)

