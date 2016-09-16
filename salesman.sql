/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : b2b_dev

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2016-09-01 16:58:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for salesman
-- ----------------------------
DROP TABLE IF EXISTS `salesman`;
CREATE TABLE `salesman` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `tel` varchar(100) NOT NULL DEFAULT '',
  `bindTime` bigint(20) DEFAULT '0',
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of salesman
-- ----------------------------
INSERT INTO `salesman` VALUES ('29', '', '', '1469248018526', null);
INSERT INTO `salesman` VALUES ('1', '专员1', '13811111111', '0', 'E10ADC3949BA59ABBE56E057F20F883E');
INSERT INTO `salesman` VALUES ('2', '专员2', '13811111112', '0', null);
INSERT INTO `salesman` VALUES ('3', '专员3', '13811111113', '0', null);
INSERT INTO `salesman` VALUES ('4', '专员4', '13811111114', '0', null);
INSERT INTO `salesman` VALUES ('5', '专员5', '13811111115', '0', null);
INSERT INTO `salesman` VALUES ('6', '刘青', '13811111116', '0', null);
INSERT INTO `salesman` VALUES ('7', '杨静敏', '13328112063', '1470447356087', null);
INSERT INTO `salesman` VALUES ('8', '潘翔', '15052227736', '0', null);
INSERT INTO `salesman` VALUES ('9', '金磊', '18652987321', '1469777247425', null);
INSERT INTO `salesman` VALUES ('10', '专员10', '13811111117', '0', null);
INSERT INTO `salesman` VALUES ('11', '黄柯冯', '15380576829', '1470366476599', null);
INSERT INTO `salesman` VALUES ('12', '倪一飞', '18961631272', '1470192193309', null);
INSERT INTO `salesman` VALUES ('13', '华涛', '13665173613', '1470459572916', null);
INSERT INTO `salesman` VALUES ('14', '王永洁', '15296617561', '1470217194598', null);
INSERT INTO `salesman` VALUES ('15', '刘明闪', '13921107687', '1471350514303', null);
INSERT INTO `salesman` VALUES ('16', '徐乾之', '18235069324', '1470449986094', null);
INSERT INTO `salesman` VALUES ('17', '任娟', '18300615857', '1470450714412', null);
INSERT INTO `salesman` VALUES ('18', '金岩', '15358057532', '1470380933350', null);
INSERT INTO `salesman` VALUES ('19', '郭晓晔', '15061539102', '0', null);
INSERT INTO `salesman` VALUES ('20', '朱峰', '15906186350', '1470453363933', null);
INSERT INTO `salesman` VALUES ('21', '刘路由', '15861663781', '1471350648151', null);
INSERT INTO `salesman` VALUES ('22', '吴金星', '15852830292', '1470376960097', null);
INSERT INTO `salesman` VALUES ('23', '杨可成', '13961708670', '1470370206067', null);
INSERT INTO `salesman` VALUES ('24', '储中园', '15190270366', '0', null);
INSERT INTO `salesman` VALUES ('25', '专员25', '18311111111', '0', null);
INSERT INTO `salesman` VALUES ('26', '专员26', '18322222222', '0', null);
INSERT INTO `salesman` VALUES ('27', '专员27', '18333333333', '0', null);
INSERT INTO `salesman` VALUES ('28', '柴营川', '18238797979', '1470474676453', null);
SET FOREIGN_KEY_CHECKS=1;
