-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: b2b
-- ------------------------------------------------------
-- Server version	5.7.12-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin_user`
--

DROP TABLE IF EXISTS `admin_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(200) NOT NULL DEFAULT '',
  `password` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_user`
--

LOCK TABLES `admin_user` WRITE;
/*!40000 ALTER TABLE `admin_user` DISABLE KEYS */;
INSERT INTO `admin_user` VALUES (1,'fengma','42d0fde669a000eaf26fdc1cc2cab454');
/*!40000 ALTER TABLE `admin_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `all_orders`
--

DROP TABLE IF EXISTS `all_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `all_orders` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `allCount` bigint(100) NOT NULL,
  `allSales` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `all_orders`
--

LOCK TABLES `all_orders` WRITE;
/*!40000 ALTER TABLE `all_orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `all_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buyer_amount`
--

DROP TABLE IF EXISTS `buyer_amount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `buyer_amount` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ironCount` bigint(20) DEFAULT '0',
  `ironMoney` double DEFAULT '0',
  `handingCount` bigint(11) DEFAULT '0',
  `handingMoney` double DEFAULT '0',
  `day` bigint(100) DEFAULT '0',
  `buyerId` varchar(200) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buyer_amount`
--

LOCK TABLES `buyer_amount` WRITE;
/*!40000 ALTER TABLE `buyer_amount` DISABLE KEYS */;
/*!40000 ALTER TABLE `buyer_amount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `id` varchar(11) NOT NULL DEFAULT '',
  `fatherId` varchar(11) DEFAULT '',
  `name` varchar(100) NOT NULL DEFAULT '',
  `type` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES ('gVgwxppO',NULL,'北京',0),('eIjqrOOc',NULL,'广东',1),('S3h5IOdU',NULL,'上海',0),('5a8QXEaX',NULL,'天津',0),('GnbCJw66',NULL,'重庆',0),('o73vuTTJ',NULL,'辽宁',1),('x6vMHzA3',NULL,'江苏',1),('XQ9HE28u',NULL,'湖北',1),('Bz7BUsCy',NULL,'四川',1),('oBmRecO8',NULL,'陕西',1),('chcyN0P1',NULL,'河北',1),('bSUEhIxP',NULL,'山西',1),('rjVszlhd',NULL,'河南',1),('8DLXysiC',NULL,'吉林',1),('PwUgn58r',NULL,'黑龙江',1),('hbs78kMp',NULL,'内蒙古',1),('LnbsZoa2',NULL,'山东',1),('YyXdlTgE',NULL,'安徽',1),('zL77rzsP',NULL,'浙江',1),('X9l6qcyK',NULL,'福建',1),('GGJiahRO',NULL,'湖南',1),('Jq6nbkUo',NULL,'广西',1),('3ij6zMY8',NULL,'江西',1),('woS4K1Vk',NULL,'贵州',1),('lgUbqdSZ',NULL,'云南',1),('Pn0YV2YP',NULL,'西藏',1),('wjxGWGBN',NULL,'海南',1),('YSY6Jn6S',NULL,'甘肃',1),('xshSiyeC',NULL,'宁夏',1),('GC7WWEJT',NULL,'青海',1),('6VXWHjs3',NULL,'新疆',1),('lHjIQRr9',NULL,'香港',0),('vBbEdPTV',NULL,'澳门',0),('JMoyEtgG',NULL,'台湾',0),('cAF7QoX2',NULL,'海外',0),('oENrmIKc',NULL,'其他',-1),('91ELW6NU','gVgwxppO','东城区',-1),('G3CYZ7zt','gVgwxppO','西城区',-1),('XKQZfr7z','gVgwxppO','崇文区',-1),('oR0V3ZHb','gVgwxppO','宣武区',-1),('fD9VOYMM','gVgwxppO','朝阳区',-1),('k73HveZ0','gVgwxppO','海淀区',-1),('6xmpF8ok','gVgwxppO','丰台区',-1),('OHUtDsFT','gVgwxppO','石景山区',-1),('soHKNjPy','gVgwxppO','房山区',-1),('1QMREAxb','gVgwxppO','通州区',-1),('DklnyzyI','gVgwxppO','顺义区',-1),('FzgGNmoZ','gVgwxppO','昌平区',-1),('6W4wv13t','gVgwxppO','大兴区',-1),('2VKACXHU','gVgwxppO','怀柔区',-1),('K6aWuIWu','gVgwxppO','平谷区',-1),('28fw29jc','gVgwxppO','门头沟区',-1),('qMRxb8vb','gVgwxppO','密云县',-1),('AovLZi9m','gVgwxppO','延庆县',-1),('C82vWQRn','gVgwxppO','其他',-1),('jBM6RFWK','eIjqrOOc','广州',0),('wYC4sP3V','eIjqrOOc','深圳',0),('sJTLbvvh','eIjqrOOc','珠海',0),('XutdFXAX','eIjqrOOc','汕头',0),('AIIiQUwG','eIjqrOOc','韶关',0),('aUojV7FZ','eIjqrOOc','佛山',0),('H4aZQNLI','eIjqrOOc','江门',0),('sVF1q1n0','eIjqrOOc','湛江',0),('HFPwO6QJ','eIjqrOOc','茂名',0),('leWkYsqj','eIjqrOOc','肇庆',0),('vlVnMWnk','eIjqrOOc','惠州',0),('zqCdT3JX','eIjqrOOc','梅州',0),('tqkROiVy','eIjqrOOc','汕尾',0),('X1uhwMce','eIjqrOOc','河源',0),('PDVcjRqY','eIjqrOOc','阳江',0),('HEsPuocf','eIjqrOOc','清远',0),('4nJ6FbXk','eIjqrOOc','东莞',0),('4Icq9Ynn','eIjqrOOc','中山',0),('hSe5PQf5','eIjqrOOc','潮州',0),('Ij0ayU8P','eIjqrOOc','揭阳',0),('eKCTnwwS','eIjqrOOc','云浮',0),('m5sNFWfH','eIjqrOOc','其他',-1),('JYdyuQ5Z','jBM6RFWK','越秀区',-1),('b1u8dQhs','jBM6RFWK','荔湾区',-1),('mmbeAtga','jBM6RFWK','海珠区',-1),('8yic4XtO','jBM6RFWK','天河区',-1),('B2TKVJet','jBM6RFWK','白云区',-1),('bEPRFE0u','jBM6RFWK','黄埔区',-1),('gE7Jw48E','jBM6RFWK','番禺区',-1),('PWsN1NGc','jBM6RFWK','花都区',-1),('k90BVGY2','jBM6RFWK','南沙区',-1),('fdSBTDnY','jBM6RFWK','萝岗区',-1),('78G4nOcE','jBM6RFWK','增城市',-1),('Ys16eLWg','jBM6RFWK','从化市',-1),('llN8OjQT','jBM6RFWK','其他',-1),('BlfNx667','wYC4sP3V','福田区',-1),('7XM7sgLC','wYC4sP3V','罗湖区',-1),('FyscUvkC','wYC4sP3V','南山区',-1),('ANTPPQIs','wYC4sP3V','宝安区',-1),('LEtbpEiO','wYC4sP3V','龙岗区',-1),('BOueLxfL','wYC4sP3V','盐田区',-1),('5FhgvKRn','wYC4sP3V','其他',-1),('s2P8is9t','sJTLbvvh','香洲区',-1),('OlpkaDsS','sJTLbvvh','斗门区',-1),('VsYPj5uv','sJTLbvvh','金湾区',-1),('izoIhNsv','sJTLbvvh','其他',-1),('sgv3WI7J','XutdFXAX','金平区',-1),('w9ZMnzUv','XutdFXAX','濠江区',-1),('TKushBPe','XutdFXAX','龙湖区',-1),('mPF6hWdW','XutdFXAX','潮阳区',-1),('iGkrpSov','XutdFXAX','潮南区',-1),('EWegJIq6','XutdFXAX','澄海区',-1),('FKRFlEC8','XutdFXAX','南澳县',-1),('ig6mO159','XutdFXAX','其他',-1),('6S9h0aqc','AIIiQUwG','浈江区',-1),('SYv0DRcP','AIIiQUwG','武江区',-1),('ZB9frDRk','AIIiQUwG','曲江区',-1),('z8iERuPz','AIIiQUwG','乐昌市',-1),('CWv1OsNj','AIIiQUwG','南雄市',-1),('u4jNVrFr','AIIiQUwG','始兴县',-1),('l4aL2pSG','AIIiQUwG','仁化县',-1),('AnZ5HLQw','AIIiQUwG','翁源县',-1),('PMGS0jo4','AIIiQUwG','新丰县',-1),('pSygkGWs','AIIiQUwG','乳源瑶族自治县',-1),('lanb7T8k','AIIiQUwG','其他',-1),('9wC0Mvqx','aUojV7FZ','禅城区',-1),('uUHVYqQa','aUojV7FZ','南海区',-1),('NKlh92Gb','aUojV7FZ','顺德区',-1),('d6YdPPrr','aUojV7FZ','三水区',-1),('rFL1SAi7','aUojV7FZ','高明区',-1),('LrInFbZW','aUojV7FZ','其他',-1),('poRaJ6bR','H4aZQNLI','蓬江区',-1),('sfnTwkPr','H4aZQNLI','江海区',-1),('pzUQhI5N','H4aZQNLI','新会区',-1),('lckM1FO2','H4aZQNLI','恩平市',-1),('wmCe6FKM','H4aZQNLI','台山市',-1),('aYBGIWnp','H4aZQNLI','开平市',-1),('DMYJjtgd','H4aZQNLI','鹤山市',-1),('yJFUysYO','H4aZQNLI','其他',-1),('x6xOCPrn','sVF1q1n0','赤坎区',-1),('U0mOil27','sVF1q1n0','霞山区',-1),('7ecBAt2t','sVF1q1n0','坡头区',-1),('tQCzgkPP','sVF1q1n0','麻章区',-1),('IVGcD7SI','sVF1q1n0','吴川市',-1),('cRlA1LVl','sVF1q1n0','廉江市',-1),('MXlfi1MT','sVF1q1n0','雷州市',-1),('5bwiG2Qf','sVF1q1n0','遂溪县',-1),('fDlaAKsC','sVF1q1n0','徐闻县',-1),('OeWNB9KD','sVF1q1n0','其他',-1),('XhsCBZOp','HFPwO6QJ','茂南区',-1),('yrh3w2y6','HFPwO6QJ','茂港区',-1),('HiDBJQZp','HFPwO6QJ','化州市',-1),('7s1x9uzf','HFPwO6QJ','信宜市',-1),('SfdW7Hz2','HFPwO6QJ','高州市',-1),('8H9aAAgq','HFPwO6QJ','电白县',-1),('ONcXycg5','HFPwO6QJ','其他',-1),('3VkR8uYd','leWkYsqj','端州区',-1),('p85dJdqG','leWkYsqj','鼎湖区',-1),('6ENfbncr','leWkYsqj','高要市',-1),('xpAASGjz','leWkYsqj','四会市',-1),('S79KVEga','leWkYsqj','广宁县',-1),('roUYWu0d','leWkYsqj','怀集县',-1),('EXx8b8cH','leWkYsqj','封开县',-1),('bugDFghN','leWkYsqj','德庆县',-1),('IxUlwP9Y','leWkYsqj','其他',-1),('usuqRaQR','vlVnMWnk','惠城区',-1),('yCo8PcGT','vlVnMWnk','惠阳区',-1),('cmlxTu8D','vlVnMWnk','博罗县',-1),('IvK0MDIo','vlVnMWnk','惠东县',-1),('kBcQzsAF','vlVnMWnk','龙门县',-1),('tu1PZfPB','vlVnMWnk','其他',-1),('pR0DGmEt','zqCdT3JX','梅江区',-1),('kuJ9r31w','zqCdT3JX','兴宁市',-1),('CBKvq9kR','zqCdT3JX','梅县',-1),('W7YUE7ff','zqCdT3JX','大埔县',-1),('EzQx3xCr','zqCdT3JX','丰顺县',-1),('xOQ7cx0s','zqCdT3JX','五华县',-1),('F1HgsYAp','zqCdT3JX','平远县',-1),('rQoFwsFy','zqCdT3JX','蕉岭县',-1),('3HAc5isI','zqCdT3JX','其他',-1),('BzYO7eJs','tqkROiVy','城区',-1),('etHtwhrV','tqkROiVy','陆丰市',-1),('IsZDYdem','tqkROiVy','海丰县',-1),('2safBqff','tqkROiVy','陆河县',-1),('spgZ1NDt','tqkROiVy','其他',-1),('YSNzPFP0','X1uhwMce','源城区',-1),('iBG7uqB6','X1uhwMce','紫金县',-1),('yMdTvP9o','X1uhwMce','龙川县',-1),('f5SmP89K','X1uhwMce','连平县',-1),('6EFHGa9h','X1uhwMce','和平县',-1),('g7jyE9rj','X1uhwMce','东源县',-1),('AZJJ2LO2','X1uhwMce','其他',-1),('MvJd8vQU','PDVcjRqY','江城区',-1),('UFqFdOyZ','PDVcjRqY','阳春市',-1),('sZqSM1xc','PDVcjRqY','阳西县',-1),('9ixngP4n','PDVcjRqY','阳东县',-1),('vbeU2tB2','PDVcjRqY','其他',-1),('2oYfo0cm','HEsPuocf','清城区',-1),('m05pVWh2','HEsPuocf','英德市',-1),('AU2gr0z8','HEsPuocf','连州市',-1),('mwMGu9lH','HEsPuocf','佛冈县',-1),('0ZeryEdX','HEsPuocf','阳山县',-1),('ZziZH2aZ','HEsPuocf','清新县',-1),('gMmFSa3j','HEsPuocf','连山壮族瑶族自治县',-1),('lo8OFtba','HEsPuocf','连南瑶族自治县',-1),('LW4NBfqN','HEsPuocf','其他',-1),('8bUWHfDp','hSe5PQf5','湘桥区',-1),('hysWclsU','hSe5PQf5','潮安县',-1),('wGVslmLD','hSe5PQf5','饶平县',-1),('4ZILRpYo','hSe5PQf5','其他',-1),('JtZJ1ncS','Ij0ayU8P','榕城区',-1),('Apu3lsN3','Ij0ayU8P','普宁市',-1),('6OFS4Jsn','Ij0ayU8P','揭东县',-1),('TR4kI3qy','Ij0ayU8P','揭西县',-1),('l1fNvUGS','Ij0ayU8P','惠来县',-1),('HTXPUssL','Ij0ayU8P','其他',-1),('mpzMKlqX','eKCTnwwS','云城区',-1),('MmHcu9Fi','eKCTnwwS','罗定市',-1),('4tYbIEJF','eKCTnwwS','云安县',-1),('JhGK62WL','eKCTnwwS','新兴县',-1),('PHXUrdm1','eKCTnwwS','郁南县',-1),('0xsnJ1F1','eKCTnwwS','其他',-1),('ohTv5aUz','S3h5IOdU','黄浦区',-1),('HsoEVkFL','S3h5IOdU','卢湾区',-1),('FYSB0tTo','S3h5IOdU','徐汇区',-1),('1OvMsymh','S3h5IOdU','长宁区',-1),('D4uHJ4oF','S3h5IOdU','静安区',-1),('MKdvqjaV','S3h5IOdU','普陀区',-1),('bWM04gbE','S3h5IOdU','闸北区',-1),('3xKTacOd','S3h5IOdU','虹口区',-1),('nwBr7cP5','S3h5IOdU','杨浦区',-1),('yVljlGAL','S3h5IOdU','宝山区',-1),('LQPW32y0','S3h5IOdU','闵行区',-1),('13Vt8WmQ','S3h5IOdU','嘉定区',-1),('VYJbV0D5','S3h5IOdU','松江区',-1),('CQJZtpce','S3h5IOdU','金山区',-1),('t7IKvVon','S3h5IOdU','青浦区',-1),('wfa96gng','S3h5IOdU','南汇区',-1),('sQeKRRAY','S3h5IOdU','奉贤区',-1),('wm6Vs6Fy','S3h5IOdU','浦东新区',-1),('stKtmz9S','S3h5IOdU','崇明县',-1),('mWicqOl3','S3h5IOdU','其他',-1),('rkPXtpXe','5a8QXEaX','和平区',-1),('txYmmiHN','5a8QXEaX','河东区',-1),('7hcsXqp3','5a8QXEaX','河西区',-1),('TaYtFBih','5a8QXEaX','南开区',-1),('DBoEgjXv','5a8QXEaX','河北区',-1),('n1dd2D2l','5a8QXEaX','红桥区',-1),('AWIEEztt','5a8QXEaX','塘沽区',-1),('xUbmohiq','5a8QXEaX','汉沽区',-1),('Z0M9BtFa','5a8QXEaX','大港区',-1),('5emDTbl5','5a8QXEaX','东丽区',-1),('m8WOFReo','5a8QXEaX','西青区',-1),('iYq5m7NV','5a8QXEaX','北辰区',-1),('Wjn3Q2ie','5a8QXEaX','津南区',-1),('qHM0uamz','5a8QXEaX','武清区',-1),('Ut1JBvPL','5a8QXEaX','宝坻区',-1),('wJErjHyG','5a8QXEaX','静海县',-1),('gJz3d3IG','5a8QXEaX','宁河县',-1),('f5ddPN4O','5a8QXEaX','蓟县',-1),('jZXU1JOk','5a8QXEaX','其他',-1),('ocHlpoUa','GnbCJw66','渝中区',-1),('BVNFP1BE','GnbCJw66','大渡口区',-1),('GqdAF4Eq','GnbCJw66','江北区',-1),('fmgF17kS','GnbCJw66','南岸区',-1),('wSgjZJ43','GnbCJw66','北碚区',-1),('rbHPQt6h','GnbCJw66','渝北区',-1),('2feNvMbd','GnbCJw66','巴南区',-1),('4pNyKqPd','GnbCJw66','长寿区',-1),('hfdrH5OG','GnbCJw66','双桥区',-1),('EyrjKsVA','GnbCJw66','沙坪坝区',-1),('RpllIsbv','GnbCJw66','万盛区',-1),('ZoaP04Zc','GnbCJw66','万州区',-1),('BJYph80b','GnbCJw66','涪陵区',-1),('NYxYy1wK','GnbCJw66','黔江区',-1),('0tR4ymBQ','GnbCJw66','永川区',-1),('fmGy3SRE','GnbCJw66','合川区',-1),('cEjU7rYa','GnbCJw66','江津区',-1),('hcQXxHbQ','GnbCJw66','九龙坡区',-1),('pxv8g7q1','GnbCJw66','南川区',-1),('7EtBP2e5','GnbCJw66','綦江县',-1),('AzNQ0RXo','GnbCJw66','潼南县',-1),('H2i41Gsn','GnbCJw66','荣昌县',-1),('DbHLrDlV','GnbCJw66','璧山县',-1),('TzhWHked','GnbCJw66','大足县',-1),('PbLbgIap','GnbCJw66','铜梁县',-1),('yjySXfpu','GnbCJw66','梁平县',-1),('IX9x7Zh5','GnbCJw66','开县',-1),('J906A8jU','GnbCJw66','忠县',-1),('XMtntyM7','GnbCJw66','城口县',-1),('jTIl8M5l','GnbCJw66','垫江县',-1),('mKQojsqz','GnbCJw66','武隆县',-1),('n9nKgGqU','GnbCJw66','丰都县',-1),('8mlB2QhU','GnbCJw66','奉节县',-1),('n4l1KFJE','GnbCJw66','云阳县',-1),('JzuClx0x','GnbCJw66','巫溪县',-1),('vFxCTOIM','GnbCJw66','巫山县',-1),('Tt4U7OPA','GnbCJw66','石柱土家族自治县',-1),('aCbdJAnI','GnbCJw66','秀山土家族苗族自治县',-1),('FyNSO2JO','GnbCJw66','酉阳土家族苗族自治县',-1),('8WQ4s0gk','GnbCJw66','彭水苗族土家族自治县',-1),('y2oawaAk','GnbCJw66','其他',-1),('2UbYgsgn','o73vuTTJ','沈阳',0),('Kkd7zeGi','o73vuTTJ','大连',0),('ikAu3JJH','o73vuTTJ','鞍山',0),('gyHDbHS5','o73vuTTJ','抚顺',0),('K8uqzCbo','o73vuTTJ','本溪',0),('NoMKzCOh','o73vuTTJ','丹东',0),('FBdjBmrG','o73vuTTJ','锦州',0),('iOUkXQxL','o73vuTTJ','营口',0),('vXpL8n0H','o73vuTTJ','阜新',0),('G91t0flh','o73vuTTJ','辽阳',0),('NW5pXbPx','o73vuTTJ','盘锦',0),('NeutZe4M','o73vuTTJ','铁岭',0),('SzC01FCt','o73vuTTJ','朝阳',0),('zbBjybUh','o73vuTTJ','葫芦岛',0),('s1ghLJNv','o73vuTTJ','其他',-1),('1Ky990kE','2UbYgsgn','沈河区',-1),('kc8o3swD','2UbYgsgn','皇姑区',-1),('QVUbxH3R','2UbYgsgn','和平区',-1),('qRLPcZ1x','2UbYgsgn','大东区',-1),('3mQiqs7n','2UbYgsgn','铁西区',-1),('rzD9VOf3','2UbYgsgn','苏家屯区',-1),('mUuWlbrk','2UbYgsgn','东陵区',-1),('LyakP6Tp','2UbYgsgn','于洪区',-1),('qeesPp5d','2UbYgsgn','新民市',-1),('IBZUzBqK','2UbYgsgn','法库县',-1),('Os3j2CQr','2UbYgsgn','辽中县',-1),('m3qXZqNB','2UbYgsgn','康平县',-1),('Y7bN0y2s','2UbYgsgn','新城子区',-1),('D3R04reI','2UbYgsgn','其他',-1),('8mwv4oiM','Kkd7zeGi','西岗区',-1),('s0kEQJ7R','Kkd7zeGi','中山区',-1),('4XNqm0DK','Kkd7zeGi','沙河口区',-1),('PNCW3PBb','Kkd7zeGi','甘井子区',-1),('fI0XFHBB','Kkd7zeGi','旅顺口区',-1),('3VFk62Up','Kkd7zeGi','金州区',-1),('NXC2MIpn','Kkd7zeGi','瓦房店市',-1),('CYPyqLqU','Kkd7zeGi','普兰店市',-1),('71vEV6np','Kkd7zeGi','庄河市',-1),('wpjsTg27','Kkd7zeGi','长海县',-1),('KiUNHJJx','Kkd7zeGi','其他',-1),('x8t2PssX','ikAu3JJH','铁东区',-1),('ONssHjKH','ikAu3JJH','铁西区',-1),('zOJJcpzw','ikAu3JJH','立山区',-1),('CnsKEcwd','ikAu3JJH','千山区',-1),('HZJdEufw','ikAu3JJH','海城市',-1),('IDGvpKlb','ikAu3JJH','台安县',-1),('mRINirR7','ikAu3JJH','岫岩满族自治县',-1),('gAcxav1P','ikAu3JJH','其他',-1),('d50Rrpie','gyHDbHS5','顺城区',-1),('FQTake6u','gyHDbHS5','新抚区',-1),('NVf2rBf7','gyHDbHS5','东洲区',-1),('BuRTrsmr','gyHDbHS5','望花区',-1),('lhuMhRoa','gyHDbHS5','抚顺县',-1),('vkfn8vnW','gyHDbHS5','清原满族自治县',-1),('Fkcro8vq','gyHDbHS5','新宾满族自治县',-1),('5pWgrDWD','gyHDbHS5','其他',-1),('htVNTTfl','K8uqzCbo','平山区',-1),('tFSZcKxx','K8uqzCbo','明山区',-1),('YhXBfZNf','K8uqzCbo','溪湖区',-1),('16zLR48I','K8uqzCbo','南芬区',-1),('XKioubnA','K8uqzCbo','本溪满族自治县',-1),('BgMCknMy','K8uqzCbo','桓仁满族自治县',-1),('xoXizRzv','K8uqzCbo','其他',-1),('1GN3nsOZ','NoMKzCOh','振兴区',-1),('iwTr3s5v','NoMKzCOh','元宝区',-1),('mR9PBaqE','NoMKzCOh','振安区',-1),('5UY8Uw2M','NoMKzCOh','东港市',-1),('NL0xExGE','NoMKzCOh','凤城市',-1),('xo1HuUco','NoMKzCOh','宽甸满族自治县',-1),('IB0pOmR1','NoMKzCOh','其他',-1),('HTv8FBBH','FBdjBmrG','太和区',-1),('KdP5yE7w','FBdjBmrG','古塔区',-1),('I4jfCu6N','FBdjBmrG','凌河区',-1),('YfjmVexF','FBdjBmrG','凌海市',-1),('0O0bLHDn','FBdjBmrG','黑山县',-1),('BLMPr5bd','FBdjBmrG','义县',-1),('tvA13sna','FBdjBmrG','北宁市',-1),('OesdhqMC','FBdjBmrG','其他',-1),('1EZLfTJn','iOUkXQxL','站前区',-1),('ulWKJVcD','iOUkXQxL','西市区',-1),('pzZ1kpyC','iOUkXQxL','鲅鱼圈区',-1),('rZbhcwrL','iOUkXQxL','老边区',-1),('7fyk3OZy','iOUkXQxL','大石桥市',-1),('NF0b8KhZ','iOUkXQxL','盖州市',-1),('mxHdlvaD','iOUkXQxL','其他',-1),('HLaisHP6','vXpL8n0H','海州区',-1),('Zj0kSSMl','vXpL8n0H','新邱区',-1),('iBbRrtsh','vXpL8n0H','太平区',-1),('nlng1ktH','vXpL8n0H','清河门区',-1),('Dh1Vvc0i','vXpL8n0H','细河区',-1),('bKAKAmk5','vXpL8n0H','彰武县',-1),('P3ffV7cZ','vXpL8n0H','阜新蒙古族自治县',-1),('ASXUuFE5','vXpL8n0H','其他',-1),('df0P5T7G','G91t0flh','白塔区',-1),('bQ1HSsQC','G91t0flh','文圣区',-1),('PvvQaG5b','G91t0flh','宏伟区',-1),('0hL3JBOH','G91t0flh','太子河区',-1),('UZkIrMqU','G91t0flh','弓长岭区',-1),('NjiGMXon','G91t0flh','灯塔市',-1),('Qg7H2aY4','G91t0flh','辽阳县',-1),('RQAQscyM','G91t0flh','其他',-1),('4JNnlF4u','NW5pXbPx','双台子区',-1),('xyhPoKYK','NW5pXbPx','兴隆台区',-1),('Co7dZgx5','NW5pXbPx','盘山县',-1),('mSWV6c6u','NW5pXbPx','大洼县',-1),('ztsrNVUT','NW5pXbPx','其他',-1),('seGQSlcw','NeutZe4M','银州区',-1),('jxfXLTZu','NeutZe4M','清河区',-1),('qcibfSCo','NeutZe4M','调兵山市',-1),('ULivRr3M','NeutZe4M','开原市',-1),('xV7qCfx3','NeutZe4M','铁岭县',-1),('9OELmjaF','NeutZe4M','昌图县',-1),('9xT4Pxqe','NeutZe4M','西丰县',-1),('XlH8clzt','NeutZe4M','其他',-1),('IYb4fSlB','SzC01FCt','双塔区',-1),('97ZXmI7g','SzC01FCt','龙城区',-1),('aT4XXuAO','SzC01FCt','凌源市',-1),('1rslZRcJ','SzC01FCt','北票市',-1),('IxrWxoDD','SzC01FCt','朝阳县',-1),('ekKIwxIB','SzC01FCt','建平县',-1),('NiVt6lal','SzC01FCt','喀喇沁左翼蒙古族自治县',-1),('4AdfwD5K','SzC01FCt','其他',-1),('BPSsSsGy','zbBjybUh','龙港区',-1),('zxsun9za','zbBjybUh','南票区',-1),('Z56sNCtr','zbBjybUh','连山区',-1),('NBccHGqn','zbBjybUh','兴城市',-1),('S2QNwobg','zbBjybUh','绥中县',-1),('VG47RD6o','zbBjybUh','建昌县',-1),('dcs86C1K','zbBjybUh','其他',-1),('vKadmg0Q','x6vMHzA3','南京',0),('yaOsYiqC','x6vMHzA3','苏州',0),('SUwqehs3','x6vMHzA3','无锡',0),('SEl5ZMbL','x6vMHzA3','常州',0),('05X0W5d4','x6vMHzA3','镇江',0),('4LkZxG5Z','x6vMHzA3','南通',0),('gqS9V8lT','x6vMHzA3','泰州',0),('bEPns0Ze','x6vMHzA3','扬州',0),('X9CRDmEp','x6vMHzA3','盐城',0),('N6vxasfq','x6vMHzA3','连云港',0),('DjhGfkA9','x6vMHzA3','徐州',0),('cznyi2qH','x6vMHzA3','淮安',0),('7sGVs5lI','x6vMHzA3','宿迁',0),('zycP0wyg','x6vMHzA3','其他',-1),('D6pKFem9','vKadmg0Q','玄武区',-1),('D9KfJY7j','vKadmg0Q','白下区',-1),('mGyu7HKx','vKadmg0Q','秦淮区',-1),('DLR9Yx0u','vKadmg0Q','建邺区',-1),('hY2sTBUz','vKadmg0Q','鼓楼区',-1),('VKYKOYGq','vKadmg0Q','下关区',-1),('n2wjhi37','vKadmg0Q','栖霞区',-1),('BPOvSKaT','vKadmg0Q','雨花台区',-1),('erSVwYnm','vKadmg0Q','浦口区',-1),('HrOTh4rz','vKadmg0Q','江宁区',-1),('SD8uqW5S','vKadmg0Q','六合区',-1),('qoDOaEdW','vKadmg0Q','溧水县',-1),('h8p2j1xb','vKadmg0Q','高淳县',-1),('N4kH6ydB','vKadmg0Q','其他',-1),('ppe309UH','yaOsYiqC','金阊区',-1),('Pb2tList','yaOsYiqC','平江区',-1),('ljiyziFu','yaOsYiqC','沧浪区',-1),('02xbZLuX','yaOsYiqC','虎丘区',-1),('FGcCroYJ','yaOsYiqC','吴中区',-1),('CFKW3Zy7','yaOsYiqC','相城区',-1),('bUCs5ACZ','yaOsYiqC','常熟市',-1),('uPX4VAI5','yaOsYiqC','张家港市',-1),('s9KXcq7e','yaOsYiqC','昆山市',-1),('TOWiP2Qs','yaOsYiqC','吴江市',-1),('g4nmsJ7X','yaOsYiqC','太仓市',-1),('obZUcrUE','yaOsYiqC','其他',-1),('QYHAu7wN','SUwqehs3','崇安区',-1),('Hu6V3P6Y','SUwqehs3','南长区',-1),('rAbnwtiI','SUwqehs3','北塘区',-1),('vB9U34nJ','SUwqehs3','滨湖区',-1),('FoZPidOL','SUwqehs3','锡山区',-1),('BOCqMCyK','SUwqehs3','惠山区',-1),('foFD3SxG','SUwqehs3','江阴市',-1),('XThQIJCv','SUwqehs3','宜兴市',-1),('gbkDPMnA','SUwqehs3','其他',-1),('RZ1BhF57','SEl5ZMbL','钟楼区',-1),('4b5GxkjB','SEl5ZMbL','天宁区',-1),('52x6adLD','SEl5ZMbL','戚墅堰区',-1),('L2zcvDiU','SEl5ZMbL','新北区',-1),('J0PTRLBZ','SEl5ZMbL','武进区',-1),('kAiMrtg4','SEl5ZMbL','金坛市',-1),('jGnnb3Ji','SEl5ZMbL','溧阳市',-1),('dyBPedVN','SEl5ZMbL','其他',-1),('SnXYlkM5','05X0W5d4','京口区',-1),('KoqKsy3k','05X0W5d4','润州区',-1),('n8RXJFpa','05X0W5d4','丹徒区',-1),('RgC8f3z3','05X0W5d4','丹阳市',-1),('zEYdbaP1','05X0W5d4','扬中市',-1),('Qg6Fc1hL','05X0W5d4','句容市',-1),('OXuYebr5','05X0W5d4','其他',-1),('oakLhPpJ','4LkZxG5Z','崇川区',-1),('09Qi3Tps','4LkZxG5Z','港闸区',-1),('qu56c6TW','4LkZxG5Z','通州市',-1),('kgUseH6s','4LkZxG5Z','如皋市',-1),('6mORrVck','4LkZxG5Z','海门市',-1),('b6YvJPkY','4LkZxG5Z','启东市',-1),('NVHFA4kY','4LkZxG5Z','海安县',-1),('TgT1JEXk','4LkZxG5Z','如东县',-1),('hrdkmrA5','4LkZxG5Z','其他',-1),('XzCNYAs1','gqS9V8lT','海陵区',-1),('RV3MjsWJ','gqS9V8lT','高港区',-1),('bF0Fp6vK','gqS9V8lT','姜堰市',-1),('Dos75aOe','gqS9V8lT','泰兴市',-1),('sqG50EGZ','gqS9V8lT','靖江市',-1),('RFsWf8Yk','gqS9V8lT','兴化市',-1),('F3P12s2Y','gqS9V8lT','其他',-1),('LWftvgRW','bEPns0Ze','广陵区',-1),('Pcd2DrF4','bEPns0Ze','维扬区',-1),('5jU52caD','bEPns0Ze','邗江区',-1),('hL7RVEq8','bEPns0Ze','江都市',-1),('s9khtRDS','bEPns0Ze','仪征市',-1),('aNLlLedv','bEPns0Ze','高邮市',-1),('A3BsgsHm','bEPns0Ze','宝应县',-1),('dX9uVWMe','bEPns0Ze','其他',-1),('6wVp74gj','X9CRDmEp','亭湖区',-1),('CEvIPTzj','X9CRDmEp','盐都区',-1),('GEOhH2My','X9CRDmEp','大丰市',-1),('bvaQPTuQ','X9CRDmEp','东台市',-1),('MU8D1Dyt','X9CRDmEp','建湖县',-1),('yk7xVsyN','X9CRDmEp','射阳县',-1),('gfOi4jAd','X9CRDmEp','阜宁县',-1),('jXwSfG2R','X9CRDmEp','滨海县',-1),('MLR8oTjI','X9CRDmEp','响水县',-1),('BJgC5fVR','X9CRDmEp','其他',-1),('H13E6Ogf','N6vxasfq','新浦区',-1),('8gTRWquz','N6vxasfq','海州区',-1),('JtaW87rz','N6vxasfq','连云区',-1),('JyPulfyI','N6vxasfq','东海县',-1),('vrazWnaE','N6vxasfq','灌云县',-1),('eyILBoY1','N6vxasfq','赣榆县',-1),('3iO2I5qL','N6vxasfq','灌南县',-1),('CpE7C4dG','N6vxasfq','其他',-1),('fLvQ3UTs','DjhGfkA9','云龙区',-1),('MyBlfj9h','DjhGfkA9','鼓楼区',-1),('3zxEOPVU','DjhGfkA9','九里区',-1),('Slu4k1i9','DjhGfkA9','泉山区',-1),('sPAkSbWs','DjhGfkA9','贾汪区',-1),('ttZJlWXt','DjhGfkA9','邳州市',-1),('FjDsyjsz','DjhGfkA9','新沂市',-1),('TPnFD0lp','DjhGfkA9','铜山县',-1),('cekzBrDG','DjhGfkA9','睢宁县',-1),('aNEGgcZv','DjhGfkA9','沛县',-1),('VD9sw9po','DjhGfkA9','丰县',-1),('5s2UzWv9','DjhGfkA9','其他',-1),('pVFuvVkT','cznyi2qH','清河区',-1),('BZSONmXu','cznyi2qH','清浦区',-1),('vDz411HW','cznyi2qH','楚州区',-1),('yPVcdOT6','cznyi2qH','淮阴区',-1),('4unDE3QZ','cznyi2qH','涟水县',-1),('ouF0uUWu','cznyi2qH','洪泽县',-1),('bmPEWTsM','cznyi2qH','金湖县',-1),('XvphGvYr','cznyi2qH','盱眙县',-1),('ZDNvIXyB','cznyi2qH','其他',-1),('f37J0te4','7sGVs5lI','宿城区',-1),('GtOXaseO','7sGVs5lI','宿豫区',-1),('5n9Ctg4P','7sGVs5lI','沭阳县',-1),('jGn4yvRP','7sGVs5lI','泗阳县',-1),('sJ5GdnoQ','7sGVs5lI','泗洪县',-1),('ew84m8gd','7sGVs5lI','其他',-1),('RjunJ1AU','XQ9HE28u','武汉',0),('HB7fOdlY','XQ9HE28u','黄石',0),('eGPRq7wm','XQ9HE28u','十堰',0),('X5kQXjr2','XQ9HE28u','荆州',0),('RTCWE1wj','XQ9HE28u','宜昌',0),('CEBGpVOP','XQ9HE28u','襄樊',0),('5SEUcb6g','XQ9HE28u','鄂州',0),('UHHwphiF','XQ9HE28u','荆门',0),('OeX8sstA','XQ9HE28u','孝感',0),('tS953JUe','XQ9HE28u','黄冈',0),('Mf91dBnI','XQ9HE28u','咸宁',0),('eJNddqGi','XQ9HE28u','随州',0),('1mnmvNik','XQ9HE28u','恩施土家族苗族自治州',0),('wBXd4MCO','XQ9HE28u','仙桃',0),('6Ha8Wc0j','XQ9HE28u','天门',0),('dQgscDLk','XQ9HE28u','潜江',0),('goE1ptaB','XQ9HE28u','神农架林区',0),('cMmYO0kV','XQ9HE28u','其他',-1),('3AvN9rDT','RjunJ1AU','江岸区',-1),('MDao0PQE','RjunJ1AU','武昌区',-1),('E4mk9Hwx','RjunJ1AU','江汉区',-1),('2WOMq0HZ','RjunJ1AU','硚口区',-1),('HdK7FqZM','RjunJ1AU','汉阳区',-1),('DZeSv9Yj','RjunJ1AU','青山区',-1),('BQnm5RTR','RjunJ1AU','洪山区',-1),('8FhkZ2CW','RjunJ1AU','东西湖区',-1),('30hIDHgq','RjunJ1AU','汉南区',-1),('hv78NBeB','RjunJ1AU','蔡甸区',-1),('M9Z0s4s7','RjunJ1AU','江夏区',-1),('ceWl1MLL','RjunJ1AU','黄陂区',-1),('pnDvLgoh','RjunJ1AU','新洲区',-1),('AOxWYOt6','RjunJ1AU','其他',-1),('i9R0wlyc','HB7fOdlY','黄石港区',-1),('1ZpS7pkr','HB7fOdlY','西塞山区',-1),('OTbmI9su','HB7fOdlY','下陆区',-1),('5j0qIlFp','HB7fOdlY','铁山区',-1),('pcTxbmx6','HB7fOdlY','大冶市',-1),('vXIl5767','HB7fOdlY','阳新县',-1),('0J0VxuNW','HB7fOdlY','其他',-1),('0vkkWynM','eGPRq7wm','张湾区',-1),('ejBGbYgp','eGPRq7wm','茅箭区',-1),('eQZprSaM','eGPRq7wm','丹江口市',-1),('GrBhbcYJ','eGPRq7wm','郧县',-1),('Qa1S7FeW','eGPRq7wm','竹山县',-1),('DktR3Xxl','eGPRq7wm','房县',-1),('QwLXbNX7','eGPRq7wm','郧西县',-1),('ms01BNZS','eGPRq7wm','竹溪县',-1),('14z6SQuC','eGPRq7wm','其他',-1),('PF5dJLo4','X5kQXjr2','沙市区',-1),('dazZKSjy','X5kQXjr2','荆州区',-1),('ixSrBA1v','X5kQXjr2','洪湖市',-1),('9fm70s9z','X5kQXjr2','石首市',-1),('sBCRRUMg','X5kQXjr2','松滋市',-1),('hjevCWKl','X5kQXjr2','监利县',-1),('rfmiJOB1','X5kQXjr2','公安县',-1),('FxqcgSMq','X5kQXjr2','江陵县',-1),('hUYr0ogJ','X5kQXjr2','其他',-1),('4xxEzupK','RTCWE1wj','西陵区',-1),('Vf7H9gce','RTCWE1wj','伍家岗区',-1),('ZzAtxlYK','RTCWE1wj','点军区',-1),('I4efbJLz','RTCWE1wj','猇亭区',-1),('hPj0E1lb','RTCWE1wj','夷陵区',-1),('Bz12MW9A','RTCWE1wj','宜都市',-1),('2pWbsDT9','RTCWE1wj','当阳市',-1),('kZaQ1yQx','RTCWE1wj','枝江市',-1),('jFQSal9j','RTCWE1wj','秭归县',-1),('18uiX7yB','RTCWE1wj','远安县',-1),('hpo3BJcB','RTCWE1wj','兴山县',-1),('50OHaAEb','RTCWE1wj','五峰土家族自治县',-1),('ew2WqI37','RTCWE1wj','长阳土家族自治县',-1),('Zysc3sQY','RTCWE1wj','其他',-1),('V9FXuljV','CEBGpVOP','襄城区',-1),('cqsOWEjq','CEBGpVOP','樊城区',-1),('1m2sFsKR','CEBGpVOP','襄阳区',-1),('hOgSjipw','CEBGpVOP','老河口市',-1),('aQvoiGOx','CEBGpVOP','枣阳市',-1),('w3i66Nyd','CEBGpVOP','宜城市',-1),('me3ilZxI','CEBGpVOP','南漳县',-1),('zE0hPMcb','CEBGpVOP','谷城县',-1),('0XHtfiTg','CEBGpVOP','保康县',-1),('PQHwdmKo','CEBGpVOP','其他',-1),('jZj6afaa','5SEUcb6g','鄂城区',-1),('v1j9GssD','5SEUcb6g','华容区',-1),('IXSO1WQK','5SEUcb6g','梁子湖区',-1),('D3Oniil6','5SEUcb6g','其他',-1),('0lKHPmtx','UHHwphiF','东宝区',-1),('sM7mSqhh','UHHwphiF','掇刀区',-1),('0G9Q9cHD','UHHwphiF','钟祥市',-1),('Y187r3qk','UHHwphiF','京山县',-1),('7oD6Zza6','UHHwphiF','沙洋县',-1),('n1nBsxwZ','UHHwphiF','其他',-1),('e1sRdHAw','OeX8sstA','孝南区',-1),('0qBu3fVx','OeX8sstA','应城市',-1),('eAospMhU','OeX8sstA','安陆市',-1),('wsnlGlSP','OeX8sstA','汉川市',-1),('aJ4MEXiT','OeX8sstA','云梦县',-1),('sDR2DuyD','OeX8sstA','大悟县',-1),('uvGJ6BQp','OeX8sstA','孝昌县',-1),('todYnQCk','OeX8sstA','其他',-1),('9chKyD9l','tS953JUe','黄州区',-1),('OT1Qdt88','tS953JUe','麻城市',-1),('bDRLkpmh','tS953JUe','武穴市',-1),('LzYMRaLU','tS953JUe','红安县',-1),('PQp1rT6k','tS953JUe','罗田县',-1),('1hb58dG4','tS953JUe','浠水县',-1),('MpYCLiYF','tS953JUe','蕲春县',-1),('fNskLYaN','tS953JUe','黄梅县',-1),('2NM9i3a1','tS953JUe','英山县',-1),('NDsoUjKs','tS953JUe','团风县',-1),('LiDwkf3r','tS953JUe','其他',-1),('kQQDcAlY','Mf91dBnI','咸安区',-1),('huZJsUyK','Mf91dBnI','赤壁市',-1),('bQLA4MJQ','Mf91dBnI','嘉鱼县',-1),('cxhRkXD6','Mf91dBnI','通山县',-1),('aftvxczy','Mf91dBnI','崇阳县',-1),('vtZR0Qag','Mf91dBnI','通城县',-1),('Frq2hGBl','Mf91dBnI','其他',-1),('toiLHWsT','eJNddqGi','曾都区',-1),('7ZXw8CAz','eJNddqGi','广水市',-1),('blCGeCvV','eJNddqGi','其他',-1),('Ai7gNMRQ','1mnmvNik','恩施市',-1),('TMUXCrbc','1mnmvNik','利川市',-1),('tSWbi5ig','1mnmvNik','建始县',-1),('2xVVknlf','1mnmvNik','来凤县',-1),('6sZFTsAd','1mnmvNik','巴东县',-1),('JQ7YRtEk','1mnmvNik','鹤峰县',-1),('Fle06HPd','1mnmvNik','宣恩县',-1),('3izshmrS','1mnmvNik','咸丰县',-1),('61yo64xa','1mnmvNik','其他',-1),('QKwU59Y2','Bz7BUsCy','成都',0),('rWV5ZoUl','Bz7BUsCy','自贡',0),('xSevMwDd','Bz7BUsCy','攀枝花',0),('ShOEA35s','Bz7BUsCy','泸州',0),('6WeLL2eq','Bz7BUsCy','德阳',0),('1zdX2OLB','Bz7BUsCy','绵阳',0),('OrpIRi4U','Bz7BUsCy','广元',0),('NKAENhba','Bz7BUsCy','遂宁',0),('n3Sr82j9','Bz7BUsCy','内江',0),('ScVdCf1m','Bz7BUsCy','乐山',0),('965CMN1p','Bz7BUsCy','南充',0),('0qvW4wap','Bz7BUsCy','眉山',0),('SrFLVSSG','Bz7BUsCy','宜宾',0),('TvHV3zuB','Bz7BUsCy','广安',0),('6XiINZLp','Bz7BUsCy','达州',0),('V6Wx040E','Bz7BUsCy','雅安',0),('3MZ2Reeb','Bz7BUsCy','巴中',0),('x5XUsP8n','Bz7BUsCy','资阳',0),('sQCr73sG','Bz7BUsCy','阿坝藏族羌族自治州',0),('TBQC3mbQ','Bz7BUsCy','甘孜藏族自治州',0),('SVDJoFQK','Bz7BUsCy','凉山彝族自治州',0),('K0HJZU22','Bz7BUsCy','其他',-1),('q2L79lIk','QKwU59Y2','青羊区',-1),('bmmCx8Th','QKwU59Y2','锦江区',-1),('K0NBTs2q','QKwU59Y2','金牛区',-1),('HniqVxXR','QKwU59Y2','武侯区',-1),('T8pWbHqd','QKwU59Y2','成华区',-1),('p9zpDzR7','QKwU59Y2','龙泉驿区',-1),('kFGlggnR','QKwU59Y2','青白江区',-1),('bssKW2C4','QKwU59Y2','新都区',-1),('v6iFpUV0','QKwU59Y2','温江区',-1),('SDLzDv31','QKwU59Y2','都江堰市',-1),('sYdE9be4','QKwU59Y2','彭州市',-1),('JWvQUR9Z','QKwU59Y2','邛崃市',-1),('PsRup5FY','QKwU59Y2','崇州市',-1),('fUlvjme8','QKwU59Y2','金堂县',-1),('L2fuJhiY','QKwU59Y2','郫县',-1),('ssY0Ro41','QKwU59Y2','新津县',-1),('Wjc7GpnQ','QKwU59Y2','双流县',-1),('TNn9jr6S','QKwU59Y2','蒲江县',-1),('icIQWuAT','QKwU59Y2','大邑县',-1),('vKRvOD1g','QKwU59Y2','其他',-1),('a5dA2sIW','rWV5ZoUl','大安区',-1),('5jrRFERb','rWV5ZoUl','自流井区',-1),('xshEmwPV','rWV5ZoUl','贡井区',-1),('TNkXBquj','rWV5ZoUl','沿滩区',-1),('cymTOeWP','rWV5ZoUl','荣县',-1),('AYNcAtOs','rWV5ZoUl','富顺县',-1),('o0tmBmGd','rWV5ZoUl','其他',-1),('ysxembkK','xSevMwDd','仁和区',-1),('Dj1qUjEP','xSevMwDd','米易县',-1),('7uSy51GM','xSevMwDd','盐边县',-1),('Gsms8tia','xSevMwDd','东区',-1),('7lyuHobj','xSevMwDd','西区',-1),('6FLTtz5q','xSevMwDd','其他',-1),('68s7tVpe','ShOEA35s','江阳区',-1),('pIP9PFvZ','ShOEA35s','纳溪区',-1),('WR8BNPUX','ShOEA35s','龙马潭区',-1),('h2UlveIU','ShOEA35s','泸县',-1),('fd0pLUs5','ShOEA35s','合江县',-1),('hUb0vS5t','ShOEA35s','叙永县',-1),('yc48TGSl','ShOEA35s','古蔺县',-1),('hrt2pts4','ShOEA35s','其他',-1),('tQERvJto','6WeLL2eq','旌阳区',-1),('tVaPzOWy','6WeLL2eq','广汉市',-1),('sR06w80D','6WeLL2eq','什邡市',-1),('y1i4EK6I','6WeLL2eq','绵竹市',-1),('hEDas91G','6WeLL2eq','罗江县',-1),('SlFy7eVD','6WeLL2eq','中江县',-1),('ZDxFYIhZ','6WeLL2eq','其他',-1),('6Ns0PXm6','1zdX2OLB','涪城区',-1),('W9to4XMS','1zdX2OLB','游仙区',-1),('f3lt3wCf','1zdX2OLB','江油市',-1),('Xg7dDVqI','1zdX2OLB','盐亭县',-1),('Z7yXOC37','1zdX2OLB','三台县',-1),('gK9jE8DA','1zdX2OLB','平武县',-1),('68whLmH1','1zdX2OLB','安县',-1),('7cm2QX2W','1zdX2OLB','梓潼县',-1),('JvxSpHr1','1zdX2OLB','北川羌族自治县',-1),('jeYt7Ctd','1zdX2OLB','其他',-1),('C0yhskps','OrpIRi4U','元坝区',-1),('pyigG1FN','OrpIRi4U','朝天区',-1),('XOfZCyd5','OrpIRi4U','青川县',-1),('jvkB5KVg','OrpIRi4U','旺苍县',-1),('aC5DWq66','OrpIRi4U','剑阁县',-1),('3KiBj4vf','OrpIRi4U','苍溪县',-1),('n8kbIadv','OrpIRi4U','市中区',-1),('TksbvAMm','OrpIRi4U','其他',-1),('bBq71wn5','NKAENhba','船山区',-1),('k3FciWuN','NKAENhba','安居区',-1),('x8ustLff','NKAENhba','射洪县',-1),('L5HbK0Sl','NKAENhba','蓬溪县',-1),('TtoXo4Sj','NKAENhba','大英县',-1),('WRz4s8DY','NKAENhba','其他',-1),('RpCPoJ2K','n3Sr82j9','市中区',-1),('pc41sELo','n3Sr82j9','东兴区',-1),('Ind6f9nu','n3Sr82j9','资中县',-1),('S4HWLWpw','n3Sr82j9','隆昌县',-1),('BkGaAVxs','n3Sr82j9','威远县',-1),('fqSEECSO','n3Sr82j9','其他',-1),('Y8zjrEbv','ScVdCf1m','市中区',-1),('OumfLjRl','ScVdCf1m','五通桥区',-1),('0FQM0KNy','ScVdCf1m','沙湾区',-1),('xmjAihwI','ScVdCf1m','金口河区',-1),('A7xuJFcr','ScVdCf1m','峨眉山市',-1),('VJ28ewt9','ScVdCf1m','夹江县',-1),('QnSC7G0J','ScVdCf1m','井研县',-1),('2XTNa6ZT','ScVdCf1m','犍为县',-1),('8qsaMW4H','ScVdCf1m','沐川县',-1),('qvVxXZ75','ScVdCf1m','马边彝族自治县',-1),('siCQc1EW','ScVdCf1m','峨边彝族自治县',-1),('ONgoswif','ScVdCf1m','其他',-1),('OgBJXeEr','965CMN1p','顺庆区',-1),('BFyJV17c','965CMN1p','高坪区',-1),('6shTNEWU','965CMN1p','嘉陵区',-1),('qsq18hQ0','965CMN1p','阆中市',-1),('tbFrSyn8','965CMN1p','营山县',-1),('jEyxgAw8','965CMN1p','蓬安县',-1),('C3YCzzdm','965CMN1p','仪陇县',-1),('nzchMFcv','965CMN1p','南部县',-1),('ZwgfDkxP','965CMN1p','西充县',-1),('hAH9Opcf','965CMN1p','其他',-1),('CYn19bjl','0qvW4wap','东坡区',-1),('OEjUxBgI','0qvW4wap','仁寿县',-1),('Amem7oy3','0qvW4wap','彭山县',-1),('rKNNodhl','0qvW4wap','洪雅县',-1),('riFgo2SM','0qvW4wap','丹棱县',-1),('JKCJxrGj','0qvW4wap','青神县',-1),('7lS87hJl','0qvW4wap','其他',-1),('TvQ0wgjh','SrFLVSSG','翠屏区',-1),('bmkGzU1x','SrFLVSSG','宜宾县',-1),('CjEJzrgU','SrFLVSSG','兴文县',-1),('z1C8gBsh','SrFLVSSG','南溪县',-1),('qsEoZvTW','SrFLVSSG','珙县',-1),('7syh2B64','SrFLVSSG','长宁县',-1),('xmtVAZh1','SrFLVSSG','高县',-1),('pXtAm4Oq','SrFLVSSG','江安县',-1),('8M89KFKN','SrFLVSSG','筠连县',-1),('3MaB2xgx','SrFLVSSG','屏山县',-1),('qCglAkpb','SrFLVSSG','其他',-1),('sarJXfIb','TvHV3zuB','广安区',-1),('Y3oWL1mY','TvHV3zuB','华蓥市',-1),('psZwFfAl','TvHV3zuB','岳池县',-1),('UmwNZzYx','TvHV3zuB','邻水县',-1),('mldkkdtV','TvHV3zuB','武胜县',-1),('JNWcPhtT','TvHV3zuB','其他',-1),('YWrhCXDE','6XiINZLp','通川区',-1),('O6Zf6Rxe','6XiINZLp','万源市',-1),('JdspQZ1z','6XiINZLp','达县',-1),('7MVdguJF','6XiINZLp','渠县',-1),('nTdUEIBK','6XiINZLp','宣汉县',-1),('1YyLjIXU','6XiINZLp','开江县',-1),('Tf59slgn','6XiINZLp','大竹县',-1),('ed8XVDMa','6XiINZLp','其他',-1),('emPiaBIb','V6Wx040E','雨城区',-1),('y6YAmrbj','V6Wx040E','芦山县',-1),('LjQws9o8','V6Wx040E','石棉县',-1),('0UJv9OMq','V6Wx040E','名山县',-1),('uN5zAYu6','V6Wx040E','天全县',-1),('9zE9ccDZ','V6Wx040E','荥经县',-1),('pqqrMtry','V6Wx040E','宝兴县',-1),('VsWrvL3w','V6Wx040E','汉源县',-1),('1msjUEqX','V6Wx040E','其他',-1),('i2xAHAw3','3MZ2Reeb','巴州区',-1),('kF6JYEAc','3MZ2Reeb','南江县',-1),('YymgT4aX','3MZ2Reeb','平昌县',-1),('abiN3ARm','3MZ2Reeb','通江县',-1),('ZyOxHXlw','3MZ2Reeb','其他',-1),('3AprtjDZ','x5XUsP8n','雁江区',-1),('kfIwYc0P','x5XUsP8n','简阳市',-1),('P8rdqjbE','x5XUsP8n','安岳县',-1),('013pmbhS','x5XUsP8n','乐至县',-1),('LQTo9sJU','x5XUsP8n','其他',-1),('ReTvrAk4','sQCr73sG','马尔康县',-1),('5fxeJBEe','sQCr73sG','九寨沟县',-1),('f6qaOEGM','sQCr73sG','红原县',-1),('Z7HUqjox','sQCr73sG','汶川县',-1),('sGlYNNO7','sQCr73sG','阿坝县',-1),('tCjovqDS','sQCr73sG','理县',-1),('fdmNT4Dl','sQCr73sG','若尔盖县',-1),('NJ4OJOgv','sQCr73sG','小金县',-1),('U7RTsu18','sQCr73sG','黑水县',-1),('saWGkOOc','sQCr73sG','金川县',-1),('5sXU2LQ5','sQCr73sG','松潘县',-1),('DIRRwb09','sQCr73sG','壤塘县',-1),('rWO1eS5b','sQCr73sG','茂县',-1),('I3Mb1Mxu','sQCr73sG','其他',-1),('prDNusAb','TBQC3mbQ','康定县',-1),('nH3X7GKy','TBQC3mbQ','丹巴县',-1),('CdoH1rZx','TBQC3mbQ','炉霍县',-1),('I9qsde36','TBQC3mbQ','九龙县',-1),('K3ZXPYBB','TBQC3mbQ','甘孜县',-1),('ONIo22Oj','TBQC3mbQ','雅江县',-1),('iFo7ovYh','TBQC3mbQ','新龙县',-1),('OiehjJ3d','TBQC3mbQ','道孚县',-1),('9amscQPc','TBQC3mbQ','白玉县',-1),('gWrgpdXq','TBQC3mbQ','理塘县',-1),('qYGJ8wEl','TBQC3mbQ','德格县',-1),('XYOacY3h','TBQC3mbQ','乡城县',-1),('R943BEml','TBQC3mbQ','石渠县',-1),('mgD6G60A','TBQC3mbQ','稻城县',-1),('J7L8U2Wk','TBQC3mbQ','色达县',-1),('OW9fCIxZ','TBQC3mbQ','巴塘县',-1),('sarEn19V','TBQC3mbQ','泸定县',-1),('s9SmtBEA','TBQC3mbQ','得荣县',-1),('Cou3gATD','TBQC3mbQ','其他',-1),('M10spMXv','SVDJoFQK','西昌市',-1),('BAEKZrA7','SVDJoFQK','美姑县',-1),('gQkq2x34','SVDJoFQK','昭觉县',-1),('1SfIXWKi','SVDJoFQK','金阳县',-1),('6MGVC6G9','SVDJoFQK','甘洛县',-1),('02JM1jcD','SVDJoFQK','布拖县',-1),('OpWehaQc','SVDJoFQK','雷波县',-1),('REES2l5B','SVDJoFQK','普格县',-1),('jQmJTlqt','SVDJoFQK','宁南县',-1),('zNXKqQ8o','SVDJoFQK','喜德县',-1),('4VZsPql1','SVDJoFQK','会东县',-1),('QVJtKWt5','SVDJoFQK','越西县',-1),('L8Vh9gC7','SVDJoFQK','会理县',-1),('eSAAmNN7','SVDJoFQK','盐源县',-1),('3VZsqqsz','SVDJoFQK','德昌县',-1),('o2LA8qOv','SVDJoFQK','冕宁县',-1),('zlJzWdKZ','SVDJoFQK','木里藏族自治县',-1),('UKrvPmhg','SVDJoFQK','其他',-1),('CCsnH669','oBmRecO8','西安',0),('9ftgnoKA','oBmRecO8','铜川',0),('86fQVeV8','oBmRecO8','宝鸡',0),('wvQt7GGD','oBmRecO8','咸阳',0),('NHhIEwDz','oBmRecO8','渭南',0),('F1sgGEjJ','oBmRecO8','延安',0),('sWCMwqfe','oBmRecO8','汉中',0),('GznrFODa','oBmRecO8','榆林',0),('Gax2HEfz','oBmRecO8','安康',0),('4MV0p1uX','oBmRecO8','商洛',0),('n1sVJrNO','oBmRecO8','其他',-1),('4frDY52O','CCsnH669','莲湖区',-1),('bRcwJ0DZ','CCsnH669','新城区',-1),('qgqHaDrl','CCsnH669','碑林区',-1),('IoFtvocY','CCsnH669','雁塔区',-1),('NEL4E3y2','CCsnH669','灞桥区',-1),('YoWWkHJV','CCsnH669','未央区',-1),('fMbm6mNo','CCsnH669','阎良区',-1),('GrAWhwAn','CCsnH669','临潼区',-1),('N3ns9S5K','CCsnH669','长安区',-1),('MZXqI2BS','CCsnH669','高陵县',-1),('wFMVj0C5','CCsnH669','蓝田县',-1),('Q6sNVTGp','CCsnH669','户县',-1),('bXOJPm6T','CCsnH669','周至县',-1),('o0BBsFQ2','CCsnH669','其他',-1),('CaFRY7v5','9ftgnoKA','耀州区',-1),('pp2WXhbn','9ftgnoKA','王益区',-1),('ruYirhkC','9ftgnoKA','印台区',-1),('ATpxfxRH','9ftgnoKA','宜君县',-1),('liSsIsId','9ftgnoKA','其他',-1),('TLq9NWMf','86fQVeV8','渭滨区',-1),('cQauPU2y','86fQVeV8','金台区',-1),('g7crn8l1','86fQVeV8','陈仓区',-1),('0VL9f7QO','86fQVeV8','岐山县',-1),('PgcnVzNO','86fQVeV8','凤翔县',-1),('4KI97cQt','86fQVeV8','陇县',-1),('QviBh6cR','86fQVeV8','太白县',-1),('cW23m19J','86fQVeV8','麟游县',-1),('gyOPSmdd','86fQVeV8','扶风县',-1),('3Mx7IYHv','86fQVeV8','千阳县',-1),('IZkoWdtN','86fQVeV8','眉县',-1),('jLvszELL','86fQVeV8','凤县',-1),('2hSonNYl','86fQVeV8','其他',-1),('LZJ8uKsZ','wvQt7GGD','秦都区',-1),('ND2rVZce','wvQt7GGD','渭城区',-1),('6kwz07jr','wvQt7GGD','杨陵区',-1),('Fmk9Sbsk','wvQt7GGD','兴平市',-1),('th7uEkjC','wvQt7GGD','礼泉县',-1),('YVL00vzr','wvQt7GGD','泾阳县',-1),('JV8CelYR','wvQt7GGD','永寿县',-1),('iQW7z3sc','wvQt7GGD','三原县',-1),('qWeOFDJs','wvQt7GGD','彬县',-1),('lgpC2zsn','wvQt7GGD','旬邑县',-1),('gONtDOXz','wvQt7GGD','长武县',-1),('Q2ajKXDH','wvQt7GGD','乾县',-1),('DLyM5tfj','wvQt7GGD','武功县',-1),('6JMFFnJy','wvQt7GGD','淳化县',-1),('vvvUW2t0','wvQt7GGD','其他',-1),('NB9RNqJD','NHhIEwDz','临渭区',-1),('l6TOgg4M','NHhIEwDz','韩城市',-1),('VlW88qJ1','NHhIEwDz','华阴市',-1),('Dn3JkiHU','NHhIEwDz','蒲城县',-1),('qfyDtJeg','NHhIEwDz','潼关县',-1),('QLtFSTSN','NHhIEwDz','白水县',-1),('fFrlA1Li','NHhIEwDz','澄城县',-1),('yAt4w0v4','NHhIEwDz','华县',-1),('Y0ctAsAL','NHhIEwDz','合阳县',-1),('v2paSJUH','NHhIEwDz','富平县',-1),('SpXL7FGw','NHhIEwDz','大荔县',-1),('LSOwKZ4l','NHhIEwDz','其他',-1),('szAMQgfz','F1sgGEjJ','宝塔区',-1),('sw7AvKoW','F1sgGEjJ','安塞县',-1),('NiQLWEkQ','F1sgGEjJ','洛川县',-1),('Sp9ygGM9','F1sgGEjJ','子长县',-1),('TZgL4XFh','F1sgGEjJ','黄陵县',-1),('G3netQUz','F1sgGEjJ','延川县',-1),('Hp8jcqi4','F1sgGEjJ','富县',-1),('b0S4y1IN','F1sgGEjJ','延长县',-1),('jJCMR1YQ','F1sgGEjJ','甘泉县',-1),('xY9n7XRr','F1sgGEjJ','宜川县',-1),('jPbAo3x2','F1sgGEjJ','志丹县',-1),('wd5wmmsF','F1sgGEjJ','黄龙县',-1),('gsK1uW8H','F1sgGEjJ','吴起县',-1),('vKBmtEfD','F1sgGEjJ','其他',-1),('0ZVOogdF','sWCMwqfe','汉台区',-1),('boERT6mG','sWCMwqfe','留坝县',-1),('bcGs9sb9','sWCMwqfe','镇巴县',-1),('ZF26r6Zk','sWCMwqfe','城固县',-1),('REBSygiK','sWCMwqfe','南郑县',-1),('RxUCraRu','sWCMwqfe','洋县',-1),('NosOu1Od','sWCMwqfe','宁强县',-1),('iC2iNKok','sWCMwqfe','佛坪县',-1),('KZNU3peV','sWCMwqfe','勉县',-1),('qUFEcAzD','sWCMwqfe','西乡县',-1),('EGcfncBO','sWCMwqfe','略阳县',-1),('uppCCjwE','sWCMwqfe','其他',-1),('WG8GVDsF','GznrFODa','榆阳区',-1),('wfjb3KWI','GznrFODa','清涧县',-1),('8uiNfAX8','GznrFODa','绥德县',-1),('9N3S6OLz','GznrFODa','神木县',-1),('1x5HfUwX','GznrFODa','佳县',-1),('p9vcN08x','GznrFODa','府谷县',-1),('a9ByYe5R','GznrFODa','子洲县',-1),('GgHYwSBn','GznrFODa','靖边县',-1),('3JsTazGy','GznrFODa','横山县',-1),('XBGJUhTX','GznrFODa','米脂县',-1),('1bpBhe7w','GznrFODa','吴堡县',-1),('LO8xuJo4','GznrFODa','定边县',-1),('r7FF7i1O','GznrFODa','其他',-1),('oBJyuWKr','Gax2HEfz','汉滨区',-1),('cQHgip2K','Gax2HEfz','紫阳县',-1),('4vm81BJg','Gax2HEfz','岚皋县',-1),('6SW5R4O6','Gax2HEfz','旬阳县',-1),('odhCWo40','Gax2HEfz','镇坪县',-1),('aioT4zoC','Gax2HEfz','平利县',-1),('blBJnQ3P','Gax2HEfz','石泉县',-1),('V5nEvWe0','Gax2HEfz','宁陕县',-1),('b5JZOKik','Gax2HEfz','白河县',-1),('G2YcswTR','Gax2HEfz','汉阴县',-1),('9iVrYsZJ','Gax2HEfz','其他',-1),('vI3iAkeB','4MV0p1uX','商州区',-1),('BQsXMpRU','4MV0p1uX','镇安县',-1),('IdxPytcB','4MV0p1uX','山阳县',-1),('OS8ps0ck','4MV0p1uX','洛南县',-1),('2oxDncW7','4MV0p1uX','商南县',-1),('UmpQl59o','4MV0p1uX','丹凤县',-1),('aEPC7DEJ','4MV0p1uX','柞水县',-1),('KCFEQQ0O','4MV0p1uX','其他',-1),('PSDQ4Wz1','chcyN0P1','石家庄',0),('7aV1rEjE','chcyN0P1','唐山',0),('dKqLxQ3C','chcyN0P1','秦皇岛',0),('J9yjsf7L','chcyN0P1','邯郸',0),('TsLOulVm','chcyN0P1','邢台',0),('CYFr79Is','chcyN0P1','保定',0),('KMlEwE76','chcyN0P1','张家口',0),('TUH2qYsG','chcyN0P1','承德',0),('p1nxsWJJ','chcyN0P1','沧州',0),('mWFWDo7z','chcyN0P1','廊坊',0),('pzjH0np6','chcyN0P1','衡水',0),('IIWmmAm2','chcyN0P1','其他',-1),('8SNAfFVp','PSDQ4Wz1','长安区',-1),('OE7cRxnF','PSDQ4Wz1','桥东区',-1),('CEU7PRs5','PSDQ4Wz1','桥西区',-1),('UEHOutzp','PSDQ4Wz1','新华区',-1),('PawIV8zZ','PSDQ4Wz1','裕华区',-1),('jbMFK1vc','PSDQ4Wz1','井陉矿区',-1),('9EPbLmBb','PSDQ4Wz1','鹿泉市',-1),('N7IoXv3l','PSDQ4Wz1','辛集市',-1),('8s9p0hHq','PSDQ4Wz1','藁城市',-1),('8UTU2F9U','PSDQ4Wz1','晋州市',-1),('NL2xKQm9','PSDQ4Wz1','新乐市',-1),('NsW3qRtI','PSDQ4Wz1','深泽县',-1),('S3PVfn4P','PSDQ4Wz1','无极县',-1),('zj2Iv5A5','PSDQ4Wz1','赵县',-1),('lIElW5V3','PSDQ4Wz1','灵寿县',-1),('qabogYZ3','PSDQ4Wz1','高邑县',-1),('cXssMXoi','PSDQ4Wz1','元氏县',-1),('JYvGosgy','PSDQ4Wz1','赞皇县',-1),('Yk7NZIgT','PSDQ4Wz1','平山县',-1),('iN1QVosL','PSDQ4Wz1','井陉县',-1),('0P5ZAKFN','PSDQ4Wz1','栾城县',-1),('NZsxJn7A','PSDQ4Wz1','正定县',-1),('QzSVnsy6','PSDQ4Wz1','行唐县',-1),('pZecUySl','PSDQ4Wz1','其他',-1),('QvnnJ2E0','7aV1rEjE','路北区',-1),('9Hsjoaiq','7aV1rEjE','路南区',-1),('NoFJzLAK','7aV1rEjE','古冶区',-1),('Psk7XLFE','7aV1rEjE','开平区',-1),('wiQQMh8e','7aV1rEjE','丰南区',-1),('nnlIMEM4','7aV1rEjE','丰润区',-1),('aoybZn62','7aV1rEjE','遵化市',-1),('C0sZOAsm','7aV1rEjE','迁安市',-1),('dNZ30ucH','7aV1rEjE','迁西县',-1),('JcKxJMoL','7aV1rEjE','滦南县',-1),('a28pCHb9','7aV1rEjE','玉田县',-1),('23e2BbtH','7aV1rEjE','唐海县',-1),('wJ6uWgC2','7aV1rEjE','乐亭县',-1),('AisJEQon','7aV1rEjE','滦县',-1),('7uGR0y9G','7aV1rEjE','其他',-1),('rsBVEM5b','dKqLxQ3C','海港区',-1),('JoTyAJug','dKqLxQ3C','山海关区',-1),('EnI6DoqP','dKqLxQ3C','北戴河区',-1),('mn73pCsg','dKqLxQ3C','昌黎县',-1),('CjNqDmLD','dKqLxQ3C','抚宁县',-1),('K6oFVDyY','dKqLxQ3C','卢龙县',-1),('lmyJJUAb','dKqLxQ3C','青龙满族自治县',-1),('wKQtVWT1','dKqLxQ3C','其他',-1),('NTZyodQ1','J9yjsf7L','邯山区',-1),('R5Ev8eD6','J9yjsf7L','丛台区',-1),('XbQGnw46','J9yjsf7L','复兴区',-1),('6bAUwbyD','J9yjsf7L','峰峰矿区',-1),('rpJDYjzz','J9yjsf7L','武安市',-1),('Drz3IIIS','J9yjsf7L','邱县',-1),('sStxKdwC','J9yjsf7L','大名县',-1),('GJibj9sy','J9yjsf7L','魏县',-1),('DDAhVaVF','J9yjsf7L','曲周县',-1),('P0FLoQaj','J9yjsf7L','鸡泽县',-1),('r5rktsLY','J9yjsf7L','肥乡县',-1),('PFPZm3Yy','J9yjsf7L','广平县',-1),('Thnm43Iq','J9yjsf7L','成安县',-1),('VINL7EpQ','J9yjsf7L','临漳县',-1),('p2d4sDcd','J9yjsf7L','磁县',-1),('pZWg9tiN','J9yjsf7L','涉县',-1),('8X3aUbFr','J9yjsf7L','永年县',-1),('Itdaeq4T','J9yjsf7L','馆陶县',-1),('TbPDYUI7','J9yjsf7L','邯郸县',-1),('l3s3nzdh','J9yjsf7L','其他',-1),('so5F1Cs6','TsLOulVm','桥东区',-1),('mhujCmEZ','TsLOulVm','桥西区',-1),('QtLViT24','TsLOulVm','南宫市',-1),('se51iLiE','TsLOulVm','沙河市',-1),('KG6cU31s','TsLOulVm','临城县',-1),('AU28Myrn','TsLOulVm','内丘县',-1),('lzfGp7F0','TsLOulVm','柏乡县',-1),('Ul6xNdlA','TsLOulVm','隆尧县',-1),('N6XNTnA7','TsLOulVm','任县',-1),('auFB6TDi','TsLOulVm','南和县',-1),('BITGM4N3','TsLOulVm','宁晋县',-1),('P9wmC7bZ','TsLOulVm','巨鹿县',-1),('9gNhLGs0','TsLOulVm','新河县',-1),('ksnAgG2v','TsLOulVm','广宗县',-1),('MsGh2Hu1','TsLOulVm','平乡县',-1),('BGtcl3ZW','TsLOulVm','威县',-1),('ZiYOFjVy','TsLOulVm','清河县',-1),('MleUjY4w','TsLOulVm','临西县',-1),('BSssC2fY','TsLOulVm','邢台县',-1),('HudHfeBb','TsLOulVm','其他',-1),('ySeReLG7','CYFr79Is','新市区',-1),('LVyUIECt','CYFr79Is','北市区',-1),('M58LOEPA','CYFr79Is','南市区',-1),('jQPStl37','CYFr79Is','定州市',-1),('XrGszoXb','CYFr79Is','涿州市',-1),('nENAsWjN','CYFr79Is','安国市',-1),('Amhtiv2A','CYFr79Is','高碑店市',-1),('MXU96fLL','CYFr79Is','易县',-1),('rJnz5JXW','CYFr79Is','徐水县',-1),('06wdiJ5f','CYFr79Is','涞源县',-1),('YRHkOChs','CYFr79Is','顺平县',-1),('eQ3i63xI','CYFr79Is','唐县',-1),('vDnO5EtY','CYFr79Is','望都县',-1),('yC7hz52C','CYFr79Is','涞水县',-1),('gAVRlIcG','CYFr79Is','高阳县',-1),('IxRG7Ukz','CYFr79Is','安新县',-1),('oNt0AKKj','CYFr79Is','雄县',-1),('pSo4Ezz1','CYFr79Is','容城县',-1),('x4H8piRQ','CYFr79Is','蠡县',-1),('8Us9QIrb','CYFr79Is','曲阳县',-1),('XfryhnQ1','CYFr79Is','阜平县',-1),('0XdpFRZb','CYFr79Is','博野县',-1),('417E5K4W','CYFr79Is','满城县',-1),('EjLDxqtE','CYFr79Is','清苑县',-1),('zAgb6KpR','CYFr79Is','定兴县',-1),('1UAnL9nS','CYFr79Is','其他',-1),('wWOvPVWh','KMlEwE76','桥东区',-1),('Z3bxwLIw','KMlEwE76','桥西区',-1),('ik7gmJIc','KMlEwE76','宣化区',-1),('RDdxwKhM','KMlEwE76','下花园区',-1),('3Y4sXTCX','KMlEwE76','张北县',-1),('6jAJibsY','KMlEwE76','康保县',-1),('TEOsSKf0','KMlEwE76','沽源县',-1),('FJitvsbc','KMlEwE76','尚义县',-1),('OCSnyHmw','KMlEwE76','蔚县',-1),('eyEun5VE','KMlEwE76','阳原县',-1),('EwN3vWHs','KMlEwE76','怀安县',-1),('QD952xY1','KMlEwE76','万全县',-1),('d0pvTgEV','KMlEwE76','怀来县',-1),('8qUG19oB','KMlEwE76','赤城县',-1),('JYc8Yqmz','KMlEwE76','崇礼县',-1),('L3DcV0HN','KMlEwE76','宣化县',-1),('AX10d1w5','KMlEwE76','涿鹿县',-1),('340NsrZi','KMlEwE76','其他',-1),('LlO04fsO','TUH2qYsG','双桥区',-1),('jI1NhnU2','TUH2qYsG','双滦区',-1),('xHhvtkRY','TUH2qYsG','鹰手营子矿区',-1),('Zq9wkqOX','TUH2qYsG','兴隆县',-1),('sJsH471q','TUH2qYsG','平泉县',-1),('aRbFdzOc','TUH2qYsG','滦平县',-1),('PN3oZiOx','TUH2qYsG','隆化县',-1),('3YHaeFUT','TUH2qYsG','承德县',-1),('ED3UseNB','TUH2qYsG','丰宁满族自治县',-1),('Tt8rw8wA','TUH2qYsG','宽城满族自治县',-1),('6EJlUCZH','TUH2qYsG','围场满族蒙古族自治县',-1),('aMgSw9eo','TUH2qYsG','其他',-1),('gS6sB2v5','p1nxsWJJ','新华区',-1),('uO42touX','p1nxsWJJ','运河区',-1),('2z0h5ToW','p1nxsWJJ','泊头市',-1),('46YolnTT','p1nxsWJJ','任丘市',-1),('cCsuiGUr','p1nxsWJJ','黄骅市',-1),('hvLnIbLa','p1nxsWJJ','河间市',-1),('Yty9NRo0','p1nxsWJJ','献县',-1),('bsGOOyXm','p1nxsWJJ','吴桥县',-1),('mRIW6oKi','p1nxsWJJ','沧县',-1),('W3KsEMzY','p1nxsWJJ','东光县',-1),('sGCawuTJ','p1nxsWJJ','肃宁县',-1),('k9Sf6QM1','p1nxsWJJ','南皮县',-1),('OEZxe7B4','p1nxsWJJ','盐山县',-1),('esYYOhFG','p1nxsWJJ','青县',-1),('swvI7Mqv','p1nxsWJJ','海兴县',-1),('vzpQzVUF','p1nxsWJJ','孟村回族自治县',-1),('sovH8atm','p1nxsWJJ','其他',-1),('tossUPdw','mWFWDo7z','安次区',-1),('Q51s0e6X','mWFWDo7z','广阳区',-1),('I0XA0q4Z','mWFWDo7z','霸州市',-1),('y0zoIZg9','mWFWDo7z','三河市',-1),('JJwbFcDg','mWFWDo7z','香河县',-1),('xTa54ap3','mWFWDo7z','永清县',-1),('s4ZIb5QE','mWFWDo7z','固安县',-1),('06agw4iT','mWFWDo7z','文安县',-1),('7j9kZWlS','mWFWDo7z','大城县',-1),('DttbW3Wb','mWFWDo7z','大厂回族自治县',-1),('jgJtfGf7','mWFWDo7z','其他',-1),('IrgJv738','pzjH0np6','桃城区',-1),('ca51VbCF','pzjH0np6','冀州市',-1),('WH1EfOya','pzjH0np6','深州市',-1),('tncy3OBi','pzjH0np6','枣强县',-1),('d8r1v5r2','pzjH0np6','武邑县',-1),('rTbO4wO8','pzjH0np6','武强县',-1),('IUIpJE1L','pzjH0np6','饶阳县',-1),('Nl7s8qwF','pzjH0np6','安平县',-1),('itYCTTHH','pzjH0np6','故城县',-1),('nIAWkAzK','pzjH0np6','景县',-1),('qaUWoCOF','pzjH0np6','阜城县',-1),('sW3ajWHz','pzjH0np6','其他',-1),('aSrMP8HT','bSUEhIxP','太原',0),('pguYMswn','bSUEhIxP','大同',0),('T7ESaLu9','bSUEhIxP','阳泉',0),('gsA2gUUO','bSUEhIxP','长治',0),('oQnC1ihU','bSUEhIxP','晋城',0),('S2Any9xh','bSUEhIxP','朔州',0),('Kppo11sj','bSUEhIxP','晋中',0),('zdwWfq8s','bSUEhIxP','运城',0),('gvPDFVpz','bSUEhIxP','忻州',0),('vyXyGOJV','bSUEhIxP','临汾',0),('Bg4jlUj0','bSUEhIxP','吕梁',0),('k5I7DjRN','bSUEhIxP','其他',-1),('uMxtikdb','aSrMP8HT','杏花岭区',-1),('nqwp4sJU','aSrMP8HT','小店区',-1),('xOquyyY7','aSrMP8HT','迎泽区',-1),('8eEF7DdR','aSrMP8HT','尖草坪区',-1),('hoVivm3d','aSrMP8HT','万柏林区',-1),('8NnxM8P9','aSrMP8HT','晋源区',-1),('iJGjvOXN','aSrMP8HT','古交市',-1),('waU7sRDW','aSrMP8HT','阳曲县',-1),('wscgpG07','aSrMP8HT','清徐县',-1),('Rkqsdhyl','aSrMP8HT','娄烦县',-1),('krzHqvGl','aSrMP8HT','其他',-1),('GtM243gW','pguYMswn','城区',-1),('yDgUUQCg','pguYMswn','矿区',-1),('xMImtlD0','pguYMswn','南郊区',-1),('s4i2Fjpw','pguYMswn','新荣区',-1),('kWkaXk9Q','pguYMswn','大同县',-1),('ur60roFj','pguYMswn','天镇县',-1),('9mTCGx8Y','pguYMswn','灵丘县',-1),('4Lse3p4U','pguYMswn','阳高县',-1),('0ChiEE6Y','pguYMswn','左云县',-1),('QHT1dFw1','pguYMswn','广灵县',-1),('32SsdJUY','pguYMswn','浑源县',-1),('HoNupJsV','pguYMswn','其他',-1),('QbGMfC96','T7ESaLu9','城区',-1),('0B0SJROH','T7ESaLu9','矿区',-1),('Gxx4Dhfk','T7ESaLu9','郊区',-1),('tIk6psOp','T7ESaLu9','平定县',-1),('XJTOHu6h','T7ESaLu9','盂县',-1),('pzXRlPlJ','T7ESaLu9','其他',-1),('O4M7soas','gsA2gUUO','城区',-1),('M6FTgs2m','gsA2gUUO','郊区',-1),('97NRZAfc','gsA2gUUO','潞城市',-1),('f9U8fXus','gsA2gUUO','长治县',-1),('hiuyUQDL','gsA2gUUO','长子县',-1),('HNqFZWJO','gsA2gUUO','平顺县',-1),('yyb6w9Fo','gsA2gUUO','襄垣县',-1),('TdxumJs8','gsA2gUUO','沁源县',-1),('eX8MRvQj','gsA2gUUO','屯留县',-1),('2dlvMQRm','gsA2gUUO','黎城县',-1),('gskK8Yb4','gsA2gUUO','武乡县',-1),('W0uC30ns','gsA2gUUO','沁县',-1),('guErdTC4','gsA2gUUO','壶关县',-1),('5ZLdBC7G','gsA2gUUO','其他',-1),('TJhpupYj','oQnC1ihU','城区',-1),('U7spIX0A','oQnC1ihU','高平市',-1),('hVkK48O8','oQnC1ihU','泽州县',-1),('Vfy4Rscz','oQnC1ihU','陵川县',-1),('MMt6BxIB','oQnC1ihU','阳城县',-1),('wu7TQ7ZA','oQnC1ihU','沁水县',-1),('ZsOZWRdN','oQnC1ihU','其他',-1),('MdvhBJqr','S2Any9xh','朔城区',-1),('s1iTpON3','S2Any9xh','平鲁区',-1),('zaL4Bgxz','S2Any9xh','山阴县',-1),('OWZk727k','S2Any9xh','右玉县',-1),('fbRsatWH','S2Any9xh','应县',-1),('CEAKaXuB','S2Any9xh','怀仁县',-1),('yzifsVPA','S2Any9xh','其他',-1),('8Sxugkxt','Kppo11sj','榆次区',-1),('WwZDHHQD','Kppo11sj','介休市',-1),('ZGL2fFF9','Kppo11sj','昔阳县',-1),('ZNqKMaax','Kppo11sj','灵石县',-1),('ThlP4Ka5','Kppo11sj','祁县',-1),('Aq1WR1qN','Kppo11sj','左权县',-1),('Ye3qjs4c','Kppo11sj','寿阳县',-1),('rsCg1fuX','Kppo11sj','太谷县',-1),('XRBlY1IB','Kppo11sj','和顺县',-1),('nIdyNoTd','Kppo11sj','平遥县',-1),('ttC9IwEk','Kppo11sj','榆社县',-1),('xrXGaFsw','Kppo11sj','其他',-1),('4mxaC1r0','zdwWfq8s','盐湖区',-1),('32H7BpaU','zdwWfq8s','河津市',-1),('s3gYx7GV','zdwWfq8s','永济市',-1),('Ae6aTwHz','zdwWfq8s','闻喜县',-1),('P0EyHQMM','zdwWfq8s','新绛县',-1),('sXMfHGOl','zdwWfq8s','平陆县',-1),('r2Y5vOUc','zdwWfq8s','垣曲县',-1),('T97MvTds','zdwWfq8s','绛县',-1),('I3jI7sXp','zdwWfq8s','稷山县',-1),('bshNIK5x','zdwWfq8s','芮城县',-1),('oF7UEJgl','zdwWfq8s','夏县',-1),('n1zZ4eHT','zdwWfq8s','万荣县',-1),('nssQsATh','zdwWfq8s','临猗县',-1),('FcSpEDlJ','zdwWfq8s','其他',-1),('DVyCy6yn','gvPDFVpz','忻府区',-1),('c8OpmsHa','gvPDFVpz','原平市',-1),('nRcrWA2J','gvPDFVpz','代县',-1),('N3vi93gQ','gvPDFVpz','神池县',-1),('YssM6DMq','gvPDFVpz','五寨县',-1),('3vY65dEH','gvPDFVpz','五台县',-1),('XPsL3GaJ','gvPDFVpz','偏关县',-1),('WdzgT5Ho','gvPDFVpz','宁武县',-1),('RMa1Zc2v','gvPDFVpz','静乐县',-1),('1VBBf1w2','gvPDFVpz','繁峙县',-1),('xaA6fB4E','gvPDFVpz','河曲县',-1),('tEwBcnH3','gvPDFVpz','保德县',-1),('OvjaKhYk','gvPDFVpz','定襄县',-1),('Fiej9dSi','gvPDFVpz','岢岚县',-1),('AXQltLHR','gvPDFVpz','其他',-1),('JLVlZSwq','vyXyGOJV','尧都区',-1),('oyf7ubTa','vyXyGOJV','侯马市',-1),('L7cXzOwN','vyXyGOJV','霍州市',-1),('sduy7HNY','vyXyGOJV','汾西县',-1),('REng5h7G','vyXyGOJV','吉县',-1),('Yss9szEx','vyXyGOJV','安泽县',-1),('zlxfpVxl','vyXyGOJV','大宁县',-1),('2dKxDkRZ','vyXyGOJV','浮山县',-1),('Udv4vuMg','vyXyGOJV','古县',-1),('yMJs9BcC','vyXyGOJV','隰县',-1),('FhslJzQf','vyXyGOJV','襄汾县',-1),('nFx5W7Fg','vyXyGOJV','翼城县',-1),('yC99Hf6f','vyXyGOJV','永和县',-1),('XozB6Fdy','vyXyGOJV','乡宁县',-1),('pgOj9QnB','vyXyGOJV','曲沃县',-1),('4n6EbQyt','vyXyGOJV','洪洞县',-1),('SyadmIgp','vyXyGOJV','蒲县',-1),('zwi354sl','vyXyGOJV','其他',-1),('FPZIslot','Bg4jlUj0','离石区',-1),('odzNYfyP','Bg4jlUj0','孝义市',-1),('pDogU4K6','Bg4jlUj0','汾阳市',-1),('NLjPgMe6','Bg4jlUj0','文水县',-1),('0erhzFn9','Bg4jlUj0','中阳县',-1),('4Nppwx75','Bg4jlUj0','兴县',-1),('N2oQrGeL','Bg4jlUj0','临县',-1),('rx84SzNh','Bg4jlUj0','方山县',-1),('S4RQqLhr','Bg4jlUj0','柳林县',-1),('fELFtlv2','Bg4jlUj0','岚县',-1),('e8Ekm0mu','Bg4jlUj0','交口县',-1),('ZWr3Jkon','Bg4jlUj0','交城县',-1),('NnYLQc9k','Bg4jlUj0','石楼县',-1),('8oyoFQBs','Bg4jlUj0','其他',-1),('lxqxqTDC','rjVszlhd','郑州',0),('5RGQTjdQ','rjVszlhd','开封',0),('4m7qFEfj','rjVszlhd','洛阳',0),('tTJ6nhit','rjVszlhd','平顶山',0),('5eLuaeLu','rjVszlhd','安阳',0),('jICMvMwB','rjVszlhd','鹤壁',0),('lNZUyDNx','rjVszlhd','新乡',0),('AEaal9Cj','rjVszlhd','焦作',0),('WmlE6381','rjVszlhd','濮阳',0),('kjOIUqTI','rjVszlhd','许昌',0),('AS4VYBeZ','rjVszlhd','漯河',0),('vg5S2S9t','rjVszlhd','三门峡',0),('1oVxFHbB','rjVszlhd','南阳',0),('iA0Misho','rjVszlhd','商丘',0),('sluVUR60','rjVszlhd','信阳',0),('jFaQPEv3','rjVszlhd','周口',0),('X2KcKl7b','rjVszlhd','驻马店',0),('wwosgjH9','rjVszlhd','焦作',0),('AwPeaOjm','rjVszlhd','其他',-1),('B0VrDsrI','lxqxqTDC','中原区',-1),('uXhL4g2m','lxqxqTDC','金水区',-1),('ARrcQPWX','lxqxqTDC','二七区',-1),('ttNVOeLZ','lxqxqTDC','管城回族区',-1),('oh2978FS','lxqxqTDC','上街区',-1),('Yj1NenCf','lxqxqTDC','惠济区',-1),('ARrjHbkm','lxqxqTDC','巩义市',-1),('AyugsDBS','lxqxqTDC','新郑市',-1),('PQhZv6dt','lxqxqTDC','新密市',-1),('f4shvrHo','lxqxqTDC','登封市',-1),('ReMkNS4F','lxqxqTDC','荥阳市',-1),('FokNq0LU','lxqxqTDC','中牟县',-1),('DqWGESCw','lxqxqTDC','其他',-1),('U3UBvTVE','5RGQTjdQ','鼓楼区',-1),('FrljQCZa','5RGQTjdQ','龙亭区',-1),('jXRIpN6x','5RGQTjdQ','顺河回族区',-1),('CfJNyb1v','5RGQTjdQ','禹王台区',-1),('LBsdzLEG','5RGQTjdQ','金明区',-1),('DVckzo2X','5RGQTjdQ','开封县',-1),('V8msqIhK','5RGQTjdQ','尉氏县',-1),('BgTdpydy','5RGQTjdQ','兰考县',-1),('m99Cp084','5RGQTjdQ','杞县',-1),('skjUnwb4','5RGQTjdQ','通许县',-1),('pbtqcA6K','5RGQTjdQ','其他',-1),('7EYpzzmA','4m7qFEfj','西工区',-1),('99heWABt','4m7qFEfj','老城区',-1),('bSUnXyWI','4m7qFEfj','涧西区',-1),('NzjmDIBu','4m7qFEfj','瀍河回族区',-1),('hUf62g7B','4m7qFEfj','洛龙区',-1),('tUTXU5U1','4m7qFEfj','吉利区',-1),('5v5r1MkZ','4m7qFEfj','偃师市',-1),('11sTyQny','4m7qFEfj','孟津县',-1),('7Xw8wMbk','4m7qFEfj','汝阳县',-1),('QWNefo0k','4m7qFEfj','伊川县',-1),('u7W99EXW','4m7qFEfj','洛宁县',-1),('1fq71nAy','4m7qFEfj','嵩县',-1),('SpMEdNKt','4m7qFEfj','宜阳县',-1),('bNcAvlc7','4m7qFEfj','新安县',-1),('Kfm5Z37E','4m7qFEfj','栾川县',-1),('RuwDnr3d','4m7qFEfj','其他',-1),('m1FSXKbV','tTJ6nhit','新华区',-1),('QBIgIOiC','tTJ6nhit','卫东区',-1),('9sC297se','tTJ6nhit','湛河区',-1),('tiTVHRlB','tTJ6nhit','石龙区',-1),('MeEAjJ9O','tTJ6nhit','汝州市',-1),('Ynnu4SgP','tTJ6nhit','舞钢市',-1),('i3DCJCW8','tTJ6nhit','宝丰县',-1),('UHJ8v1Tq','tTJ6nhit','叶县',-1),('V7eLWo9b','tTJ6nhit','郏县',-1),('5JwEzGXb','tTJ6nhit','鲁山县',-1),('v3cHktjh','tTJ6nhit','其他',-1),('rvgS5Q4k','5eLuaeLu','北关区',-1),('OKoyAxuE','5eLuaeLu','文峰区',-1),('SlgQuYLC','5eLuaeLu','殷都区',-1),('MxsBFIUA','5eLuaeLu','龙安区',-1),('NSV2W6li','5eLuaeLu','林州市',-1),('mJ75vsA3','5eLuaeLu','安阳县',-1),('si6CNkUA','5eLuaeLu','滑县',-1),('pnNhyZho','5eLuaeLu','内黄县',-1),('TBpfEg8z','5eLuaeLu','汤阴县',-1),('lUvQnMSW','5eLuaeLu','其他',-1),('CIs0gpDm','jICMvMwB','淇滨区',-1),('ZMpG3bP2','jICMvMwB','山城区',-1),('4lEaSsGT','jICMvMwB','鹤山区',-1),('cIEXqY1H','jICMvMwB','浚县',-1),('qK7o3IHK','jICMvMwB','淇县',-1),('uUoPkiDp','jICMvMwB','其他',-1),('MwMQteUO','lNZUyDNx','卫滨区',-1),('iPBvT9go','lNZUyDNx','红旗区',-1),('TCUfMgzj','lNZUyDNx','凤泉区',-1),('9PKsOWq3','lNZUyDNx','牧野区',-1),('8RC01HfF','lNZUyDNx','卫辉市',-1),('CR1USpgW','lNZUyDNx','辉县市',-1),('1RNduEwD','lNZUyDNx','新乡县',-1),('zms9rFv0','lNZUyDNx','获嘉县',-1),('OIgLQeiz','lNZUyDNx','原阳县',-1),('zHs5rzaN','lNZUyDNx','长垣县',-1),('lBs1fZS6','lNZUyDNx','封丘县',-1),('UeFz14ne','lNZUyDNx','延津县',-1),('a6pFv6sX','lNZUyDNx','其他',-1),('NU76HIMV','AEaal9Cj','解放区',-1),('kAGQw8xQ','AEaal9Cj','中站区',-1),('QUtXGfKG','AEaal9Cj','马村区',-1),('r4k5dGLR','AEaal9Cj','山阳区',-1),('5GmoDZKZ','AEaal9Cj','沁阳市',-1),('L6LrcPMt','AEaal9Cj','孟州市',-1),('TTHCXgXx','AEaal9Cj','修武县',-1),('Ns4SDMFZ','AEaal9Cj','温县',-1),('CtzBmB6u','AEaal9Cj','武陟县',-1),('cGiKaMR1','AEaal9Cj','博爱县',-1),('6LvoDAVB','AEaal9Cj','其他',-1),('bQMhPpam','WmlE6381','华龙区',-1),('dn26zGoL','WmlE6381','濮阳县',-1),('uueuHA1r','WmlE6381','南乐县',-1),('LBiAjvZF','WmlE6381','台前县',-1),('QN2Mfcn4','WmlE6381','清丰县',-1),('sVbMjbLZ','WmlE6381','范县',-1),('18UG4bpL','WmlE6381','其他',-1),('P15lSMzs','kjOIUqTI','魏都区',-1),('pgFuZgs8','kjOIUqTI','禹州市',-1),('O1qAEGge','kjOIUqTI','长葛市',-1),('x5aPuveF','kjOIUqTI','许昌县',-1),('LwBDrPiZ','kjOIUqTI','鄢陵县',-1),('pf10nWgW','kjOIUqTI','襄城县',-1),('G7Qou0ZA','kjOIUqTI','其他',-1),('nIqDymJS','AS4VYBeZ','源汇区',-1),('e6s4HsEf','AS4VYBeZ','郾城区',-1),('OPA2NFjl','AS4VYBeZ','召陵区',-1),('q5wC42SC','AS4VYBeZ','临颍县',-1),('9oyLYoa5','AS4VYBeZ','舞阳县',-1),('8FkS7PAN','AS4VYBeZ','其他',-1),('3TRG5Iwx','vg5S2S9t','湖滨区',-1),('U7NhpEND','vg5S2S9t','义马市',-1),('t7bOTtV3','vg5S2S9t','灵宝市',-1),('8sAjICMV','vg5S2S9t','渑池县',-1),('mEPWeusj','vg5S2S9t','卢氏县',-1),('ekNk2uy0','vg5S2S9t','陕县',-1),('HNxdE9Hq','vg5S2S9t','其他',-1),('yXf3YDeX','1oVxFHbB','卧龙区',-1),('F3udd01f','1oVxFHbB','宛城区',-1),('bhXDcsCf','1oVxFHbB','邓州市',-1),('BXnqPUc9','1oVxFHbB','桐柏县',-1),('yAat1Hmf','1oVxFHbB','方城县',-1),('RFysrLbg','1oVxFHbB','淅川县',-1),('5TaI6JRt','1oVxFHbB','镇平县',-1),('qIUYb55g','1oVxFHbB','唐河县',-1),('HE1XUgEQ','1oVxFHbB','南召县',-1),('rb8t1SSg','1oVxFHbB','内乡县',-1),('Ib9vdL4D','1oVxFHbB','新野县',-1),('Ms23J43S','1oVxFHbB','社旗县',-1),('afJKTCCv','1oVxFHbB','西峡县',-1),('uqsXxDCp','1oVxFHbB','其他',-1),('IP3sdAQI','iA0Misho','梁园区',-1),('KEbouN3G','iA0Misho','睢阳区',-1),('GosBF0dL','iA0Misho','永城市',-1),('9oUvNNn4','iA0Misho','宁陵县',-1),('VQCFbFNF','iA0Misho','虞城县',-1),('MFbq14IL','iA0Misho','民权县',-1),('Sl4ReB1F','iA0Misho','夏邑县',-1),('ndt0nzZ2','iA0Misho','柘城县',-1),('Css5xz1h','iA0Misho','睢县',-1),('s14MzstD','iA0Misho','其他',-1),('jjW1xNS7','sluVUR60','浉河区',-1),('ZdejVEv3','sluVUR60','平桥区',-1),('VRJoBT2f','sluVUR60','潢川县',-1),('0UB2h68T','sluVUR60','淮滨县',-1),('2cdHB7sM','sluVUR60','息县',-1),('Ssgpe1vv','sluVUR60','新县',-1),('LMliFCqJ','sluVUR60','商城县',-1),('xmdkuqlo','sluVUR60','固始县',-1),('dtpYbFhD','sluVUR60','罗山县',-1),('6gEsxFHz','sluVUR60','光山县',-1),('swKRBFFD','sluVUR60','其他',-1),('wopxVsJb','jFaQPEv3','川汇区',-1),('gESGh7ss','jFaQPEv3','项城市',-1),('mYmeksLH','jFaQPEv3','商水县',-1),('5Af9MbRj','jFaQPEv3','淮阳县',-1),('NJLUepZu','jFaQPEv3','太康县',-1),('VdBBt8y4','jFaQPEv3','鹿邑县',-1),('gxmF96j0','jFaQPEv3','西华县',-1),('f3EyKkZI','jFaQPEv3','扶沟县',-1),('0K9Ud2UW','jFaQPEv3','沈丘县',-1),('cxIfGExd','jFaQPEv3','郸城县',-1),('91xss9cc','jFaQPEv3','其他',-1),('pPgGLCvh','X2KcKl7b','驿城区',-1),('Z1ATpCcK','X2KcKl7b','确山县',-1),('wrZjvSx7','X2KcKl7b','新蔡县',-1),('2OOsO9ca','X2KcKl7b','上蔡县',-1),('TCKZTjpV','X2KcKl7b','西平县',-1),('pR5G8lhD','X2KcKl7b','泌阳县',-1),('ROx38eH9','X2KcKl7b','平舆县',-1),('2hoYWwYb','X2KcKl7b','汝南县',-1),('HOsEiTYz','X2KcKl7b','遂平县',-1),('y36JMkAC','X2KcKl7b','正阳县',-1),('P5eBLYvg','X2KcKl7b','其他',-1),('v8o20hos','wwosgjH9','济源市',-1),('7FgXS9Sv','wwosgjH9','其他',-1),('V5DlJLRu','8DLXysiC','长春',0),('NDlKAYPj','8DLXysiC','吉林',0),('UxFL511y','8DLXysiC','四平',0),('xcEeFZtw','8DLXysiC','辽源',0),('wbWshyKu','8DLXysiC','通化',0),('sEsYL1n9','8DLXysiC','白山',0),('mXD1BKWl','8DLXysiC','松原',0),('pcfT19yT','8DLXysiC','白城',0),('tYTyCiMj','8DLXysiC','延边朝鲜族自治州',0),('uqsnmdqi','8DLXysiC','其他',-1),('tkQXvn38','V5DlJLRu','朝阳区',-1),('f6R8nJMT','V5DlJLRu','宽城区',-1),('Qrsl2mbf','V5DlJLRu','二道区',-1),('xJSsNrIn','V5DlJLRu','南关区',-1),('F8Y7GKgk','V5DlJLRu','绿园区',-1),('tS6rH7xi','V5DlJLRu','双阳区',-1),('WKVgdVNY','V5DlJLRu','九台市',-1),('QVj3zPuP','V5DlJLRu','榆树市',-1),('ZM9RTcyc','V5DlJLRu','德惠市',-1),('CsqmaaDh','V5DlJLRu','农安县',-1),('OcxIaAkv','V5DlJLRu','其他',-1),('kvY5ujlx','NDlKAYPj','船营区',-1),('f9boCwvt','NDlKAYPj','昌邑区',-1),('JQ2MQ9I7','NDlKAYPj','龙潭区',-1),('sVIlUiKi','NDlKAYPj','丰满区',-1),('nnu6rFiA','NDlKAYPj','舒兰市',-1),('i1dhQnF3','NDlKAYPj','桦甸市',-1),('ctRXtCv6','NDlKAYPj','蛟河市',-1),('orPLnnPD','NDlKAYPj','磐石市',-1),('ss8k64oI','NDlKAYPj','永吉县',-1),('jYsp4CrA','NDlKAYPj','其他',-1),('S3qISMBQ','UxFL511y','铁西区',-1),('H6pAXMSj','UxFL511y','铁东区',-1),('2G6BXcYi','UxFL511y','公主岭市',-1),('ZKlk6w3l','UxFL511y','双辽市',-1),('QXCanWwO','UxFL511y','梨树县',-1),('y8LsqEWQ','UxFL511y','伊通满族自治县',-1),('sF9uqzIM','UxFL511y','其他',-1),('raLQlI0L','xcEeFZtw','龙山区',-1),('UuPr29sb','xcEeFZtw','西安区',-1),('FQsI5t9u','xcEeFZtw','东辽县',-1),('xJYRT9o2','xcEeFZtw','东丰县',-1),('LUz31BJo','xcEeFZtw','其他',-1),('rGutZJzU','wbWshyKu','东昌区',-1),('Bv9N1cEN','wbWshyKu','二道江区',-1),('IiglEQPa','wbWshyKu','梅河口市',-1),('4vpyxUTU','wbWshyKu','集安市',-1),('PI3oDoZ5','wbWshyKu','通化县',-1),('MsgCHebg','wbWshyKu','辉南县',-1),('BGQt4ju6','wbWshyKu','柳河县',-1),('KOjSfpEb','wbWshyKu','其他',-1),('mWgPKoO0','sEsYL1n9','八道江区',-1),('uFVn3L4x','sEsYL1n9','江源区',-1),('idiAbJsB','sEsYL1n9','临江市',-1),('fNizQkiU','sEsYL1n9','靖宇县',-1),('Qs3HKG92','sEsYL1n9','抚松县',-1),('CcCEyV2l','sEsYL1n9','长白朝鲜族自治县',-1),('AZhQNuOe','sEsYL1n9','其他',-1),('kriOnndn','mXD1BKWl','宁江区',-1),('EXzXO5X2','mXD1BKWl','乾安县',-1),('tVrmMfzj','mXD1BKWl','长岭县',-1),('JZn9AIr8','mXD1BKWl','扶余县',-1),('Q2R1ElT3','mXD1BKWl','前郭尔罗斯蒙古族自治县',-1),('fwbRrid8','mXD1BKWl','其他',-1),('wdotYwZL','pcfT19yT','洮北区',-1),('45qNyJrD','pcfT19yT','大安市',-1),('qCsiY8Qi','pcfT19yT','洮南市',-1),('dsZHtQW1','pcfT19yT','镇赉县',-1),('dyQR9nBK','pcfT19yT','通榆县',-1),('gjpfwhxG','pcfT19yT','其他',-1),('cwwNmgMG','tYTyCiMj','延吉市',-1),('9xG5BiSK','tYTyCiMj','图们市',-1),('eCN8o6Jr','tYTyCiMj','敦化市',-1),('hgLhMcIp','tYTyCiMj','龙井市',-1),('nwuGsTpg','tYTyCiMj','珲春市',-1),('iqsW4qrW','tYTyCiMj','和龙市',-1),('GpqXUPO2','tYTyCiMj','安图县',-1),('oHwSM9Lc','tYTyCiMj','汪清县',-1),('zzojQtYg','tYTyCiMj','其他',-1),('BjMHgzs4','PwUgn58r','哈尔滨',0),('MCY7er6z','PwUgn58r','齐齐哈尔',0),('8ffsL4Y9','PwUgn58r','鹤岗',0),('ns4Zy8dH','PwUgn58r','双鸭山',0),('nT1Y2dlg','PwUgn58r','鸡西',0),('sVbB1qM5','PwUgn58r','大庆',0),('SOYSjcWs','PwUgn58r','伊春',0),('aCy3HEW6','PwUgn58r','牡丹江',0),('7lhgeuRC','PwUgn58r','佳木斯',0),('Ut3gWmw5','PwUgn58r','七台河',0),('F9QAyEyc','PwUgn58r','黑河',0),('Wb8JBYxj','PwUgn58r','绥化',0),('Ke7zy0cG','PwUgn58r','大兴安岭地区',0),('Y6sM7HB0','PwUgn58r','其他',-1),('x3jxjuKs','BjMHgzs4','松北区',-1),('o8b9DKsr','BjMHgzs4','道里区',-1),('VwrXI8oL','BjMHgzs4','南岗区',-1),('C6SZiOvr','BjMHgzs4','平房区',-1),('0fz8N8sw','BjMHgzs4','香坊区',-1),('RglssjyC','BjMHgzs4','道外区',-1),('xozoDft1','BjMHgzs4','呼兰区',-1),('MhxuuUkd','BjMHgzs4','阿城区',-1),('jcanF6T0','BjMHgzs4','双城市',-1),('7UP2HzRF','BjMHgzs4','尚志市',-1),('l8rfdtlJ','BjMHgzs4','五常市',-1),('1VmcsMUu','BjMHgzs4','宾县',-1),('AMpCJFfF','BjMHgzs4','方正县',-1),('2ELtcwiu','BjMHgzs4','通河县',-1),('nEj9K7BH','BjMHgzs4','巴彦县',-1),('UysRrfOV','BjMHgzs4','延寿县',-1),('JywVNztE','BjMHgzs4','木兰县',-1),('LRZRGByt','BjMHgzs4','依兰县',-1),('fEvsMyU8','BjMHgzs4','其他',-1),('iqgp4x1k','MCY7er6z','龙沙区',-1),('mywQrLWn','MCY7er6z','昂昂溪区',-1),('p8NmCDHC','MCY7er6z','铁锋区',-1),('5PDjBGsy','MCY7er6z','建华区',-1),('Z5yUqOG3','MCY7er6z','富拉尔基区',-1),('SDLWRWgb','MCY7er6z','碾子山区',-1),('F7LaICpR','MCY7er6z','梅里斯达斡尔族区',-1),('25Ey3XpL','MCY7er6z','讷河市',-1),('kFbmfqMS','MCY7er6z','富裕县',-1),('c3CNsgeY','MCY7er6z','拜泉县',-1),('lIP5BNIX','MCY7er6z','甘南县',-1),('O9nn61mU','MCY7er6z','依安县',-1),('4oZgoajy','MCY7er6z','克山县',-1),('EGta3uVq','MCY7er6z','泰来县',-1),('zKBIdJUJ','MCY7er6z','克东县',-1),('tTm9aLny','MCY7er6z','龙江县',-1),('dxJjyl2o','MCY7er6z','其他',-1),('RyKM0YcX','8ffsL4Y9','兴山区',-1),('uVskEAFU','8ffsL4Y9','工农区',-1),('v2QvP473','8ffsL4Y9','南山区',-1),('f4uiMM9U','8ffsL4Y9','兴安区',-1),('JKVnR2dv','8ffsL4Y9','向阳区',-1),('ymvY8TdB','8ffsL4Y9','东山区',-1),('0hlQ7nOa','8ffsL4Y9','萝北县',-1),('jod6GVNu','8ffsL4Y9','绥滨县',-1),('3aNYQ7OC','8ffsL4Y9','其他',-1),('JXwT69pF','ns4Zy8dH','尖山区',-1),('gX1s8fxd','ns4Zy8dH','岭东区',-1),('zQ2K8WmI','ns4Zy8dH','四方台区',-1),('WdEyyqMp','ns4Zy8dH','宝山区',-1),('DM2W2Ovw','ns4Zy8dH','集贤县',-1),('E2NeU76y','ns4Zy8dH','宝清县',-1),('opG5GLBl','ns4Zy8dH','友谊县',-1),('BbxUTFpW','ns4Zy8dH','饶河县',-1),('jEejfkoJ','ns4Zy8dH','其他',-1),('MzffS3Fp','nT1Y2dlg','鸡冠区',-1),('ar0ISqoh','nT1Y2dlg','恒山区',-1),('ReBwV2si','nT1Y2dlg','城子河区',-1),('6yopVmEH','nT1Y2dlg','滴道区',-1),('appz9cZx','nT1Y2dlg','梨树区',-1),('mIrZQyqL','nT1Y2dlg','麻山区',-1),('tqkRVIoI','nT1Y2dlg','密山市',-1),('MFEToWpG','nT1Y2dlg','虎林市',-1),('i2NnVJ1M','nT1Y2dlg','鸡东县',-1),('jC4136Yz','nT1Y2dlg','其他',-1),('vttwhHr3','sVbB1qM5','萨尔图区',-1),('hIiW2bqR','sVbB1qM5','红岗区',-1),('lWcNa0kb','sVbB1qM5','龙凤区',-1),('fbdBglns','sVbB1qM5','让胡路区',-1),('j3OJhMIh','sVbB1qM5','大同区',-1),('54Z55Z6V','sVbB1qM5','林甸县',-1),('uXdwiA65','sVbB1qM5','肇州县',-1),('zVXCRzww','sVbB1qM5','肇源县',-1),('FD9197q7','sVbB1qM5','杜尔伯特蒙古族自治县',-1),('t9Jn9MSa','sVbB1qM5','其他',-1),('SMuRBysJ','SOYSjcWs','伊春区',-1),('4REMC1b2','SOYSjcWs','带岭区',-1),('udh8fc6i','SOYSjcWs','南岔区',-1),('bS8vfRAP','SOYSjcWs','金山屯区',-1),('EwzrwIs0','SOYSjcWs','西林区',-1),('mgdplGBL','SOYSjcWs','美溪区',-1),('pb16VfMH','SOYSjcWs','乌马河区',-1),('79Ln1OGo','SOYSjcWs','翠峦区',-1),('Gza2P2xV','SOYSjcWs','友好区',-1),('yEs8FdSs','SOYSjcWs','上甘岭区',-1),('nVBryVjU','SOYSjcWs','五营区',-1),('eECcdT4y','SOYSjcWs','红星区',-1),('0aghQmuY','SOYSjcWs','新青区',-1),('OVXESr3I','SOYSjcWs','汤旺河区',-1),('8PqdLfhK','SOYSjcWs','乌伊岭区',-1),('xTPrBsv1','SOYSjcWs','铁力市',-1),('UzYS1zsl','SOYSjcWs','嘉荫县',-1),('0PCunuKf','SOYSjcWs','其他',-1),('RQvKti27','aCy3HEW6','爱民区',-1),('nbrga1qv','aCy3HEW6','东安区',-1),('LCdyB5sd','aCy3HEW6','阳明区',-1),('gH6j5NSJ','aCy3HEW6','西安区',-1),('lkk425IC','aCy3HEW6','绥芬河市',-1),('cmeaiKJ9','aCy3HEW6','宁安市',-1),('aeET0ULQ','aCy3HEW6','海林市',-1),('7gMjU8hN','aCy3HEW6','穆棱市',-1),('STnwyT1n','aCy3HEW6','林口县',-1),('PYz5mp8h','aCy3HEW6','东宁县',-1),('nraYlLAa','aCy3HEW6','其他',-1),('6scivqFB','7lhgeuRC','向阳区',-1),('y5OZ0KY3','7lhgeuRC','前进区',-1),('VtdAZIUn','7lhgeuRC','东风区',-1),('pNwWbe63','7lhgeuRC','郊区',-1),('K3y0qaFR','7lhgeuRC','同江市',-1),('RYOANGHG','7lhgeuRC','富锦市',-1),('t3tAKSVo','7lhgeuRC','桦川县',-1),('52rJNMlr','7lhgeuRC','抚远县',-1),('bt1sUF02','7lhgeuRC','桦南县',-1),('aV4DoJJT','7lhgeuRC','汤原县',-1),('s1Ha9Opr','7lhgeuRC','其他',-1),('298H5NVL','Ut3gWmw5','桃山区',-1),('aazOx8Xo','Ut3gWmw5','新兴区',-1),('T1RXUZZy','Ut3gWmw5','茄子河区',-1),('drtAQCGj','Ut3gWmw5','勃利县',-1),('l55hMKnj','Ut3gWmw5','其他',-1),('Iux6y8Da','F9QAyEyc','爱辉区',-1),('JQWALO9r','F9QAyEyc','北安市',-1),('Is76Mhgi','F9QAyEyc','五大连池市',-1),('UOjOHQEe','F9QAyEyc','逊克县',-1),('gxnXURPq','F9QAyEyc','嫩江县',-1),('6m62OMGg','F9QAyEyc','孙吴县',-1),('tuU68mBK','F9QAyEyc','其他',-1),('pDJQmK5D','Wb8JBYxj','北林区',-1),('zpTP87ws','Wb8JBYxj','安达市',-1),('YDaqNtug','Wb8JBYxj','肇东市',-1),('qCp2wdsQ','Wb8JBYxj','海伦市',-1),('PgADbd1E','Wb8JBYxj','绥棱县',-1),('kYxLBiHk','Wb8JBYxj','兰西县',-1),('jO2s6QIf','Wb8JBYxj','明水县',-1),('sO9DuR3P','Wb8JBYxj','青冈县',-1),('8JeAyldQ','Wb8JBYxj','庆安县',-1),('nc0TOaXI','Wb8JBYxj','望奎县',-1),('h80MFXiy','Wb8JBYxj','其他',-1),('l0xdPi6z','Ke7zy0cG','呼玛县',-1),('tba3gUgH','Ke7zy0cG','塔河县',-1),('1bBeCJOl','Ke7zy0cG','漠河县',-1),('0Ys2E5Fa','Ke7zy0cG','大兴安岭辖区',-1),('wd3Y78I8','Ke7zy0cG','其他',-1),('kuWi3hAS','hbs78kMp','呼和浩特',0),('UQkNxZsU','hbs78kMp','包头',0),('TySUvI1N','hbs78kMp','乌海',0),('XhsXrOD7','hbs78kMp','赤峰',0),('wfSqOFtH','hbs78kMp','通辽',0),('Lxvexwjg','hbs78kMp','鄂尔多斯',0),('O0vFNN4I','hbs78kMp','呼伦贝尔',0),('p9Ck6Kf1','hbs78kMp','巴彦淖尔',0),('Xs3kIcL0','hbs78kMp','乌兰察布',0),('MOaSyrJJ','hbs78kMp','锡林郭勒盟',0),('458SZArr','hbs78kMp','兴安盟',0),('mfGVzUTJ','hbs78kMp','阿拉善盟',0),('2xNyK7Fd','hbs78kMp','其他',-1),('0bNyfViP','kuWi3hAS','回民区',-1),('CwNDesv5','kuWi3hAS','玉泉区',-1),('qbKh6HM4','kuWi3hAS','新城区',-1),('cqIHnv3s','kuWi3hAS','赛罕区',-1),('Yt9wsCB9','kuWi3hAS','托克托县',-1),('oJLfbLYd','kuWi3hAS','清水河县',-1),('sAQU7lLg','kuWi3hAS','武川县',-1),('0zJvrksN','kuWi3hAS','和林格尔县',-1),('6NmxtFRb','kuWi3hAS','土默特左旗',-1),('RJjgDKMv','kuWi3hAS','其他',-1),('b8BElFnW','UQkNxZsU','昆都仑区',-1),('yrBb0qTD','UQkNxZsU','青山区',-1),('6UBT0jHw','UQkNxZsU','东河区',-1),('nDL8ioHz','UQkNxZsU','九原区',-1),('QaG53cqr','UQkNxZsU','石拐区',-1),('9fymxGwu','UQkNxZsU','白云矿区',-1),('KqTS4yrb','UQkNxZsU','固阳县',-1),('FtPNFkKm','UQkNxZsU','土默特右旗',-1),('HUdKIFeB','UQkNxZsU','达尔罕茂明安联合旗',-1),('WsFJpCTs','UQkNxZsU','其他',-1),('ivm4sIec','TySUvI1N','海勃湾区',-1),('rk0ekQPw','TySUvI1N','乌达区',-1),('NqAtMxAG','TySUvI1N','海南区',-1),('KL6cziSo','TySUvI1N','其他',-1),('aDEyOWDS','XhsXrOD7','红山区',-1),('e1PLUzWv','XhsXrOD7','元宝山区',-1),('uP6GPhb8','XhsXrOD7','松山区',-1),('bX9SEnq9','XhsXrOD7','宁城县',-1),('ARof0qTp','XhsXrOD7','林西县',-1),('mQMgRGZM','XhsXrOD7','喀喇沁旗',-1),('ClHgViiT','XhsXrOD7','巴林左旗',-1),('lp18nkSj','XhsXrOD7','敖汉旗',-1),('pKOOj9PW','XhsXrOD7','阿鲁科尔沁旗',-1),('BZMi7BRr','XhsXrOD7','翁牛特旗',-1),('sSsuxApC','XhsXrOD7','克什克腾旗',-1),('VPC8IGpq','XhsXrOD7','巴林右旗',-1),('JIos9ofY','XhsXrOD7','其他',-1),('E1dVm4Cz','wfSqOFtH','科尔沁区',-1),('QXWvoM4n','wfSqOFtH','霍林郭勒市',-1),('pGQLzqaw','wfSqOFtH','开鲁县',-1),('tQoflsXf','wfSqOFtH','科尔沁左翼中旗',-1),('kH5sdVRA','wfSqOFtH','科尔沁左翼后旗',-1),('YcbAGgs7','wfSqOFtH','库伦旗',-1),('Pg6YdlZr','wfSqOFtH','奈曼旗',-1),('yjWqlsRA','wfSqOFtH','扎鲁特旗',-1),('HQssP4Of','wfSqOFtH','其他',-1),('cbSSwB74','Lxvexwjg','东胜区',-1),('VfK7mnDi','Lxvexwjg','准格尔旗',-1),('ywulDBwh','Lxvexwjg','乌审旗',-1),('VTr4V6Ut','Lxvexwjg','伊金霍洛旗',-1),('Nm2f1xev','Lxvexwjg','鄂托克旗',-1),('JE4HM80V','Lxvexwjg','鄂托克前旗',-1),('oZQk7Bp3','Lxvexwjg','杭锦旗',-1),('Lopf3r4x','Lxvexwjg','达拉特旗',-1),('fqNmeDzC','Lxvexwjg','其他',-1),('yeWOndF4','O0vFNN4I','海拉尔区',-1),('R1Bs59Mb','O0vFNN4I','满洲里市',-1),('Vgo1FldI','O0vFNN4I','牙克石市',-1),('ELYGXqTe','O0vFNN4I','扎兰屯市',-1),('GLprVrxU','O0vFNN4I','根河市',-1),('GTS5GBJV','O0vFNN4I','额尔古纳市',-1),('27Izh2d7','O0vFNN4I','陈巴尔虎旗',-1),('DVsOsLFT','O0vFNN4I','阿荣旗',-1),('sBStoHGi','O0vFNN4I','新巴尔虎左旗',-1),('EZCDLNaM','O0vFNN4I','新巴尔虎右旗',-1),('Fix43ohd','O0vFNN4I','鄂伦春自治旗',-1),('gHRsy9PM','O0vFNN4I','莫力达瓦达斡尔族自治旗',-1),('x59ogMHI','O0vFNN4I','鄂温克族自治旗',-1),('yMKbV0iv','O0vFNN4I','其他',-1),('7NbPCXFZ','p9Ck6Kf1','临河区',-1),('4QdPNKKr','p9Ck6Kf1','五原县',-1),('Zj38XIy4','p9Ck6Kf1','磴口县',-1),('WdRn1m9W','p9Ck6Kf1','杭锦后旗',-1),('ZvBHJ3VA','p9Ck6Kf1','乌拉特中旗',-1),('w5c2G6tX','p9Ck6Kf1','乌拉特前旗',-1),('ctSyVAjg','p9Ck6Kf1','乌拉特后旗',-1),('mrarGO4O','p9Ck6Kf1','其他',-1),('XObdpuqt','Xs3kIcL0','集宁区',-1),('7ETdWQct','Xs3kIcL0','丰镇市',-1),('qOlRbzPe','Xs3kIcL0','兴和县',-1),('wkWIptGw','Xs3kIcL0','卓资县',-1),('YqmRzOOP','Xs3kIcL0','商都县',-1),('DTfIGHyB','Xs3kIcL0','凉城县',-1),('cwz5tZTE','Xs3kIcL0','化德县',-1),('eEtaMOHR','Xs3kIcL0','四子王旗',-1),('te0Ex0oa','Xs3kIcL0','察哈尔右翼前旗',-1),('7TfB1Er2','Xs3kIcL0','察哈尔右翼中旗',-1),('1tfmjCc3','Xs3kIcL0','察哈尔右翼后旗',-1),('qkvQQb9V','Xs3kIcL0','其他',-1),('Qq6xpwZ4','MOaSyrJJ','锡林浩特市',-1),('GWCWOIiS','MOaSyrJJ','二连浩特市',-1),('m9FGGezg','MOaSyrJJ','多伦县',-1),('I0iTP8j9','MOaSyrJJ','阿巴嘎旗',-1),('3SJlCDsc','MOaSyrJJ','西乌珠穆沁旗',-1),('TitlJ1NE','MOaSyrJJ','东乌珠穆沁旗',-1),('wHLf7gop','MOaSyrJJ','苏尼特左旗',-1),('Fsn7kcco','MOaSyrJJ','苏尼特右旗',-1),('zSEPqhv0','MOaSyrJJ','太仆寺旗',-1),('nspTG9YM','MOaSyrJJ','正镶白旗',-1),('LDluKujQ','MOaSyrJJ','正蓝旗',-1),('RdxNdJHp','MOaSyrJJ','镶黄旗',-1),('nUMZXV1v','MOaSyrJJ','其他',-1),('iS8CSPm3','458SZArr','乌兰浩特市',-1),('PZ4bvlYV','458SZArr','阿尔山市',-1),('O8eyLr9n','458SZArr','突泉县',-1),('yEjN4b6f','458SZArr','扎赉特旗',-1),('0YSNsjwQ','458SZArr','科尔沁右翼前旗',-1),('rGpqY1Iv','458SZArr','科尔沁右翼中旗',-1),('d9d6XkNl','458SZArr','其他',-1),('8lEvMh5k','mfGVzUTJ','阿拉善左旗',-1),('MDAubLyi','mfGVzUTJ','阿拉善右旗',-1),('sNangM7w','mfGVzUTJ','额济纳旗',-1),('RF3MnTJI','mfGVzUTJ','其他',-1),('3WT1vEDe','LnbsZoa2','济南',0),('ffscsuQw','LnbsZoa2','青岛',0),('YxaU1iOJ','LnbsZoa2','淄博',0),('0PExWVEE','LnbsZoa2','枣庄',0),('mnifivpZ','LnbsZoa2','东营',0),('sCCfXHF1','LnbsZoa2','烟台',0),('eodYsohl','LnbsZoa2','潍坊',0),('MlDFxgAm','LnbsZoa2','济宁',0),('1gmOV1Vq','LnbsZoa2','泰安',0),('MsN4B1Lc','LnbsZoa2','威海',0),('9188wZ9I','LnbsZoa2','日照',0),('a8Xia5nz','LnbsZoa2','莱芜',0),('gQQdnx6K','LnbsZoa2','临沂',0),('srQ5kB7P','LnbsZoa2','德州',0),('C8TUaaOK','LnbsZoa2','聊城',0),('fk3LEEe6','LnbsZoa2','滨州',0),('VBJTsiM9','LnbsZoa2','菏泽',0),('AtusmNtx','LnbsZoa2','其他',-1),('x3tUNwlk','3WT1vEDe','市中区',-1),('5ln82KWT','3WT1vEDe','历下区',-1),('5luNUQ3Q','3WT1vEDe','天桥区',-1),('3M5i4Ax2','3WT1vEDe','槐荫区',-1),('rFyrpgsa','3WT1vEDe','历城区',-1),('QJ0cPwgu','3WT1vEDe','长清区',-1),('vZOAi9WD','3WT1vEDe','章丘市',-1),('eANY7QrO','3WT1vEDe','平阴县',-1),('gGYfYhZ4','3WT1vEDe','济阳县',-1),('3xKCqGUz','3WT1vEDe','商河县',-1),('tqHaHU4D','3WT1vEDe','其他',-1),('0UzQSfxK','ffscsuQw','市南区',-1),('EUcFH8u0','ffscsuQw','市北区',-1),('xXjf7FBD','ffscsuQw','城阳区',-1),('FpWTzVVN','ffscsuQw','四方区',-1),('JSGpCbXj','ffscsuQw','李沧区',-1),('nl5SRpRs','ffscsuQw','黄岛区',-1),('brMoH4Wh','ffscsuQw','崂山区',-1),('LLyXy9BZ','ffscsuQw','胶南市',-1),('UguvQ2Ll','ffscsuQw','胶州市',-1),('nPfc9f03','ffscsuQw','平度市',-1),('zdgoHCMK','ffscsuQw','莱西市',-1),('jLZCZyh5','ffscsuQw','即墨市',-1),('yG9SzBzx','ffscsuQw','其他',-1),('QEhd1ifW','YxaU1iOJ','张店区',-1),('XA1Z91cW','YxaU1iOJ','临淄区',-1),('bmqdskJX','YxaU1iOJ','淄川区',-1),('lqUO4o7k','YxaU1iOJ','博山区',-1),('HChJQ5cM','YxaU1iOJ','周村区',-1),('zrowtBlK','YxaU1iOJ','桓台县',-1),('FGcNWpe3','YxaU1iOJ','高青县',-1),('krsjwabr','YxaU1iOJ','沂源县',-1),('m7hyF9U3','YxaU1iOJ','其他',-1),('J0ow122q','0PExWVEE','市中区',-1),('iqpOIovu','0PExWVEE','山亭区',-1),('sZIRytUU','0PExWVEE','峄城区',-1),('lSZgfkQB','0PExWVEE','台儿庄区',-1),('2xImutWB','0PExWVEE','薛城区',-1),('qrYognDx','0PExWVEE','滕州市',-1),('46hamZ8l','0PExWVEE','其他',-1),('y3WgkErh','mnifivpZ','东营区',-1),('0DguCTEF','mnifivpZ','河口区',-1),('oD5Ijcnx','mnifivpZ','垦利县',-1),('bTav96G2','mnifivpZ','广饶县',-1),('aT5pdij7','mnifivpZ','利津县',-1),('hwDDsPI6','mnifivpZ','其他',-1),('ekGJPsEP','sCCfXHF1','芝罘区',-1),('f0YbPkKb','sCCfXHF1','福山区',-1),('PZ5cH0dr','sCCfXHF1','牟平区',-1),('q671NOfL','sCCfXHF1','莱山区',-1),('joAXwmdZ','sCCfXHF1','龙口市',-1),('Es7Ixce0','sCCfXHF1','莱阳市',-1),('BYrOY5Ms','sCCfXHF1','莱州市',-1),('br5Lkq71','sCCfXHF1','招远市',-1),('BFsR1JIo','sCCfXHF1','蓬莱市',-1),('veORNOEg','sCCfXHF1','栖霞市',-1),('sEK1cT7c','sCCfXHF1','海阳市',-1),('wGg5KBaS','sCCfXHF1','长岛县',-1),('YfqLeHPf','sCCfXHF1','其他',-1),('1A9AEXPH','eodYsohl','潍城区',-1),('oVG5cRTv','eodYsohl','寒亭区',-1),('vZr5BD9T','eodYsohl','坊子区',-1),('vvZjq9XF','eodYsohl','奎文区',-1),('5uMsbPJo','eodYsohl','青州市',-1),('MWTZKQVn','eodYsohl','诸城市',-1),('1eYui8U7','eodYsohl','寿光市',-1),('cqse2Cj7','eodYsohl','安丘市',-1),('tdlXQ5MA','eodYsohl','高密市',-1),('nqPyWzsu','eodYsohl','昌邑市',-1),('k17gsbqW','eodYsohl','昌乐县',-1),('VmZWswsZ','eodYsohl','临朐县',-1),('4i0AVDru','eodYsohl','其他',-1),('8F5jAf93','MlDFxgAm','市中区',-1),('KBszcvjH','MlDFxgAm','任城区',-1),('14CN41YU','MlDFxgAm','曲阜市',-1),('noFkcAEx','MlDFxgAm','兖州市',-1),('UEjpXWpw','MlDFxgAm','邹城市',-1),('sZNM1Tbg','MlDFxgAm','鱼台县',-1),('CnisJ7Ao','MlDFxgAm','金乡县',-1),('IavSy2wO','MlDFxgAm','嘉祥县',-1),('vdrX1lx7','MlDFxgAm','微山县',-1),('cP5ssLpQ','MlDFxgAm','汶上县',-1),('RstkV6tr','MlDFxgAm','泗水县',-1),('iB6R3h5A','MlDFxgAm','梁山县',-1),('J9MsLp7p','MlDFxgAm','其他',-1),('0PO70msM','1gmOV1Vq','泰山区',-1),('iSmVY2qQ','1gmOV1Vq','岱岳区',-1),('ngmASKIo','1gmOV1Vq','新泰市',-1),('LbD7rO0R','1gmOV1Vq','肥城市',-1),('F3fcxgbw','1gmOV1Vq','宁阳县',-1),('BLmkj2sN','1gmOV1Vq','东平县',-1),('jp9CloaY','1gmOV1Vq','其他',-1),('1oN8hrHl','MsN4B1Lc','环翠区',-1),('9iAwArSr','MsN4B1Lc','乳山市',-1),('wioWexxv','MsN4B1Lc','文登市',-1),('owZNKgKv','MsN4B1Lc','荣成市',-1),('Hr5fXcuh','MsN4B1Lc','其他',-1),('bO9MtYTd','9188wZ9I','东港区',-1),('a04evF9g','9188wZ9I','岚山区',-1),('t0SDDwUI','9188wZ9I','五莲县',-1),('mOTdy65D','9188wZ9I','莒县',-1),('8mbUuk9s','9188wZ9I','其他',-1),('BpnYpJVD','a8Xia5nz','莱城区',-1),('ofrqINrs','a8Xia5nz','钢城区',-1),('kWKVLuih','a8Xia5nz','其他',-1),('3vGArPDx','gQQdnx6K','兰山区',-1),('grsxk0uH','gQQdnx6K','罗庄区',-1),('YvV3miAh','gQQdnx6K','河东区',-1),('2RQZDj5K','gQQdnx6K','沂南县',-1),('9EbDFHSi','gQQdnx6K','郯城县',-1),('kXElMshx','gQQdnx6K','沂水县',-1),('nEA5y1QJ','gQQdnx6K','苍山县',-1),('JFzpJD4b','gQQdnx6K','费县',-1),('C8GacUcb','gQQdnx6K','平邑县',-1),('SLcQsOJM','gQQdnx6K','莒南县',-1),('A3Ybynmb','gQQdnx6K','蒙阴县',-1),('Fw4Li2eF','gQQdnx6K','临沭县',-1),('RYZfA4Wy','gQQdnx6K','其他',-1),('L2DPpWR6','srQ5kB7P','德城区',-1),('oKhVsD8K','srQ5kB7P','乐陵市',-1),('MCjAYVd7','srQ5kB7P','禹城市',-1),('eIufLKcp','srQ5kB7P','陵县',-1),('9v3ccTAf','srQ5kB7P','宁津县',-1),('Lz6yWf54','srQ5kB7P','齐河县',-1),('vroclAnc','srQ5kB7P','武城县',-1),('bd0aHEfw','srQ5kB7P','庆云县',-1),('ck1PsNfv','srQ5kB7P','平原县',-1),('ID7Dslrw','srQ5kB7P','夏津县',-1),('ZnmR57E8','srQ5kB7P','临邑县',-1),('GWFn364J','srQ5kB7P','其他',-1),('wW4XTwm7','C8TUaaOK','东昌府区',-1),('J9VdV0gq','C8TUaaOK','临清市',-1),('uLFl9z6V','C8TUaaOK','高唐县',-1),('sBDDtQkg','C8TUaaOK','阳谷县',-1),('XOzSwmwJ','C8TUaaOK','茌平县',-1),('9BUYcG35','C8TUaaOK','莘县',-1),('SsMBBFQr','C8TUaaOK','东阿县',-1),('0P3muxf1','C8TUaaOK','冠县',-1),('iWNVX7bo','C8TUaaOK','其他',-1),('ltFDQDHf','fk3LEEe6','滨城区',-1),('4QiNC5jR','fk3LEEe6','邹平县',-1),('nRSfsTJ7','fk3LEEe6','沾化县',-1),('VyPCxukf','fk3LEEe6','惠民县',-1),('zZH4VvJ3','fk3LEEe6','博兴县',-1),('JdWiHpDx','fk3LEEe6','阳信县',-1),('TTlEzOil','fk3LEEe6','无棣县',-1),('ruOJ5sab','fk3LEEe6','其他',-1),('ni8akrZk','VBJTsiM9','牡丹区',-1),('T97dg5KU','VBJTsiM9','鄄城县',-1),('iRV7O7Sa','VBJTsiM9','单县',-1),('DQPbPuuV','VBJTsiM9','郓城县',-1),('sQtHqiad','VBJTsiM9','曹县',-1),('yZLXZbT4','VBJTsiM9','定陶县',-1),('OoZzeqcH','VBJTsiM9','巨野县',-1),('lOysWQms','VBJTsiM9','东明县',-1),('1FnJLrkS','VBJTsiM9','成武县',-1),('vWfTHjrJ','VBJTsiM9','其他',-1),('ceZOgs55','YyXdlTgE','合肥',0),('cLKMY4MZ','YyXdlTgE','芜湖',0),('oLYUzOXF','YyXdlTgE','蚌埠',0),('bSIAQZNg','YyXdlTgE','淮南',0),('jxkXZCT4','YyXdlTgE','马鞍山',0),('KIkOR9jg','YyXdlTgE','淮北',0),('sX3Aeekr','YyXdlTgE','铜陵',0),('ePttBfPm','YyXdlTgE','安庆',0),('2lKca46d','YyXdlTgE','黄山',0),('cuvLXbfK','YyXdlTgE','滁州',0),('Bi9BhZ55','YyXdlTgE','阜阳',0),('Ebp39nAS','YyXdlTgE','宿州',0),('SS14kLM8','YyXdlTgE','巢湖',0),('EBTwWa6t','YyXdlTgE','六安',0),('38fUiJqj','YyXdlTgE','亳州',0),('ZCSKoTVp','YyXdlTgE','池州',0),('mMVXsJKi','YyXdlTgE','宣城',0),('pFFZRdqn','YyXdlTgE','其他',-1),('kGdhIWfW','ceZOgs55','庐阳区',-1),('dGLLTu17','ceZOgs55','瑶海区',-1),('cV7YAAA1','ceZOgs55','蜀山区',-1),('Udq4DuQq','ceZOgs55','包河区',-1),('4Z7G6ZC3','ceZOgs55','长丰县',-1),('t0rGKOmm','ceZOgs55','肥东县',-1),('n0eDdKr8','ceZOgs55','肥西县',-1),('SxGSLBkO','ceZOgs55','其他',-1),('314ysJBD','cLKMY4MZ','镜湖区',-1),('lt6oM7wh','cLKMY4MZ','弋江区',-1),('eUzuwq6w','cLKMY4MZ','鸠江区',-1),('bwlsHsJ8','cLKMY4MZ','三山区',-1),('toqfDFjF','cLKMY4MZ','芜湖县',-1),('cES9qC1y','cLKMY4MZ','南陵县',-1),('nsIpnjsT','cLKMY4MZ','繁昌县',-1),('PMtbqMu5','cLKMY4MZ','其他',-1),('PlxkEyBZ','oLYUzOXF','蚌山区',-1),('l6BEWwuj','oLYUzOXF','龙子湖区',-1),('z7QAZ5DA','oLYUzOXF','禹会区',-1),('XgHBsak6','oLYUzOXF','淮上区',-1),('3qSPTkhc','oLYUzOXF','怀远县',-1),('tPji6AH4','oLYUzOXF','固镇县',-1),('YvX98pIv','oLYUzOXF','五河县',-1),('Em4UlXTh','oLYUzOXF','其他',-1),('pYQVL0lP','bSIAQZNg','田家庵区',-1),('1ifDEBMM','bSIAQZNg','大通区',-1),('2aK7hk7f','bSIAQZNg','谢家集区',-1),('rG03NGHP','bSIAQZNg','八公山区',-1),('ahRsSbQ6','bSIAQZNg','潘集区',-1),('qkkTKL6y','bSIAQZNg','凤台县',-1),('wG3I9ZKT','bSIAQZNg','其他',-1),('wshTk2ps','jxkXZCT4','雨山区',-1),('W90gy2JZ','jxkXZCT4','花山区',-1),('KctSEgMy','jxkXZCT4','金家庄区',-1),('Zys3yrON','jxkXZCT4','当涂县',-1),('zaCRBGGK','jxkXZCT4','其他',-1),('YrGSdmFD','KIkOR9jg','相山区',-1),('EgMl8nK8','KIkOR9jg','杜集区',-1),('asyIKeDd','KIkOR9jg','烈山区',-1),('3bMcBEqH','KIkOR9jg','濉溪县',-1),('wu4rGPK6','KIkOR9jg','其他',-1),('fhMNSiQs','sX3Aeekr','铜官山区',-1),('J7nDhhnT','sX3Aeekr','狮子山区',-1),('cz2xGfsd','sX3Aeekr','郊区',-1),('lG3TAlSV','sX3Aeekr','铜陵县',-1),('kkiOxGqK','sX3Aeekr','其他',-1),('zWj8yW9d','ePttBfPm','迎江区',-1),('bmkMPZor','ePttBfPm','大观区',-1),('U5NNypwh','ePttBfPm','宜秀区',-1),('iZF03bDR','ePttBfPm','桐城市',-1),('qNkZnZkO','ePttBfPm','宿松县',-1),('b7lKbWf1','ePttBfPm','枞阳县',-1),('u3Mcz43w','ePttBfPm','太湖县',-1),('TWSqpM85','ePttBfPm','怀宁县',-1),('y5K94NkL','ePttBfPm','岳西县',-1),('3Wdi7fh9','ePttBfPm','望江县',-1),('yEjeiQwl','ePttBfPm','潜山县',-1),('wlbLxWDB','ePttBfPm','其他',-1),('FHdNXpFE','2lKca46d','屯溪区',-1),('F8eXTZs2','2lKca46d','黄山区',-1),('fCfspq4f','2lKca46d','徽州区',-1),('WJ2KWr7g','2lKca46d','休宁县',-1),('cJNtRwIq','2lKca46d','歙县',-1),('M4Cro6hk','2lKca46d','祁门县',-1),('E1vUNtZf','2lKca46d','黟县',-1),('gwjcfHi9','2lKca46d','其他',-1),('ku0HL3sV','cuvLXbfK','琅琊区',-1),('4VuL9rpY','cuvLXbfK','南谯区',-1),('LwQJxMyx','cuvLXbfK','天长市',-1),('aaZ9E73o','cuvLXbfK','明光市',-1),('MOIQUwp4','cuvLXbfK','全椒县',-1),('A1jYgsT4','cuvLXbfK','来安县',-1),('mFES9zqN','cuvLXbfK','定远县',-1),('7qKDKbBA','cuvLXbfK','凤阳县',-1),('8wsuOXuX','cuvLXbfK','其他',-1),('K54TAC7k','Bi9BhZ55','颍州区',-1),('qt2MhtoJ','Bi9BhZ55','颍东区',-1),('VyrxCPqS','Bi9BhZ55','颍泉区',-1),('ZdhSis6H','Bi9BhZ55','界首市',-1),('yVexCsjt','Bi9BhZ55','临泉县',-1),('qXG8v5xI','Bi9BhZ55','颍上县',-1),('8hmgMO4W','Bi9BhZ55','阜南县',-1),('aa1DQDMY','Bi9BhZ55','太和县',-1),('HSgNNXsC','Bi9BhZ55','其他',-1),('UfxCwcJI','Ebp39nAS','埇桥区',-1),('odJJwsmN','Ebp39nAS','萧县',-1),('BT2lU7cT','Ebp39nAS','泗县',-1),('8rec5aHB','Ebp39nAS','砀山县',-1),('2a1G07zQ','Ebp39nAS','灵璧县',-1),('pWp6hb4r','Ebp39nAS','其他',-1),('psECceJ6','SS14kLM8','居巢区',-1),('90ziQD0m','SS14kLM8','含山县',-1),('3an9sdk0','SS14kLM8','无为县',-1),('tu0QFvKB','SS14kLM8','庐江县',-1),('WrnxioL3','SS14kLM8','和县',-1),('LBu8oVxs','SS14kLM8','其他',-1),('0Ya7sANH','EBTwWa6t','金安区',-1),('9SPbzybE','EBTwWa6t','裕安区',-1),('2mckLcjt','EBTwWa6t','寿县',-1),('kLA4Kl6l','EBTwWa6t','霍山县',-1),('7gkiC7sL','EBTwWa6t','霍邱县',-1),('JrlLaFYd','EBTwWa6t','舒城县',-1),('gg3Da2Yb','EBTwWa6t','金寨县',-1),('9wV8swUJ','EBTwWa6t','其他',-1),('wk5g5AaV','38fUiJqj','谯城区',-1),('CSgL2t5w','38fUiJqj','利辛县',-1),('b3kzqNDS','38fUiJqj','涡阳县',-1),('sx5h3JkT','38fUiJqj','蒙城县',-1),('Yh9dXZty','38fUiJqj','其他',-1),('fV6vAURP','ZCSKoTVp','贵池区',-1),('ZBhQkAgW','ZCSKoTVp','东至县',-1),('dhDNvSop','ZCSKoTVp','石台县',-1),('OXrB8Ksf','ZCSKoTVp','青阳县',-1),('Ab8Rs0bO','ZCSKoTVp','其他',-1),('eBse3iwR','mMVXsJKi','宣州区',-1),('k3Kt59eo','mMVXsJKi','宁国市',-1),('pQD8knDg','mMVXsJKi','广德县',-1),('7NShJTvZ','mMVXsJKi','郎溪县',-1),('tQiqSTbE','mMVXsJKi','泾县',-1),('56nOJXKE','mMVXsJKi','旌德县',-1),('89PCpeZa','mMVXsJKi','绩溪县',-1),('flZa2dH6','mMVXsJKi','其他',-1),('DsKts358','zL77rzsP','杭州',0),('PdejvOHS','zL77rzsP','宁波',0),('yopbl2y9','zL77rzsP','温州',0),('2Q8sQiy6','zL77rzsP','嘉兴',0),('biIe8OFg','zL77rzsP','湖州',0),('pqw7GGDK','zL77rzsP','绍兴',0),('hC9xahJS','zL77rzsP','金华',0),('9YSTdN5w','zL77rzsP','衢州',0),('DuHsNtrF','zL77rzsP','舟山',0),('A9nKlNxA','zL77rzsP','台州',0),('jCMtQeSn','zL77rzsP','丽水',0),('M0WsGZR7','zL77rzsP','其他',-1),('U0kCQFRQ','DsKts358','拱墅区',-1),('X4SgR1UX','DsKts358','西湖区',-1),('nMmbRLo3','DsKts358','上城区',-1),('PvEPA2bH','DsKts358','下城区',-1),('LKn7vKsL','DsKts358','江干区',-1),('QLEVL8O4','DsKts358','滨江区',-1),('7svlv6zw','DsKts358','余杭区',-1),('Mqnxt97a','DsKts358','萧山区',-1),('J0Md7erB','DsKts358','建德市',-1),('vPDnAd6k','DsKts358','富阳市',-1),('kbJJlNWC','DsKts358','临安市',-1),('cilYoRtM','DsKts358','桐庐县',-1),('kfDQPwBi','DsKts358','淳安县',-1),('O2p1WKxc','DsKts358','其他',-1),('qXt54sSg','PdejvOHS','海曙区',-1),('GrzyfZTs','PdejvOHS','江东区',-1),('kDZu6ePT','PdejvOHS','江北区',-1),('UJ5RjEPJ','PdejvOHS','镇海区',-1),('pijbeRbD','PdejvOHS','北仑区',-1),('Pt97cGF9','PdejvOHS','鄞州区',-1),('Ggr46EZz','PdejvOHS','余姚市',-1),('C1dHlwAw','PdejvOHS','慈溪市',-1),('o9QiNizS','PdejvOHS','奉化市',-1),('pMqVqq4q','PdejvOHS','宁海县',-1),('mv2I7azS','PdejvOHS','象山县',-1),('08JGmJAi','PdejvOHS','其他',-1),('ifh025I6','yopbl2y9','鹿城区',-1),('vNzhvTv7','yopbl2y9','龙湾区',-1),('1mKTorvG','yopbl2y9','瓯海区',-1),('002wrnF1','yopbl2y9','瑞安市',-1),('rCAih89m','yopbl2y9','乐清市',-1),('Jg6VKnnG','yopbl2y9','永嘉县',-1),('v5NeU7oE','yopbl2y9','洞头县',-1),('iBzz71xH','yopbl2y9','平阳县',-1),('mtREs6aw','yopbl2y9','苍南县',-1),('0fXSBFCf','yopbl2y9','文成县',-1),('TKPlrLEW','yopbl2y9','泰顺县',-1),('gcmAEyWz','yopbl2y9','其他',-1),('QCOBGjpv','2Q8sQiy6','秀城区',-1),('tfKsnN4N','2Q8sQiy6','秀洲区',-1),('2DCz7fnQ','2Q8sQiy6','海宁市',-1),('bC9axnhs','2Q8sQiy6','平湖市',-1),('yQxhTFaj','2Q8sQiy6','桐乡市',-1),('PEKTUbOf','2Q8sQiy6','嘉善县',-1),('Wvf9BxbQ','2Q8sQiy6','海盐县',-1),('OlJD2R8S','2Q8sQiy6','其他',-1),('JRJesWm2','biIe8OFg','吴兴区',-1),('RVxG8Ugq','biIe8OFg','南浔区',-1),('fHLChPtj','biIe8OFg','长兴县',-1),('mB9fTPJs','biIe8OFg','德清县',-1),('v01tegny','biIe8OFg','安吉县',-1),('j8fJUSQE','biIe8OFg','其他',-1),('vIDvdHjD','pqw7GGDK','越城区',-1),('MVd04tJw','pqw7GGDK','诸暨市',-1),('MKvoprf7','pqw7GGDK','上虞市',-1),('Qe1ose3Y','pqw7GGDK','嵊州市',-1),('8ZfuvB3o','pqw7GGDK','绍兴县',-1),('1GgHFkPJ','pqw7GGDK','新昌县',-1),('nN1hTmzu','pqw7GGDK','其他',-1),('Adsfg6Os','hC9xahJS','婺城区',-1),('nsDWFAae','hC9xahJS','金东区',-1),('WtztTgoO','hC9xahJS','兰溪市',-1),('Snd5b4RR','hC9xahJS','义乌市',-1),('YxSKOoJl','hC9xahJS','东阳市',-1),('rZp7PyOa','hC9xahJS','永康市',-1),('FUOS5HkA','hC9xahJS','武义县',-1),('9iH2IHDk','hC9xahJS','浦江县',-1),('XVl3Hnow','hC9xahJS','磐安县',-1),('r4suR2Rs','hC9xahJS','其他',-1),('F473jaw1','9YSTdN5w','柯城区',-1),('ODoTer3A','9YSTdN5w','衢江区',-1),('B91VG8tD','9YSTdN5w','江山市',-1),('4IfuW54u','9YSTdN5w','龙游县',-1),('7Vrk1bAe','9YSTdN5w','常山县',-1),('xl6OE4Bt','9YSTdN5w','开化县',-1),('fUBsytHA','9YSTdN5w','其他',-1),('KR6bjYvI','DuHsNtrF','定海区',-1),('FPbXFd0h','DuHsNtrF','普陀区',-1),('oy5s3wKZ','DuHsNtrF','岱山县',-1),('92VUjxjg','DuHsNtrF','嵊泗县',-1),('2TlCpJU2','DuHsNtrF','其他',-1),('jnZAlOjP','A9nKlNxA','椒江区',-1),('0QUxbcOm','A9nKlNxA','黄岩区',-1),('EHHsUVt2','A9nKlNxA','路桥区',-1),('vvXvPDsp','A9nKlNxA','临海市',-1),('MoY7osH8','A9nKlNxA','温岭市',-1),('1hzdisol','A9nKlNxA','玉环县',-1),('QprsqFQq','A9nKlNxA','天台县',-1),('Mgrq0j4d','A9nKlNxA','仙居县',-1),('qW5wHJl8','A9nKlNxA','三门县',-1),('RVtU07vU','A9nKlNxA','其他',-1),('AkVZ4DIs','jCMtQeSn','莲都区',-1),('nTiAskxf','jCMtQeSn','龙泉市',-1),('qq8k3xR6','jCMtQeSn','缙云县',-1),('FhbEyyiM','jCMtQeSn','青田县',-1),('PRPOVNR2','jCMtQeSn','云和县',-1),('I9sIdc2N','jCMtQeSn','遂昌县',-1),('hx8J9e9l','jCMtQeSn','松阳县',-1),('lyVJmaxs','jCMtQeSn','庆元县',-1),('6Wm6rybV','jCMtQeSn','景宁畲族自治县',-1),('8PzznroS','jCMtQeSn','其他',-1),('z03whyxG','X9l6qcyK','福州',0),('RsZPXjuT','X9l6qcyK','厦门',0),('STssrpRn','X9l6qcyK','莆田',0),('rbDorpXI','X9l6qcyK','三明',0),('Yjh5lqum','X9l6qcyK','泉州',0),('evIoR2Cy','X9l6qcyK','漳州',0),('LD6lsshL','X9l6qcyK','南平',0),('jBBzn5wi','X9l6qcyK','龙岩',0),('IWssYzWJ','X9l6qcyK','宁德',0),('kF0Q0col','X9l6qcyK','其他',-1),('NsMQcIhg','z03whyxG','鼓楼区',-1),('XDCKRlbj','z03whyxG','台江区',-1),('gN96CCtW','z03whyxG','仓山区',-1),('FJsYr3B8','z03whyxG','马尾区',-1),('u7Nmt1I7','z03whyxG','晋安区',-1),('iZ8suzbr','z03whyxG','福清市',-1),('soMiEbfz','z03whyxG','长乐市',-1),('v4QaZdCf','z03whyxG','闽侯县',-1),('IWJMS1Wi','z03whyxG','闽清县',-1),('EM3aziik','z03whyxG','永泰县',-1),('o8wRyLag','z03whyxG','连江县',-1),('6qp0RcVe','z03whyxG','罗源县',-1),('oxsgS9Cw','z03whyxG','平潭县',-1),('wcYc7mSp','z03whyxG','其他',-1),('LqfbFzNO','RsZPXjuT','思明区',-1),('s3OQHbtD','RsZPXjuT','海沧区',-1),('yjtn5YAz','RsZPXjuT','湖里区',-1),('u7vF8j2m','RsZPXjuT','集美区',-1),('5YT1RSoO','RsZPXjuT','同安区',-1),('nKwrBck2','RsZPXjuT','翔安区',-1),('Yyv1qD81','RsZPXjuT','其他',-1),('4ixmucSt','STssrpRn','城厢区',-1),('JYv1Wb0n','STssrpRn','涵江区',-1),('xXyTyrEG','STssrpRn','荔城区',-1),('U6rbdVXH','STssrpRn','秀屿区',-1),('y7fFWr2i','STssrpRn','仙游县',-1),('a54xcPcK','STssrpRn','其他',-1),('Wxssa9hn','rbDorpXI','梅列区',-1),('uSI2sis4','rbDorpXI','三元区',-1),('7pKsZvap','rbDorpXI','永安市',-1),('tFkpwZY5','rbDorpXI','明溪县',-1),('9Y6yEFId','rbDorpXI','将乐县',-1),('el5Y6cKK','rbDorpXI','大田县',-1),('vTYsxAV5','rbDorpXI','宁化县',-1),('sYhUn9SK','rbDorpXI','建宁县',-1),('DAclmf91','rbDorpXI','沙县',-1),('l3KeSky7','rbDorpXI','尤溪县',-1),('xO4W5i9j','rbDorpXI','清流县',-1),('7EvCQLIx','rbDorpXI','泰宁县',-1),('6X4dVd01','rbDorpXI','其他',-1),('mt3P9LsW','Yjh5lqum','鲤城区',-1),('bPsOvmHa','Yjh5lqum','丰泽区',-1),('8BqX2gXM','Yjh5lqum','洛江区',-1),('U0MgmtQg','Yjh5lqum','泉港区',-1),('OUEKBKtw','Yjh5lqum','石狮市',-1),('yksoPJmT','Yjh5lqum','晋江市',-1),('FP8H7BP2','Yjh5lqum','南安市',-1),('HYrbBqp6','Yjh5lqum','惠安县',-1),('OzcmmZ9Q','Yjh5lqum','永春县',-1),('RSDZneJa','Yjh5lqum','安溪县',-1),('dQIZLSkk','Yjh5lqum','德化县',-1),('kXbMw4q7','Yjh5lqum','金门县',-1),('PwEb4gcE','Yjh5lqum','其他',-1),('fOVg2LLj','evIoR2Cy','芗城区',-1),('iaU2Zop5','evIoR2Cy','龙文区',-1),('RlMSegt2','evIoR2Cy','龙海市',-1),('u1TIs9mx','evIoR2Cy','平和县',-1),('Pbu3ypMb','evIoR2Cy','南靖县',-1),('lsVm2KsQ','evIoR2Cy','诏安县',-1),('PQrhUr62','evIoR2Cy','漳浦县',-1),('Q65fkYs8','evIoR2Cy','华安县',-1),('Q4x8yqB5','evIoR2Cy','东山县',-1),('i3hPxtXt','evIoR2Cy','长泰县',-1),('1UIGn4Vm','evIoR2Cy','云霄县',-1),('WXimpKeD','evIoR2Cy','其他',-1),('uNPK3GZK','LD6lsshL','延平区',-1),('dJwCA0Mr','LD6lsshL','建瓯市',-1),('NyT0TIDO','LD6lsshL','邵武市',-1),('6Ts3KyFy','LD6lsshL','武夷山市',-1),('ZWP4WXXj','LD6lsshL','建阳市',-1),('EaJ1xXT0','LD6lsshL','松溪县',-1),('tVHTxdBb','LD6lsshL','光泽县',-1),('e44bw1Xs','LD6lsshL','顺昌县',-1),('dglo2ind','LD6lsshL','浦城县',-1),('TqbWaucE','LD6lsshL','政和县',-1),('Vmusq0F6','LD6lsshL','其他',-1),('cjdY2xo8','jBBzn5wi','新罗区',-1),('CJszrDR5','jBBzn5wi','漳平市',-1),('zkKh3cUU','jBBzn5wi','长汀县',-1),('tFNH7ETk','jBBzn5wi','武平县',-1),('SpFjZiDH','jBBzn5wi','上杭县',-1),('NHjgGuhs','jBBzn5wi','永定县',-1),('FFse0KTv','jBBzn5wi','连城县',-1),('rRK5l4jZ','jBBzn5wi','其他',-1),('s7KaG1tO','IWssYzWJ','蕉城区',-1),('lKhBFom8','IWssYzWJ','福安市',-1),('NgMna4xU','IWssYzWJ','福鼎市',-1),('kSTuODrN','IWssYzWJ','寿宁县',-1),('PIHRaQON','IWssYzWJ','霞浦县',-1),('cf7hhM1e','IWssYzWJ','柘荣县',-1),('pH08kfMY','IWssYzWJ','屏南县',-1),('11jMovs0','IWssYzWJ','古田县',-1),('8wPgbkSO','IWssYzWJ','周宁县',-1),('yA62AYfm','IWssYzWJ','其他',-1),('RZHvYsNF','GGJiahRO','长沙',0),('qo2XfmyX','GGJiahRO','株洲',0),('C2cwbJRM','GGJiahRO','湘潭',0),('FSWWRJQM','GGJiahRO','衡阳',0),('YG5kF3ar','GGJiahRO','邵阳',0),('gZfmTenF','GGJiahRO','岳阳',0),('0Pvm8m1i','GGJiahRO','常德',0),('58WZWdos','GGJiahRO','张家界',0),('HWLxuTJa','GGJiahRO','益阳',0),('F3klLIzB','GGJiahRO','郴州',0),('kr5cAAkg','GGJiahRO','永州',0),('n5yvls1s','GGJiahRO','怀化',0),('OmBZfVSw','GGJiahRO','娄底',0),('79hI88hK','GGJiahRO','湘西土家族苗族自治州',0),('e1lwS3NO','GGJiahRO','其他',-1),('O6uO9bAa','RZHvYsNF','岳麓区',-1),('EYNWV5Kz','RZHvYsNF','芙蓉区',-1),('B5kyMZb9','RZHvYsNF','天心区',-1),('nwS5LpvI','RZHvYsNF','开福区',-1),('tPAjGfUp','RZHvYsNF','雨花区',-1),('krzHYeVU','RZHvYsNF','浏阳市',-1),('b1R1j13l','RZHvYsNF','长沙县',-1),('3TOFUbGx','RZHvYsNF','望城县',-1),('nRKMgwJO','RZHvYsNF','宁乡县',-1),('ckdnkGlR','RZHvYsNF','其他',-1),('WmavxhTM','qo2XfmyX','天元区',-1),('zmZWZN9g','qo2XfmyX','荷塘区',-1),('RJiMbapq','qo2XfmyX','芦淞区',-1),('3irJZxFt','qo2XfmyX','石峰区',-1),('UnwLs30Z','qo2XfmyX','醴陵市',-1),('LyVNYvJn','qo2XfmyX','株洲县',-1),('VGsUq1nd','qo2XfmyX','炎陵县',-1),('KiVu0XPp','qo2XfmyX','茶陵县',-1),('chFcaOuS','qo2XfmyX','攸县',-1),('VOgr8NIQ','qo2XfmyX','其他',-1),('3xjqXoxq','C2cwbJRM','岳塘区',-1),('4htI9OoK','C2cwbJRM','雨湖区',-1),('BJqwUTsc','C2cwbJRM','湘乡市',-1),('adP4Daus','C2cwbJRM','韶山市',-1),('E19IyBz3','C2cwbJRM','湘潭县',-1),('QRw7kJvM','C2cwbJRM','其他',-1),('PbTOEsq9','FSWWRJQM','雁峰区',-1),('HbuNEmUo','FSWWRJQM','珠晖区',-1),('H6iZApm6','FSWWRJQM','石鼓区',-1),('jy8xYm6Z','FSWWRJQM','蒸湘区',-1),('PGLB8UI5','FSWWRJQM','南岳区',-1),('5FJCgtNm','FSWWRJQM','耒阳市',-1),('3zhf7l5Z','FSWWRJQM','常宁市',-1),('5iJRQomN','FSWWRJQM','衡阳县',-1),('YJ5atgBI','FSWWRJQM','衡东县',-1),('LM7URAxL','FSWWRJQM','衡山县',-1),('4y9mBhEP','FSWWRJQM','衡南县',-1),('SiM3aRNS','FSWWRJQM','祁东县',-1),('scFUJUes','FSWWRJQM','其他',-1),('ZAIxo9pN','YG5kF3ar','双清区',-1),('vXTsp8Ok','YG5kF3ar','大祥区',-1),('hP7SaY1M','YG5kF3ar','北塔区',-1),('ZAnNgfUs','YG5kF3ar','武冈市',-1),('KTrNE0nG','YG5kF3ar','邵东县',-1),('tKnaG3CC','YG5kF3ar','洞口县',-1),('8f5hSWPl','YG5kF3ar','新邵县',-1),('xUIb8hhJ','YG5kF3ar','绥宁县',-1),('9DKu16U2','YG5kF3ar','新宁县',-1),('820fhPTa','YG5kF3ar','邵阳县',-1),('i3tElza9','YG5kF3ar','隆回县',-1),('sJQj2eYE','YG5kF3ar','城步苗族自治县',-1),('NEterEY8','YG5kF3ar','其他',-1),('6IWlappE','gZfmTenF','岳阳楼区',-1),('OdzkFvqt','gZfmTenF','云溪区',-1),('gs9qdL0W','gZfmTenF','君山区',-1),('DRHoMEiO','gZfmTenF','临湘市',-1),('c3vaBdt3','gZfmTenF','汨罗市',-1),('A8ibl6Ug','gZfmTenF','岳阳县',-1),('MAJtA25e','gZfmTenF','湘阴县',-1),('KuMplP6t','gZfmTenF','平江县',-1),('ETSDOMvT','gZfmTenF','华容县',-1),('RwV1yRQZ','gZfmTenF','其他',-1),('ENrIf80s','0Pvm8m1i','武陵区',-1),('fzVCsNfE','0Pvm8m1i','鼎城区',-1),('auExoyOE','0Pvm8m1i','津市市',-1),('iWPv0qqd','0Pvm8m1i','澧县',-1),('HSBDhjYY','0Pvm8m1i','临澧县',-1),('jQwFPOPg','0Pvm8m1i','桃源县',-1),('8XcZh4z2','0Pvm8m1i','汉寿县',-1),('yh87WTrg','0Pvm8m1i','安乡县',-1),('sb7mB7W6','0Pvm8m1i','石门县',-1),('bch8FXGi','0Pvm8m1i','其他',-1),('jUWkOUSd','58WZWdos','永定区',-1),('vCybqHJ7','58WZWdos','武陵源区',-1),('BeagrrzK','58WZWdos','慈利县',-1),('Np3bIfrl','58WZWdos','桑植县',-1),('e1ZGJVDs','58WZWdos','其他',-1),('9aP6wCv2','HWLxuTJa','赫山区',-1),('JV4YxZuK','HWLxuTJa','资阳区',-1),('WzKDgd40','HWLxuTJa','沅江市',-1),('9tsYTOEw','HWLxuTJa','桃江县',-1),('zDSdrBzv','HWLxuTJa','南县',-1),('ElMOvOKi','HWLxuTJa','安化县',-1),('uk7DXSg3','HWLxuTJa','其他',-1),('vI8sF8z5','F3klLIzB','北湖区',-1),('bj4IfFUJ','F3klLIzB','苏仙区',-1),('adhpJ7TO','F3klLIzB','资兴市',-1),('PWDJLVs8','F3klLIzB','宜章县',-1),('t7lD7svT','F3klLIzB','汝城县',-1),('tHB2odu1','F3klLIzB','安仁县',-1),('rOyAlaUC','F3klLIzB','嘉禾县',-1),('kCehwmn5','F3klLIzB','临武县',-1),('PFsugyKw','F3klLIzB','桂东县',-1),('BuBJi6Qg','F3klLIzB','永兴县',-1),('EVzxJLMN','F3klLIzB','桂阳县',-1),('gDXg1kaI','F3klLIzB','其他',-1),('yiYlKEW8','kr5cAAkg','冷水滩区',-1),('z7eHyiAC','kr5cAAkg','零陵区',-1),('BPBXsKRl','kr5cAAkg','祁阳县',-1),('bf4k9B0Q','kr5cAAkg','蓝山县',-1),('Ufc4UYxl','kr5cAAkg','宁远县',-1),('lWHZyQdU','kr5cAAkg','新田县',-1),('Xlv3S0Tm','kr5cAAkg','东安县',-1),('puVCgu17','kr5cAAkg','江永县',-1),('pj66poTa','kr5cAAkg','道县',-1),('mTnB0vuc','kr5cAAkg','双牌县',-1),('ekhQ1tYv','kr5cAAkg','江华瑶族自治县',-1),('vQWqSSGu','kr5cAAkg','其他',-1),('qLvPyNyz','n5yvls1s','鹤城区',-1),('fUECyfpx','n5yvls1s','洪江市',-1),('rYoWEvAm','n5yvls1s','会同县',-1),('dMZ1uFxx','n5yvls1s','沅陵县',-1),('X6OhBxSs','n5yvls1s','辰溪县',-1),('9cewDZ5q','n5yvls1s','溆浦县',-1),('mDVwGtC8','n5yvls1s','中方县',-1),('k0R10IAJ','n5yvls1s','新晃侗族自治县',-1),('sIb2xlYT','n5yvls1s','芷江侗族自治县',-1),('hZaV0esA','n5yvls1s','通道侗族自治县',-1),('iztdth9T','n5yvls1s','靖州苗族侗族自治县',-1),('wUL3qN5R','n5yvls1s','麻阳苗族自治县',-1),('Ydem94vX','n5yvls1s','其他',-1),('taneahgy','OmBZfVSw','娄星区',-1),('MPFoBxM9','OmBZfVSw','冷水江市',-1),('nitHItfC','OmBZfVSw','涟源市',-1),('4eFAUv5U','OmBZfVSw','新化县',-1),('2QC8woEq','OmBZfVSw','双峰县',-1),('YW3eJ0HN','OmBZfVSw','其他',-1),('ZBFmsMYn','79hI88hK','吉首市',-1),('JNq2qr8N','79hI88hK','古丈县',-1),('uRQzSAQu','79hI88hK','龙山县',-1),('9ViyYcyz','79hI88hK','永顺县',-1),('PjuMVR0e','79hI88hK','凤凰县',-1),('882THX89','79hI88hK','泸溪县',-1),('5T19moaH','79hI88hK','保靖县',-1),('1VIOYAmX','79hI88hK','花垣县',-1),('K1qTAT1k','79hI88hK','其他',-1),('FxOhKBD8','Jq6nbkUo','南宁',0),('MJUVaLZJ','Jq6nbkUo','柳州',0),('NOVx9sFw','Jq6nbkUo','桂林',0),('9zyLn5BA','Jq6nbkUo','梧州',0),('5imirHyV','Jq6nbkUo','北海',0),('41KzPGYY','Jq6nbkUo','防城港',0),('K33eSvPu','Jq6nbkUo','钦州',0),('feayprPJ','Jq6nbkUo','贵港',0),('PdmAbHIC','Jq6nbkUo','玉林',0),('Gg8tmgSa','Jq6nbkUo','百色',0),('nmfNfoim','Jq6nbkUo','贺州',0),('mXL8sWzT','Jq6nbkUo','河池',0),('Gg9NakPd','Jq6nbkUo','来宾',0),('rtwLnSSz','Jq6nbkUo','崇左',0),('eTbr8F7b','Jq6nbkUo','其他',-1),('SY7pziv6','FxOhKBD8','青秀区',-1),('4XUFsS9J','FxOhKBD8','兴宁区',-1),('eRhzMaMN','FxOhKBD8','西乡塘区',-1),('B2YiwXc8','FxOhKBD8','良庆区',-1),('xSrTB5uq','FxOhKBD8','江南区',-1),('oVHb9EXI','FxOhKBD8','邕宁区',-1),('6Bm9DJcF','FxOhKBD8','武鸣县',-1),('lKSEneA7','FxOhKBD8','隆安县',-1),('xOi7sSzZ','FxOhKBD8','马山县',-1),('t4LBckW2','FxOhKBD8','上林县',-1),('5dqRHfrq','FxOhKBD8','宾阳县',-1),('qhjrXNUG','FxOhKBD8','横县',-1),('9kAKWCwv','FxOhKBD8','其他',-1),('NKOhshIb','MJUVaLZJ','城中区',-1),('LFxO0LfY','MJUVaLZJ','鱼峰区',-1),('gkBshsnt','MJUVaLZJ','柳北区',-1),('K9l3u4tD','MJUVaLZJ','柳南区',-1),('AngvndNZ','MJUVaLZJ','柳江县',-1),('q02LTPfH','MJUVaLZJ','柳城县',-1),('TUweNG0x','MJUVaLZJ','鹿寨县',-1),('sSZAx9Zu','MJUVaLZJ','融安县',-1),('xG1HiYcp','MJUVaLZJ','融水苗族自治县',-1),('7u9IzZN5','MJUVaLZJ','三江侗族自治县',-1),('UBefecWw','MJUVaLZJ','其他',-1),('pQ7i4J0l','NOVx9sFw','象山区',-1),('WP7dUV0h','NOVx9sFw','秀峰区',-1),('navw0qL2','NOVx9sFw','叠彩区',-1),('kYH9096h','NOVx9sFw','七星区',-1),('J0HXAz4n','NOVx9sFw','雁山区',-1),('C7xxOIUM','NOVx9sFw','阳朔县',-1),('xVEPrOJe','NOVx9sFw','临桂县',-1),('NO4IKy2i','NOVx9sFw','灵川县',-1),('0t8hEF3e','NOVx9sFw','全州县',-1),('Y9Bpx6Fq','NOVx9sFw','平乐县',-1),('2dsVLp4l','NOVx9sFw','兴安县',-1),('c1KMY2p6','NOVx9sFw','灌阳县',-1),('4ELHkrpO','NOVx9sFw','荔浦县',-1),('TLkx90O1','NOVx9sFw','资源县',-1),('pX85M2O7','NOVx9sFw','永福县',-1),('lM5MGv1n','NOVx9sFw','龙胜各族自治县',-1),('1vDUQ8ZQ','NOVx9sFw','恭城瑶族自治县',-1),('Soka3Q9w','NOVx9sFw','其他',-1),('V1O1bN1I','9zyLn5BA','万秀区',-1),('Tm6ruesU','9zyLn5BA','蝶山区',-1),('Bv1EsGjz','9zyLn5BA','长洲区',-1),('4q3FIXPf','9zyLn5BA','岑溪市',-1),('3JCGaMSI','9zyLn5BA','苍梧县',-1),('YJi15TH5','9zyLn5BA','藤县',-1),('sQuekvwc','9zyLn5BA','蒙山县',-1),('UqZIAzDK','9zyLn5BA','其他',-1),('jKiI8QmG','5imirHyV','海城区',-1),('JwseZJnR','5imirHyV','银海区',-1),('8rUJSZoy','5imirHyV','铁山港区',-1),('luPMs5yn','5imirHyV','合浦县',-1),('T2eAaI0T','5imirHyV','其他',-1),('3MiMbfkI','41KzPGYY','港口区',-1),('PySNhYIK','41KzPGYY','防城区',-1),('wmzmZUQv','41KzPGYY','东兴市',-1),('6GDllf4j','41KzPGYY','上思县',-1),('ouiYw4ha','41KzPGYY','其他',-1),('FSESXR1T','K33eSvPu','钦南区',-1),('KygI3MQc','K33eSvPu','钦北区',-1),('SejnhJUL','K33eSvPu','灵山县',-1),('aYCsblAX','K33eSvPu','浦北县',-1),('aEvKlMxb','K33eSvPu','其他',-1),('6ks7oV36','feayprPJ','港北区',-1),('dhvlNysy','feayprPJ','港南区',-1),('bSl4oa6V','feayprPJ','覃塘区',-1),('rEOgDyLK','feayprPJ','桂平市',-1),('ZHCXCbRT','feayprPJ','平南县',-1),('MCkDXUTd','feayprPJ','其他',-1),('rp8ziCrs','PdmAbHIC','玉州区',-1),('JnttJRhf','PdmAbHIC','北流市',-1),('k9ufpvPc','PdmAbHIC','容县',-1),('j0xCNsh1','PdmAbHIC','陆川县',-1),('FFgKV1hI','PdmAbHIC','博白县',-1),('1c9DKw0o','PdmAbHIC','兴业县',-1),('Wj8ztcdb','PdmAbHIC','其他',-1),('hXh6HpAl','Gg8tmgSa','右江区',-1),('yeuhdBJ3','Gg8tmgSa','凌云县',-1),('4W8MJA6b','Gg8tmgSa','平果县',-1),('2qlOqLlB','Gg8tmgSa','西林县',-1),('ZZJOdqth','Gg8tmgSa','乐业县',-1),('aP5A1Ols','Gg8tmgSa','德保县',-1),('wx3nDVaC','Gg8tmgSa','田林县',-1),('WHbEL8wb','Gg8tmgSa','田阳县',-1),('3sUc2vMR','Gg8tmgSa','靖西县',-1),('wQ14j4zT','Gg8tmgSa','田东县',-1),('W1gsxb3l','Gg8tmgSa','那坡县',-1),('IfHiJlon','Gg8tmgSa','隆林各族自治县',-1),('JRVQJ2HX','Gg8tmgSa','其他',-1),('XO47HJ0Y','nmfNfoim','八步区',-1),('KivQAYRW','nmfNfoim','钟山县',-1),('Bpysyek6','nmfNfoim','昭平县',-1),('aUPuxQVG','nmfNfoim','富川瑶族自治县',-1),('YWLGFQEh','nmfNfoim','其他',-1),('Ar7U9pZT','mXL8sWzT','金城江区',-1),('44ECBFD8','mXL8sWzT','宜州市',-1),('ijMi5QBk','mXL8sWzT','天峨县',-1),('jUJIlT1e','mXL8sWzT','凤山县',-1),('HGTYssTB','mXL8sWzT','南丹县',-1),('NvHfeDxM','mXL8sWzT','东兰县',-1),('FZBppDSt','mXL8sWzT','都安瑶族自治县',-1),('nxfMoLAe','mXL8sWzT','罗城仫佬族自治县',-1),('brVBRVAw','mXL8sWzT','巴马瑶族自治县',-1),('gvbYPxdJ','mXL8sWzT','环江毛南族自治县',-1),('7AsPJ4yE','mXL8sWzT','大化瑶族自治县',-1),('END3NsI2','mXL8sWzT','其他',-1),('y0qz5RTz','Gg9NakPd','兴宾区',-1),('AOiHTsup','Gg9NakPd','合山市',-1),('auJX8NhB','Gg9NakPd','象州县',-1),('khES3kwM','Gg9NakPd','武宣县',-1),('AszwCurE','Gg9NakPd','忻城县',-1),('3MJI3gY5','Gg9NakPd','金秀瑶族自治县',-1),('Cqk8jY6U','Gg9NakPd','其他',-1),('DcJ33dmA','rtwLnSSz','江州区',-1),('vgV6RpKP','rtwLnSSz','凭祥市',-1),('CejkbAsl','rtwLnSSz','宁明县',-1),('3bsdYTcz','rtwLnSSz','扶绥县',-1),('z8YqkPdx','rtwLnSSz','龙州县',-1),('Zs7UBxcC','rtwLnSSz','大新县',-1),('ysr3ssZe','rtwLnSSz','天等县',-1),('meWan496','rtwLnSSz','其他',-1),('6mWVL331','3ij6zMY8','南昌',0),('sUhLPwSL','3ij6zMY8','景德镇',0),('lkEPRCY6','3ij6zMY8','萍乡',0),('uHGoifyk','3ij6zMY8','九江',0),('CBHsRMUy','3ij6zMY8','新余',0),('PJZ5Hqvn','3ij6zMY8','鹰潭',0),('Jj8BQw2w','3ij6zMY8','赣州',0),('sTWOscoT','3ij6zMY8','吉安',0),('FoyE5dPA','3ij6zMY8','宜春',0),('UHAFJzyH','3ij6zMY8','抚州',0),('JTZysWcs','3ij6zMY8','上饶',0),('SPIjyl97','3ij6zMY8','其他',-1),('4Re5hnK6','6mWVL331','东湖区',-1),('fdoiSZsY','6mWVL331','西湖区',-1),('hs7F0xIW','6mWVL331','青云谱区',-1),('3IRZflrT','6mWVL331','湾里区',-1),('nzBUOOxN','6mWVL331','青山湖区',-1),('XUsm0ZWs','6mWVL331','新建县',-1),('dws1xFBg','6mWVL331','南昌县',-1),('UuyGbKWW','6mWVL331','进贤县',-1),('esPfKnZt','6mWVL331','安义县',-1),('qX5TVHHx','6mWVL331','其他',-1),('6QPAJJtn','sUhLPwSL','珠山区',-1),('1WxdKiBt','sUhLPwSL','昌江区',-1),('x33zv3PL','sUhLPwSL','乐平市',-1),('YyweUBsG','sUhLPwSL','浮梁县',-1),('nntWNGJK','sUhLPwSL','其他',-1),('IiguysyY','lkEPRCY6','安源区',-1),('MEYi7ZOw','lkEPRCY6','湘东区',-1),('mDqEaeNI','lkEPRCY6','莲花县',-1),('syhEw8Nj','lkEPRCY6','上栗县',-1),('IfcDdpwY','lkEPRCY6','芦溪县',-1),('qYidX12O','lkEPRCY6','其他',-1),('yzNsWbmP','uHGoifyk','浔阳区',-1),('xGKLXa50','uHGoifyk','庐山区',-1),('asgl3Gyq','uHGoifyk','瑞昌市',-1),('VODlQOhS','uHGoifyk','九江县',-1),('SEHPdwLM','uHGoifyk','星子县',-1),('Jfrwf1Ol','uHGoifyk','武宁县',-1),('CpUmX8ir','uHGoifyk','彭泽县',-1),('F4LWLY6Q','uHGoifyk','永修县',-1),('f1gIqUpS','uHGoifyk','修水县',-1),('8s4mxPle','uHGoifyk','湖口县',-1),('XhrxmPFB','uHGoifyk','德安县',-1),('6NglIzsr','uHGoifyk','都昌县',-1),('XQPc5iWr','uHGoifyk','其他',-1),('tBzUa0pe','CBHsRMUy','渝水区',-1),('Pz2wmFOm','CBHsRMUy','分宜县',-1),('R2KcyFGf','CBHsRMUy','其他',-1),('sYHg6Ry2','PJZ5Hqvn','月湖区',-1),('q7FBDCVs','PJZ5Hqvn','贵溪市',-1),('PEj7vUNH','PJZ5Hqvn','余江县',-1),('r77QU9mF','PJZ5Hqvn','其他',-1),('ohW8so6K','Jj8BQw2w','章贡区',-1),('wGnoisOL','Jj8BQw2w','瑞金市',-1),('IADssbyA','Jj8BQw2w','南康市',-1),('O70Cg7Om','Jj8BQw2w','石城县',-1),('87F4x78V','Jj8BQw2w','安远县',-1),('nsaU7y05','Jj8BQw2w','赣县',-1),('3FFscENl','Jj8BQw2w','宁都县',-1),('wvtfWapT','Jj8BQw2w','寻乌县',-1),('GNEza96v','Jj8BQw2w','兴国县',-1),('iOxEi5wJ','Jj8BQw2w','定南县',-1),('oTDKGKXy','Jj8BQw2w','上犹县',-1),('b3BUL7Kz','Jj8BQw2w','于都县',-1),('O19kUtF0','Jj8BQw2w','龙南县',-1),('dy4Sk8qn','Jj8BQw2w','崇义县',-1),('T7fXwZsx','Jj8BQw2w','信丰县',-1),('6UXYR3HR','Jj8BQw2w','全南县',-1),('TbmtEIHN','Jj8BQw2w','大余县',-1),('sCEjrJvu','Jj8BQw2w','会昌县',-1),('LCQ6cxuO','Jj8BQw2w','其他',-1),('KjvgtWaq','sTWOscoT','吉州区',-1),('WH5CLSG0','sTWOscoT','青原区',-1),('3LGcvbe1','sTWOscoT','井冈山市',-1),('XshQs22b','sTWOscoT','吉安县',-1),('eqfPt43E','sTWOscoT','永丰县',-1),('psUy17h2','sTWOscoT','永新县',-1),('WruewKbP','sTWOscoT','新干县',-1),('rrqXJCBy','sTWOscoT','泰和县',-1),('X6KFWAef','sTWOscoT','峡江县',-1),('2Ib3K5Qk','sTWOscoT','遂川县',-1),('vyqqRssu','sTWOscoT','安福县',-1),('IY8nV5MW','sTWOscoT','吉水县',-1),('5d9hNUMb','sTWOscoT','万安县',-1),('mv8rhi4p','sTWOscoT','其他',-1),('FLyzGhEp','FoyE5dPA','袁州区',-1),('n1q6BOfY','FoyE5dPA','丰城市',-1),('tDWoBflK','FoyE5dPA','樟树市',-1),('Tp7GF5ZZ','FoyE5dPA','高安市',-1),('6g4Jx7gm','FoyE5dPA','铜鼓县',-1),('KfsszOTR','FoyE5dPA','靖安县',-1),('SU56ZnPV','FoyE5dPA','宜丰县',-1),('FLBjvkWo','FoyE5dPA','奉新县',-1),('VRcpRm2C','FoyE5dPA','万载县',-1),('FZcy50go','FoyE5dPA','上高县',-1),('TxpSn4G1','FoyE5dPA','其他',-1),('SzyAe52g','UHAFJzyH','临川区',-1),('W0ztMRK2','UHAFJzyH','南丰县',-1),('o7zkppwG','UHAFJzyH','乐安县',-1),('4sKmS0YH','UHAFJzyH','金溪县',-1),('ySdsd2Us','UHAFJzyH','南城县',-1),('3Bgmegqw','UHAFJzyH','东乡县',-1),('UNe5tv9u','UHAFJzyH','资溪县',-1),('BT7cm4Pz','UHAFJzyH','宜黄县',-1),('fRaAi8Xf','UHAFJzyH','广昌县',-1),('gjEOzEEy','UHAFJzyH','黎川县',-1),('MkvCBViK','UHAFJzyH','崇仁县',-1),('qH3o0S7X','UHAFJzyH','其他',-1),('k99LsjWy','JTZysWcs','信州区',-1),('MrZcv5IF','JTZysWcs','德兴市',-1),('J7rF7sAx','JTZysWcs','上饶县',-1),('g59r9VPH','JTZysWcs','广丰县',-1),('EAtlKSE1','JTZysWcs','鄱阳县',-1),('WBO10nvX','JTZysWcs','婺源县',-1),('cjHipQHI','JTZysWcs','铅山县',-1),('AaWHuqSR','JTZysWcs','余干县',-1),('wM3IVNJD','JTZysWcs','横峰县',-1),('Y20L60Gg','JTZysWcs','弋阳县',-1),('Qx0JqQXF','JTZysWcs','玉山县',-1),('VDsFffCn','JTZysWcs','万年县',-1),('PVCasZFe','JTZysWcs','其他',-1),('3QTxbDsb','woS4K1Vk','贵阳',0),('IzMa8Tqe','woS4K1Vk','六盘水',0),('5lk15d8m','woS4K1Vk','遵义',0),('6YunEHkt','woS4K1Vk','安顺',0),('uEBmUB7s','woS4K1Vk','铜仁地区',0),('7tHd1cpw','woS4K1Vk','毕节地区',0),('hgVeE8VZ','woS4K1Vk','黔西南布依族苗族自治州',0),('3m1ubOpG','woS4K1Vk','黔东南苗族侗族自治州',0),('h8xo0fzW','woS4K1Vk','黔南布依族苗族自治州',0),('hp2oGk9G','woS4K1Vk','其他',-1),('kaOBFu7h','3QTxbDsb','南明区',-1),('VxtXehdt','3QTxbDsb','云岩区',-1),('BiF722kU','3QTxbDsb','花溪区',-1),('X6RqC1Lu','3QTxbDsb','乌当区',-1),('zl1b2x5W','3QTxbDsb','白云区',-1),('kOPeG0EG','3QTxbDsb','小河区',-1),('pMFDYYf8','3QTxbDsb','清镇市',-1),('bbgiWizc','3QTxbDsb','开阳县',-1),('Zs1DWrpP','3QTxbDsb','修文县',-1),('obCYQC48','3QTxbDsb','息烽县',-1),('kVbMEa82','3QTxbDsb','其他',-1),('D1AlHiJ3','IzMa8Tqe','钟山区',-1),('yju9ptlc','IzMa8Tqe','水城县',-1),('TkiHoj7d','IzMa8Tqe','盘县',-1),('jUv6NEQl','IzMa8Tqe','六枝特区',-1),('ooE12Odl','IzMa8Tqe','其他',-1),('qJXS4IoV','5lk15d8m','红花岗区',-1),('a5ebLN0B','5lk15d8m','汇川区',-1),('PblwO5rR','5lk15d8m','赤水市',-1),('Jr5Le4jX','5lk15d8m','仁怀市',-1),('GO3F5sOM','5lk15d8m','遵义县',-1),('KJIElZyI','5lk15d8m','绥阳县',-1),('ikE5ahSt','5lk15d8m','桐梓县',-1),('zFmC4JkM','5lk15d8m','习水县',-1),('ctLtzw5O','5lk15d8m','凤冈县',-1),('ujlmWYWf','5lk15d8m','正安县',-1),('87NN7iKI','5lk15d8m','余庆县',-1),('NsuNsVXR','5lk15d8m','湄潭县',-1),('WauhBMRC','5lk15d8m','道真仡佬族苗族自治县',-1),('61srrJ6u','5lk15d8m','务川仡佬族苗族自治县',-1),('dpy3sGPk','5lk15d8m','其他',-1),('UO5R0v1w','6YunEHkt','西秀区',-1),('suJDyInk','6YunEHkt','普定县',-1),('NzzRZ1d3','6YunEHkt','平坝县',-1),('sW7vePGs','6YunEHkt','镇宁布依族苗族自治县',-1),('oOlz0WZQ','6YunEHkt','紫云苗族布依族自治县',-1),('sHzxby35','6YunEHkt','关岭布依族苗族自治县',-1),('LYXqed17','6YunEHkt','其他',-1),('7T18mQJK','uEBmUB7s','铜仁市',-1),('TIxoqFQy','uEBmUB7s','德江县',-1),('4YgGkUrc','uEBmUB7s','江口县',-1),('yOxcAAgs','uEBmUB7s','思南县',-1),('Qr2DAzBq','uEBmUB7s','石阡县',-1),('YSZ6p7gf','uEBmUB7s','玉屏侗族自治县',-1),('BoKTFTzA','uEBmUB7s','松桃苗族自治县',-1),('TSgoSyaV','uEBmUB7s','印江土家族苗族自治县',-1),('VtZkh67g','uEBmUB7s','沿河土家族自治县',-1),('XrjsKt11','uEBmUB7s','万山特区',-1),('Kxm2tIjW','uEBmUB7s','其他',-1),('C5B09XRm','7tHd1cpw','毕节市',-1),('1pqHsQh1','7tHd1cpw','黔西县',-1),('TiGVyQkv','7tHd1cpw','大方县',-1),('7Bm1vof3','7tHd1cpw','织金县',-1),('FE0EYJ4I','7tHd1cpw','金沙县',-1),('IswzEPEo','7tHd1cpw','赫章县',-1),('o5EFEKvo','7tHd1cpw','纳雍县',-1),('YAjY8Nus','7tHd1cpw','威宁彝族回族苗族自治县',-1),('sOp5KSuC','7tHd1cpw','其他',-1),('fQbpZHny','hgVeE8VZ','兴义市',-1),('ERcW5VjM','hgVeE8VZ','望谟县',-1),('7xxJwLBI','hgVeE8VZ','兴仁县',-1),('oBy7igQ9','hgVeE8VZ','普安县',-1),('ryTEakKO','hgVeE8VZ','册亨县',-1),('Frn8Uw0g','hgVeE8VZ','晴隆县',-1),('Sd3bxO6c','hgVeE8VZ','贞丰县',-1),('1Fa6V7uQ','hgVeE8VZ','安龙县',-1),('jtmsGBiL','hgVeE8VZ','其他',-1),('wL4yyHTC','3m1ubOpG','凯里市',-1),('L45Hxbh7','3m1ubOpG','施秉县',-1),('wI3RSopl','3m1ubOpG','从江县',-1),('1hcmyBjG','3m1ubOpG','锦屏县',-1),('RdlOrUV5','3m1ubOpG','镇远县',-1),('g6Vq8wHf','3m1ubOpG','麻江县',-1),('MTsb2YVu','3m1ubOpG','台江县',-1),('EpxVZs9E','3m1ubOpG','天柱县',-1),('g464VRyJ','3m1ubOpG','黄平县',-1),('YDB1hVhI','3m1ubOpG','榕江县',-1),('7rhM4Gih','3m1ubOpG','剑河县',-1),('q7itOfis','3m1ubOpG','三穗县',-1),('qTkRCFRy','3m1ubOpG','雷山县',-1),('WhaolFPL','3m1ubOpG','黎平县',-1),('NnhZh8kI','3m1ubOpG','岑巩县',-1),('U2wdw17F','3m1ubOpG','丹寨县',-1),('prFvnDjq','3m1ubOpG','其他',-1),('6GCjnmgw','h8xo0fzW','都匀市',-1),('t7ktoIRu','h8xo0fzW','福泉市',-1),('dYFWMyEv','h8xo0fzW','贵定县',-1),('AXOUYv50','h8xo0fzW','惠水县',-1),('wA5LZEus','h8xo0fzW','罗甸县',-1),('cLlKG08P','h8xo0fzW','瓮安县',-1),('ruYsrp3m','h8xo0fzW','荔波县',-1),('l0sP6Gpb','h8xo0fzW','龙里县',-1),('dudrMIq5','h8xo0fzW','平塘县',-1),('bcXP92CZ','h8xo0fzW','长顺县',-1),('glqySycQ','h8xo0fzW','独山县',-1),('ix2fsSVa','h8xo0fzW','三都水族自治县',-1),('RYEhwB2Z','h8xo0fzW','其他',-1),('PPsgE18G','lgUbqdSZ','昆明',0),('puiSECB5','lgUbqdSZ','曲靖',0),('93jCVOo5','lgUbqdSZ','玉溪',0),('MZXpBwM9','lgUbqdSZ','保山',0),('N3PU2uHn','lgUbqdSZ','昭通',0),('fb9fYJWR','lgUbqdSZ','丽江',0),('gtfxQQ7V','lgUbqdSZ','普洱',0),('68LA5JCD','lgUbqdSZ','临沧',0),('B1bSlBql','lgUbqdSZ','德宏傣族景颇族自治州',0),('APtmDZsT','lgUbqdSZ','怒江傈僳族自治州',0),('Pn4BPy8p','lgUbqdSZ','迪庆藏族自治州',0),('otTpHB3h','lgUbqdSZ','大理白族自治州',0),('ag5MOASY','lgUbqdSZ','楚雄彝族自治州',0),('Trz9SgCj','lgUbqdSZ','红河哈尼族彝族自治州',0),('S9Ws0Kny','lgUbqdSZ','文山壮族苗族自治州',0),('9xJPNH2t','lgUbqdSZ','西双版纳傣族自治州',0),('PwUvrUko','lgUbqdSZ','其他',-1),('UiO4x2le','PPsgE18G','盘龙区',-1),('izrC7wXL','PPsgE18G','五华区',-1),('biFO4zsi','PPsgE18G','官渡区',-1),('ywLdqgqy','PPsgE18G','西山区',-1),('FG5utQiN','PPsgE18G','东川区',-1),('flvjq6U8','PPsgE18G','安宁市',-1),('i1iugsZ8','PPsgE18G','呈贡县',-1),('vsrGBggv','PPsgE18G','晋宁县',-1),('pKDg5etR','PPsgE18G','富民县',-1),('t5C0eIdA','PPsgE18G','宜良县',-1),('XmqSDBs6','PPsgE18G','嵩明县',-1),('u9ZfSVnp','PPsgE18G','石林彝族自治县',-1),('JzCeAjYS','PPsgE18G','禄劝彝族苗族自治县',-1),('hwD6XtAC','PPsgE18G','寻甸回族彝族自治县',-1),('NhB5VcrU','PPsgE18G','其他',-1),('F7m2J9c7','puiSECB5','麒麟区',-1),('9wVsXB1i','puiSECB5','宣威市',-1),('gyKorLVn','puiSECB5','马龙县',-1),('aw259aiq','puiSECB5','沾益县',-1),('KDYnbk93','puiSECB5','富源县',-1),('9VeEkDBm','puiSECB5','罗平县',-1),('wXCZvIhU','puiSECB5','师宗县',-1),('s6RrsQ6v','puiSECB5','陆良县',-1),('5gLOpOuK','puiSECB5','会泽县',-1),('VvJXrMyo','puiSECB5','其他',-1),('QEUWtzx1','93jCVOo5','红塔区',-1),('CTPsMOtZ','93jCVOo5','江川县',-1),('6dZlD2dI','93jCVOo5','澄江县',-1),('SKDorHwE','93jCVOo5','通海县',-1),('2LiOYZzL','93jCVOo5','华宁县',-1),('f8Vm17Ja','93jCVOo5','易门县',-1),('g8RpNC5f','93jCVOo5','峨山彝族自治县',-1),('UXMiC4RG','93jCVOo5','新平彝族傣族自治县',-1),('6A9pKtiW','93jCVOo5','元江哈尼族彝族傣族自治县',-1),('AXsQGUTU','93jCVOo5','其他',-1),('7NLwLpsj','MZXpBwM9','隆阳区',-1),('oJFuOHEH','MZXpBwM9','施甸县',-1),('ooqT6pxw','MZXpBwM9','腾冲县',-1),('lYDVcvcs','MZXpBwM9','龙陵县',-1),('fgK7vWVC','MZXpBwM9','昌宁县',-1),('AbyNIj18','MZXpBwM9','其他',-1),('PkK9vu9C','N3PU2uHn','昭阳区',-1),('yFFis98v','N3PU2uHn','鲁甸县',-1),('td9G8YeG','N3PU2uHn','巧家县',-1),('OgryMrYT','N3PU2uHn','盐津县',-1),('IxoqVQrV','N3PU2uHn','大关县',-1),('ufn0ijhq','N3PU2uHn','永善县',-1),('LYeDRasX','N3PU2uHn','绥江县',-1),('BPJP4JjW','N3PU2uHn','镇雄县',-1),('wwyUNChD','N3PU2uHn','彝良县',-1),('2k7Uilez','N3PU2uHn','威信县',-1),('9eB1CRRN','N3PU2uHn','水富县',-1),('GsKZg98p','N3PU2uHn','其他',-1),('soqxIgER','fb9fYJWR','古城区',-1),('KexqsCqq','fb9fYJWR','永胜县',-1),('cOJM0N2B','fb9fYJWR','华坪县',-1),('uXSvThzM','fb9fYJWR','玉龙纳西族自治县',-1),('DwoPygc5','fb9fYJWR','宁蒗彝族自治县',-1),('HWj51H9E','fb9fYJWR','其他',-1),('4g9e97Ig','gtfxQQ7V','思茅区',-1),('GW7oK1cJ','gtfxQQ7V','普洱哈尼族彝族自治县',-1),('khNXT5KX','gtfxQQ7V','墨江哈尼族自治县',-1),('8CCLqFih','gtfxQQ7V','景东彝族自治县',-1),('YUNGRMZT','gtfxQQ7V','景谷傣族彝族自治县',-1),('ijzAWMzK','gtfxQQ7V','镇沅彝族哈尼族拉祜族自治县',-1),('WdMPjvon','gtfxQQ7V','江城哈尼族彝族自治县',-1),('kwQ0iLly','gtfxQQ7V','孟连傣族拉祜族佤族自治县',-1),('sb2kcjkY','gtfxQQ7V','澜沧拉祜族自治县',-1),('HiM6Zm0i','gtfxQQ7V','西盟佤族自治县',-1),('pXjezV93','gtfxQQ7V','其他',-1),('HvyFXKOG','68LA5JCD','临翔区',-1),('HbxOaDYi','68LA5JCD','凤庆县',-1),('o07c4m46','68LA5JCD','云县',-1),('iYjIXKBJ','68LA5JCD','永德县',-1),('aUTnD3UF','68LA5JCD','镇康县',-1),('cR2nyByy','68LA5JCD','双江拉祜族佤族布朗族傣族自治县',-1),('GxCl6z4h','68LA5JCD','耿马傣族佤族自治县',-1),('uG05Md2U','68LA5JCD','沧源佤族自治县',-1),('24nAzub9','68LA5JCD','其他',-1),('C0NO5uZW','B1bSlBql','潞西市',-1),('vhNZsLOz','B1bSlBql','瑞丽市',-1),('AscUuf2u','B1bSlBql','梁河县',-1),('FDILCSmw','B1bSlBql','盈江县',-1),('ti1mongs','B1bSlBql','陇川县',-1),('dAIP0kr3','B1bSlBql','其他',-1),('qXELFKnM','APtmDZsT','泸水县',-1),('zmuZ1bZe','APtmDZsT','福贡县',-1),('Yc0LO6kl','APtmDZsT','贡山独龙族怒族自治县',-1),('1pEK0LjS','APtmDZsT','兰坪白族普米族自治县',-1),('RDlT8yNg','APtmDZsT','其他',-1),('zc0Tz6Hd','Pn4BPy8p','香格里拉县',-1),('8nc2UbDW','Pn4BPy8p','德钦县',-1),('SAKkSWZa','Pn4BPy8p','维西傈僳族自治县',-1),('Gf79rnxF','Pn4BPy8p','其他',-1),('DRQTDLBC','otTpHB3h','大理市',-1),('fUsg3Wvq','otTpHB3h','祥云县',-1),('OhIYHwfY','otTpHB3h','宾川县',-1),('PvhgA3Mw','otTpHB3h','弥渡县',-1),('0Nec0uq8','otTpHB3h','永平县',-1),('B2vePkvl','otTpHB3h','云龙县',-1),('MhKe07Xz','otTpHB3h','洱源县',-1),('fW5LYLPs','otTpHB3h','剑川县',-1),('v7Df79XT','otTpHB3h','鹤庆县',-1),('5APo5MEY','otTpHB3h','漾濞彝族自治县',-1),('22ODgHs0','otTpHB3h','南涧彝族自治县',-1),('wRXwnbQO','otTpHB3h','巍山彝族回族自治县',-1),('Zjp9aYp6','otTpHB3h','其他',-1),('qNTbdGQ0','ag5MOASY','楚雄市',-1),('XRXg2BZ2','ag5MOASY','双柏县',-1),('e44XIoly','ag5MOASY','牟定县',-1),('Bv5PgFzy','ag5MOASY','南华县',-1),('MUSkIDBZ','ag5MOASY','姚安县',-1),('xb1sCAke','ag5MOASY','大姚县',-1),('beBs6fzG','ag5MOASY','永仁县',-1),('tohUieu2','ag5MOASY','元谋县',-1),('zdpVGjSX','ag5MOASY','武定县',-1),('5NO1qHm9','ag5MOASY','禄丰县',-1),('bx792o73','ag5MOASY','其他',-1),('mPAlkmbH','Trz9SgCj','蒙自县',-1),('2gFdMxwV','Trz9SgCj','个旧市',-1),('cjuo5urp','Trz9SgCj','开远市',-1),('vLcJvm0D','Trz9SgCj','绿春县',-1),('psG5qqpy','Trz9SgCj','建水县',-1),('FBvssI3h','Trz9SgCj','石屏县',-1),('18SEgM4K','Trz9SgCj','弥勒县',-1),('LcyEzGRA','Trz9SgCj','泸西县',-1),('a3jyHslK','Trz9SgCj','元阳县',-1),('ykm0w02V','Trz9SgCj','红河县',-1),('VIJuvvdp','Trz9SgCj','金平苗族瑶族傣族自治县',-1),('ai4ysX6v','Trz9SgCj','河口瑶族自治县',-1),('syHyPaSo','Trz9SgCj','屏边苗族自治县',-1),('gkJKyynY','Trz9SgCj','其他',-1),('1GYd8Dcb','S9Ws0Kny','文山县',-1),('yH1K1ezg','S9Ws0Kny','砚山县',-1),('OoeCyUpD','S9Ws0Kny','西畴县',-1),('DJC6hJWP','S9Ws0Kny','麻栗坡县',-1),('NDBUe50o','S9Ws0Kny','马关县',-1),('YHmXB2BA','S9Ws0Kny','丘北县',-1),('85MZJ3y0','S9Ws0Kny','广南县',-1),('6DqjIXIH','S9Ws0Kny','富宁县',-1),('3cUQDUBV','S9Ws0Kny','其他',-1),('HW4vhBOd','9xJPNH2t','景洪市',-1),('lAyhl17l','9xJPNH2t','勐海县',-1),('pIkE9HHf','9xJPNH2t','勐腊县',-1),('jfDPmPWV','9xJPNH2t','其他',-1),('H5mSCqVr','Pn0YV2YP','拉萨',0),('HmgVqjAO','Pn0YV2YP','那曲地区',0),('oXa94aGe','Pn0YV2YP','昌都地区',0),('rlU6ssy8','Pn0YV2YP','林芝地区',0),('9TxdJCs2','Pn0YV2YP','山南地区',0),('ZVw1miwx','Pn0YV2YP','日喀则地区',0),('ADKQ5tU8','Pn0YV2YP','阿里地区',0),('4iAVakvf','Pn0YV2YP','其他',-1),('qxrNH42d','H5mSCqVr','城关区',-1),('Nthdn0yo','H5mSCqVr','林周县',-1),('E2cWrMLn','H5mSCqVr','当雄县',-1),('R9E3SEIP','H5mSCqVr','尼木县',-1),('hK1XoMzR','H5mSCqVr','曲水县',-1),('YQsHUWWV','H5mSCqVr','堆龙德庆县',-1),('qqEniIb0','H5mSCqVr','达孜县',-1),('aiTeojDn','H5mSCqVr','墨竹工卡县',-1),('d039JNQH','H5mSCqVr','其他',-1),('EJE22EgV','HmgVqjAO','那曲县',-1),('vlPoi0Jd','HmgVqjAO','嘉黎县',-1),('LpZHptfh','HmgVqjAO','比如县',-1),('SlunhiNF','HmgVqjAO','聂荣县',-1),('tgmYeDSA','HmgVqjAO','安多县',-1),('kFg8Gwf5','HmgVqjAO','申扎县',-1),('ybsNExfC','HmgVqjAO','索县',-1),('aq6KP1cq','HmgVqjAO','班戈县',-1),('2mnepleJ','HmgVqjAO','巴青县',-1),('uteD2FB4','HmgVqjAO','尼玛县',-1),('HZwknzJJ','HmgVqjAO','其他',-1),('waTCPzeF','oXa94aGe','昌都县',-1),('oNbzQC1z','oXa94aGe','江达县',-1),('tjVjhpDL','oXa94aGe','贡觉县',-1),('tw5Q3T3j','oXa94aGe','类乌齐县',-1),('xsoJQP1l','oXa94aGe','丁青县',-1),('I67YkSCP','oXa94aGe','察雅县',-1),('kxQFvaSH','oXa94aGe','八宿县',-1),('SblDNTPT','oXa94aGe','左贡县',-1),('RxQyJ4pB','oXa94aGe','芒康县',-1),('PkZZABih','oXa94aGe','洛隆县',-1),('zihSsoBi','oXa94aGe','边坝县',-1),('e6DIT9hF','oXa94aGe','其他',-1),('GNSvh3AZ','rlU6ssy8','林芝县',-1),('LshNhFeo','rlU6ssy8','工布江达县',-1),('krBM0Xgu','rlU6ssy8','米林县',-1),('TKPh3jyK','rlU6ssy8','墨脱县',-1),('mQVRePZe','rlU6ssy8','波密县',-1),('tjpREJoC','rlU6ssy8','察隅县',-1),('h0JAjuTC','rlU6ssy8','朗县',-1),('87Jefadx','rlU6ssy8','其他',-1),('QcsbVYsu','9TxdJCs2','乃东县',-1),('RDDsPrsU','9TxdJCs2','扎囊县',-1),('cDbfNVWx','9TxdJCs2','贡嘎县',-1),('1V5O2KHY','9TxdJCs2','桑日县',-1),('6Vmwn12s','9TxdJCs2','琼结县',-1),('2mNG5gd9','9TxdJCs2','曲松县',-1),('oD4LlYst','9TxdJCs2','措美县',-1),('TjhJbkcV','9TxdJCs2','洛扎县',-1),('KZIADw9L','9TxdJCs2','加查县',-1),('aGUG0y0k','9TxdJCs2','隆子县',-1),('1I94yLAJ','9TxdJCs2','错那县',-1),('xO2HsIYh','9TxdJCs2','浪卡子县',-1),('12eZXstZ','9TxdJCs2','其他',-1),('0MXg5NJb','ZVw1miwx','日喀则市',-1),('c5R1m1XA','ZVw1miwx','南木林县',-1),('mgOQgbzN','ZVw1miwx','江孜县',-1),('3dLUm41Q','ZVw1miwx','定日县',-1),('RPomn9Om','ZVw1miwx','萨迦县',-1),('YBnucGnr','ZVw1miwx','拉孜县',-1),('izY5qbRe','ZVw1miwx','昂仁县',-1),('QcX1NYNY','ZVw1miwx','谢通门县',-1),('9OXwVhqE','ZVw1miwx','白朗县',-1),('XVDhzu2q','ZVw1miwx','仁布县',-1),('b9wm8PkD','ZVw1miwx','康马县',-1),('nDxlUgDm','ZVw1miwx','定结县',-1),('FsFtYToi','ZVw1miwx','仲巴县',-1),('JKhwE0aX','ZVw1miwx','亚东县',-1),('S3VB5yqN','ZVw1miwx','吉隆县',-1),('YCCK9pyg','ZVw1miwx','聂拉木县',-1),('FDNENz1e','ZVw1miwx','萨嘎县',-1),('c3m4gk4O','ZVw1miwx','岗巴县',-1),('5ViX19xm','ZVw1miwx','其他',-1),('WIk8LQa7','ADKQ5tU8','噶尔县',-1),('iysPkwg9','ADKQ5tU8','普兰县',-1),('IJe0Hyzm','ADKQ5tU8','札达县',-1),('CS9MXsTO','ADKQ5tU8','日土县',-1),('2PCPwhZ9','ADKQ5tU8','革吉县',-1),('0jc5suxh','ADKQ5tU8','改则县',-1),('uBo0AMI8','ADKQ5tU8','措勤县',-1),('Aa8SG69B','ADKQ5tU8','其他',-1),('cjida3qw','wjxGWGBN','海口',0),('saN7scr4','wjxGWGBN','三亚',0),('chYPFc6u','wjxGWGBN','五指山',0),('4qhcdgbZ','wjxGWGBN','琼海',0),('iOYnZTIB','wjxGWGBN','儋州',0),('JHaoYDd1','wjxGWGBN','文昌',0),('jidASlp1','wjxGWGBN','万宁',0),('ekbav8lB','wjxGWGBN','东方',0),('yTQUsfzC','wjxGWGBN','澄迈县',0),('AoJXUs2g','wjxGWGBN','定安县',0),('VzKJ0a4t','wjxGWGBN','屯昌县',0),('M2n7WLld','wjxGWGBN','临高县',0),('vAitvQrB','wjxGWGBN','白沙黎族自治县',0),('KtohUckj','wjxGWGBN','昌江黎族自治县',0),('WEYa7p0h','wjxGWGBN','乐东黎族自治县',0),('x6HNElYf','wjxGWGBN','陵水黎族自治县',0),('hBKs2Q91','wjxGWGBN','保亭黎族苗族自治县',0),('0Akz87E0','wjxGWGBN','琼中黎族苗族自治县',0),('OTUmZXaT','wjxGWGBN','其他',-1),('JIuJkUhC','cjida3qw','龙华区',-1),('ENcKqeCK','cjida3qw','秀英区',-1),('Jr6Ibz91','cjida3qw','琼山区',-1),('8pgVGXSC','cjida3qw','美兰区',-1),('oNXNDTUh','cjida3qw','其他',-1),('JBVLMS2j','saN7scr4','三亚市',-1),('zkkia3Ys','saN7scr4','其他',-1),('u0y9Fc8w','YSY6Jn6S','兰州',0),('SUpu1iAn','YSY6Jn6S','嘉峪关',0),('vr5g9bgr','YSY6Jn6S','金昌',0),('IBOcyvuK','YSY6Jn6S','白银',0),('P8IC8wg2','YSY6Jn6S','天水',0),('JpA1EHXL','YSY6Jn6S','武威',0),('8JohARTb','YSY6Jn6S','酒泉',0),('2cmI79sF','YSY6Jn6S','张掖',0),('BPQHdbSl','YSY6Jn6S','庆阳',0),('JBIgkqBa','YSY6Jn6S','平凉',0),('P8ZPuCGw','YSY6Jn6S','定西',0),('93yXVTg4','YSY6Jn6S','陇南',0),('YgkWKcu9','YSY6Jn6S','临夏回族自治州',0),('vuBRvsUV','YSY6Jn6S','甘南藏族自治州',0),('A9g9jkJ4','YSY6Jn6S','其他',-1),('llDFWJkz','u0y9Fc8w','城关区',-1),('ufICg14i','u0y9Fc8w','七里河区',-1),('XD8B5ZtT','u0y9Fc8w','西固区',-1),('hsDWUJKf','u0y9Fc8w','安宁区',-1),('fgbSgjgK','u0y9Fc8w','红古区',-1),('UEcCsIC4','u0y9Fc8w','永登县',-1),('OVlcJTXW','u0y9Fc8w','皋兰县',-1),('em7cmPXs','u0y9Fc8w','榆中县',-1),('js6QozAv','u0y9Fc8w','其他',-1),('VJoT33sf','SUpu1iAn','嘉峪关市',-1),('p38AkMVJ','SUpu1iAn','其他',-1),('u9vH7a99','vr5g9bgr','金川区',-1),('SO05pbJ4','vr5g9bgr','永昌县',-1),('yGHyhUm5','vr5g9bgr','其他',-1),('GHPyLhEC','IBOcyvuK','白银区',-1),('2ETYteIy','IBOcyvuK','平川区',-1),('CPB2MASg','IBOcyvuK','靖远县',-1),('0zmzLOIl','IBOcyvuK','会宁县',-1),('KnDwTLHm','IBOcyvuK','景泰县',-1),('hD7aqmho','IBOcyvuK','其他',-1),('h1qJrLaL','P8IC8wg2','清水县',-1),('yJZ72FKG','P8IC8wg2','秦安县',-1),('rfnvmfyI','P8IC8wg2','甘谷县',-1),('tliBHBOa','P8IC8wg2','武山县',-1),('Ny89jMCQ','P8IC8wg2','张家川回族自治县',-1),('sbg2IfNz','P8IC8wg2','北道区',-1),('oU1nG9cy','P8IC8wg2','秦城区',-1),('KVmMbPxN','P8IC8wg2','其他',-1),('LgplNTDq','JpA1EHXL','凉州区',-1),('BnZrUgWU','JpA1EHXL','民勤县',-1),('QsKhL8U6','JpA1EHXL','古浪县',-1),('OuVQX8uL','JpA1EHXL','天祝藏族自治县',-1),('2FQRtdLY','JpA1EHXL','其他',-1),('0mmee27N','8JohARTb','肃州区',-1),('tcQl8RxZ','8JohARTb','玉门市',-1),('fxuWveOK','8JohARTb','敦煌市',-1),('t1VOCbf6','8JohARTb','金塔县',-1),('iepAeHrv','8JohARTb','肃北蒙古族自治县',-1),('DpwT3z1j','8JohARTb','阿克塞哈萨克族自治县',-1),('Dr8gsMMi','8JohARTb','安西县',-1),('8A7TuUla','8JohARTb','其他',-1),('BxrWlIKv','2cmI79sF','甘州区',-1),('D4Zy8sQY','2cmI79sF','民乐县',-1),('RVZOhkOG','2cmI79sF','临泽县',-1),('Icx2Lxsg','2cmI79sF','高台县',-1),('dv2asOcT','2cmI79sF','山丹县',-1),('D6anP56Y','2cmI79sF','肃南裕固族自治县',-1),('Bi1Kqf5G','2cmI79sF','其他',-1),('QoQzuSi5','BPQHdbSl','西峰区',-1),('n1SnCIKw','BPQHdbSl','庆城县',-1),('Z0NOwY8Z','BPQHdbSl','环县',-1),('ObK5B0QE','BPQHdbSl','华池县',-1),('slhSFTiq','BPQHdbSl','合水县',-1),('BKCfO5rl','BPQHdbSl','正宁县',-1),('smepaeE6','BPQHdbSl','宁县',-1),('zuOrswGS','BPQHdbSl','镇原县',-1),('ftnicHu3','BPQHdbSl','其他',-1),('bwp5BbA4','JBIgkqBa','崆峒区',-1),('Agvl6oNg','JBIgkqBa','泾川县',-1),('teKtVamI','JBIgkqBa','灵台县',-1),('cexisZAB','JBIgkqBa','崇信县',-1),('TDVzGhvj','JBIgkqBa','华亭县',-1),('1qwURblN','JBIgkqBa','庄浪县',-1),('uo28bWMd','JBIgkqBa','静宁县',-1),('LntNBscX','JBIgkqBa','其他',-1),('0x0OqYB0','P8ZPuCGw','安定区',-1),('uIDtinNO','P8ZPuCGw','通渭县',-1),('blvFoCPM','P8ZPuCGw','临洮县',-1),('wScsbXbS','P8ZPuCGw','漳县',-1),('61ltHBT4','P8ZPuCGw','岷县',-1),('X1zGpOz4','P8ZPuCGw','渭源县',-1),('79fw4lMi','P8ZPuCGw','陇西县',-1),('2s6nuzKd','P8ZPuCGw','其他',-1),('8UlpL4Oy','93yXVTg4','武都区',-1),('cpScBW8y','93yXVTg4','成县',-1),('YCOGR4ib','93yXVTg4','宕昌县',-1),('955qwApT','93yXVTg4','康县',-1),('wxmiMUWm','93yXVTg4','文县',-1),('IZ36e0DG','93yXVTg4','西和县',-1),('rklCQmG5','93yXVTg4','礼县',-1),('mKs1EaYO','93yXVTg4','两当县',-1),('T6buGsOq','93yXVTg4','徽县',-1),('xxR29AY3','93yXVTg4','其他',-1),('fVsnLscH','YgkWKcu9','临夏市',-1),('T3qRSJzv','YgkWKcu9','临夏县',-1),('sTYtxB2w','YgkWKcu9','康乐县',-1),('W2EfL20G','YgkWKcu9','永靖县',-1),('t4sPmHzE','YgkWKcu9','广河县',-1),('Nrrsx667','YgkWKcu9','和政县',-1),('5dpqcqdk','YgkWKcu9','东乡族自治县',-1),('pzg8YPfS','YgkWKcu9','积石山保安族东乡族撒拉族自治县',-1),('vd05ZBms','YgkWKcu9','其他',-1),('L56xOq0i','vuBRvsUV','合作市',-1),('kwiXHIAo','vuBRvsUV','临潭县',-1),('DqHrQ4TK','vuBRvsUV','卓尼县',-1),('AfeTBDoh','vuBRvsUV','舟曲县',-1),('brP5hJaZ','vuBRvsUV','迭部县',-1),('fCeYlAv3','vuBRvsUV','玛曲县',-1),('PPXNLPNu','vuBRvsUV','碌曲县',-1),('qfZt8y3I','vuBRvsUV','夏河县',-1),('GS8BFs3L','vuBRvsUV','其他',-1),('vE1QsIQK','xshSiyeC','银川',0),('5ujt7rZA','xshSiyeC','石嘴山',0),('bsf9Xez7','xshSiyeC','吴忠',0),('3GjDnDfB','xshSiyeC','固原',0),('ICqkxCsb','xshSiyeC','中卫',0),('2zP6stGZ','xshSiyeC','其他',-1),('Z0hIrMHk','vE1QsIQK','兴庆区',-1),('kisIywPV','vE1QsIQK','西夏区',-1),('CVbOGAvk','vE1QsIQK','金凤区',-1),('NPTrOMAA','vE1QsIQK','灵武市',-1),('X5VO0u9i','vE1QsIQK','永宁县',-1),('mzh5TEST','vE1QsIQK','贺兰县',-1),('fms7s782','vE1QsIQK','其他',-1),('uJZSWk81','5ujt7rZA','大武口区',-1),('sH6n9LnR','5ujt7rZA','惠农区',-1),('d3YMDCDt','5ujt7rZA','平罗县',-1),('fu6HYyaN','5ujt7rZA','其他',-1),('3gchOnxl','bsf9Xez7','利通区',-1),('zscJ9yKc','bsf9Xez7','青铜峡市',-1),('NujCd3EC','bsf9Xez7','盐池县',-1),('ABEKV4WF','bsf9Xez7','同心县',-1),('wExWzlbW','bsf9Xez7','其他',-1),('kLkPzwsv','3GjDnDfB','原州区',-1),('qB0lhFEo','3GjDnDfB','西吉县',-1),('FUt8HcYj','3GjDnDfB','隆德县',-1),('dlUtS1Ms','3GjDnDfB','泾源县',-1),('pgVa168y','3GjDnDfB','彭阳县',-1),('iQ0Fo8Gm','3GjDnDfB','其他',-1),('V8lZZdsg','ICqkxCsb','沙坡头区',-1),('sbMQpfz3','ICqkxCsb','中宁县',-1),('TtdP9LHV','ICqkxCsb','海原县',-1),('23QH0PhU','ICqkxCsb','其他',-1),('3bCt2TQl','GC7WWEJT','西宁',0),('8LJxsKPo','GC7WWEJT','海东地区',0),('qsucg187','GC7WWEJT','海北藏族自治州',0),('9ugF5tIs','GC7WWEJT','海南藏族自治州',0),('6sTEL2uY','GC7WWEJT','黄南藏族自治州',0),('ygICUv31','GC7WWEJT','果洛藏族自治州',0),('5cRtlyQZ','GC7WWEJT','玉树藏族自治州',0),('EtsRX4IU','GC7WWEJT','海西蒙古族藏族自治州',0),('b5SksxBq','GC7WWEJT','其他',-1),('ZWoWfEwi','3bCt2TQl','城中区',-1),('AiUpisHc','3bCt2TQl','城东区',-1),('bN6Fm4Y5','3bCt2TQl','城西区',-1),('ZHEKxvrS','3bCt2TQl','城北区',-1),('rwglsj2n','3bCt2TQl','湟源县',-1),('40GejIC5','3bCt2TQl','湟中县',-1),('dStdX9MS','3bCt2TQl','大通回族土族自治县',-1),('BMcgh9Za','3bCt2TQl','其他',-1),('bsMYIWJu','8LJxsKPo','平安县',-1),('TuJy5GVa','8LJxsKPo','乐都县',-1),('KWssek3X','8LJxsKPo','民和回族土族自治县',-1),('xqI1a3mh','8LJxsKPo','互助土族自治县',-1),('Jey06cU3','8LJxsKPo','化隆回族自治县',-1),('sgghzpya','8LJxsKPo','循化撒拉族自治县',-1),('UaP6zozg','8LJxsKPo','其他',-1),('QcibYsN0','qsucg187','海晏县',-1),('fNVtw26a','qsucg187','祁连县',-1),('Sw24jAsx','qsucg187','刚察县',-1),('AoKzkb5d','qsucg187','门源回族自治县',-1),('CKgxUXxI','qsucg187','其他',-1),('naAuVq0g','9ugF5tIs','共和县',-1),('6K1ko02m','9ugF5tIs','同德县',-1),('nMYjYcQb','9ugF5tIs','贵德县',-1),('am0SkOKa','9ugF5tIs','兴海县',-1),('g0wsNO3I','9ugF5tIs','贵南县',-1),('rebr09cQ','9ugF5tIs','其他',-1),('c7QOQK2f','6sTEL2uY','同仁县',-1),('r2yuobxB','6sTEL2uY','尖扎县',-1),('YxMnMqgI','6sTEL2uY','泽库县',-1),('Eq7vsHbw','6sTEL2uY','河南蒙古族自治县',-1),('Gx6QhPdh','6sTEL2uY','其他',-1),('GjSBDTXd','ygICUv31','玛沁县',-1),('HAvIKAsU','ygICUv31','班玛县',-1),('xnGnItOk','ygICUv31','甘德县',-1),('cH2OCJNx','ygICUv31','达日县',-1),('zODSUbQI','ygICUv31','久治县',-1),('oUQhJNKU','ygICUv31','玛多县',-1),('QRokmllJ','ygICUv31','其他',-1),('ms45qQkN','5cRtlyQZ','玉树县',-1),('OqgSOhYB','5cRtlyQZ','杂多县',-1),('hTW25ksb','5cRtlyQZ','称多县',-1),('AsYvEj3Y','5cRtlyQZ','治多县',-1),('KUJrnV9H','5cRtlyQZ','囊谦县',-1),('hBmhz9Ja','5cRtlyQZ','曲麻莱县',-1),('RY0XJ4F5','5cRtlyQZ','其他',-1),('6c1MtbEm','EtsRX4IU','德令哈市',-1),('1M9JXKqt','EtsRX4IU','格尔木市',-1),('zTj6om5l','EtsRX4IU','乌兰县',-1),('or3PZZiL','EtsRX4IU','都兰县',-1),('dNGDLEPC','EtsRX4IU','天峻县',-1),('MLaEi5gb','EtsRX4IU','其他',-1),('wAnG5eHt','6VXWHjs3','乌鲁木齐',0),('7J6k9RT3','6VXWHjs3','克拉玛依',0),('OI3GDjdy','6VXWHjs3','吐鲁番地区',0),('ZOxfNn6u','6VXWHjs3','哈密地区',0),('M4CExZKb','6VXWHjs3','和田地区',0),('uRZhCkw2','6VXWHjs3','阿克苏地区',0),('lSTmiMNO','6VXWHjs3','喀什地区',0),('8ITc7BHY','6VXWHjs3','克孜勒苏柯尔克孜自治州',0),('KYvTAwUG','6VXWHjs3','巴音郭楞蒙古自治州',0),('AB559oCT','6VXWHjs3','昌吉回族自治州',0),('zLYsB2kY','6VXWHjs3','博尔塔拉蒙古自治州',0),('9UpVFc3f','6VXWHjs3','石河子',0),('QCbRkLS2','6VXWHjs3','阿拉尔',0),('7F2CrY6W','6VXWHjs3','图木舒克',0),('wMPkqY2C','6VXWHjs3','五家渠',0),('WeSVIInv','6VXWHjs3','伊犁哈萨克自治州',0),('ErngDW4B','6VXWHjs3','其他',-1),('Yhz6KgX2','wAnG5eHt','天山区',-1),('mPDyxgqo','wAnG5eHt','沙依巴克区',-1),('H3Iwhqel','wAnG5eHt','新市区',-1),('qnH8j3uh','wAnG5eHt','水磨沟区',-1),('braO2QzM','wAnG5eHt','头屯河区',-1),('3D60U4TA','wAnG5eHt','达坂城区',-1),('vhD1z6hB','wAnG5eHt','东山区',-1),('avZFFStO','wAnG5eHt','乌鲁木齐县',-1),('Fq6MZqfO','wAnG5eHt','其他',-1),('AmVSt7FN','7J6k9RT3','克拉玛依区',-1),('WWay83s4','7J6k9RT3','独山子区',-1),('zdM1s2CR','7J6k9RT3','白碱滩区',-1),('9oxFLuIB','7J6k9RT3','乌尔禾区',-1),('8qqh04yt','7J6k9RT3','其他',-1),('s29wd3Yr','OI3GDjdy','吐鲁番市',-1),('UrGliVP2','OI3GDjdy','托克逊县',-1),('YgbW5FRh','OI3GDjdy','鄯善县',-1),('qVP23wid','OI3GDjdy','其他',-1),('PceeAxhK','ZOxfNn6u','哈密市',-1),('gvev4Y2X','ZOxfNn6u','伊吾县',-1),('H5pDdh7S','ZOxfNn6u','巴里坤哈萨克自治县',-1),('MvmxviMA','ZOxfNn6u','其他',-1),('gwOy7IdV','M4CExZKb','和田市',-1),('Xcxw8KIP','M4CExZKb','和田县',-1),('79h9csOa','M4CExZKb','洛浦县',-1),('jWi5h1BT','M4CExZKb','民丰县',-1),('OXnkt9y2','M4CExZKb','皮山县',-1),('TzFuTMx3','M4CExZKb','策勒县',-1),('r09g6SEi','M4CExZKb','于田县',-1),('A7EgDBeq','M4CExZKb','墨玉县',-1),('rAQGEZRs','M4CExZKb','其他',-1),('jSSC64Ta','uRZhCkw2','阿克苏市',-1),('hwMs3i6p','uRZhCkw2','温宿县',-1),('LDLkaBIX','uRZhCkw2','沙雅县',-1),('XPhIgenY','uRZhCkw2','拜城县',-1),('KatzAnF8','uRZhCkw2','阿瓦提县',-1),('hySpeIZi','uRZhCkw2','库车县',-1),('sBX6qw8W','uRZhCkw2','柯坪县',-1),('GzD41O5I','uRZhCkw2','新和县',-1),('0Q737Mfs','uRZhCkw2','乌什县',-1),('Me3scolY','uRZhCkw2','其他',-1),('SNL9LaBh','lSTmiMNO','喀什市',-1),('vcEEx4BO','lSTmiMNO','巴楚县',-1),('4KB0bYPs','lSTmiMNO','泽普县',-1),('21AFnpPi','lSTmiMNO','伽师县',-1),('DHcuAgpV','lSTmiMNO','叶城县',-1),('Uy71N7lC','lSTmiMNO','岳普湖县',-1),('VsGNNjqS','lSTmiMNO','疏勒县',-1),('aosNzIBb','lSTmiMNO','麦盖提县',-1),('V59Fwfj4','lSTmiMNO','英吉沙县',-1),('LVbApRMb','lSTmiMNO','莎车县',-1),('vVbfHJeO','lSTmiMNO','疏附县',-1),('PKZ7dXNV','lSTmiMNO','塔什库尔干塔吉克自治县',-1),('sFKYkznj','lSTmiMNO','其他',-1),('jaBDRNW4','8ITc7BHY','阿图什市',-1),('dYf62Ake','8ITc7BHY','阿合奇县',-1),('d5ZlvBXT','8ITc7BHY','乌恰县',-1),('CC3vIX26','8ITc7BHY','阿克陶县',-1),('JvEJKZJc','8ITc7BHY','其他',-1),('LmsbQlKK','KYvTAwUG','库尔勒市',-1),('jNIW2fWw','KYvTAwUG','和静县',-1),('NjOZ6iGS','KYvTAwUG','尉犁县',-1),('5pFSfD4B','KYvTAwUG','和硕县',-1),('4qowggAl','KYvTAwUG','且末县',-1),('fMgusmmN','KYvTAwUG','博湖县',-1),('BZJeUOms','KYvTAwUG','轮台县',-1),('PKvyTwsp','KYvTAwUG','若羌县',-1),('W7vZZHX6','KYvTAwUG','焉耆回族自治县',-1),('qYB5VlF0','KYvTAwUG','其他',-1),('NJOPd4U6','AB559oCT','昌吉市',-1),('pyHXWMrI','AB559oCT','阜康市',-1),('wSitCl7y','AB559oCT','奇台县',-1),('PespBCOj','AB559oCT','玛纳斯县',-1),('rhqiWEYC','AB559oCT','吉木萨尔县',-1),('UICLKTJ8','AB559oCT','呼图壁县',-1),('7IFB2s7c','AB559oCT','木垒哈萨克自治县',-1),('T5pvqlPP','AB559oCT','米泉市',-1),('tVLOhvwT','AB559oCT','其他',-1),('aBGccX3o','zLYsB2kY','博乐市',-1),('LnB3mZwA','zLYsB2kY','精河县',-1),('shabgsuR','zLYsB2kY','温泉县',-1),('9MzbiOvw','zLYsB2kY','其他',-1),('qZuzBQ2v','WeSVIInv','伊宁市',-1),('04sQfufD','WeSVIInv','奎屯市',-1),('DUqOjb0j','WeSVIInv','伊宁县',-1),('Hs3nGKS1','WeSVIInv','特克斯县',-1),('UPg82mVk','WeSVIInv','尼勒克县',-1),('PNNhsJy5','WeSVIInv','昭苏县',-1),('3zsUzQpL','WeSVIInv','新源县',-1),('OffhUya1','WeSVIInv','霍城县',-1),('JrU8tQB7','WeSVIInv','巩留县',-1),('7AnnS7zH','WeSVIInv','察布查尔锡伯自治县',-1),('aZWF8iaY','WeSVIInv','塔城地区',-1),('S1X4WVhb','WeSVIInv','阿勒泰地区',-1),('kOLGJvMm','WeSVIInv','其他',-1),('f3eojFUR','lHjIQRr9','中西区',-1),('rngL2KyS','lHjIQRr9','湾仔区',-1),('ZuXmaBm4','lHjIQRr9','东区',-1),('3Q9rRDHN','lHjIQRr9','南区',-1),('7liSj0ym','lHjIQRr9','深水埗区',-1),('vtJ2s5U9','lHjIQRr9','油尖旺区',-1),('tsyzRSSA','lHjIQRr9','九龙城区',-1),('hTdE9VMn','lHjIQRr9','黄大仙区',-1),('xoLZsRVk','lHjIQRr9','观塘区',-1),('5Nhj7QYw','lHjIQRr9','北区',-1),('ngMxO0wB','lHjIQRr9','大埔区',-1),('jpM5Wmpk','lHjIQRr9','沙田区',-1),('oBTVG1JL','lHjIQRr9','西贡区',-1),('s0eWKKPH','lHjIQRr9','元朗区',-1),('Qtkcx1tZ','lHjIQRr9','屯门区',-1),('ogyKABge','lHjIQRr9','荃湾区',-1),('kkbVqVAs','lHjIQRr9','葵青区',-1),('FL28sfws','lHjIQRr9','离岛区',-1),('clvMAncC','lHjIQRr9','其他',-1),('UHqrQ4bS','vBbEdPTV','花地玛堂区',-1),('ei9cEF0n','vBbEdPTV','圣安多尼堂区',-1),('Flx14R47','vBbEdPTV','大堂区',-1),('Oxn3EAfD','vBbEdPTV','望德堂区',-1),('dU79sxuz','vBbEdPTV','风顺堂区',-1),('ZnHN8xis','vBbEdPTV','嘉模堂区',-1),('LiJhSH2C','vBbEdPTV','圣方济各堂区',-1),('geZu55QF','vBbEdPTV','路凼',-1),('gRabXtmF','vBbEdPTV','其他',-1),('JwnSspWw','JMoyEtgG','台北市',-1),('RnkoYZvB','JMoyEtgG','高雄市',-1),('OLeo4YKm','JMoyEtgG','台北县',-1),('yCFsp9wF','JMoyEtgG','桃园县',-1),('oOhMwjsz','JMoyEtgG','新竹县',-1),('WftIfHrR','JMoyEtgG','苗栗县',-1),('RIIfqZvg','JMoyEtgG','台中县',-1),('EhQaeHJv','JMoyEtgG','彰化县',-1),('igHzsCQw','JMoyEtgG','南投县',-1),('lwXljXDN','JMoyEtgG','云林县',-1),('RoFWiIlK','JMoyEtgG','嘉义县',-1),('89xW1TGp','JMoyEtgG','台南县',-1),('lvZysaDY','JMoyEtgG','高雄县',-1),('YJgupQqw','JMoyEtgG','屏东县',-1),('TWjHoBzT','JMoyEtgG','宜兰县',-1),('UbUFpDBT','JMoyEtgG','花莲县',-1),('DxWVJNs1','JMoyEtgG','台东县',-1),('Warwa2P2','JMoyEtgG','澎湖县',-1),('OqaK9wM2','JMoyEtgG','基隆市',-1),('JTxX7Voc','JMoyEtgG','新竹市',-1),('aKfnPLYd','JMoyEtgG','台中市',-1),('YCxJQopM','JMoyEtgG','嘉义市',-1),('x1aJWEO4','JMoyEtgG','台南市',-1),('3wNT6V2I','JMoyEtgG','其他',-1),('baYm6FT7','cAF7QoX2','其他',-1);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `handing_buy`
