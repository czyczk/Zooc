/*
Navicat MySQL Data Transfer

Source Server         : Lab
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : zooc

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-07-18 15:12:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator` (
  `administrator_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(16) COLLATE utf8mb4_general_ci NOT NULL,
  `type` enum('SYSTEM','ENTERPRISE') COLLATE utf8mb4_general_ci NOT NULL,
  `enterprise_id` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`administrator_id`),
  UNIQUE KEY `unique_administrator_pk` (`administrator_id`),
  UNIQUE KEY `unique_administrator_enterprise_id` (`enterprise_id`),
  CONSTRAINT `fk_administrator_enterprise_id` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`enterprise_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for branch
-- ----------------------------
DROP TABLE IF EXISTS `branch`;
CREATE TABLE `branch` (
  `branch_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) unsigned NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(127) COLLATE utf8mb4_general_ci NOT NULL,
  `latitude` decimal(11,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `telephone` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `is_disabled` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`branch_id`),
  UNIQUE KEY `unique_branch_pk` (`branch_id`),
  KEY `fk_branch_enterprise_id` (`enterprise_id`) USING BTREE,
  CONSTRAINT `fk_branch_enterprise_id` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`enterprise_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) unsigned NOT NULL,
  `name` varchar(63) COLLATE utf8mb4_general_ci NOT NULL,
  `detail` text COLLATE utf8mb4_general_ci NOT NULL,
  `img_url` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  `category_id` bigint(20) unsigned NOT NULL,
  `release_time` datetime NOT NULL,
  `price` decimal(10,2) unsigned NOT NULL,
  `status` enum('AVAILABLE','OFF','IN_REVIEW') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'AVAILABLE',
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `unique_course_pk` (`course_id`),
  KEY `idx_suit_available_course_by_category` (`status`,`category_id`) USING BTREE,
  KEY `fk_course_category_id` (`category_id`),
  KEY `fk_course_enterprise_id` (`enterprise_id`),
  CONSTRAINT `fk_course_category_id` FOREIGN KEY (`category_id`) REFERENCES `course_category` (`category_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_course_enterprise_id` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`enterprise_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for course_category
-- ----------------------------
DROP TABLE IF EXISTS `course_category`;
CREATE TABLE `course_category` (
  `category_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `unique_course_category_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for course_offering
-- ----------------------------
DROP TABLE IF EXISTS `course_offering`;
CREATE TABLE `course_offering` (
  `course_offering_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `course_id` bigint(20) unsigned NOT NULL,
  `branch_id` bigint(20) unsigned NOT NULL,
  `lecturer_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`course_offering_id`),
  KEY `fk_course_offering` (`course_id`),
  KEY `fk_course_offering_branch_id` (`branch_id`),
  KEY `fk_course_offering_lecturer_id` (`lecturer_id`),
  CONSTRAINT `fk_course_offering` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_course_offering_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_course_offering_lecturer_id` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`lecturer_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for enterprise
-- ----------------------------
DROP TABLE IF EXISTS `enterprise`;
CREATE TABLE `enterprise` (
  `enterprise_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `img_url` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `introduction` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `video_url` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `detail` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`enterprise_id`),
  UNIQUE KEY `unique_enterprise_pk` (`enterprise_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for lecturer
-- ----------------------------
DROP TABLE IF EXISTS `lecturer`;
CREATE TABLE `lecturer` (
  `lecturer_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) unsigned NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `photo_url` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  `introduction` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  `is_disabled` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`lecturer_id`),
  KEY `fk_lecturer_enterprise_id` (`enterprise_id`),
  CONSTRAINT `fk_lecturer_enterprise_id` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`enterprise_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for moment
-- ----------------------------
DROP TABLE IF EXISTS `moment`;
CREATE TABLE `moment` (
  `moment_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `enterprise_id` bigint(20) unsigned NOT NULL,
  `content` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`moment_id`),
  KEY `fk_moment_enterprise_id` (`enterprise_id`),
  CONSTRAINT `fk_moment_enterprise_id` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`enterprise_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for moment_comment
-- ----------------------------
DROP TABLE IF EXISTS `moment_comment`;
CREATE TABLE `moment_comment` (
  `moment_comment_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `moment_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `content` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`moment_comment_id`),
  KEY `fk_moment_comment_moment_id` (`moment_id`),
  KEY `fk_moment_comment_user_id` (`user_id`),
  CONSTRAINT `fk_moment_comment_moment_id` FOREIGN KEY (`moment_id`) REFERENCES `moment` (`moment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_moment_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for moment_img
-- ----------------------------
DROP TABLE IF EXISTS `moment_img`;
CREATE TABLE `moment_img` (
  `moment_img_index` tinyint(20) unsigned NOT NULL,
  `moment_id` bigint(20) unsigned NOT NULL,
  `img_url` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`moment_img_index`,`moment_id`),
  KEY `fk_moment_img_moment_id` (`moment_id`),
  CONSTRAINT `fk_moment_img_moment_id` FOREIGN KEY (`moment_id`) REFERENCES `moment` (`moment_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for moment_like
-- ----------------------------
DROP TABLE IF EXISTS `moment_like`;
CREATE TABLE `moment_like` (
  `moment_like_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `moment_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`moment_like_id`),
  KEY `fk_moment_like_moment_id` (`moment_id`),
  KEY `fk_moment_like_user_id` (`user_id`),
  CONSTRAINT `fk_moment_like_moment_id` FOREIGN KEY (`moment_id`) REFERENCES `moment` (`moment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_moment_like_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `course_id` bigint(20) unsigned NOT NULL,
  `time` datetime NOT NULL,
  `status` enum('PENDING','CANCELED','AVAILABLE','REFUND_REQUESTED','REFUNDED') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`order_id`),
  KEY `fk_order_user_id` (`user_id`),
  KEY `fk_order_course_id` (`course_id`),
  CONSTRAINT `fk_order_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_order_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for refund
-- ----------------------------
DROP TABLE IF EXISTS `refund`;
CREATE TABLE `refund` (
  `refund_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) unsigned NOT NULL,
  `time` datetime NOT NULL,
  `reason` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`refund_id`),
  UNIQUE KEY `unique_refund_order_id` (`order_id`),
  CONSTRAINT `fk_refund_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for trial
-- ----------------------------
DROP TABLE IF EXISTS `trial`;
CREATE TABLE `trial` (
  `trial_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(63) COLLATE utf8mb4_general_ci NOT NULL,
  `detail` text COLLATE utf8mb4_general_ci NOT NULL,
  `img_url` varchar(511) COLLATE utf8mb4_general_ci NOT NULL,
  `category_id` bigint(20) unsigned NOT NULL,
  `branch_id` bigint(20) unsigned NOT NULL,
  `lecturer_id` bigint(20) unsigned NOT NULL,
  `release_time` datetime NOT NULL,
  `status` enum('AVAILABLE','OFF','IN_REVIEW') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'IN_REVIEW',
  PRIMARY KEY (`trial_id`),
  KEY `fk_trial_branch_id` (`branch_id`),
  KEY `fk_trial_category_id` (`category_id`),
  KEY `fk_trial_lecturer_id` (`lecturer_id`),
  CONSTRAINT `fk_trial_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_trial_category_id` FOREIGN KEY (`category_id`) REFERENCES `course_category` (`category_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_trial_lecturer_id` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturer` (`lecturer_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for trial_reservation
-- ----------------------------
DROP TABLE IF EXISTS `trial_reservation`;
CREATE TABLE `trial_reservation` (
  `reservation_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `trial_id` bigint(20) unsigned NOT NULL,
  `time` datetime NOT NULL,
  `message` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` enum('PENDING','CANCELED','AVAILABLE','USED','EXPIRED') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`reservation_id`),
  KEY `fk_trial_reservation` (`user_id`),
  KEY `fk_trial_reservation_trial_id` (`trial_id`),
  CONSTRAINT `fk_trial_reservation` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_trial_reservation_trial_id` FOREIGN KEY (`trial_id`) REFERENCES `trial` (`trial_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(16) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `avatar_url` varchar(511) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_mobile` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- View structure for view_available_branch
-- ----------------------------
DROP VIEW IF EXISTS `view_available_branch`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_available_branch` AS select `branch`.`branch_id` AS `branch_id`,`branch`.`enterprise_id` AS `enterprise_id`,`branch`.`name` AS `name`,`branch`.`address` AS `address`,`branch`.`latitude` AS `latitude`,`branch`.`longitude` AS `longitude`,`branch`.`telephone` AS `telephone`,`branch`.`is_disabled` AS `is_disabled` from `branch` where (`branch`.`is_disabled` = 0x00) ;

-- ----------------------------
-- View structure for view_available_course
-- ----------------------------
DROP VIEW IF EXISTS `view_available_course`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_available_course` AS select `course`.`course_id` AS `course_id`,`course`.`enterprise_id` AS `enterprise_id`,`course`.`name` AS `name`,`course`.`detail` AS `detail`,`course`.`img_url` AS `img_url`,`course`.`category_id` AS `category_id`,`course`.`release_time` AS `release_time`,`course`.`price` AS `price`,`course`.`status` AS `status` from `course` where (`course`.`status` = 'AVAILABLE') ;

-- ----------------------------
-- View structure for view_available_lecturer
-- ----------------------------
DROP VIEW IF EXISTS `view_available_lecturer`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_available_lecturer` AS select `lecturer`.`lecturer_id` AS `lecturer_id`,`lecturer`.`enterprise_id` AS `enterprise_id`,`lecturer`.`name` AS `name`,`lecturer`.`photo_url` AS `photo_url`,`lecturer`.`introduction` AS `introduction`,`lecturer`.`is_disabled` AS `is_disabled` from `lecturer` where (`lecturer`.`is_disabled` = 0) ;

-- ----------------------------
-- View structure for view_course_detail
-- ----------------------------
DROP VIEW IF EXISTS `view_course_detail`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_course_detail` AS select `course`.`course_id` AS `course_id`,`course`.`enterprise_id` AS `enterprise_id`,`course`.`name` AS `name`,`course`.`detail` AS `detail`,`course`.`img_url` AS `img_url`,`course`.`category_id` AS `category_id`,`course_category`.`name` AS `category_name`,`course`.`release_time` AS `release_time`,`course`.`price` AS `price`,`course`.`status` AS `status` from (`course` join `course_category` on((`course`.`category_id` = `course_category`.`category_id`))) ;

-- ----------------------------
-- View structure for view_course_offering_detail
-- ----------------------------
DROP VIEW IF EXISTS `view_course_offering_detail`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_course_offering_detail` AS select `course_offering`.`course_offering_id` AS `course_offering_id`,`course_offering`.`course_id` AS `course_id`,`course_offering`.`branch_id` AS `branch_id`,`branch`.`name` AS `branch_name`,`course_offering`.`lecturer_id` AS `lecturer_id`,`lecturer`.`name` AS `lecturer_name` from ((`course_offering` join `branch` on((`course_offering`.`branch_id` = `branch`.`branch_id`))) join `lecturer` on((`course_offering`.`lecturer_id` = `lecturer`.`lecturer_id`))) ;

-- ----------------------------
-- View structure for view_order_detail
-- ----------------------------
DROP VIEW IF EXISTS `view_order_detail`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_order_detail` AS select `order`.`order_id` AS `order_id`,`course`.`enterprise_id` AS `enterprise_id`,`order`.`user_id` AS `user_id`,`user`.`username` AS `username`,`user`.`email` AS `user_email`,`user`.`mobile` AS `user_mobile`,`order`.`course_id` AS `course_id`,`course`.`name` AS `course_name`,`course`.`price` AS `course_price`,`order`.`time` AS `time`,`order`.`status` AS `status`,`refund`.`refund_id` AS `refund_id`,`refund`.`time` AS `refund_time`,`refund`.`reason` AS `refund_reason` from (((`order` join `user` on((`order`.`user_id` = `user`.`user_id`))) join `course` on((`order`.`course_id` = `course`.`course_id`))) left join `refund` on((`refund`.`order_id` = `order`.`order_id`))) ;

-- ----------------------------
-- View structure for view_refund_detail
-- ----------------------------
DROP VIEW IF EXISTS `view_refund_detail`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_refund_detail` AS select `view_order_detail`.`order_id` AS `order_id`,`view_order_detail`.`enterprise_id` AS `enterprise_id`,`view_order_detail`.`user_id` AS `user_id`,`view_order_detail`.`username` AS `username`,`view_order_detail`.`user_email` AS `user_email`,`view_order_detail`.`user_mobile` AS `user_mobile`,`view_order_detail`.`course_id` AS `course_id`,`view_order_detail`.`course_name` AS `course_name`,`view_order_detail`.`course_price` AS `course_price`,`view_order_detail`.`time` AS `time`,`view_order_detail`.`status` AS `status`,`view_order_detail`.`refund_id` AS `refund_id`,`view_order_detail`.`refund_time` AS `refund_time`,`view_order_detail`.`refund_reason` AS `refund_reason` from `view_order_detail` where ((`view_order_detail`.`status` = 'REFUND_REQUESTED') or (`view_order_detail`.`status` = 'REFUNDED')) ;

-- ----------------------------
-- View structure for view_trial_detail
-- ----------------------------
DROP VIEW IF EXISTS `view_trial_detail`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_trial_detail` AS select `trial`.`trial_id` AS `trial_id`,`trial`.`name` AS `name`,`trial`.`detail` AS `detail`,`trial`.`img_url` AS `img_url`,`trial`.`category_id` AS `category_id`,`course_category`.`name` AS `category_name`,`branch`.`enterprise_id` AS `enterprise_id`,`enterprise`.`name` AS `enterprise_name`,`trial`.`branch_id` AS `branch_id`,`branch`.`name` AS `branch_name`,`trial`.`lecturer_id` AS `lecturer_id`,`lecturer`.`name` AS `lecturer_name`,`trial`.`release_time` AS `release_time`,`trial`.`status` AS `status` from ((((`trial` join `course_category` on((`trial`.`category_id` = `course_category`.`category_id`))) join `branch` on((`trial`.`branch_id` = `branch`.`branch_id`))) join `lecturer` on((`trial`.`lecturer_id` = `lecturer`.`lecturer_id`))) join `enterprise` on((`branch`.`enterprise_id` = `enterprise`.`enterprise_id`))) ;
SET FOREIGN_KEY_CHECKS=1;
