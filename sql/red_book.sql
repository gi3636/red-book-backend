/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : red_book

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 21/09/2022 22:23:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_comment
-- ----------------------------
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

-- ----------------------------
-- Table structure for tbl_note
-- ----------------------------
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

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
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

-- ----------------------------
-- Table structure for tbl_user_comment_like
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_comment_like`;
CREATE TABLE `tbl_user_comment_like`  (
  `id` bigint NOT NULL COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `comment_id` bigint NOT NULL COMMENT '评论id',
  `note_id` bigint NOT NULL COMMENT '笔记id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论点赞数表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_user_note_follow
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_note_follow`;
CREATE TABLE `tbl_user_note_follow`  (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `note_id` bigint NOT NULL COMMENT '笔记Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '笔记收藏数表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_user_note_like
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_note_like`;
CREATE TABLE `tbl_user_note_like`  (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `note_id` bigint NOT NULL COMMENT '笔记id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '笔记点赞数表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tbl_user_note_view
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_note_view`;
CREATE TABLE `tbl_user_note_view`  (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `note_id` bigint NOT NULL COMMENT '笔记id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '笔记观看数表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
