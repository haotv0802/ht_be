DROP DATABASE IF EXISTS `finance_management`;
CREATE DATABASE IF NOT EXISTS `finance_management`;
USE `finance_management`;

--
-- Table structure for table `user_role`
--
DROP TABLE IF EXISTS `fm_user_roles`;
CREATE TABLE `fm_user_roles` (
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
DROP TABLE IF EXISTS `fm_users`;
CREATE TABLE `fm_users` (
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
  `amount`  DOUBLE NOT NULL,
  `date`    DATETIME DEFAULT now(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `fm_earnings_id_unique` (`id`),
  CONSTRAINT `fm_earnings_user_id` FOREIGN KEY (`user_id`) REFERENCES `fm_user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `fm_expenses`;
CREATE TABLE `fm_expenses` (
  `id`             BIGINT      NOT NULL,
  `amount`         DOUBLE      NULL, # if `is_an_event is TRUE, amount can be updated later when event is over
  `date`           DATETIME DEFAULT now(),
  `place`          VARCHAR(45) NOT NULL,
  `for_person`     VARCHAR(45) NULL, # for_person means you spend for them, not for you, so this will not be listed in monthly report.
  `is_an_event`    BOOLEAN  DEFAULT FALSE,
  `payment_method` VARCHAR(45) NULL, # if `is_an_event is TRUE, payment_method is NULL
  PRIMARY KEY (`id`),
  UNIQUE KEY `fm_earnings_id_unique` (`id`),
  CONSTRAINT `fm_earnings_user_id` FOREIGN KEY (`user_id`) REFERENCES `fm_user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `fm_event_expenses`; # the event that you involve in spending money with others in the group
# in the end, this data will be used for calculation of expense of each person
# it is mandatory that, you involve in this even you are not a host (main person spending, but the other)
CREATE TABLE `fm_event_expenses` (
  `id`             BIGINT      NOT NULL,
  `name`           VARCHAR(45) NOT NULL, # group sharing, traveling, group party
  `amount`         DOUBLE      NOT NULL,
  `date`           DATETIME DEFAULT now(),
  `place`          VARCHAR(45) NOT NULL,
  `for_person`     VARCHAR(45) NULL,
  `by_person`      VARCHAR(45) NULL,
  `is_over`        BOOLEAN  DEFAULT FALSE,
  `payment_method` VARCHAR(45) NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE KEY `fm_earnings_id_unique` (`id`),
  CONSTRAINT `fm_earnings_user_id` FOREIGN KEY (`user_id`) REFERENCES `fm_user` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
