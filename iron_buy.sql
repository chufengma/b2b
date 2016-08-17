/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : b2b_dev

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2016-08-17 19:27:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for iron_buy
-- ----------------------------
DROP TABLE IF EXISTS `iron_buy`;
CREATE TABLE `iron_buy` (
  `id` varchar(100) NOT NULL DEFAULT '',
  `ironType` varchar(100) NOT NULL DEFAULT '',
  `material` varchar(100) NOT NULL DEFAULT '',
  `surface` varchar(100) NOT NULL DEFAULT '',
  `proPlace` varchar(100) NOT NULL DEFAULT '',
  `locationCityId` varchar(100) NOT NULL DEFAULT '',
  `userId` varchar(100) NOT NULL DEFAULT '',
  `message` varchar(500) NOT NULL DEFAULT '',
  `pushTime` bigint(20) NOT NULL,
  `length` varchar(500) NOT NULL DEFAULT '',
  `width` varchar(500) NOT NULL DEFAULT '',
  `height` varchar(500) NOT NULL DEFAULT '',
  `tolerance` varchar(100) NOT NULL DEFAULT '',
  `numbers` varchar(500) NOT NULL DEFAULT '',
  `timeLimit` bigint(20) NOT NULL,
  `status` int(11) DEFAULT '0',
  `supplyUserId` varchar(200) DEFAULT NULL,
  `supplyWinTime` bigint(100) NOT NULL DEFAULT '0',
  `salesmanId` int(10) DEFAULT '0',
  `unit` varchar(50) DEFAULT 'kg',
  `lastGetDetailTime` bigint(20) DEFAULT '0',
  `newSupplyNum` int(10) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;
