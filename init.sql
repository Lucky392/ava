CREATE DATABASE  IF NOT EXISTS `ava` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `ava`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(1000) NOT NULL,
  `country` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `admin` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3;


INSERT INTO `user` VALUES (1,'admin','car','admin@gmail.com','$2a$10$xi5A2muGuPjay21BntuDeukypPbdcDcFtNidojNGC3RtWzKYE0.kO',NULL,NULL,1),(2,'user','user','user','$2a$10$xSdkYGTBVFhBfWrMEW5ge.JYQRfm6Eb2Il9xm2ndBkWiOpB9sUFHu',NULL,NULL,0);

DROP TABLE IF EXISTS `user_audit`;
CREATE TABLE `user_audit` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `path` varchar(100) NOT NULL,
  `username` varchar(255) NOT NULL,
  `method` varchar(10) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1;