--

DROP TABLE IF EXISTS `handing_buy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `handing_buy` (
  `id` varchar(100) NOT NULL DEFAULT '',
  `handingType` varchar(100) NOT NULL DEFAULT '',
  `souCityId` varchar(100) NOT NULL DEFAULT '',
  `message` varchar(500) NOT NULL DEFAULT '',
  `userId` varchar(100) NOT NULL DEFAULT '',
  `pushTime` bigint(20) NOT NULL,
  `timeLimit` bigint(20) NOT NULL,
  `status` int(11) DEFAULT '0',
  `supplyUserId` varchar(200) DEFAULT NULL,
  `supplyWinTime` bigint(100) DEFAULT '0',
  `salesmanId` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `handing_buy`
--

LOCK TABLES `handing_buy` WRITE;
/*!40000 ALTER TABLE `handing_buy` DISABLE KEYS */;
INSERT INTO `handing_buy` VALUES ('0ZRcyknEkUhT','整卷油磨','x6vMHzA3','aaaa','qfhHS6oSfTqb',1468761252743,259200000,1,'527cec6a380046b5b813537e10d065e9',1468761481404,0),('6jLrBmDgtTZt','整卷油磨','5a8QXEaX','safasdf','qfhHS6oSfTqb',1468760800769,259200000,1,'527cec6a380046b5b813537e10d065e9',1468761179743,0),('hE2XYCAk7RAu','整卷油磨','o73vuTTJ','12*12*98','qfhHS6oSfTqb',1468757844755,259200000,1,'527cec6a380046b5b813537e10d065e9',1468758394209,0);
/*!40000 ALTER TABLE `handing_buy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `handing_buy_seller`
--

