/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : laomall

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 08/07/2022 09:36:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for carousel
-- ----------------------------
DROP TABLE IF EXISTS `carousel`;
CREATE TABLE `carousel`  (
  `carousel_id` int NOT NULL AUTO_INCREMENT,
  `carousel_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `redirect_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `carousel_rank` int NULL DEFAULT NULL,
  `is_deleted` int NULL DEFAULT NULL COMMENT '1 已删除 0 未删除',
  PRIMARY KEY (`carousel_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of carousel
-- ----------------------------
INSERT INTO `carousel` VALUES (1, '新年精选产品', 'https://pixl.decathlon.com.cn/s904137/banner.jpg', NULL, 0, 0);
INSERT INTO `carousel` VALUES (2, '宅家健身 即刻开练', 'https://pixl.decathlon.com.cn/s904166/banner.jpg', NULL, 0, 0);
INSERT INTO `carousel` VALUES (3, '成长之旅 如虎添翼', 'https://pixl.decathlon.com.cn/s904164/banner.jpg', NULL, 0, 0);
INSERT INTO `carousel` VALUES (4, '运动运来', 'https://pixl.decathlon.com.cn/s904168/banner.jpg', NULL, 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
