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


DROP TABLE IF EXISTS `fm_payment_methods`;
CREATE TABLE `fm_payment_methods` (
  `id`   BIGINT      NOT NULL,
  `name` VARCHAR(45) NULL, # cash, credit card, debit card, master card, jbc, amex, visa credit
  PRIMARY KEY (`id`),
  UNIQUE KEY `fm_event_types_id_unique` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `fm_cards_information`;
CREATE TABLE `fm_cards_information` (
  `id`           BIGINT      NOT NULL,
  `name`         VARCHAR(45) NULL, # HSBC, ANZ 123
  `start_date`   DATE        NULL,
  `expiry_date`  DATE        NULL,
  `card_number`  VARCHAR(45) NULL, # last 6 digits
  `amount`       VARCHAR(45) NULL,
  `card_type_id` BIGINT      NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fm_cards_information_id_unique` (`id`),
  CONSTRAINT `fm_cards_information_user_id` FOREIGN KEY (`card_type_id`) REFERENCES `fm_payment_methods` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `fm_expenses`;
CREATE TABLE `fm_expenses` (
  `id`                BIGINT      NOT NULL,
  `amount`            DOUBLE      NULL, # if `is_an_event is TRUE, amount can be updated later when event is over
  `date`              DATETIME DEFAULT now(),
  `place`             VARCHAR(45) NOT NULL,
  `for_person`        VARCHAR(45) NULL, # for_person means you spend for them, not for you, so this will not be listed in monthly report.
  # borrow someone money, if this is not important, you can let it hear,
  # otherwise, you want to put it in reminder, should place an event for it.
  `is_an_event`       BOOLEAN  DEFAULT FALSE,
  `payment_method_id` BIGINT      NOT NULL, # if `is_an_event is TRUE, payment_method is NULL
  PRIMARY KEY (`id`),
  UNIQUE KEY `fm_expenses_id_unique` (`id`),
  CONSTRAINT `fm_payment_methods_payment_method_id` FOREIGN KEY (`payment_method_id`) REFERENCES `fm_payment_methods` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `fm_event_expenses`; # the event that you involve in spending money with others in the group
# in the end, this data will be used for calculation of expense of each person
# it is mandatory that, you involve in this even you are not a host (main person spending, but the other)
CREATE TABLE `fm_event_expenses` (
  `id`             BIGINT      NOT NULL,
  `name`           VARCHAR(45) NOT NULL,
  `event_type_id`  BIGINT      NOT NULL,
  `amount`         DOUBLE      NOT NULL,
  `date`           DATETIME DEFAULT now(),
  `place`          VARCHAR(45) NOT NULL,
  `for_person`     VARCHAR(45) NULL, # NULL means for only you.
  `by_person`      VARCHAR(45) NULL,
  `is_over`        BOOLEAN  DEFAULT FALSE,
  `payment_method` VARCHAR(45) NOT NULL,
  `expense_id`     BIGINT      NOT NULL, # group sharing, traveling, group party, borrow money, pay back money

  PRIMARY KEY (`id`),
  UNIQUE KEY `fm_event_expenses_id_unique` (`id`),
  CONSTRAINT `fm_event_expenses_user_id` FOREIGN KEY (`expense_id`) REFERENCES `fm_expenses` (`id`),
  CONSTRAINT `fm_event_expenses_event_type_id` FOREIGN KEY (`event_type_id`) REFERENCES `fm_event_types` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `fm_event_types`;
CREATE TABLE `fm_event_types` (
  `id`   BIGINT      NOT NULL,
  `name` VARCHAR(45) NULL, # group sharing, traveling, group party, borrow money, pay back money
  PRIMARY KEY (`id`),
  UNIQUE KEY `fm_event_types_id_unique` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
