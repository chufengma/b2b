# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.1.63)
# Database: b2b_dev
# Generation Time: 2016-08-21 11:13:13 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table iron_buy_qt
# ------------------------------------------------------------

DROP TABLE IF EXISTS `iron_buy_qt`;

CREATE TABLE `iron_buy_qt` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `qtId` varchar(100) NOT NULL DEFAULT '',
  `salesmanId` varchar(11) NOT NULL DEFAULT '',
  `ironBuyId` varchar(100) NOT NULL DEFAULT '',
  `status` int(11) NOT NULL DEFAULT '0',
  `pushTime` bigint(20) NOT NULL DEFAULT '0',
  `finishTime` bigint(20) DEFAULT '0',
  `userId` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `iron_buy_qt` WRITE;
/*!40000 ALTER TABLE `iron_buy_qt` DISABLE KEYS */;

INSERT INTO `iron_buy_qt` (`id`, `qtId`, `salesmanId`, `ironBuyId`, `status`, `pushTime`, `finishTime`, `userId`)
VALUES
	(2,'XAUyW2G5mWeJ','17','gETLhuza34P4',0,1471752810044,0,'c6lMYPljuC80');

/*!40000 ALTER TABLE `iron_buy_qt` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
