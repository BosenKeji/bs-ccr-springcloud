/*
 Navicat Premium Data Transfer

 Source Server         : docker
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 127.0.0.1:3306
 Source Schema         : bs-ccr

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 23/09/2019 15:13:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for combo_day_reason
-- ----------------------------
DROP TABLE IF EXISTS `combo_day_reason`;
CREATE TABLE `combo_day_reason` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '1980-01-01 00:00:01',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of combo_day_reason
-- ----------------------------
BEGIN;
INSERT INTO `combo_day_reason` VALUES (1, '活动奖励', 1, '2019-09-23 02:47:44', '1980-01-01 00:00:01');
INSERT INTO `combo_day_reason` VALUES (2, '购买奖励', 1, '2019-09-23 02:47:52', '1980-01-01 00:00:01');
INSERT INTO `combo_day_reason` VALUES (3, '续费', 1, '2019-09-23 02:47:59', '1980-01-01 00:00:01');
INSERT INTO `combo_day_reason` VALUES (4, '其他', 1, '2019-09-23 02:48:07', '1980-01-01 00:00:01');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
