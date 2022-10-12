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

 Date: 12/10/2022 15:20:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_comment
-- ----------------------------
DROP TABLE IF EXISTS `tbl_comment`;
CREATE TABLE `tbl_comment` (
  `id` bigint NOT NULL COMMENT '评论id',
  `note_id` bigint NOT NULL COMMENT '笔记id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `parent_id` bigint DEFAULT NULL COMMENT '回复的评论id',
  `to_user_id` bigint DEFAULT NULL COMMENT '回复的对象id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `like_count` int DEFAULT NULL COMMENT '评论点赞数',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='评论表';

-- ----------------------------
-- Records of tbl_comment
-- ----------------------------
BEGIN;
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578584337543155714, 1577468339544956929, 125878278, NULL, NULL, '测试', NULL, '2022-10-08 11:13:29', '2022-10-08 11:13:29', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578584361681375233, 1577468339544956929, 125878278, NULL, NULL, '测试1', NULL, '2022-10-08 11:13:35', '2022-10-08 11:13:35', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578584534411202561, 1577468339544956929, 125878278, 1578584337543155714, 125878278, '测试1-1', NULL, '2022-10-08 11:14:16', '2022-10-08 11:14:16', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578631799179886593, 1578198657126166530, 125878278, NULL, NULL, 'Ce', NULL, '2022-10-08 14:22:05', '2022-10-08 14:22:05', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578632028448931841, 1578198657126166530, 125878278, NULL, NULL, 'zailai', NULL, '2022-10-08 14:23:00', '2022-10-08 14:23:00', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578632576606715906, 1578198657126166530, 125878278, NULL, NULL, 'fake', NULL, '2022-10-08 14:25:10', '2022-10-08 14:25:10', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578632785055236097, 1578198657126166530, 125878278, NULL, NULL, 'zailai', NULL, '2022-10-08 14:26:00', '2022-10-08 14:26:00', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578634044189491201, 1578198657126166530, 125878278, NULL, NULL, 'shush I', NULL, '2022-10-08 14:31:00', '2022-10-08 14:31:00', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578634960456171522, 1578198657126166530, 125878278, 1578634044189491201, NULL, 'Cc', NULL, '2022-10-08 14:34:39', '2022-10-08 14:34:39', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578635001166086146, 1578198657126166530, 125878278, 1578632785055236097, NULL, '11', NULL, '2022-10-08 14:34:49', '2022-10-08 14:34:49', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578635049572548609, 1578198657126166530, 125878278, 1578635001166086146, NULL, '11', NULL, '2022-10-08 14:35:00', '2022-10-08 14:35:00', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578635437432422402, 1578198657126166530, 125878278, 1578635001166086146, 125878278, '123', NULL, '2022-10-08 14:36:33', '2022-10-08 14:36:33', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578636433537679361, 1578198657126166530, 125878278, NULL, NULL, 'Craig', NULL, '2022-10-08 14:40:30', '2022-10-08 14:40:30', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578636860031287297, 1578198657126166530, 125878278, NULL, NULL, 'Ces1', NULL, '2022-10-08 14:42:12', '2022-10-08 14:42:12', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578637803196039169, 1578198657126166530, 125878278, 1578636860031287297, 125878278, 'ceshi1', NULL, '2022-10-08 14:45:57', '2022-10-08 14:45:57', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578637832249982977, 1578198657126166530, 125878278, 1578636860031287297, 125878278, 'Ceshi2', NULL, '2022-10-08 14:46:04', '2022-10-08 14:46:04', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578637872037150722, 1578198657126166530, 125878278, 1578636860031287297, 125878278, 'Cehi3', NULL, '2022-10-08 14:46:13', '2022-10-08 14:46:13', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578643176506310657, 1578198657126166530, 125878278, NULL, NULL, '123123123123123vdsfadsfadsfadsfadsfasdfadsfadsfadsfads', NULL, '2022-10-08 15:07:18', '2022-10-08 15:07:18', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1578643202657796098, 1578198657126166530, 125878278, 1578636860031287297, 125878278, 'GewgawFaewfawefawefawefawefawefaewfewfaewfs', NULL, '2022-10-08 15:07:24', '2022-10-08 15:07:24', 0);
INSERT INTO `tbl_comment` (`id`, `note_id`, `user_id`, `parent_id`, `to_user_id`, `content`, `like_count`, `created_time`, `updated_time`, `deleted`) VALUES (1579416385774190593, 1578198623496237058, 125878278, NULL, NULL, 'ceshi', NULL, '2022-10-10 18:19:45', '2022-10-10 18:19:45', 0);
COMMIT;