DROP TABLE IF EXISTS `handing_buy_seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `handing_buy_seller` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `handingId` varchar(200) NOT NULL DEFAULT '',
  `sellerId` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `handing_buy_seller`
--

LOCK TABLES `handing_buy_seller` WRITE;
/*!40000 ALTER TABLE `handing_buy_seller` DISABLE KEYS */;
INSERT INTO `handing_buy_seller` VALUES (37,'0ZRcyknEkUhT','1111'),(36,'0ZRcyknEkUhT','tykuj'),(35,'0ZRcyknEkUhT','fggg'),(34,'0ZRcyknEkUhT','dfdfdf'),(33,'0ZRcyknEkUhT','527cec6a380046b5b813537e10d065e9'),(32,'6jLrBmDgtTZt','1111'),(31,'6jLrBmDgtTZt','tykuj'),(30,'6jLrBmDgtTZt','fggg'),(29,'6jLrBmDgtTZt','dfdfdf'),(28,'6jLrBmDgtTZt','527cec6a380046b5b813537e10d065e9'),(27,'hE2XYCAk7RAu','1111'),(26,'hE2XYCAk7RAu','tykuj'),(25,'hE2XYCAk7RAu','fggg'),(24,'hE2XYCAk7RAu','dfdfdf'),(23,'hE2XYCAk7RAu','527cec6a380046b5b813537e10d065e9');
/*!40000 ALTER TABLE `handing_buy_seller` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `handing_buy_supply`
--

DROP TABLE IF EXISTS `handing_buy_supply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `handing_buy_supply` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `handingId` varchar(200) NOT NULL DEFAULT '',
  `sellerId` varchar(200) NOT NULL DEFAULT '',
  `supplyPrice` float NOT NULL DEFAULT '0',
  `supplyMsg` varchar(1000) NOT NULL DEFAULT '',
  `status` int(11) DEFAULT '0',
  `salesmanId` int(11) DEFAULT '0',
  `unit` varchar(100) DEFAULT 'kg',
  `offerTime` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `handing_buy_supply`
