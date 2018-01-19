CREATE DATABASE  IF NOT EXISTS `internet_provider` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `internet_provider`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: internet_provider
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
-- Table structure for table `ban`
--

DROP TABLE IF EXISTS `ban`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ban` (
  `b_id` int(11) NOT NULL AUTO_INCREMENT,
  `b_set_date` datetime NOT NULL,
  `b_reset_date` datetime DEFAULT NULL,
  `b_comment` text,
  `ban_reason_br_id` int(11) NOT NULL,
  `user_u_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`b_id`),
  KEY `fk_ban_ban_reason1_idx` (`ban_reason_br_id`),
  KEY `fk_ban_user1_idx` (`user_u_id`),
  CONSTRAINT `fk_ban_ban_reason1` FOREIGN KEY (`ban_reason_br_id`) REFERENCES `ban_reason` (`br_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ban_user1` FOREIGN KEY (`user_u_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ban`
--

LOCK TABLES `ban` WRITE;
/*!40000 ALTER TABLE `ban` DISABLE KEYS */;
INSERT INTO `ban` VALUES (1,'2016-12-12 00:00:00','2016-12-14 00:00:00','',1,2),(2,'2016-12-15 00:00:00','2016-12-20 00:00:00','',1,5),(3,'2015-05-01 00:00:00','2015-06-01 00:00:00','Clients holydays :)',2,4),(4,'2016-10-23 00:00:00','2016-10-24 00:00:00','',1,8),(5,'2017-01-19 00:00:00','2017-01-24 00:00:00','',1,9),(6,'2017-02-01 00:00:00',NULL,'The customer did repairs at home, and could not use the equipment',3,3),(7,'2017-03-03 13:00:00','2017-06-28 23:26:33','test UNBAN!!',1,2),(8,'2017-06-28 23:25:52',NULL,'',1,6),(10,'2017-07-08 09:08:52','2017-07-17 17:34:27','',1,14),(11,'2017-06-06 00:00:00','2017-06-07 00:00:00','',2,14),(12,'2017-07-17 17:34:36','2017-12-15 13:23:31','',1,14),(13,'2017-07-30 09:03:28','2017-11-25 14:47:43','test ban with comment - user request blocking',3,16),(14,'2017-12-27 14:09:38','2017-12-27 14:10:16','Test ban for bad habbids :)',2,16);
/*!40000 ALTER TABLE `ban` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ban_reason`
--

DROP TABLE IF EXISTS `ban_reason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ban_reason` (
  `br_id` int(11) NOT NULL,
  `br_title` varchar(60) NOT NULL,
  PRIMARY KEY (`br_id`),
  UNIQUE KEY `br_title_UNIQUE` (`br_title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ban_reason`
--

LOCK TABLES `ban_reason` WRITE;
/*!40000 ALTER TABLE `ban_reason` DISABLE KEYS */;
INSERT INTO `ban_reason` VALUES (3,'Blocking at the client\'s request'),(1,'Blocking due to negative balance of account'),(2,'Policy violation');
/*!40000 ALTER TABLE `ban_reason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `p_id` int(11) NOT NULL AUTO_INCREMENT,
  `p_payment_date` datetime NOT NULL,
  `p_amount` decimal(5,2) NOT NULL,
  `user_u_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`p_id`),
  KEY `fk_payment_user1_idx` (`user_u_id`),
  CONSTRAINT `fk_payment_user1` FOREIGN KEY (`user_u_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,'2016-12-14 12:00:00',10.00,2),(2,'2016-12-20 14:00:00',20.00,5),(3,'2016-10-23 11:00:00',16.00,8),(4,'2017-02-24 09:00:00',25.00,6),(5,'2016-01-02 20:00:00',30.00,2),(6,'2015-09-03 22:00:00',25.00,3),(7,'2014-03-12 12:00:00',25.00,4),(8,'2016-05-22 09:00:00',35.00,5),(9,'2017-02-01 14:40:00',20.00,6),(10,'2014-06-12 22:00:00',30.00,8),(11,'2015-09-21 17:40:00',40.00,9),(12,'2017-04-16 15:45:00',25.00,10),(13,'2015-09-03 22:00:00',35.00,3),(14,'2015-06-12 12:00:00',31.00,4),(15,'2016-05-21 17:40:00',40.00,9),(16,'2017-05-14 12:45:00',35.00,10),(19,'2017-07-11 22:36:29',5.00,2),(29,'2017-07-15 09:41:42',1.00,2),(30,'2017-01-15 15:04:14',10.00,14),(31,'2017-04-10 16:04:58',15.00,14),(32,'2017-07-02 11:15:54',13.00,14),(34,'2017-07-17 17:38:00',12.00,2),(35,'2017-07-23 12:55:23',13.00,2),(36,'2017-07-27 23:02:45',20.00,16),(37,'2017-12-11 12:56:23',11.00,2),(38,'2017-12-24 10:55:44',15.00,5),(39,'2017-12-27 14:55:02',21.20,9);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_request_date` datetime NOT NULL,
  `r_processed_date` datetime DEFAULT NULL,
  `r_processed_by` int(11) DEFAULT NULL,
  `user_u_id` int(10) unsigned NOT NULL,
  `tariff_t_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`r_id`),
  KEY `fk_request_user1_idx` (`user_u_id`),
  KEY `fk_request_tariff1_idx` (`tariff_t_id`),
  CONSTRAINT `fk_request_tariff1` FOREIGN KEY (`tariff_t_id`) REFERENCES `tariff` (`t_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_request_user1` FOREIGN KEY (`user_u_id`) REFERENCES `user` (`u_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
INSERT INTO `request` VALUES (1,'2017-05-10 08:20:00','2017-05-10 12:00:00',1,2,3),(2,'2017-05-08 04:32:00','2017-05-08 10:00:00',1,6,5),(4,'2016-10-02 19:00:00','2016-10-03 09:00:00',1,2,2),(5,'2016-05-03 11:00:00','2016-05-03 16:00:00',1,4,4),(6,'2016-04-10 10:00:00','2016-04-11 14:00:00',1,5,5),(7,'2017-01-12 22:00:00','2017-01-13 10:00:00',1,6,3),(8,'2016-12-03 07:00:00','2016-12-03 11:00:00',1,9,6),(9,'2017-03-01 21:10:00','2017-03-02 09:05:00',7,3,3),(25,'2017-06-27 10:46:55','2017-06-27 17:34:45',7,10,3),(40,'2017-01-01 00:00:00','2017-07-17 17:37:07',1,5,3),(42,'2017-02-03 00:00:00','2017-07-16 12:45:01',1,6,4),(61,'2017-07-16 17:48:48','2017-07-16 17:49:13',1,2,8),(63,'2017-07-16 18:00:42','2017-07-23 12:51:48',1,2,38),(67,'2017-07-27 23:03:01','2017-07-27 23:09:48',1,16,2),(82,'2017-12-13 13:35:33','2017-12-13 13:38:13',1,2,8),(86,'2017-12-24 10:55:55','2017-12-24 10:59:04',1,5,6);
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariff`
--

DROP TABLE IF EXISTS `tariff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tariff` (
  `t_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `t_title` varchar(45) NOT NULL,
  `t_monthly_cost` decimal(5,2) NOT NULL,
  `t_monthly_data_limit` bigint(20) DEFAULT NULL,
  `t_is_unlim_traffic` tinyint(4) NOT NULL,
  `t_overload_limit_cost` decimal(5,2) DEFAULT NULL,
  `t_description` text,
  `technology_tech_id` int(11) NOT NULL,
  PRIMARY KEY (`t_id`),
  UNIQUE KEY `t_title_UNIQUE` (`t_title`),
  KEY `fk_tariff_technology1_idx` (`technology_tech_id`),
  CONSTRAINT `fk_tariff_technology1` FOREIGN KEY (`technology_tech_id`) REFERENCES `technology` (`tech_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff`
--

LOCK TABLES `tariff` WRITE;
/*!40000 ALTER TABLE `tariff` DISABLE KEYS */;
INSERT INTO `tariff` VALUES (1,'Premium',20.00,0,1,0.00,'High-speed unlimited internet with 50 HD-quality TV Channels',1),(2,'Just TV',10.00,0,1,0.00,'50 HD-quality TV Channels',2),(3,'Home 50',13.00,50000,0,0.13,'50 GB of internet traffic for country houses',2),(4,'City U',16.00,0,1,0.00,'Unlimited internet only',3),(5,'City U+TV',21.00,0,1,0.00,'Unlimited internet only and TV',3),(6,'Air 100',15.00,100000,0,0.09,'100 GB of internet via WI-MAX technology',4),(7,'Air Unlim',18.00,0,1,0.00,'Unlimited internet only via WI-MAX',4),(8,'Simply 30',10.00,30000,0,0.11,'Only internet tariff, 30 GB are Included',1),(38,'test tariff two',12.31,0,1,0.00,'New tariff description test',1);
/*!40000 ALTER TABLE `tariff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `technology`
--

DROP TABLE IF EXISTS `technology`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `technology` (
  `tech_id` int(11) NOT NULL,
  `tech_title` varchar(50) NOT NULL,
  `tech_rent_equipment` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`tech_id`),
  UNIQUE KEY `tech_title_UNIQUE` (`tech_title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `technology`
--

LOCK TABLES `technology` WRITE;
/*!40000 ALTER TABLE `technology` DISABLE KEYS */;
INSERT INTO `technology` VALUES (1,'Ethernet',0),(2,'ADSL',1),(3,'xPON',1),(4,'WiMAX',1);
/*!40000 ALTER TABLE `technology` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `u_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `u_role` enum('admin','client') NOT NULL,
  `u_login` varchar(25) NOT NULL,
  `u_password` varchar(25) NOT NULL,
  `u_email` varchar(45) NOT NULL,
  `u_first_name` varchar(30) NOT NULL,
  `u_last_name` varchar(30) NOT NULL,
  `u_passport_number` varchar(20) NOT NULL,
  `u_reg_date` date NOT NULL,
  `u_monthly_data_usage` bigint(20) NOT NULL,
  `u_total_data_usage` bigint(20) NOT NULL,
  `u_account_ballance` decimal(5,2) DEFAULT '0.00',
  `tariff_t_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `u_login_UNIQUE` (`u_login`),
  KEY `fk_user_tariff1_idx` (`tariff_t_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin','admin','bret_hart@bk.ru','Pavel','Sorokoletov','2347612341','2015-05-30',2000,243500,0.00,1),(2,'client','user','123qweQ','jn.smith@gmail.com','John','Smith','9475827535120','2016-01-02',3244,50020,13.00,8),(3,'client','sarara','1234','c.sarah@hotmail.com','Sarah','Connor','6572873457','2015-08-03',1233,234644,3.40,5),(4,'client','marks','123qweA','mark78@mail.ru','Mark','Roberts','46346546745464','2014-03-12',3243,423556,2.00,4),(5,'client','simonthecat','123qweQ','s.simon@yahoo.com','Simon','Smolsky','25343463464434','2016-05-22',6534,65467,20.60,6),(6,'client','mary','1234','mary.black@gmail.com','Mary','Black','9384756783','2017-02-01',3422,45432,-1.23,6),(7,'client','admin1','admin1TEST','qwerty@site.com','Juri','Pupkin','25252523314534','2016-04-01',3411,65432,0.00,1),(8,'client','beer_you','1234qweA','mike123@bk.ru','Michael','Wynne','93485767824543','2014-06-12',6563,645634,9.99,5),(9,'client','samuel','1234','samuel_bart@ibm.com','Samuel','Barton','9238776235','2015-09-21',4323,228387,23.63,6),(10,'client','kate_mate','123qweQ','catherine_j@mail.com','Catherine','Jackson','456454344645','2017-04-16',3344,234324,9.40,3),(14,'client','dw11ddd','123qweA','free_wilkie@yahoo.com','George','Wilkinson','1241235351345','2017-01-07',1234,14444,16.92,8),(16,'client','colin123','123qweA','colin@goldie.de','Colin','Goldie','355513453434643','2017-07-27',3222,3225,14.00,2),(23,'client','g34fq3f34g','123qweQ','sefef@vevevwe.df','Testr133','Testttt12','346436464364','2017-12-27',343,2343,23.00,6);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'internet_provider'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-15 22:10:20