-- ----------------------------
-- Table structure for tbl_note
-- ----------------------------
DROP TABLE IF EXISTS `tbl_note`;
CREATE TABLE `tbl_note` (
  `id` bigint NOT NULL COMMENT '笔记Id',
  `user_id` bigint NOT NULL COMMENT '用户Id',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '笔记标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '笔记内容',
  `follow_count` int DEFAULT '0' COMMENT '笔记收藏数',
  `like_count` int DEFAULT '0' COMMENT '笔记点赞数',
  `view_count` int DEFAULT '0' COMMENT '浏览数',
  `images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '笔记图片,多个图片用逗号分隔',
  `is_public` tinyint(1) unsigned zerofill DEFAULT NULL COMMENT '是否公开 1是公开，0是个人可见',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='笔记表';

-- ----------------------------
-- Records of tbl_note
-- ----------------------------
BEGIN;
INSERT INTO `tbl_note` (`id`, `user_id`, `title`, `content`, `follow_count`, `like_count`, `view_count`, `images`, `is_public`, `created_time`, `updated_time`, `deleted`) VALUES (1577468339544956929, 125878278, '这是第一篇标题', '这是第一篇的内容', 0, 3, 0, 'http://dummyimage.com/400x400,http://dummyimage.com/400x400', 1, '2022-10-05 09:18:55', '2022-10-05 10:21:01', 0);
INSERT INTO `tbl_note` (`id`, `user_id`, `title`, `content`, `follow_count`, `like_count`, `view_count`, `images`, `is_public`, `created_time`, `updated_time`, `deleted`) VALUES (1578198538079236098, 125878278, '还理置族民打用', '这是第2篇的内容', 0, 0, 0, 'http://dummyimage.com/400x400', 0, '2022-10-07 09:40:28', '2022-10-07 09:40:28', 0);
INSERT INTO `tbl_note` (`id`, `user_id`, `title`, `content`, `follow_count`, `like_count`, `view_count`, `images`, `is_public`, `created_time`, `updated_time`, `deleted`) VALUES (1578198550364352514, 125878278, '始例多约会', '这是第3篇的内容', 0, 0, 0, 'http://dummyimage.com/400x400,http://dummyimage.com/400x400,http://dummyimage.com/400x400', 0, '2022-10-07 09:40:31', '2022-10-07 09:40:31', 0);
INSERT INTO `tbl_note` (`id`, `user_id`, `title`, `content`, `follow_count`, `like_count`, `view_count`, `images`, `is_public`, `created_time`, `updated_time`, `deleted`) VALUES (1578198557989597186, 125878278, '界民里所', '这是第4篇的内容', 0, 0, 0, 'http://dummyimage.com/400x400,http://dummyimage.com/400x400,http://dummyimage.com/400x400', 0, '2022-10-07 09:40:32', '2022-10-07 09:40:32', 0);
INSERT INTO `tbl_note` (`id`, `user_id`, `title`, `content`, `follow_count`, `like_count`, `view_count`, `images`, `is_public`, `created_time`, `updated_time`, `deleted`) VALUES (1578198569993695234, 125878278, '思方其者结', '这是第8篇的内容', 0, 0, 0, 'http://dummyimage.com/400x400,http://dummyimage.com/400x400,http://dummyimage.com/400x400', 0, '2022-10-07 09:40:35', '2022-10-07 09:40:35', 0);
INSERT INTO `tbl_note` (`id`, `user_id`, `title`, `content`, `follow_count`, `like_count`, `view_count`, `images`, `is_public`, `created_time`, `updated_time`, `deleted`) VALUES (1578198582954094594, 125878278, '物起解阶太', '这是第5篇的内容', 0, 2, 0, 'http://dummyimage.com/400x400', 1, '2022-10-07 09:40:38', '2022-10-10 19:00:56', 0);
INSERT INTO `tbl_note` (`id`, `user_id`, `title`, `content`, `follow_count`, `like_count`, `view_count`, `images`, `is_public`, `created_time`, `updated_time`, `deleted`) VALUES (1578198623496237058, 125878278, '组基圆等', '这是第6篇的内容', 0, 4, 0, 'http://dummyimage.com/400x400,http://dummyimage.com/400x400,http://dummyimage.com/400x400,http://dummyimage.com/400x400', 1, '2022-10-07 09:40:48', '2022-10-10 19:06:55', 0);
INSERT INTO `tbl_note` (`id`, `user_id`, `title`, `content`, `follow_count`, `like_count`, `view_count`, `images`, `is_public`, `created_time`, `updated_time`, `deleted`) VALUES (1578198657126166530, 125878278, '本装着图相', '这是第7篇的内容st', 0, 5, 0, 'http://dummyimage.com/400x400,http://dummyimage.com/400x400', 1, '2022-10-07 09:40:56', '2022-10-10 19:06:55', 0);
COMMIT;

-- ----------------------------
-- Table structure for tbl_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_menu`;
CREATE TABLE `tbl_sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL COMMENT '上级菜单',
  `title` varchar(200) DEFAULT NULL COMMENT '显示名称',
  `type` varchar(10) DEFAULT NULL COMMENT '类型',
  `name` varchar(100) DEFAULT NULL COMMENT '别名',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `path` varchar(100) DEFAULT NULL COMMENT '路由地址',
  `redirect` varchar(200) DEFAULT NULL COMMENT '重定向',
  `hidden` bit(1) DEFAULT b'0' COMMENT '隐藏菜单',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建者',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='菜单表';

-- ----------------------------
-- Records of tbl_sys_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_role`;
CREATE TABLE `tbl_sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `label` varchar(200) DEFAULT NULL COMMENT '角色名称',
  `alias` varchar(100) DEFAULT NULL COMMENT '角色别名',
  `sort` int DEFAULT '0' COMMENT '排序',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建者',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色表';

-- ----------------------------
-- Records of tbl_sys_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_role_menu`;
CREATE TABLE `tbl_sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色菜单表';

-- ----------------------------
-- Records of tbl_sys_role_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_user`;
CREATE TABLE `tbl_sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(200) NOT NULL COMMENT '密码',
  `salt` varchar(10) DEFAULT NULL COMMENT '盐',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建者',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='用户账户';

-- ----------------------------
-- Records of tbl_sys_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_user_role`;
CREATE TABLE `tbl_sys_user_role` (
  `id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户角色表';

-- ----------------------------
-- Records of tbl_sys_user_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '昵称;昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
  `sex` int DEFAULT '0' COMMENT '性别 0是保密 1是男 2是女',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `country` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '国家',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '城市',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '简介',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '个人介绍的背景图',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=125878279 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
BEGIN;
INSERT INTO `tbl_user` (`id`, `username`, `password`, `mobile`, `nickname`, `avatar`, `sex`, `birthday`, `country`, `city`, `description`, `cover`, `created_time`, `updated_time`, `deleted`) VALUES (125878278, 'franky', '4297f44b13955235245b2497399d7a93', 'franky', '吃饱没事干', 'https://avatars1.githubusercontent.com/u/294', 0, NULL, NULL, NULL, NULL, NULL, '2022-10-05 09:16:21', '2022-10-05 09:16:21', 0);
COMMIT;

-- ----------------------------
-- Table structure for tbl_user_comment_like
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_comment_like`;
CREATE TABLE `tbl_user_comment_like` (
  `id` bigint NOT NULL COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `comment_id` bigint NOT NULL COMMENT '评论id',
  `note_id` bigint NOT NULL COMMENT '笔记id',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='评论点赞数表';

-- ----------------------------
-- Records of tbl_user_comment_like
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_user_note_follow
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_note_follow`;
CREATE TABLE `tbl_user_note_follow` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `note_id` bigint NOT NULL COMMENT '笔记Id',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='笔记收藏数表';

-- ----------------------------
-- Records of tbl_user_note_follow
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tbl_user_note_like
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_note_like`;
CREATE TABLE `tbl_user_note_like` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `note_id` bigint NOT NULL COMMENT '笔记id',
  `status` int NOT NULL COMMENT '点赞状态 0是取消 1是点赞',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='笔记点赞数表';

-- ----------------------------
-- Records of tbl_user_note_like
-- ----------------------------
BEGIN;
INSERT INTO `tbl_user_note_like` (`id`, `user_id`, `note_id`, `status`, `created_time`, `updated_time`) VALUES (1577474826698792961, 125878278, 1577468339544956929, 1, '2022-10-05 09:44:41', '2022-10-05 10:21:01');
INSERT INTO `tbl_user_note_like` (`id`, `user_id`, `note_id`, `status`, `created_time`, `updated_time`) VALUES (1579405602885500930, 125878278, 1578198623496237058, 1, '2022-10-10 17:36:54', '2022-10-10 19:06:55');
INSERT INTO `tbl_user_note_like` (`id`, `user_id`, `note_id`, `status`, `created_time`, `updated_time`) VALUES (1579408120562614273, 125878278, 1578198657126166530, 1, '2022-10-10 17:46:55', '2022-10-10 19:06:55');
INSERT INTO `tbl_user_note_like` (`id`, `user_id`, `note_id`, `status`, `created_time`, `updated_time`) VALUES (1579426744425807874, 125878278, 1578198582954094594, 1, '2022-10-10 19:00:55', '2022-10-10 19:00:55');
COMMIT;

-- ----------------------------
-- Table structure for tbl_user_note_view
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_note_view`;
CREATE TABLE `tbl_user_note_view` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `note_id` bigint NOT NULL COMMENT '笔记id',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='笔记观看数表';

-- ----------------------------
-- Records of tbl_user_note_view
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
