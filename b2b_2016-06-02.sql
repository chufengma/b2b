# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.1.63)
# Database: b2b
# Generation Time: 2016-06-02 15:17:07 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table seller
# ------------------------------------------------------------

DROP TABLE IF EXISTS `seller`;

CREATE TABLE `seller` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` varchar(100) NOT NULL DEFAULT '',
  `companyName` varchar(100) NOT NULL DEFAULT '',
  `regMoney` int(11) NOT NULL,
  `contact` varchar(100) NOT NULL DEFAULT '',
  `cantactTel` varchar(20) NOT NULL DEFAULT '',
  `fax` varchar(100) NOT NULL DEFAULT '',
  `cityId` varchar(100) NOT NULL DEFAULT '',
  `officeAddress` varchar(300) NOT NULL DEFAULT '',
  `qq` varchar(100) DEFAULT NULL,
  `shopProfile` varchar(400) NOT NULL DEFAULT '',
  `allCer` varchar(200) DEFAULT '',
  `businessLic` varchar(100) DEFAULT NULL,
  `codeLic` varchar(100) DEFAULT NULL,
  `financeLic` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `seller` WRITE;
/*!40000 ALTER TABLE `seller` DISABLE KEYS */;

INSERT INTO `seller` (`id`, `userId`, `companyName`, `regMoney`, `contact`, `cantactTel`, `fax`, `cityId`, `officeAddress`, `qq`, `shopProfile`, `allCer`, `businessLic`, `codeLic`, `financeLic`)
VALUES
	(7,'527cec6a380046b5b813537e10d065e9','风马科技有限公司',1000,'将三米','18355551276','12300991','11111','你打打发打发斯蒂芬','578323123','阿斯顿发撒旦法师打发士大夫','','./res/files/2016/6/2/QxVs9p44.png','./res/files/2016/6/2/qOeHw5MW.png','./res/files/2016/6/2/Pw7C3iBS.png');

/*!40000 ALTER TABLE `seller` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT '',
  `password` varchar(200) NOT NULL DEFAULT '',
  `mobile` varchar(11) NOT NULL DEFAULT '',
  `userId` varchar(200) NOT NULL DEFAULT '',
  `isSeller` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `name`, `password`, `mobile`, `userId`, `isSeller`)
VALUES
	(1,'fengma','42D0FDE669A000EAF26FDC1CC2CAB454','18355551276','527cec6a380046b5b813537e10d065e9',1),
	(2,NULL,'42D0FDE669A000EAF26FDC1CC2CAB454','18366661276','5afa98c48214438dad364113e3a82ce9',0);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
