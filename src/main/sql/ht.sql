CREATE DATABASE  IF NOT EXISTS `security_db`;
USE `security_db`;

--
-- Table structure for table `user_role`
--
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` BIGINT NOT NULL,
  `role_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_unique` (`id`),
  UNIQUE KEY `role_name_unique` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user_role` VALUES (1,'ADMIN'),(3,'DIRECTOR'),(2,'MANAGER'),(4,'USER');
--
-- Table structure for table `user_table`
--
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table` (
  `id` BIGINT NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_unique` (`id`),
  UNIQUE KEY `user_name_unique` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user_table`
VALUES (1,'admin','admin'),(2,'haho','hoanhhao'),(3,'hao','hiep'),(4,'sd','hiep'),
  (6,'admin1','admin'),(7,'admin2','admin'),(8,'admin3','admin'),
  (9,'admin4','admin'),(12,'haho1','hoanhhao'),(13,'haho13','hoanhhao'),
  (14,'haho14','hoanhhao'),(15,'haho15','hoanhhao'),(16,'haho16','hoanhhao'),
  (17,'haho17','hoanhhao'),(18,'haho18','hoanhhao'),(19,'haho19','hoanhhao'),
  (20,'haho20','hoanhhao'),(21,'haho21','hoanhhao'),(22,'haho22','hoanhhao'),
  (23,'haho23','hoanhhao'),(24,'haho24','hoanhhao'),(25,'haho25','hoanhhao'),
  (26,'haho26','hoanhhao'),(27,'haho27','hoanhhao'),(28,'haho28','hoanhhao'),
  (29,'haho29','hoanhhao'),(30,'haho30','hoanhhao'),(31,'haho31','hoanhhao'),
  (32,'haho32','hoanhhao'),(33,'haho33','hoanhhao'),(34,'haho34','hoanhhao'),
  (36,'haho36','hoanhhao'),(37,'haho37','hoanhhao'),(38,'haho38','hoanhhao');

--
-- Table structure for table `user_role_details`
--
DROP TABLE IF EXISTS `user_role_details`;
CREATE TABLE `user_role_details` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_role_unique` (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `user_role` (`id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user_table` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

INSERT INTO `user_role_details` VALUES (1,1,1),(10,2,1),(2,2,2),(3,2,3),(4,2,4),(9,3,1),(6,4,1);

--
-- Table structure for table `auth_token`
--
DROP TABLE IF EXISTS `auth_token`;
CREATE TABLE `auth_token` (
  `id` BIGINT AUTO_INCREMENT,
  `token_type` varchar(45) NOT NULL,
  `auth_object` BLOB NOT NULL,
  `exp_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_unique` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `image`
--
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id` BIGINT AUTO_INCREMENT,
  `room_id` varchar(45) NOT NULL,
  `image_url` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_unique` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `room_type`
--
DROP TABLE IF EXISTS `room_type`;
CREATE TABLE `room_type` (
  `id` BIGINT AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_unique` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `room`
--
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` BIGINT AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `floor_number` VARCHAR(45) NOT NULL,
  `is_occupied` BOOLEAN,
  `image_id` BIGINT NOT NULL,
  `room_type_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_unique` (`id`),
  CONSTRAINT `image_id` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
  CONSTRAINT `room_type_id` FOREIGN KEY (`room_type_id`) REFERENCES `room_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `customer`
--
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` BIGINT AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `middle_name` VARCHAR(45) NULL,
  `age` TINYINT NULL,
  `gender` VARCHAR(10) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `phone_number` VARCHAR(50) NULL,
  `image` BLOB NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_unique` (`id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user_table` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;