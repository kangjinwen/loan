/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 5.6.25-log : Database - framework
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;



/*Table structure for table `hous_person_type` */

DROP TABLE IF EXISTS `hous_person_type`;

CREATE TABLE `hous_person_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `creator` int(11) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程id',
  `remark` varchar(2048) DEFAULT NULL COMMENT '备注',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型',
  `person_name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `person_number` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `person_phone` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;


