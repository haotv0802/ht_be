DROP DATABASE IF EXISTS `ht_db`;
CREATE DATABASE IF NOT EXISTS `ht_db`;
USE `ht_db`;

--
-- Table structure for table `user_role`
--
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id`        BIGINT      NOT NULL,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_role_id_unique` (`id`),
  UNIQUE KEY `user_role_role_name_unique` (`role_name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `user_role` VALUES (1, 'ADMIN'), (2, 'STAFF'), (3, 'CUSTOMER'), (4, 'USER');

--
-- Table structure for table `user_table`
--
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table` (
  `id`        BIGINT      NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `password`  VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `_user_tableid_unique` (`id`),
  UNIQUE KEY `user_table_user_name_unique` (`user_name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `user_table`
VALUES
  (1, 'admin', 'admin'), (2, 'haho', 'hoanhhao'), (3, 'hao', 'hiep'), (4, 'sd', 'hiep'),
  (6, 'admin1', 'admin'), (7, 'admin2', 'admin'), (8, 'admin3', 'admin'),
  (9, 'admin4', 'admin'), (12, 'haho1', 'hoanhhao'), (13, 'haho13', 'hoanhhao'),
  (14, 'haho14', 'hoanhhao'), (15, 'haho15', 'hoanhhao'), (16, 'haho16', 'hoanhhao'),
  (17, 'haho17', 'hoanhhao'), (18, 'haho18', 'hoanhhao'), (19, 'haho19', 'hoanhhao'),
  (20, 'haho20', 'hoanhhao'), (21, 'haho21', 'hoanhhao'), (22, 'haho22', 'hoanhhao'),
  (23, 'haho23', 'hoanhhao'), (24, 'haho24', 'hoanhhao'), (25, 'haho25', 'hoanhhao'),
  (26, 'haho26', 'hoanhhao'), (27, 'haho27', 'hoanhhao'), (28, 'haho28', 'hoanhhao'),
  (29, 'haho29', 'hoanhhao'), (30, 'haho30', 'hoanhhao'), (31, 'haho31', 'hoanhhao'),
  (32, 'haho32', 'hoanhhao'), (33, 'haho33', 'hoanhhao'), (34, 'haho34', 'hoanhhao'),
  (36, 'haho36', 'hoanhhao'), (37, 'haho37', 'hoanhhao'), (38, 'haho38', 'hoanhhao'),
  (39, 'customer', 'customer'), (40, 'staff', 'staff');

--
-- Table structure for table `user_role_details`
--
DROP TABLE IF EXISTS `user_role_details`;
CREATE TABLE `user_role_details` (
  `id`      BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_role_unique` (`user_id`, `role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_details_role_id` FOREIGN KEY (`role_id`) REFERENCES `user_role` (`id`),
  CONSTRAINT `user_role_details_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_table` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8;

INSERT INTO `user_role_details` VALUES
  (1, 1, 1), (10, 2, 1), (2, 2, 2), (3, 2, 3), (4, 2, 4), (9, 3, 1), (6, 4, 1),
  (7, 39, 3), (8, 40, 2)
;

--
-- Table structure for table `auth_token`
--
DROP TABLE IF EXISTS `auth_token`;
CREATE TABLE `auth_token` (
  `id`          BIGINT AUTO_INCREMENT,
  `token_type`  VARCHAR(45) NOT NULL,
  `auth_object` BLOB        NOT NULL,
  `exp_date`    DATETIME    NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_token_id_unique` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `image`
--
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id`          BIGINT       NOT NULL,
  #   `room_id` VARCHAR(45) NOT NULL,
  #   `entity_id` BIGINT NOT NULL, #Id of any entity in the system, entity can be a room, promotion, user.
  `image_url`   VARCHAR(45)  NOT NULL,
  `image_info`  VARCHAR(100) NOT NULL,
  `description` VARCHAR(100),
  `date`        DATETIME     NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `image_id_unique` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `room_type`
--
DROP TABLE IF EXISTS `room_type`;
CREATE TABLE `room_type` (
  `id`       BIGINT      NOT NULL,
  `name`     VARCHAR(45) NOT NULL,
  `image_id` BIGINT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `room_type_id_unique` (`id`),
  CONSTRAINT `room_type_image_id` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `room_type` VALUES (1, 'Balcony room', NULL);
INSERT INTO `room_type` VALUES (2, 'Near-elevator room', NULL);
INSERT INTO `room_type` VALUES (3, 'Room 3', NULL);
INSERT INTO `room_type` VALUES (4, 'Family or big room', NULL);

--
-- Table structure for table `room`
--
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id`               BIGINT AUTO_INCREMENT,
  `name`             VARCHAR(45) NOT NULL,
  `floor_number`     TINYINT     NOT NULL,
  `number_of_people` TINYINT     NOT NULL,
  #   `is_occupied` BOOLEAN,
  #   `image_id`         BIGINT      NOT NULL,
  `room_type_id`     BIGINT      NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `room_id_unique` (`id`),
  #   CONSTRAINT `room_image_id` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
  CONSTRAINT `room_room_type_id` FOREIGN KEY (`room_type_id`) REFERENCES `room_type` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 101', 1, 4, 1);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 102', 1, 2, 2);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 103', 1, 2, 3);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 201', 2, 4, 1);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 202', 2, 2, 2);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 203', 2, 2, 3);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 301', 3, 4, 1);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 302', 3, 2, 2);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 303', 3, 2, 3);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 401', 4, 4, 1);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 402', 4, 2, 2);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 403', 4, 2, 3);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 501', 5, 4, 1);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 502', 5, 2, 2);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 503', 5, 2, 3);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 601', 6, 4, 4);
INSERT INTO `room` (`name`, `floor_number`, `number_of_people`, `room_type_id`)
VALUES ('Room 602', 6, 2, 4);

--
-- Table structure for table `individual`
--
DROP TABLE IF EXISTS `individual`;
CREATE TABLE `individual` (
  `id`           BIGINT AUTO_INCREMENT,
  `first_name`   VARCHAR(45) NOT NULL,
  `last_name`    VARCHAR(45) NOT NULL,
  `middle_name`  VARCHAR(45),
  `age`          TINYINT,
  `gender`       VARCHAR(10) NOT NULL,
  `email`        VARCHAR(50) NOT NULL,
  `phone_number` VARCHAR(50),
  `image_id`     BIGINT,
  `user_id`      BIGINT      NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `individual_id_unique` (`id`),
  CONSTRAINT `individual_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_table` (`id`),
  CONSTRAINT `individual_image_id` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `expense_type`
--
DROP TABLE IF EXISTS `expense_type`;
CREATE TABLE `expense_type` (
  `id`   BIGINT AUTO_INCREMENT,
  `name` BIGINT NOT NULL,
  `cost` DOUBLE,
  PRIMARY KEY (`id`),
  UNIQUE KEY `expense_type_id_unique` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `payment_history`
--
DROP TABLE IF EXISTS `payment_history`;
CREATE TABLE `payment_history` (
  `id`                  BIGINT AUTO_INCREMENT,
  `user_id`             BIGINT NOT NULL,
  `date`                DATETIME,
  `status`              VARCHAR(45), #Staying, Not arrived yet, Finished
  `booking_method`      VARCHAR(45), #Phone contact with receptionist, website, booking info, etc
  `staff_id`            BIGINT NULL,
  #user_id is stored in case receptionist supports and books for customer. Staff can earn commission.
  #user_id can be any one who register their information in the system, such as, Security guards, taxi drivers, etc.
  #   commission will be sent to such person at the end of the month, 2 months, quarter, and so on.
  `payment_in_advanced` DOUBLE, #by default, NULL. Customer paying in advanced will be served better.
  PRIMARY KEY (`id`),
  UNIQUE KEY `booking_history_id_unique` (`id`),
  CONSTRAINT `booking_history_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_table` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `expense_history`
--
DROP TABLE IF EXISTS `expense_history`;
CREATE TABLE `expense_history` (
  `id`                 BIGINT AUTO_INCREMENT,
  `room_id`            BIGINT NOT NULL,
  #   `other_expense_id` BIGINT NOT NULL,
  `cost`               DOUBLE,
  `arrival`            DATETIME,
  `leave`              DATETIME,
  `payment_history_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `expense_history_id_unique` (`id`),
  CONSTRAINT `expense_history_room_id` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`),
  CONSTRAINT `expense_history_payment_history_id` FOREIGN KEY (`payment_history_id`) REFERENCES `payment_history` (`id`)
  #   CONSTRAINT `expense_history_other_expense_id` FOREIGN KEY (`other_expense_id`) REFERENCES `other_expense` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `other_expense`
--
DROP TABLE IF EXISTS `other_expense`;
CREATE TABLE `other_expense` (
  `id`                 BIGINT AUTO_INCREMENT,
  `expense_type_id`    BIGINT       NULL, # in case type id is 'other' or NULL, name should be put #this case happens when there's a new expense.
  # case: customer buy something, and have hotel pay for it
  `expense_history_id` BIGINT       NOT NULL,
  `quantity`           TINYINT      NOT NULL,
  `name`               VARCHAR(200) NOT NULL,
  `cost`               DOUBLE, # total cost is calculated by quantity and cost of specific expense.
  `date`               DATETIME,
  PRIMARY KEY (`id`),
  UNIQUE KEY `other_expense_id_unique` (`id`),
  CONSTRAINT `other_expense_expense_type_id` FOREIGN KEY (`expense_type_id`) REFERENCES `expense_type` (`id`),
  CONSTRAINT `other_expense_expense_history_id` FOREIGN KEY (`expense_history_id`) REFERENCES `expense_history` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `promotion`
--    for Customer
--
DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion` (
  `id`       BIGINT AUTO_INCREMENT,
  `title`    VARCHAR(100) NOT NULL,
  `content`  TEXT         NOT NULL,
  `image_id` BIGINT       NOT NULL,
  `user_id`  BIGINT       NOT NULL,
  `percent`  DOUBLE,
  `exp_date` DATETIME,
  PRIMARY KEY (`id`),
  UNIQUE KEY `promotion_id_unique` (`id`),
  CONSTRAINT `promotion_image_id` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`),
  CONSTRAINT `promotion_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_table` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `commission`
--    for Staff (earn commission)
--
DROP TABLE IF EXISTS `commission`;
CREATE TABLE `commission` (
  `id`         BIGINT AUTO_INCREMENT,
  `name`       VARCHAR(100) NOT NULL,
  `type`       VARCHAR(20)  NOT NULL, #Percent or Money
  `user_id`    BIGINT       NOT NULL, #specific staff has been offered commission
  `percent`    DOUBLE,
  `start_date` DATETIME,
  `exp_date`   DATETIME,
  PRIMARY KEY (`id`),
  UNIQUE KEY `commission_id_unique` (`user_id`, `name`),
  CONSTRAINT `commission_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_table` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- Table structure for table `email_template`
--
# DROP TABLE IF EXISTS `email_template`;
# CREATE TABLE `email_template` (
#   `id`         BIGINT AUTO_INCREMENT,
#   `name`       VARCHAR(100) NOT NULL,
#   `type`       VARCHAR(20)  NOT NULL, #Percent or Money
#   `user_id`    BIGINT       NOT NULL, #specific staff has been offered commission
#   `percent`    DOUBLE,
#   `start_date` DATETIME,
#   `exp_date`   DATETIME,
#   PRIMARY KEY (`id`),
#   UNIQUE KEY `commission_id_unique` (`user_id`, `name`),
#   CONSTRAINT `commission_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_table` (`id`)
# )
#   ENGINE = InnoDB
#   DEFAULT CHARSET = utf8;

-- 14 tables

-- Tables for managing customer's order and expectation time of cleaning service.

-- Tables for managing products, goods (beer, tissues, foods, etc).

-- Tables for managing customer's searching behaviors.

-- Tables for managing finance, stuff like drinks, tissues, and so on which are spent on Hotel business.
--    For example: box of Lavie's having 24 bottles. Such quantity will be put to specific table including price for 1 bottle.
--        Once, customer pay for a bottle, the quantity will be deduct by 1
--        This helps predict when we should buy new stuff.
--    In addition, there should be a table storing a scan of bill.