DROP DATABASE IF EXISTS `finance_management`;
CREATE DATABASE IF NOT EXISTS `finance_management`;
USE `finance_management`;

--
-- Table structure for table `user_role`
--
DROP TABLE IF EXISTS `fm_user_role`;
CREATE TABLE `fm_user_role` (
  `id`        BIGINT      NOT NULL,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fm_user_role_id_unique` (`id`),
  UNIQUE KEY `fm_user_role_role_name_unique` (`role_name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `user_table`
--
DROP TABLE IF EXISTS `fm_user`;
CREATE TABLE `fm_user` (
  `id`        BIGINT      NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `password`  VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fm_user_table_id_unique` (`id`),
  UNIQUE KEY `fm_user_table_user_name_unique` (`user_name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `user_role_details`
--
DROP TABLE IF EXISTS `fm_user_role_details`;
CREATE TABLE `fm_user_role_details` (
  `id`      BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fm_user_role_unique` (`user_id`, `role_id`),
  UNIQUE KEY `fm_user_role_user_id_unique` (`user_id`), #  A user has just ONLY 1 role.
  KEY `role_id` (`role_id`),
  CONSTRAINT `fm_user_role_details_role_id` FOREIGN KEY (`role_id`) REFERENCES `fm_user_role` (`id`),
  CONSTRAINT `fm_user_role_details_user_id` FOREIGN KEY (`user_id`) REFERENCES `fm_user` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS `fm_earnings`;
CREATE TABLE `fm_earnings` (
  `id`      BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fm_earnings_id_unique` (`id`),
  CONSTRAINT `fm_earnings_user_id` FOREIGN KEY (`user_id`) REFERENCES `fm_user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
