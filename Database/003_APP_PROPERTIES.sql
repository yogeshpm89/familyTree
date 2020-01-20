-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: rfid_collect
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Dumping data for table `app_properties`
--

LOCK TABLES `app_properties` WRITE;
/*!40000 ALTER TABLE `app_properties` DISABLE KEYS */;
INSERT INTO `app_properties` VALUES (1000,'APP_SALT_KEY','5FAD18DE4E4B43F984A0E750A41E90ED','Salt key for client side encryption',NULL,0,1,NULL,'2018-08-27 01:52:08',NULL,'2018-08-27 01:52:08'),(1002,'APP_MAIL_SMTP_HOST','smtp.ionos.com','Salt key for client side encryption',19178,5,1,NULL,'2018-09-02 16:41:19','admin@mopstar.us','2019-02-24 00:11:02'),(1003,'APP_MAIL_SMTP_USER','no-reply@mopstar.us','',19178,5,1,NULL,'2018-09-02 16:41:19','admin@mopstar.us','2019-02-24 00:11:02'),(1004,'APP_MAIL_SMTP_PASSWORD','Passw0rd!','',19178,5,1,NULL,'2018-09-02 16:41:19','admin@mopstar.us','2019-02-24 00:11:02'),(1005,'APP_MAIL_SMTP_PORT','587','',19178,5,1,NULL,'2018-09-02 16:41:19','admin@mopstar.us','2019-02-24 00:11:02'),(1006,'APP_MAIL_SMTP_AUTH','true','',19178,5,1,NULL,'2018-09-02 16:41:19','admin@mopstar.us','2019-02-24 00:11:02'),(1007,'APP_MAIL_SMTP_STARTTLS_ENABLE','true','',19178,5,1,NULL,'2018-09-02 16:41:19','admin@mopstar.us','2019-02-24 00:11:02'),(1008,'APP_URL','http://localhost:4200/#/','Application URL',NULL,0,1,NULL,'2018-09-14 03:33:37',NULL,'2018-09-14 03:33:37'),(1009,'APP_NAME','mopstarTest','application name',NULL,0,1,NULL,'2019-05-31 14:55:14',NULL,'2019-05-31 14:55:14');
/*!40000 ALTER TABLE `app_properties` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-15  9:48:45