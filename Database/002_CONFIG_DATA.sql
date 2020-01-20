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
-- Dumping data for table `component_config`
--

LOCK TABLES `component_config` WRITE;
/*!40000 ALTER TABLE `component_config` DISABLE KEYS */;
INSERT INTO `component_config` VALUES (100,'/component/users','select \nu.user_id userId,\nu.user_email email,\nu.user_first_name firstName,\nu.user_middle_name middleName,\nu.user_last_name lastName,\nu.user_display_name displayName,\nu.user_address address,\nu.user_phone phone,\nu.user_role role,\nu.is_first isFirst,\nu.version version,\nu.is_active isActive,\nu.created_by createdBy,\nu.created_date createdDate,\nu.modified_by modifiedBy,\nu.modified_date modifiedDate\nfrom user_library u\nwhere 1 = 1 ','',0,'Y','SYSTEM','2019-06-13 01:13:33','SYSTEM','2019-06-13 01:13:33'),(101,'/component/mailSettings',' select property_id propertyId,\nproperty_name propertyName,\nproperty_value propertyValue,\nproperty_desc propertyDesc \nfrom app_properties \nwhere is_active = 1 and property_name like \'APP_MAIL%\' ','',0,'Y','SYSTEM','2019-06-13 02:36:26','SYSTEM','2019-06-13 02:36:26'),(102,'/component/appUrl',' select property_id propertyId,\nproperty_name propertyName,\nproperty_value propertyValue,\nproperty_desc propertyDesc \nfrom app_properties \nwhere is_active = 1 and property_name = \'APP_URL\' ','',0,'Y','SYSTEM','2019-06-13 17:38:33','SYSTEM','2019-06-13 17:38:33');
/*!40000 ALTER TABLE `component_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `component_filter_master`
--

LOCK TABLES `component_filter_master` WRITE;
/*!40000 ALTER TABLE `component_filter_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `component_filter_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `component_filter_param_config`
--

LOCK TABLES `component_filter_param_config` WRITE;
/*!40000 ALTER TABLE `component_filter_param_config` DISABLE KEYS */;
INSERT INTO `component_filter_param_config` VALUES (17,100,'userId','u.userId','=',0,'N',0,'Y','SYSTEM','2019-06-13 01:13:35','SYSTEM','2019-06-13 01:13:35'),(18,100,'email','u.user_email','=',0,'N',0,'Y','SYSTEM','2019-06-13 01:13:35','SYSTEM','2019-06-13 01:13:35'),(19,100,'firstName','u.user_first_name','=',0,'N',0,'Y','SYSTEM','2019-06-13 01:13:35','SYSTEM','2019-06-13 01:13:35'),(20,100,'middleName','u.user_middle_name','=',0,'N',0,'Y','SYSTEM','2019-06-13 01:13:35','SYSTEM','2019-06-13 01:13:35'),(21,100,'lastName','u.user_last_name','=',0,'N',0,'Y','SYSTEM','2019-06-13 01:13:35','SYSTEM','2019-06-13 01:13:35'),(22,100,'displayName','u.user_display_name','=',0,'N',0,'Y','SYSTEM','2019-06-13 01:13:35','SYSTEM','2019-06-13 01:13:35'),(23,100,'role','u.user_role','=',0,'N',0,'Y','SYSTEM','2019-06-13 01:13:35','SYSTEM','2019-06-13 01:13:35');
/*!40000 ALTER TABLE `component_filter_param_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `crud_config`
--

LOCK TABLES `crud_config` WRITE;
/*!40000 ALTER TABLE `crud_config` DISABLE KEYS */;
INSERT INTO `crud_config` VALUES (1,'activity',0,'ACTIVITY_ID',NULL,'ACTIVITY',NULL),(100,'user_library',0,'user_id',NULL,'user','userhist'),(101,'user_library_hist',0,'user_hist_id',NULL,'userhist',NULL);
/*!40000 ALTER TABLE `crud_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `crud_config_param`
--

LOCK TABLES `crud_config_param` WRITE;
/*!40000 ALTER TABLE `crud_config_param` DISABLE KEYS */;
INSERT INTO `crud_config_param` VALUES (19,1,'activityName','ACTIVITY_NAME','STRING',1,1),(20,1,'createdBy','CREATED_BY','STRING',1,1),(21,1,'createdDate','CREATED_DATE','DATE',1,1),(98,100,'userId','user_id','NUMBER',1,1),(99,100,'activityId','activity_id','NUMBER',1,1),(100,100,'email','user_email','STRING',1,1),(101,100,'firstName','user_first_name','STRING',1,0),(102,100,'middleName','user_middle_name','STRING',1,0),(103,100,'lastName','user_last_name','STRING',1,0),(104,100,'address','user_address','STRING',1,0),(105,100,'phone','user_phone','STRING',1,0),(106,100,'displayName','user_display_name','STRING',1,0),(107,100,'password','password','STRING',1,1),(108,100,'userRole','user_role','STRING',1,1),(109,100,'isFirst','is_first','NUMBER',1,1),(110,100,'isActive','is_active','NUMBER',1,1),(111,100,'version','VERSION','NUMBER',1,1),(112,100,'createdBy','CREATED_BY','STRING',1,1),(113,100,'createdDate','CREATED_DATE','DATE',1,1),(114,100,'modifiedBy','MODIFIED_BY','STRING',1,1),(115,100,'modifiedDate','MODIFIED_DATE','DATE',1,1),(116,101,'userHistId','user_hist_id','NUMBER',1,1),(117,101,'beginDate','begin_date','DATE',1,1);
/*!40000 ALTER TABLE `crud_config_param` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-15  9:48:05
