CREATE TABLE `url_desc` (
  `url_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(256) DEFAULT NULL,
  `url_desc` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`url_id`)
) 