--

LOCK TABLES `handing_buy_supply` WRITE;
/*!40000 ALTER TABLE `handing_buy_supply` DISABLE KEYS */;
INSERT INTO `handing_buy_supply` VALUES (7,'0ZRcyknEkUhT','527cec6a380046b5b813537e10d065e9',141,'asdf',0,0,'平方米',1468761429690),(6,'6jLrBmDgtTZt','527cec6a380046b5b813537e10d065e9',122,'hahahahah',0,0,'米',1468760946940),(5,'hE2XYCAk7RAu','527cec6a380046b5b813537e10d065e9',13,'旋涡吧，我比较吊',0,0,'kg',1468757913821);
/*!40000 ALTER TABLE `handing_buy_supply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `handing_product`
--

DROP TABLE IF EXISTS `handing_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `handing_product` (
  `id` varchar(100) NOT NULL DEFAULT '',
  `type` varchar(200) NOT NULL DEFAULT '',
  `souCityId` varchar(100) NOT NULL DEFAULT '',
  `title` varchar(200) NOT NULL DEFAULT '',
  `price` float NOT NULL,
  `unit` varchar(100) NOT NULL DEFAULT '',
  `cover` varchar(200) NOT NULL DEFAULT '',
  `image1` varchar(200) DEFAULT NULL,
  `image2` varchar(200) DEFAULT NULL,
  `image3` varchar(200) DEFAULT NULL,
  `userId` varchar(100) NOT NULL DEFAULT '',
  `reviewed` tinyint(4) NOT NULL DEFAULT '0',
  `score` float NOT NULL DEFAULT '0',
  `pushTime` bigint(100) DEFAULT '0',
  `refuseMessage` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `handing_product`
--

LOCK TABLES `handing_product` WRITE;
/*!40000 ALTER TABLE `handing_product` DISABLE KEYS */;
INSERT INTO `handing_product` VALUES ('A2amYGBYWgVu','整卷油磨','wYC4sP3V','sss',199,'米','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','527cec6a380046b5b813537e10d065e9',1,0,0,NULL),('5Gpr2IaEUKm9','整卷油磨','wYC4sP3V','sssfeng',1233,'米','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','527cec6a380046b5b813537e10d065e9',1,2,0,NULL),('IHNg9WXvwLci','整卷油磨','wYC4sP3V','sss',222,'米','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','527cec6a380046b5b813537e10d065e9',2,0,0,'chou yi bi'),('vI2aYlNGorTS','激光切割','wYC4sP3V','sss',4111,'米','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','527cec6a380046b5b813537e10d065e9',1,5,0,'pass'),('afsdfffffsdf','整卷油磨','wYC4sP3V','sssfeng',1233,'米','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','527cec6a380046b5b813537e10d065e9',1,2,0,NULL),('aasdfasdfasdff','整卷油磨','wYC4sP3V','sss',222,'米','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','527cec6a380046b5b813537e10d065e9',2,0,0,'chou yi bi'),('ga23df1212','激光切割','wYC4sP3V','sss',4111,'米','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','527cec6a380046b5b813537e10d065e9',1,5,0,'pass');
/*!40000 ALTER TABLE `handing_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `help_find_product`
--

DROP TABLE IF EXISTS `help_find_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `help_find_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `applyTime` bigint(100) NOT NULL,
  `mobile` varchar(45) COLLATE utf8_bin NOT NULL,
  `type` varchar(100) COLLATE utf8_bin NOT NULL,
  `city` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `comment` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `spec` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `help_find_product`
--

LOCK TABLES `help_find_product` WRITE;
/*!40000 ALTER TABLE `help_find_product` DISABLE KEYS */;
INSERT INTO `help_find_product` VALUES (1,13333123,'18355551276','1231','安徽','哈哈哈哈哈哈',NULL);
/*!40000 ALTER TABLE `help_find_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inner_message`
--

DROP TABLE IF EXISTS `inner_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inner_message` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(300) NOT NULL DEFAULT ' ',
  `message` text NOT NULL,
  `userId` varchar(100) NOT NULL DEFAULT '',
  `reviewed` tinyint(4) NOT NULL DEFAULT '0',
  `pushTime` bigint(100) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inner_message`
--

LOCK TABLES `inner_message` WRITE;
/*!40000 ALTER TABLE `inner_message` DISABLE KEYS */;
INSERT INTO `inner_message` VALUES (1,'欢迎注册淘不秀','淘不秀是很厉害的哈哈哈哈哈哈哈哈哈\n淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈','527cec6a380046b5b813537e10d065e9',1,1468135042000),(2,'恭喜您成为卖家','淘不秀是很厉害的哈哈哈哈哈哈哈哈哈\n淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈','527cec6a380046b5b813537e10d065e9',1,1468135042000),(3,'恭喜您成为卖家','淘不秀是很厉害的哈哈哈哈哈哈哈哈哈\n淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈','527cec6a380046b5b813537e10d065e9',1,1468135042000),(4,'恭喜您成为卖家','淘不秀是很厉害的哈哈哈哈哈哈哈哈哈\n淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈','527cec6a380046b5b813537e10d065e9',1,1468135042000),(5,'恭喜您成为卖家','淘不秀是很厉害的哈哈哈哈哈哈哈哈哈\n淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈','527cec6a380046b5b813537e10d065e9',0,1468135042000),(6,'恭喜您成为卖家','淘不秀是很厉害的哈哈哈哈哈哈哈哈哈\n淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈','527cec6a380046b5b813537e10d065e9',1,1468135042000),(7,'恭喜您成为卖家','淘不秀是很厉害的哈哈哈哈哈哈哈哈哈\n淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈淘不秀是很厉害的哈哈哈哈哈哈哈哈哈','527cec6a380046b5b813537e10d065e9',0,1468135042000),(8,'恭喜您申请成为商家成功！','恭喜您成功入住淘不锈','5afa98c48214438dad364113e3a82ce1',0,1468575606394),(9,'恭喜您申请成为商家成功！','恭喜您成功入住淘不锈','23',0,1468575666094),(10,'很抱歉，申请成为商家失败！','很抱歉，申请成为商家失败！','yjyy',0,1468575712949),(11,'很抱歉，申请成为商家失败！','很抱歉，申请成为商家失败！','1123123',0,1468575898066),(12,'很抱歉，申请成为商家失败！','很抱歉，申请成为商家失败！','fdfdf',0,1468577995888),(13,'恭喜资源失败！','很抱歉，恭喜资源失败！','8mqBXAuVnxZ9',0,1468580166040),(14,'恭喜资源成功','恭喜资源成功','Cmxxa08XqnVV',0,1468580215087),(15,'恭喜资源成功','恭喜资源成功','527cec6a380046b5b813537e10d065e9',1,1468580465895),(16,'恭喜资源失败！','很抱歉，恭喜资源失败！','527cec6a380046b5b813537e10d065e9',0,1468580465898),(17,'恭喜资源成功','恭喜资源成功','527cec6a380046b5b813537e10d065e9',0,1468580471399),(18,'恭喜资源失败！','很抱歉，恭喜资源失败！','527cec6a380046b5b813537e10d065e9',0,1468580471402),(19,'恭喜资源成功','恭喜资源成功','527cec6a380046b5b813537e10d065e9',0,1468581638125),(20,'恭喜资源失败！','很抱歉，恭喜资源失败！','527cec6a380046b5b813537e10d065e9',1,1468581645470),(21,'112','123','527cec6a380046b5b813537e10d065e9',1,1468595236344),(22,'欢迎加入淘不秀','淘不秀好得很淘不秀好得很淘不秀好得很淘不秀好得很淘不秀好得很淘不秀好得很','qfhHS6oSfTqb',1,1468743676541),(23,'恭喜您成功中标','您已经被买家加工求购中标','527cec6a380046b5b813537e10d065e9',0,1468758394215),(24,'恭喜您成功中标','您已经被买家加工求购中标','527cec6a380046b5b813537e10d065e9',0,1468761179747),(25,'恭喜您成功中标','您已经被买家加工求购中标','527cec6a380046b5b813537e10d065e9',0,1468761481407);
/*!40000 ALTER TABLE `inner_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inner_news`
--

DROP TABLE IF EXISTS `inner_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inner_news` (
  `id` varchar(100) NOT NULL DEFAULT '',
  `title` varchar(300) NOT NULL DEFAULT '',
  `content` mediumtext NOT NULL,
  `pushTime` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inner_news`
--

LOCK TABLES `inner_news` WRITE;
/*!40000 ALTER TABLE `inner_news` DISABLE KEYS */;
INSERT INTO `inner_news` VALUES ('jHIDAS012','淘不锈电商商城正式上线','淘不锈电商商城正式上线淘不锈电商商城正式上线淘不锈电商商城正式上线淘不锈电商商城正式上线',1466265600084),('jHIDAS02','淘不锈电商商城客户端版本隆重推出','淘不锈电商商城客户端版本隆重推出淘不锈电商商城客户端版本隆重推出',1466260600084),('iHIDAS011211','打造不锈钢产业一条龙服务生产线','打造不锈钢产业一条龙服务生产线打造不锈钢产业一条龙服务生产线',1466260120084),('iHI5AS01331','卖家买家放心入驻','卖家买家放心入驻卖家买家放心入驻',1466260120084);
/*!40000 ALTER TABLE `inner_news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iron_buy`
--

DROP TABLE IF EXISTS `iron_buy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `length` bigint(20) NOT NULL,
  `width` bigint(20) NOT NULL,
  `height` bigint(20) NOT NULL,
  `tolerance` varchar(100) NOT NULL DEFAULT '',
  `numbers` bigint(20) NOT NULL,
  `timeLimit` bigint(20) NOT NULL,
  `status` int(11) DEFAULT '0',
  `supplyUserId` varchar(200) DEFAULT NULL,
  `supplyWinTime` bigint(100) DEFAULT '0',
  `salesmanId` int(10) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iron_buy`
--

LOCK TABLES `iron_buy` WRITE;
/*!40000 ALTER TABLE `iron_buy` DISABLE KEYS */;
INSERT INTO `iron_buy` VALUES ('7Hb0olEmuQcJ','不锈钢卷','201(L1,L2)','No.1','太钢','S3h5IOdU','527cec6a380046b5b813537e10d065e9','1212',1468761512568,121221,212,212,'1-12',100,86400000,0,NULL,0,2),('yeQUxBlTMm8k','不锈钢卷','201(L1,L2)','No.1','太钢','1OvMsymh','qfhHS6oSfTqb','我想要不锈钢卷',1468757776767,134,14,33,'1-3',34,86400000,1,'527cec6a380046b5b813537e10d065e9',1468758542519,0);
/*!40000 ALTER TABLE `iron_buy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iron_buy_seller`
--

DROP TABLE IF EXISTS `iron_buy_seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iron_buy_seller` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ironId` varchar(200) NOT NULL DEFAULT '',
  `sellerId` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iron_buy_seller`
--

LOCK TABLES `iron_buy_seller` WRITE;
/*!40000 ALTER TABLE `iron_buy_seller` DISABLE KEYS */;
INSERT INTO `iron_buy_seller` VALUES (4,'yeQUxBlTMm8k','527cec6a380046b5b813537e10d065e9');
/*!40000 ALTER TABLE `iron_buy_seller` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iron_buy_supply`
--

DROP TABLE IF EXISTS `iron_buy_supply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iron_buy_supply` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ironId` varchar(200) NOT NULL DEFAULT '',
  `sellerId` varchar(200) NOT NULL DEFAULT '',
  `supplyPrice` float NOT NULL DEFAULT '0',
  `supplyMsg` varchar(1000) NOT NULL DEFAULT '',
  `status` int(11) DEFAULT '0',
  `salesmanId` int(11) DEFAULT '0',
  `unit` varchar(100) DEFAULT 'kg',
  `offerTime` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iron_buy_supply`
--

LOCK TABLES `iron_buy_supply` WRITE;
/*!40000 ALTER TABLE `iron_buy_supply` DISABLE KEYS */;
INSERT INTO `iron_buy_supply` VALUES (4,'yeQUxBlTMm8k','527cec6a380046b5b813537e10d065e9',13,'旋涡，我更吊',0,0,'kg',1468757923843);
/*!40000 ALTER TABLE `iron_buy_supply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iron_product`
--

DROP TABLE IF EXISTS `iron_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iron_product` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `proId` varchar(100) NOT NULL DEFAULT '',
  `surface` varchar(100) NOT NULL DEFAULT '',
  `ironType` varchar(100) NOT NULL DEFAULT '',
  `proPlace` varchar(100) NOT NULL DEFAULT '',
  `material` varchar(100) NOT NULL DEFAULT '',
  `sourceCityId` varchar(100) NOT NULL DEFAULT '',
  `title` varchar(100) NOT NULL DEFAULT '',
  `price` float NOT NULL,
  `cover` varchar(100) NOT NULL DEFAULT '',
  `image1` varchar(100) DEFAULT NULL,
  `image2` varchar(100) DEFAULT NULL,
  `image3` varchar(100) DEFAULT NULL,
  `isSpec` tinyint(4) NOT NULL DEFAULT '0',
  `userId` varchar(100) NOT NULL DEFAULT '',
  `pushTime` bigint(100) DEFAULT NULL,
  `numbers` bigint(20) NOT NULL,
  `reviewed` tinyint(4) NOT NULL DEFAULT '0',
  `score` float DEFAULT '0',
  `refuseMessage` varchar(500) DEFAULT NULL,
  `spec` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iron_product`
--

LOCK TABLES `iron_product` WRITE;
/*!40000 ALTER TABLE `iron_product` DISABLE KEYS */;
INSERT INTO `iron_product` VALUES (11,'3tp0TCGYqjXk','No.1','不锈钢管','宝新','201(L1,L2)','SzC01FCt','AAAAAA',133,'./files/2016/6/19/WdDfdnobGk7Y.jpg',NULL,NULL,NULL,1,'527cec6a380046b5b813537e10d065e9',2016,123123,1,0,NULL,NULL),(14,'7USwfNZuL2IV','No.1','不锈钢管','宝新','201(L1,L2)','SzC01FCt','AAAAAA',100,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',1,'527cec6a380046b5b813537e10d065e9',2016,0,1,0,NULL,NULL),(15,'8mqBXAuVnxZ9','No.1','不锈钢管','宝新','201(L1,L2)','SzC01FCt','AAAAAA',33,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',1,'527cec6a380046b5b813537e10d065e9',2016,10086,1,0,'pass',NULL),(16,'Cmxxa08XqnVV','No.1','不锈钢管','宝新','201(L1,L2)','SzC01FCt','AAAAAA',113,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',0,'527cec6a380046b5b813537e10d065e9',2016,0,1,0,NULL,NULL),(17,'lW3zEDexjb1v','No.1','不锈钢管','宝新','201(L1,L2)','SzC01FCt','AAAAAA',44,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',1,'527cec6a380046b5b813537e10d065e9',2016,0,2,0,'照片太丑',NULL),(18,'rDydI0kSlq5l','No.1','不锈钢管','宝新','201(L1,L2)','SzC01FCt','AAAAAAB',444,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',1,'527cec6a380046b5b813537e10d065e9',1465112350182,0,0,1,NULL,NULL),(19,'1b9GAlY1sGvr','No.1','不锈钢管','宝新','201(L1,L2)','SzC01FCt','风马',54,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',1,'527cec6a380046b5b813537e10d065e9',1465112434388,0,1,0,NULL,NULL),(20,'dwzL4HiPwEeG','No.1','不锈钢管','宝新','409L','SzC01FCt','风马',334,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',1,'527cec6a380046b5b813537e10d065e9',1465112515586,0,1,0,NULL,NULL),(21,'fDiY9RwZmbtq','No.1','不锈钢管','宝新','409L','SzC01FCt','风马',32423,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',0,'527cec6a380046b5b813537e10d065e9',1466227033813,0,1,0,NULL,NULL),(22,'jm7CnzdXsfyF','No.1','不锈钢管','宝新','409L','SzC01FCt','风马',233,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',1,'527cec6a380046b5b813537e10d065e9',1466227071566,0,0,4,NULL,NULL),(23,'UtbE3Ne8lUXm','No.1','不锈钢管','宝新','409L','SzC01FCt','风马',2344,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',1,'527cec6a380046b5b813537e10d065e9',1466227160921,0,1,0,NULL,NULL),(24,'tqASvVRGZZmX','No.1','不锈钢管','宝新','409L','SzC01FCt','风马',5555,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',1,'527cec6a380046b5b813537e10d065e9',1466227170221,0,1,0,NULL,NULL),(25,'dHQn66Pe7bhu','No.1','不锈钢管','宝新','409L','SzC01FCt','风马',23444,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',0,'527cec6a380046b5b813537e10d065e9',1466227212183,0,1,0,NULL,NULL),(26,'bJxDcHkLstJu','No.1','不锈钢管','宝新','409L','SzC01FCt','风马',222,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',1,'527cec6a380046b5b813537e10d065e9',1466227226321,0,1,0,NULL,NULL),(27,'gyI0qkiALB6K','No.1','角钢','宝新哈','409L','SzC01FCt','风马',2234,'./files/2016/6/19/WdDfdnobGk7Y.jpg','','','',1,'527cec6a380046b5b813537e10d065e9',1466227259973,123,1,0,NULL,NULL),(28,'5MO2BeOniuF2','No.1','角钢','宝新','409L','SzC01FCt','123*123*333',222,'./files/2016/6/25/ng0CSGIT3TdM.jpg','','','',1,'527cec6a380046b5b813537e10d065e9',1466822703282,1123123,0,0,NULL,NULL),(29,'UCJNhdQG0LjC','No.1','角钢','宝新','409L','SzC01FCt','风马112',876,'./files/2016/6/25/4HSBoMIb3aEu.jpg','','','',0,'527cec6a380046b5b813537e10d065e9',1466836239899,868,1,0,NULL,NULL);
/*!40000 ALTER TABLE `iron_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` varchar(100) NOT NULL DEFAULT '',
  `title` varchar(300) NOT NULL DEFAULT '',
  `content` mediumtext NOT NULL,
  `pushTime` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES ('iHI6AS011','这是行业新闻1','这是行业新闻1这是行业新闻1这是行业新闻1这是行业新闻1这是行业新闻1这是行业新闻1这是行业新闻1这是行业新闻1这是行业新闻1这是行业新闻1这是行业新闻1这是行业新闻1',1468823382617),('iHI6AS012','这是行业新闻2','这是行业新闻2这是行业新闻2',1466260120800),('iHI6AS013','这是行业新闻3','这是行业新闻3这是行业新闻3',1466260120084),('iHI6AS014','这是行业新闻41','这是行业新闻4这是行业新闻4223',1468823398576),('iHI6AS015','这是行业新闻5','这是行业新闻5这是行业新闻5',1466260120084),('iHI6AS016','这是行业新闻6','这是行业新闻6这是行业新闻6',1466260120084),('iHI6AS017','这是行业新闻7','这是行业新闻7这是行业新闻7',1466260120084),('iHI6AS018','这是行业新闻8','这是行业新闻8这是行业新闻8',1466260120084),('iHI6AS019','这是行业新闻9','这是行业新闻9这是行业新闻9',1466260120084),('2ebPLmEPXvXJ','fasdf ','asdfasdfasdfasdf',1468823404672);
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_car`
--

DROP TABLE IF EXISTS `order_car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_car` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userId` varchar(200) NOT NULL DEFAULT '',
  `proId` varchar(200) NOT NULL DEFAULT '',
  `productType` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_car`
--

LOCK TABLES `order_car` WRITE;
/*!40000 ALTER TABLE `order_car` DISABLE KEYS */;
INSERT INTO `order_car` VALUES (9,'527cec6a380046b5b813537e10d065e9','3tp0TCGYqjXk',0),(8,'527cec6a380046b5b813537e10d065e9','3tp0TCGYqjXk',0);
/*!40000 ALTER TABLE `order_car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_orders`
--

DROP TABLE IF EXISTS `product_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_orders` (
  `id` varchar(200) NOT NULL DEFAULT '',
  `buyerId` varchar(100) NOT NULL DEFAULT '',
  `sellerId` varchar(100) NOT NULL DEFAULT '',
  `productId` varchar(100) DEFAULT NULL,
  `productType` int(11) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `salesmanId` int(11) DEFAULT NULL,
  `sellTime` bigint(100) NOT NULL,
  `timeLimit` bigint(20) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0：买家已下单，1，卖家已确认,未评价 2,卖家已确认并且买家已评价',
  `ironCount` bigint(11) DEFAULT '0',
  `ironPrice` float DEFAULT '0',
  `singleScore` float DEFAULT '0',
  `totalMoney` float unsigned NOT NULL DEFAULT '0',
  `deleteBy` int(4) DEFAULT '0',
  `cancelBy` int(11) DEFAULT '0',
  `finishTime` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_orders`
--

LOCK TABLES `product_orders` WRITE;
/*!40000 ALTER TABLE `product_orders` DISABLE KEYS */;
INSERT INTO `product_orders` VALUES ('2','527cec6a380046b5b813537e10d065e9','111111','8mqBXAuVnxZ9',0,12,3,0,199,3,990,22,11,11,0,3,1465956800000),('3','527cec6a380046b5b813537e10d065e9','5afa98c48214438dad364113e3a82ce9','8mqBXAuVnxZ9',0,12,3,0,199,4,0,0,11,22,1,3,1468650283000),('4','5afa98c48214438dad364113e3a82ce9','527cec6a380046b5b813537e10d065e9','gyI0qkiALB6K',0,12,3,0,199,1,100,11,11,33,0,NULL,1465956800000),('12016062601','5afa98c48214438dad364113e3a82ce9','527cec6a380046b5b813537e10d065e9','vI2aYlNGorTS',1,909,3,1466915023547,1466915024547,1,0,0,11,123,0,NULL,1468650283000),('02016070600','527cec6a380046b5b813537e10d065e9','5afa98c48214438dad364113e3a82ce9','Cmxxa08XqnVV',0,2,3,1467816854031,1467816855031,4,0,0,0,0,2,2,1468650283000),('02016070601','5afa98c48214438dad364113e3a82ce9','527cec6a380046b5b813537e10d065e9','Cmxxa08XqnVV',0,2,3,1467817005348,1467817006348,4,0,0,0,0,3,NULL,1468650283000),('02016070602','527cec6a380046b5b813537e10d065e9','5afa98c48214438dad364113e3a82ce9','Cmxxa08XqnVV',0,2,3,1467817041894,1467817042894,4,0,0,0,0,1,NULL,1468650283000),('12016070603','5afa98c48214438dad364113e3a82ce9','527cec6a380046b5b813537e10d065e9','IHNg9WXvwLci',1,3,3,1467817041894,1467817042894,3,0,0,0,0,0,2,1468650283000),('02016070704','527cec6a380046b5b813537e10d065e9','5afa98c48214438dad364113e3a82ce9','lW3zEDexjb1v',0,3,3,1467898542895,1467898642898,0,0,0,0,0,0,NULL,1465956800000),('12016071000','5afa98c48214438dad364113e3a82ce9','527cec6a380046b5b813537e10d065e9','vI2aYlNGorTS',1,1,3,1468153551334,1468153552334,1,0,0,0,4111,0,0,1468722877192),('02016071700','527cec6a380046b5b813537e10d065e9','527cec6a380046b5b813537e10d065e9','3tp0TCGYqjXk',0,10,2,1468694380701,1468694467101,3,0,0,0,4444340,0,2,0),('02016071701','527cec6a380046b5b813537e10d065e9','527cec6a380046b5b813537e10d065e9','8mqBXAuVnxZ9',0,2,2,1468724556780,1468724655360,3,0,0,0,20174,0,2,0),('02016071702','527cec6a380046b5b813537e10d065e9','527cec6a380046b5b813537e10d065e9','3tp0TCGYqjXk',0,11,2,1468725756911,1468725843311,3,0,0,0,4888770,0,2,0),('02016071703','527cec6a380046b5b813537e10d065e9','527cec6a380046b5b813537e10d065e9','3tp0TCGYqjXk',0,38,2,1468725874746,1468726051146,1,0,0,0,16888500,0,0,1468742843623),('02016071704','qfhHS6oSfTqb','527cec6a380046b5b813537e10d065e9','3tp0TCGYqjXk',0,13,0,1468744790461,1468744876861,4,0,0,0,1729,1,0,0),('02016071705','qfhHS6oSfTqb','527cec6a380046b5b813537e10d065e9','3tp0TCGYqjXk',0,10,0,1468745897700,86400,4,0,0,0,1330,1,3,0),('02016071706','qfhHS6oSfTqb','527cec6a380046b5b813537e10d065e9','3tp0TCGYqjXk',0,13,0,1468746491610,86400,3,0,0,0,1729,0,3,0),('02016071707','qfhHS6oSfTqb','527cec6a380046b5b813537e10d065e9','dwzL4HiPwEeG',0,12,0,1468749806243,86400000,2,0,0,9,4008,0,0,1468750214954),('02016071708','qfhHS6oSfTqb','527cec6a380046b5b813537e10d065e9','3tp0TCGYqjXk',0,2,0,1468750024400,432000000,3,0,0,0,266,0,2,0),('02016071709','qfhHS6oSfTqb','527cec6a380046b5b813537e10d065e9','bJxDcHkLstJu',0,21,0,1468750491591,1900800000,2,0,0,4,4662,0,0,1468750600862),('020160717010','qfhHS6oSfTqb','527cec6a380046b5b813537e10d065e9','dwzL4HiPwEeG',0,2,0,1468750491606,1900800000,3,0,0,0,668,0,2,0);
/*!40000 ALTER TABLE `product_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recruit`
--

DROP TABLE IF EXISTS `recruit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recruit` (
  `id` varchar(100) NOT NULL DEFAULT '',
  `pushTime` bigint(20) NOT NULL DEFAULT '0',
  `companyName` varchar(200) NOT NULL,
  `place` varchar(200) NOT NULL,
  `tel` varchar(100) NOT NULL,
  `position` varchar(200) NOT NULL,
  `salary` varchar(100) NOT NULL,
  `welfare` mediumtext NOT NULL,
  `description` mediumtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recruit`
--

LOCK TABLES `recruit` WRITE;
/*!40000 ALTER TABLE `recruit` DISABLE KEYS */;
INSERT INTO `recruit` VALUES ('2334234',1468830637131,'风马科技有限公司','上海','18899091212','搬砖2','9090/月','烈日诱惑','烈日诱惑烈日诱惑烈日诱惑烈日诱惑烈日诱惑'),('kv2az0BPhgpl',1468830637131,'风马科技有限公司','上海','18899091212','搬砖1','9090/月','烈日诱惑','烈日诱惑烈日诱惑烈日诱惑烈日诱惑烈日诱惑'),('kv2az0BPh34gpl',1468830637131,'风马科技有限公司','上海','18899091212','搬砖3','9090/月','烈日诱惑','烈日诱惑烈日诱惑烈日诱惑烈日诱惑烈日诱惑'),('kv2az0BP223hgpl',1468830637131,'风马科技有限公司','上海','18899091212','搬砖4','9090/月','烈日诱惑','烈日诱惑烈日诱惑烈日诱惑烈日诱惑烈日诱惑'),('kv2az0BP33hgpl',1468830637131,'风马科技有限公司','上海','18899091212','搬砖5','9090/月','烈日诱惑','烈日诱惑烈日诱惑烈日诱惑烈日诱惑烈日诱惑');
/*!40000 ALTER TABLE `recruit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salesman`
--

DROP TABLE IF EXISTS `salesman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salesman` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `tel` varchar(100) NOT NULL DEFAULT '',
  `bindTime` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salesman`
--

LOCK TABLES `salesman` WRITE;
/*!40000 ALTER TABLE `salesman` DISABLE KEYS */;
INSERT INTO `salesman` VALUES (0,'','',NULL),(2,'将三米s','18388888888',NULL),(3,'雷米','13908082323',NULL),(4,'asdfasdf','18355551237',NULL);
/*!40000 ALTER TABLE `salesman` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller`
--

DROP TABLE IF EXISTS `seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  `cover` varchar(200) NOT NULL DEFAULT '',
  `ironTypeDesc` varchar(300) DEFAULT '',
  `handingTypeDesc` varchar(300) DEFAULT '',
  `salesmanId` int(11) DEFAULT '-1',
  `productCount` int(11) DEFAULT '0',
  `monthSellCount` int(11) DEFAULT '0',
  `monthSellMoney` float DEFAULT '0',
  `score` float DEFAULT '0',
  `passed` tinyint(4) DEFAULT '0',
  `passTime` bigint(20) DEFAULT '0',
  `winningTimes` int(11) DEFAULT '0',
  `integral` float DEFAULT '0',
  `reviewed` tinyint(4) DEFAULT '0',
  `applyTime` bigint(20) DEFAULT '0',
  `productNumbers` int(11) DEFAULT '0',
  `refuseMessage` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller`
--

LOCK TABLES `seller` WRITE;
/*!40000 ALTER TABLE `seller` DISABLE KEYS */;
INSERT INTO `seller` VALUES (11,'527cec6a380046b5b813537e10d065e9','风马科技有限公司',1000,'再不斩','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','绝对非凡，值得拥有','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','整卷油磨,激光切割',-1,0,0,0,0,1,0,0,32,0,0,0,NULL),(12,'dfdfdf','三米科技有限公司',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','我家只卖高端产品','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','整卷油磨,激光切割',-1,0,0,0,1,2,0,0,0,0,0,0,NULL),(13,'5afa98c48214438dad364113e3a82ce1','三米科技有限公司',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,3,1,0,0,0,0,0,111,NULL),(14,'fffff','崂山科技有限公司',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,2,1,0,0,0,0,0,2,NULL),(15,'nhnh','崂山科技有限公司',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,1,0,0,0,0,0,332,NULL),(16,'23','崂山科技有限公司',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','不锈钢管,角钢',-1,0,0,0,1,1,0,0,0,0,0,0,NULL),(17,'1123123','崂山科技有限公司',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,2,0,0,0,0,0,0,NULL),(18,'0000','崂山科技有限公司',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','不锈钢管,角钢',-1,0,0,0,1,0,0,0,0,0,0,0,NULL),(19,'fdfdf','崂山科技有限公司',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,2,0,0,0,0,0,0,'AFASDASDASD'),(20,'fggg','崂山科技有限公司',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','','整卷油磨,激光切割',-1,0,0,0,1,0,0,0,0,0,0,0,NULL),(21,'yjyy','崂山科技有限公司',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,2,0,0,0,0,0,0,NULL),(22,'23123123','崂山科技有限公司',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,0,0,0,0,0,0,0,NULL),(23,'fgfgde','崂山科技有限公司14',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,0,0,0,0,0,0,0,NULL),(24,'fdvfvfv','崂山科技有限公司',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,0,0,0,0,0,0,0,NULL),(25,'tykuj','崂山科技有限公司13',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','','整卷油磨,激光切割',-1,0,0,0,4,0,0,0,0,0,0,0,NULL),(26,'dfdfsafdf','崂山科技有限公司12',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,0,0,0,0,0,0,0,NULL),(27,'23123','崂山科技有限公司11',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,3,0,0,0,0,0,0,0,NULL),(28,'gtt','崂山科技有限公司10',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,0,0,0,0,0,0,0,NULL),(29,'222','崂山科技有限公司9',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,0,0,0,0,0,0,0,NULL),(30,'5afa98c48214438dad364113e3a82ce9','崂山科技有限公司8',1000,'taoshan','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,2,0,0,0,0.4,0,0,0,NULL),(31,'1111','崂山科技有限公司3',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','','整卷油磨,激光切割',-1,0,0,0,1,0,0,0,0,0,0,0,NULL),(32,'ggfg','崂山科技有限公司4',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,0,0,0,0,0,0,0,NULL),(33,'asdfaw','崂山科技有限公司5',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','./files/2016/6/19/WdDfdnobGk7Y.jpg','','','','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,0,0,0,0,0,0,0,NULL),(35,'12gsdfsadf','崂山科技有限公司7',1000,'将三米','18355551276','910-123123123','F1HgsYAp','安徽 宣城 东里杰','578323123','这是一个非常吊的项目','','./files/2016/6/19/WdDfdnobGk7Y.jpg','./files/2016/6/19/WdDfdnobGk7Y.jpg','./files/2016/6/19/WdDfdnobGk7Y.jpg','./files/2016/6/19/WdDfdnobGk7Y.jpg','不锈钢管,角钢','',-1,0,0,0,1,0,0,0,0,0,0,0,NULL);
/*!40000 ALTER TABLE `seller` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_amount`
--

DROP TABLE IF EXISTS `seller_amount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seller_amount` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ironCount` bigint(20) NOT NULL DEFAULT '0',
  `ironMoney` double NOT NULL DEFAULT '0',
  `handingCount` bigint(11) NOT NULL DEFAULT '0',
  `handingMoney` double NOT NULL DEFAULT '0',
  `day` bigint(100) NOT NULL DEFAULT '0',
  `sellerId` varchar(200) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_amount`
--

LOCK TABLES `seller_amount` WRITE;
/*!40000 ALTER TABLE `seller_amount` DISABLE KEYS */;
INSERT INTO `seller_amount` VALUES (1,123001,898978.12,12,0,1467994606000,'527cec6a380046b5b813537e10d065e9'),(2,991,898.13,9,0,0,'333'),(3,1000,1000,1000,1000,1,'527cec6a380046b5b813537e10d065e9');
/*!40000 ALTER TABLE `seller_amount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_transactions`
--

DROP TABLE IF EXISTS `seller_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seller_transactions` (
  `transId` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `sellerId` varchar(200) NOT NULL DEFAULT '0',
  `productType` int(11) NOT NULL,
  `productId` varchar(200) NOT NULL DEFAULT '',
  `money` float NOT NULL DEFAULT '0',
  `count` float NOT NULL DEFAULT '0',
  `finishTime` bigint(30) NOT NULL DEFAULT '0',
  PRIMARY KEY (`transId`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_transactions`
--

LOCK TABLES `seller_transactions` WRITE;
/*!40000 ALTER TABLE `seller_transactions` DISABLE KEYS */;
INSERT INTO `seller_transactions` VALUES (1,'527cec6a380046b5b813537e10d065e9',1,'d0p50HOYdWCc',111,26,1468650283000),(2,'527cec6a380046b5b813537e10d065e9',0,'L5RGy7O98VYk',113,24,1468650283000),(3,'527cec6a380046b5b813537e10d065e9',2,'12016062601',111,25,1468650283000),(4,'527cec6a380046b5b813537e10d065e9',1,'d0p50HOYdWCc',111,26,1468650283000),(5,'527cec6a380046b5b813537e10d065e9',2,'12016062601',111,25,1468650283000),(6,'527cec6a380046b5b813537e10d065e9',0,'L5RGy7O98VYk',113,24,1468650283000),(7,'527cec6a380046b5b813537e10d065e9',2,'12016071000',0,1,1468722877214),(8,'527cec6a380046b5b813537e10d065e9',2,'02016071703',0,38,1468742843631),(9,'527cec6a380046b5b813537e10d065e9',2,'02016071707',0,12,1468750214958),(10,'527cec6a380046b5b813537e10d065e9',2,'02016071709',0,21,1468750600866);
/*!40000 ALTER TABLE `seller_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_orders`
--

DROP TABLE IF EXISTS `shop_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shop_orders` (
  `sellerId` varchar(100) NOT NULL DEFAULT '',
  `ironCount` bigint(20) DEFAULT '0',
  `ironMoney` double DEFAULT '0',
  `handingCount` bigint(11) DEFAULT '0',
  `handingMoney` double DEFAULT '0',
  PRIMARY KEY (`sellerId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_orders`
--

LOCK TABLES `shop_orders` WRITE;
/*!40000 ALTER TABLE `shop_orders` DISABLE KEYS */;
INSERT INTO `shop_orders` VALUES ('527cec6a380046b5b813537e10d065e9',123001,898978.12,12,NULL),('5afa98c48214438dad364113e3a82ce9',991,898.13,9,NULL);
/*!40000 ALTER TABLE `shop_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `orderId` varchar(11) NOT NULL DEFAULT '',
  `test` int(11) DEFAULT NULL,
  PRIMARY KEY (`orderId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES ('123',123),('222',223),('333',334);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `they_say`
--

DROP TABLE IF EXISTS `they_say`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `they_say` (
  `id` varchar(100) NOT NULL DEFAULT '',
  `title` varchar(300) NOT NULL DEFAULT '',
  `content` mediumtext NOT NULL,
  `pushTime` bigint(20) NOT NULL,
  `who` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `they_say`
--

LOCK TABLES `they_say` WRITE;
/*!40000 ALTER TABLE `they_say` DISABLE KEYS */;
INSERT INTO `they_say` VALUES ('iHI6AS019','','感谢淘不秀提供了这么好的一个平台，让我们能够找到质量好价格好的客户',1466260120084,'江西钢铁商户'),('iHI6AS021','','感谢淘不秀提供了这么好的一个平台，让我们能够找到质量好价格好的客户',1466260120084,'江西钢铁商户'),('iHI6AS022','','感谢淘不秀提供了这么好的一个平台，让我们能够找到质量好价格好的客户',1466260120084,'江西钢铁商户'),('iHI6AS023','','感谢淘不秀提供了这么好的一个平台，让我们能够找到质量好价格好的客户',1466260120084,'江西钢铁商户'),('iHI6AS024','','感谢淘不秀提供了这么好的一个平台，让我们能够找到质量好价格好的客户',1466260120084,'江西钢铁商户'),('iHI6AS025','','感谢淘不秀提供了这么好的一个平台，让我们能够找到质量好价格好的客户',1466260120084,'江西钢铁商户'),('iHI6AS026','','感谢淘不秀提供了这么好的一个平台，让我们能够找到质量好价格好的客户',1466260120084,'江西钢铁商户');
/*!40000 ALTER TABLE `they_say` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT '',
  `password` varchar(200) NOT NULL DEFAULT '',
  `mobile` varchar(11) NOT NULL DEFAULT '',
  `userId` varchar(200) NOT NULL DEFAULT '',
  `isSeller` tinyint(4) NOT NULL DEFAULT '0',
  `monthBuyMoney` float DEFAULT '0',
  `buySellerId` int(11) NOT NULL DEFAULT '0',
  `registerTime` bigint(20) NOT NULL,
  `salesManId` int(11) DEFAULT '0',
  `avator` varchar(200) DEFAULT '',
  `integral` float DEFAULT '0',
  `salesBindTime` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Jeremy','42D0FDE669A000EAF26FDC1CC2CAB454','18355551276','527cec6a380046b5b813537e10d065e9',1,NULL,0,1466915034889,2,'./files/2016/7/17/RQ8LrJV0Nh5s.jpeg',150,1454256000001),(2,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18895561611','5afa98c48214438dad364113e3a82ce9',0,NULL,0,1462915034889,0,'',2,NULL),(3,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18355551277','dfdfdf',1,NULL,0,1466915034889,0,'',0,NULL),(4,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18355551275','5afa98c48214438dad364113e3a82ce1',1,NULL,0,1466915034889,0,'',0,NULL),(5,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18355551274','fffff',1,NULL,0,1466915034889,0,'',0,NULL),(7,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18355551272','nhnh',1,NULL,0,1466915034889,3,'',0,NULL),(8,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18355551271','23',1,NULL,0,1466915034889,0,'',0,NULL),(9,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18355551222','1123123',1,NULL,0,1466915034889,0,'',0,NULL),(10,'asdfasdf','42D0FDE669A000EAF26FDC1CC2CAB454','18355551111','111111',0,NULL,0,1466915034889,0,'',0,NULL),(11,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18355552222','fdfdf',1,NULL,0,1466915034889,0,'',0,NULL),(12,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18355552223','fggg',1,NULL,0,1466915034889,0,'',0,NULL),(15,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18395552225','asdfaw',1,NULL,0,1466915034889,0,'',0,NULL),(16,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18355552227','3312412',1,NULL,0,1466915034889,0,'',0,NULL),(17,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18365551212','aaaaaaaaaaa',1,NULL,0,1466915034889,3,'',0,NULL),(18,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18355551213','adsfasdfasfddddd',1,NULL,0,1466915034889,0,'',0,NULL),(19,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18355551214','adsfasdfasfdcccc',1,NULL,0,1466915034889,0,'',0,NULL),(20,'fengma123','42D0FDE669A000EAF26FDC1CC2CAB454','18355551215','adsfasdfasfdbbb',1,NULL,0,1466915034889,0,'',0,1454256000001),(22,'','','','',0,0,0,0,0,'',0,0),(23,'帅哥','42D0FDE669A000EAF26FDC1CC2CAB454','18895561621','qfhHS6oSfTqb',0,0,0,1468743676533,0,'./files/2016/7/17/p9E5gc4JfFAn.jpg',4,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_bak`
--

DROP TABLE IF EXISTS `user_bak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_bak` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT '',
  `password` varchar(200) NOT NULL DEFAULT '',
  `mobile` varchar(11) NOT NULL DEFAULT '',
  `userId` varchar(200) NOT NULL DEFAULT '',
  `isSeller` tinyint(4) NOT NULL DEFAULT '0',
  `monthBuyMoney` float DEFAULT '0',
  `buySellerId` int(11) NOT NULL DEFAULT '0',
  `registerTime` bigint(20) NOT NULL,
  `salesManId` int(11) DEFAULT '0',
  `avator` varchar(200) DEFAULT '',
  `integral` float DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_bak`
--

LOCK TABLES `user_bak` WRITE;
/*!40000 ALTER TABLE `user_bak` DISABLE KEYS */;
INSERT INTO `user_bak` VALUES (21,'fengma','42D0FDE669A000EAF26FDC1CC2CAB454','18355551216','adsfasdfasfdaaaa',1,NULL,0,1466915034889,0,'',0);
/*!40000 ALTER TABLE `user_bak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'b2b'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-18 16:40:26
