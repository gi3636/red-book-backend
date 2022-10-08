/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : red_book

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 05/10/2022 09:15:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_hotel
-- ----------------------------
DROP TABLE IF EXISTS `tb_hotel`;
CREATE TABLE `tb_hotel`
(
    `id`        bigint       NOT NULL COMMENT '酒店id',
    `name`      varchar(255) NOT NULL COMMENT '酒店名称',
    `address`   varchar(255) NOT NULL COMMENT '酒店地址',
    `price`     int          NOT NULL COMMENT '酒店价格',
    `score`     int          NOT NULL COMMENT '酒店评分',
    `brand`     varchar(32)  NOT NULL COMMENT '酒店品牌',
    `city`      varchar(32)  NOT NULL COMMENT '所在城市',
    `star_name` varchar(16)  DEFAULT NULL COMMENT '酒店星级;1星到5星，1钻到5钻',
    `business`  varchar(255) DEFAULT NULL COMMENT '商圈',
    `latitude`  varchar(32)  NOT NULL COMMENT '纬度',
    `longitude` varchar(32)  NOT NULL COMMENT '经度',
    `pic`       varchar(255) DEFAULT NULL COMMENT '酒店图片',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tbl_comment
-- ----------------------------
DROP TABLE IF EXISTS `tbl_comment`;
CREATE TABLE `tbl_comment`
(
    `id`           bigint                                                        NOT NULL COMMENT '评论id',
    `note_id`      bigint                                                        NOT NULL COMMENT '笔记id',
    `user_id`      bigint     DEFAULT NULL COMMENT '用户id',
    `parent_id`    bigint     DEFAULT NULL COMMENT '回复的评论id',
    `to_user_id`   bigint     DEFAULT NULL COMMENT '回复的对象id',
    `content`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
    `like_count`   int        DEFAULT 0 COMMENT '评论点赞数',
    `created_time` datetime   DEFAULT NULL COMMENT '创建时间',
    `updated_time` datetime   DEFAULT NULL COMMENT '更新时间',
    `deleted`      tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='评论表';

-- ----------------------------
-- Table structure for tbl_note
-- ----------------------------
DROP TABLE IF EXISTS `tbl_note`;
CREATE TABLE `tbl_note`
(
    `id`           bigint                                                       NOT NULL COMMENT '笔记Id',
    `user_id`      bigint                                                       NOT NULL COMMENT '用户Id',
    `title`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '笔记标题',
    `content`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci        NOT NULL COMMENT '笔记内容',
    `follow_count` int                          DEFAULT '0' COMMENT '笔记收藏数',
    `like_count`   int                          DEFAULT '0' COMMENT '笔记点赞数',
    `view_count`   int                          DEFAULT '0' COMMENT '浏览数',
    `images`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '笔记图片,多个图片用逗号分隔',
    `is_public`    tinyint(1) unsigned zerofill DEFAULT NULL COMMENT '是否公开 1是公开，0是个人可见',
    `created_time` datetime                     DEFAULT NULL COMMENT '创建时间',
    `updated_time` datetime                     DEFAULT NULL COMMENT '更新时间',
    `deleted`      tinyint(1)                   DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='笔记表';

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user`
(
    `id`           bigint                                                       NOT NULL AUTO_INCREMENT COMMENT '用户',
    `username`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
    `password`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
    `mobile`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '手机号',
    `nickname`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '昵称;昵称',
    `avatar`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
    `sex`          int                                                           DEFAULT '0' COMMENT '性别 0是保密 1是男 2是女',
    `birthday`     datetime                                                      DEFAULT NULL COMMENT '生日',
    `country`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '国家',
    `city`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  DEFAULT NULL COMMENT '城市',
    `description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '简介',
    `cover`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '个人介绍的背景图',
    `created_time` datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `updated_time` datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `deleted`      tinyint(1)                                                    DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 125878278
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户表';

-- ----------------------------
-- Table structure for tbl_user_comment_like
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_comment_like`;
CREATE TABLE `tbl_user_comment_like`
(
    `id`           bigint NOT NULL COMMENT 'id',
    `user_id`      bigint NOT NULL COMMENT '用户id',
    `comment_id`   bigint NOT NULL COMMENT '评论id',
    `note_id`      bigint NOT NULL COMMENT '笔记id',
    `created_time` datetime DEFAULT NULL COMMENT '创建时间',
    `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='评论点赞数表';

-- ----------------------------
-- Table structure for tbl_user_note_follow
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_note_follow`;
CREATE TABLE `tbl_user_note_follow`
(
    `id`           bigint NOT NULL,
    `user_id`      bigint NOT NULL COMMENT '用户id',
    `note_id`      bigint NOT NULL COMMENT '笔记Id',
    `created_time` datetime DEFAULT NULL COMMENT '创建时间',
    `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='笔记收藏数表';

-- ----------------------------
-- Table structure for tbl_user_note_like
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_note_like`;
CREATE TABLE `tbl_user_note_like`
(
    `id`           bigint NOT NULL,
    `user_id`      bigint NOT NULL COMMENT '用户id',
    `note_id`      bigint NOT NULL COMMENT '笔记id',
    `status`       int    NOT NULL COMMENT '点赞状态 0是取消 1是点赞',
    `created_time` datetime DEFAULT NULL COMMENT '创建时间',
    `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='笔记点赞数表';

-- ----------------------------
-- Table structure for tbl_user_note_view
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_note_view`;
CREATE TABLE `tbl_user_note_view`
(
    `id`           bigint NOT NULL,
    `user_id`      bigint NOT NULL COMMENT '用户id',
    `note_id`      bigint NOT NULL COMMENT '笔记id',
    `created_time` datetime DEFAULT NULL COMMENT '创建时间',
    `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='笔记观看数表';

SET FOREIGN_KEY_CHECKS = 1;
