use `family_tree`;

drop table image_details;
drop table image_details_hist;

CREATE TABLE `image_details` (
  `image_details_id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) DEFAULT NULL,
  `image_name` varchar(100) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `parent_name` varchar(100) DEFAULT NULL,
  `parent_name_1` varchar(100) DEFAULT NULL,
  `parent_name_2` varchar(100) DEFAULT NULL,
  `mobile` int(10) DEFAULT NULL,
  `base_address` varchar(4000) DEFAULT NULL,
  `base_country` int(11) DEFAULT NULL,
  `base_state` int(11) DEFAULT NULL,
  `base_city` int(11) DEFAULT NULL,
  `current_address` varchar(100) DEFAULT NULL,
  `country` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `city`int(11) DEFAULT NULL,
  `pincode` int(6) DEFAULT NULL,
  `nookh` varchar(100) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`image_details_id`),
  UNIQUE KEY `image_details_id` (`image_details_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

CREATE TABLE `image_details_hist` (
  `image_details_hist_id` int(11) NOT NULL AUTO_INCREMENT,
  `begin_date` timestamp NULL DEFAULT NULL,
  `image_details_id` int(11) NOT NULL,
  `activity_id` int(11) DEFAULT NULL,
  `image_name` varchar(100) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `parent_name` varchar(100) DEFAULT NULL,
  `parent_name_1` varchar(100) DEFAULT NULL,
  `parent_name_2` varchar(100) DEFAULT NULL,
  `mobile` int(10) DEFAULT NULL,
  `base_address` varchar(4000) DEFAULT NULL,
  `base_country` int(11) DEFAULT NULL,
  `base_state` int(11) DEFAULT NULL,
  `base_city` int(11) DEFAULT NULL,
  `current_address` varchar(100) DEFAULT NULL,
  `country` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `city`int(11) DEFAULT NULL,
  `pincode` int(6) DEFAULT NULL,
  `nookh` varchar(100) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `modified_by` varchar(100) DEFAULT NULL,
  `modified_date` timestamp NULL DEFAULT NULL,
   PRIMARY KEY (`image_details_hist_id`),
   FOREIGN KEY (base_country) REFERENCES countries(id),
   FOREIGN KEY (country) REFERENCES countries(id),
   FOREIGN KEY (base_state) REFERENCES states(id),
   FOREIGN KEY (state) REFERENCES states(id),
   FOREIGN KEY (base_city) REFERENCES cities(id),
   FOREIGN KEY (city) REFERENCES cities(id),
  UNIQUE KEY `image_details_hist_id` (`image_details_hist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


delete from crud_config_param where crud_config_id in (select crud_config_id from crud_config where entity_name in ('image_details', 'image_details_hist'));
delete from crud_config where entity_name in ('image_details', 'image_details_hist');
INSERT INTO `crud_config`
(`crud_config_id`, `entity_name`, `HARD_DELETE`, `primary_key_column`, `sequence_name`, `crud_component`, `entity_History_Name`) VALUES
(200, 'image_details', 0, 'image_details_id', null, 'IMAGEDETAILS', 'IMAGEDETAILSHIST'),
(201, 'image_details_hist', 0, 'image_details_hist_id', null, 'IMAGEDETAILSHIST', null);


INSERT INTO `crud_config_param` (`crud_config_param_id`,`crud_config_id`,`entity_attribute_name`,`entity_attribute_mapping`,`entity_attribute_type`,`is_active`,`is_mandatory`) VALUES 
(null,200,'imageDetailsId','image_details_id','NUMBER',1,1),
(null,200,'activityId','activity_id','NUMBER',1,1),
(null,200,'imageName','image_name','STRING',1,1),
(null,200,'firstName','first_name','STRING',1,1),
(null,200,'lastName','last_name','STRING',1,0),
(null,200,'parentName','parent_name','STRING',1,0),
(null,200,'parentName1','parent_name_1','STRING',1,0),
(null,200,'parentName2','parent_name_2','STRING',1,0),
(null,200,'mobile','mobile','STRING',1,0),
(null,200,'baseAddress','base_address','STRING',1,0),
(null,200,'baseCountry','base_country','NUMBER',1,0),
(null,200,'baseState','base_state','NUMBER',1,0),
(null,200,'baseCity','base_city','NUMBER',1,0),
(null,200,'currentAddress','current_address','STRING',1,0),
(null,200,'country','country','NUMBER',1,0),
(null,200,'state','state','NUMBER',1,0),
(null,200,'city','city','NUMBER',1,0),
(null,200,'pincode','pincode','NUMBER',1,0),
(null,200,'nookh','nookh','NUMBER',1,0),
(null,200,'version','VERSION','NUMBER',1,1),
(null,200,'isActive','IS_ACTIVE','NUMBER',1,1),
(null,200,'createdBy','CREATED_BY','STRING',1,1),
(null,200,'createdDate','CREATED_DATE','DATE',1,1),
(null,200,'modifiedBy','MODIFIED_BY','STRING',1,1),
(null,200,'modifiedDate','MODIFIED_DATE','DATE',1,1),
(null,201,'imageDetailsHistId','imageDetailsId','NUMBER',1,1),
(null,201,'beginDate','begin_date','DATE',1,1);


delete from component_filter_param_config where component_id = (select component_id from component_config where component_url = '/component/images');               
delete from component_config where component_url = '/component/images';


INSERT INTO `component_config` (component_id, component_url,component_sql,component_default_sort,version,is_active,created_by,created_date, modified_by, modified_date) VALUES
(401, '/component/images',
'  SELECT id.image_name imageName,
id.first_name firstName,
id.last_name lastName,
id.parent_name parentName,
id.parent_name_1 parentName1,
id.parent_name_2 parentName2,
id.mobile mobile,
id.base_address baseAddress,
id.base_country baseCountry,
id.base_state baseState,
id.base_city baseCity,
id.current_address currentAddress,
id.country country,
id.state state,
id.city city,
id.pincode pincode,
id.nookh nookh
FROM image_details id
WHERE is_active = 1  ',
' order by first_name, lastName desc '
,0,'Y','SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp());

INSERT INTO `component_filter_param_config` (component_id,param_identifier,param_db_code,db_operator,is_static_param,child_in_master,version,is_active,created_by,created_date, modified_by, modified_date)  VALUES 
(401,'firstName','id.first_name','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'lastName','id.last_name','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'parentName','id.parent_name','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'parentName1','id.parent_name_1','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'parentName2','id.parent_name_1','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'mobile','id.mobile','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'baseAddress','id.base_address','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'baseCountry','id.base_country','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'baseState','id.base_state','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'baseCity','id.base_city','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'address','id.address','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'country','id.country','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'state','id.state','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'city','id.city','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'pincode','id.pincode','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp()),
(401,'nookh','id.nookh','=',0,'N',0,'Y', 'SYSTEM', current_timestamp(), 'SYSTEM', current_timestamp());