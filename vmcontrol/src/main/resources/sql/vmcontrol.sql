/*
 Navicat Premium Data Transfer

 Source Server         : DBConection
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : vmcontrol

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 26/12/2019 14:38:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
BEGIN;
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);
COMMIT;

-- ----------------------------
-- Table structure for host_record
-- ----------------------------
DROP TABLE IF EXISTS `host_record`;
CREATE TABLE `host_record` (
  `hid` varchar(255) NOT NULL,
  `cpu_num` int(11) DEFAULT NULL,
  `host_desc` varchar(255) DEFAULT NULL,
  `host_name` varchar(255) DEFAULT NULL,
  `ip_addr` varchar(255) DEFAULT NULL,
  `mem_size` int(11) DEFAULT NULL,
  `mem_used` int(11) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`hid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of host_record
-- ----------------------------
BEGIN;
INSERT INTO `host_record` VALUES ('c358d1c1-9f67-4b18-bb13-5bb1e385086a', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `host_record` VALUES ('e2af56fe-d026-4e70-b956-e3d6bb4a0234', NULL, '第一个主机结点', '主机一', NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for node
-- ----------------------------
DROP TABLE IF EXISTS `node`;
CREATE TABLE `node` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一标识',
  `cluster_id` varchar(255) DEFAULT NULL,
  `host_id` varchar(255) DEFAULT NULL,
  `node_desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结点描述',
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父ID',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态（1：可用，0：禁用）',
  `vm_id` varchar(255) DEFAULT NULL,
  `node_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '结点名称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of node
-- ----------------------------
BEGIN;
INSERT INTO `node` VALUES ('d441ec26-598e-4bd0-af50-a8d72057efa2', NULL, NULL, 'the first cluster node', '0', 1, NULL, '集群一');
INSERT INTO `node` VALUES ('6a5f0814-8ffe-418f-890c-8ad7db63d9c8', NULL, NULL, 'the second cluster node', '0', 1, NULL, '集群二');
INSERT INTO `node` VALUES ('14ddf6ab-43c6-4c20-9e9a-8a4b5a225712', NULL, NULL, 'the third cluster node', '0', 1, NULL, '集群三');
INSERT INTO `node` VALUES ('1245fc3f-fa75-4355-8bd8-656e082b086d', 'd441ec26-598e-4bd0-af50-a8d72057efa2', 'ef9686e3-3b89-4b3f-84b8-cd93c0f0caad', NULL, 'd441ec26-598e-4bd0-af50-a8d72057efa2', 1, NULL, '主机一');
INSERT INTO `node` VALUES ('fb3533f9-75f2-4ef0-b55c-0ef710db2b11', 'd441ec26-598e-4bd0-af50-a8d72057efa2', '18d29467-d950-4191-9a89-277f223e2491', NULL, 'd441ec26-598e-4bd0-af50-a8d72057efa2', 1, NULL, '主机一');
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL,
  `available` int(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `resource_type` enum('menu','button') DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (1, 0, '用户管理', 0, '0/', 'userInfo:view', 'menu', 'userInfo/userList');
INSERT INTO `sys_permission` VALUES (2, 0, '用户添加', 1, '0/1', 'userInfo:add', 'button', 'userInfo/userAdd');
INSERT INTO `sys_permission` VALUES (3, 0, '用户删除', 1, '0/1', 'userInfo:del', 'button', 'userInfo/userDel');
INSERT INTO `sys_permission` VALUES (4, 0, '访问主机页', 0, '0/', 'host', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL,
  `available` int(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, 0, '管理员', 'admin');
INSERT INTO `sys_role` VALUES (2, 0, 'VIP会员', 'vip');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  KEY `FKomxrs8a388bknvhjokh440waq` (`permission_id`),
  KEY `FK9q28ewrhntqeipl1t04kh1be7` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES (1, 4);
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (2, 1);
INSERT INTO `sys_role_permission` VALUES (3, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `uid` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  KEY `FKgkmyslkrfeyn9ukmolvek8b8f` (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1);
COMMIT;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `uid` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `state` tinyint(4) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `UK_f2ksd6h8hsjtd57ipfq9myr64` (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
INSERT INTO `user_info` VALUES (1, '管理员', '123456', '8d78869f470951332959580424d4bf4f', 0, 'admin');
COMMIT;

-- ----------------------------
-- Table structure for vm_record
-- ----------------------------
DROP TABLE IF EXISTS `vm_record`;
CREATE TABLE `vm_record` (
  `vm_id` varchar(255) NOT NULL,
  `cpu_num` int(11) DEFAULT NULL,
  `cpu_used` int(11) DEFAULT NULL,
  `disk_size` bigint(20) DEFAULT NULL,
  `ios` varchar(255) DEFAULT NULL,
  `mem_size` bigint(20) DEFAULT NULL,
  `mem_used` int(11) DEFAULT NULL,
  `os` varchar(255) DEFAULT NULL,
  `states` int(11) DEFAULT NULL,
  `storage_path` varchar(255) DEFAULT NULL,
  `vm_desc` varchar(255) DEFAULT NULL,
  `vm_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`vm_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
