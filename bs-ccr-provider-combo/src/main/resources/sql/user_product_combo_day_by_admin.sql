/*
 Navicat Premium Data Transfer

 Source Server         : docker
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : bs-ccr

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 26/09/2019 20:13:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_product_combo_day_by_admin
-- ----------------------------
DROP TABLE IF EXISTS `user_product_combo_day_by_admin`;
CREATE TABLE `user_product_combo_day_by_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) NOT NULL,
  `user_product_combo_day_id` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '1980-01-01 00:00:01',
  `order_number` varchar(128) NOT NULL,
  `remark` varchar(80) NOT NULL,
  `combo_day_reason_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
