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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `activity_id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(4000) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`activity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_properties`
--

DROP TABLE IF EXISTS `app_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_properties` (
  `property_id` int(11) NOT NULL AUTO_INCREMENT,
  `property_name` varchar(100) DEFAULT NULL,
  `property_value` varchar(100) DEFAULT NULL,
  `property_desc` varchar(4000) DEFAULT NULL,
  `activity_id` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`property_id`),
  KEY `property_id_idx` (`property_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1010 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `component_config`
--

DROP TABLE IF EXISTS `component_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `component_config` (
  `component_id` int(11) NOT NULL AUTO_INCREMENT,
  `component_url` varchar(1000) NOT NULL,
  `component_sql` blob,
  `component_default_sort` varchar(4000) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `is_active` varchar(1) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`component_id`),
  UNIQUE KEY `component_url` (`component_url`),
  KEY `component_id_idx` (`component_url`(100))
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `component_filter_master`
--

DROP TABLE IF EXISTS `component_filter_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `component_filter_master` (
  `component_master_filter_id` decimal(10,0) DEFAULT NULL,
  `component_id` decimal(10,0) DEFAULT NULL,
  `child_in_master` varchar(100) DEFAULT NULL,
  `master_sql` blob,
  `version` decimal(10,0) DEFAULT NULL,
  `is_active` varchar(1) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `component_filter_param_config`
--

DROP TABLE IF EXISTS `component_filter_param_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `component_filter_param_config` (
  `component_filter_param_id` int(11) NOT NULL AUTO_INCREMENT,
  `component_id` int(11) NOT NULL,
  `param_identifier` varchar(100) DEFAULT NULL,
  `param_db_code` varchar(400) DEFAULT NULL,
  `db_operator` varchar(100) DEFAULT NULL,
  `is_static_param` int(11) DEFAULT NULL,
  `child_in_master` varchar(100) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `is_active` varchar(1) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`component_filter_param_id`),
  KEY `component_filter_param_config_idx` (`component_id`,`param_identifier`),
  CONSTRAINT `component_filter_param_config_ibfk_1` FOREIGN KEY (`component_id`) REFERENCES `component_config` (`component_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `crud_config`
--

DROP TABLE IF EXISTS `crud_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crud_config` (
  `crud_config_id` int(11) NOT NULL AUTO_INCREMENT,
  `entity_name` varchar(200) NOT NULL,
  `HARD_DELETE` int(11) DEFAULT NULL,
  `primary_key_column` varchar(200) DEFAULT NULL,
  `sequence_name` varchar(200) DEFAULT NULL,
  `crud_component` varchar(200) DEFAULT NULL,
  `entity_History_Name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`crud_config_id`),
  UNIQUE KEY `entity_name_UNIQUE` (`entity_name`),
  KEY `crud_config_id_idx` (`entity_name`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `crud_config_param`
--

DROP TABLE IF EXISTS `crud_config_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crud_config_param` (
  `crud_config_param_id` int(11) NOT NULL AUTO_INCREMENT,
  `crud_config_id` int(11) DEFAULT NULL,
  `entity_attribute_name` varchar(200) DEFAULT NULL,
  `entity_attribute_mapping` varchar(200) DEFAULT NULL,
  `entity_attribute_type` varchar(200) DEFAULT NULL,
  `is_active` int(11) DEFAULT NULL,
  `is_mandatory` int(11) DEFAULT NULL,
  PRIMARY KEY (`crud_config_param_id`),
  KEY `crud_config_id_idx` (`crud_config_id`),
  KEY `crud_config_param_idx` (`crud_config_id`,`entity_attribute_name`),
  CONSTRAINT `crud_config_id` FOREIGN KEY (`crud_config_id`) REFERENCES `crud_config` (`crud_config_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_access_token`
--

DROP TABLE IF EXISTS `oauth_access_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_access_token` (
  `authentication_id` varchar(256) NOT NULL,
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oauth_refresh_token`
--

DROP TABLE IF EXISTS `oauth_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_library`
--

DROP TABLE IF EXISTS `user_library`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_library` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) DEFAULT NULL,
  `user_email` varchar(100) NOT NULL,
  `user_first_name` varchar(100) DEFAULT NULL,
  `user_middle_name` varchar(100) DEFAULT NULL,
  `user_last_name` varchar(100) DEFAULT NULL,
  `user_display_name` varchar(100) DEFAULT NULL,
  `user_address` varchar(400) DEFAULT NULL,
  `user_phone` varchar(400) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `user_role` varchar(100) DEFAULT NULL,
  `is_first` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_email`),
  KEY `user_id_idx` (`user_id`,`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_library_hist`
--

DROP TABLE IF EXISTS `user_library_hist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_library_hist` (
  `user_hist_id` int(11) NOT NULL AUTO_INCREMENT,
  `begin_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `activity_id` int(11) DEFAULT NULL,
  `user_email` varchar(100) NOT NULL,
  `user_first_name` varchar(100) DEFAULT NULL,
  `user_middle_name` varchar(100) DEFAULT NULL,
  `user_last_name` varchar(100) DEFAULT NULL,
  `user_display_name` varchar(100) DEFAULT NULL,
  `user_address` varchar(400) DEFAULT NULL,
  `user_phone` varchar(400) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `user_role` varchar(100) DEFAULT NULL,
  `is_first` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_hist_id`),
  KEY `user_hist_id_idx` (`user_hist_id`,`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-15  9:47:02
