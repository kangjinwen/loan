/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.40 : Database - fangdai
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fangdai` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `fangdai`;

/*Table structure for table `act_evt_log` */

DROP TABLE IF EXISTS `act_evt_log`;

CREATE TABLE `act_evt_log` (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DATA_` longblob,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ge_bytearray` */

DROP TABLE IF EXISTS `act_ge_bytearray`;

CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ge_property` */

DROP TABLE IF EXISTS `act_ge_property`;

CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_actinst` */

DROP TABLE IF EXISTS `act_hi_actinst`;

CREATE TABLE `act_hi_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_PROCINST` (`PROC_INST_ID_`,`ACT_ID_`),
  KEY `ACT_IDX_HI_ACT_INST_EXEC` (`EXECUTION_ID_`,`ACT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_attachment` */

DROP TABLE IF EXISTS `act_hi_attachment`;

CREATE TABLE `act_hi_attachment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_comment` */

DROP TABLE IF EXISTS `act_hi_comment`;

CREATE TABLE `act_hi_comment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_detail` */

DROP TABLE IF EXISTS `act_hi_detail`;

CREATE TABLE `act_hi_detail` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_identitylink` */

DROP TABLE IF EXISTS `act_hi_identitylink`;

CREATE TABLE `act_hi_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_TASK` (`TASK_ID_`),
  KEY `ACT_IDX_HI_IDENT_LNK_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_procinst` */

DROP TABLE IF EXISTS `act_hi_procinst`;

CREATE TABLE `act_hi_procinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_taskinst` */

DROP TABLE IF EXISTS `act_hi_taskinst`;

CREATE TABLE `act_hi_taskinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `CLAIM_TIME_` datetime(3) DEFAULT NULL,
  `END_TIME_` datetime(3) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_TASK_INST_PROCINST` (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_hi_varinst` */

DROP TABLE IF EXISTS `act_hi_varinst`;

CREATE TABLE `act_hi_varinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` datetime(3) DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_PROCVAR_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_PROCVAR_NAME_TYPE` (`NAME_`,`VAR_TYPE_`),
  KEY `ACT_IDX_HI_PROCVAR_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_id_group` */

DROP TABLE IF EXISTS `act_id_group`;

CREATE TABLE `act_id_group` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_id_info` */

DROP TABLE IF EXISTS `act_id_info`;

CREATE TABLE `act_id_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_id_membership` */

DROP TABLE IF EXISTS `act_id_membership`;

CREATE TABLE `act_id_membership` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_id_user` */

DROP TABLE IF EXISTS `act_id_user`;

CREATE TABLE `act_id_user` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_procdef_info` */

DROP TABLE IF EXISTS `act_procdef_info`;

CREATE TABLE `act_procdef_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_IDX_INFO_PROCDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_INFO_JSON_BA` (`INFO_JSON_ID_`),
  CONSTRAINT `ACT_FK_INFO_JSON_BA` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_INFO_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_re_deployment` */

DROP TABLE IF EXISTS `act_re_deployment`;

CREATE TABLE `act_re_deployment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `DEPLOY_TIME_` timestamp(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_re_model` */

DROP TABLE IF EXISTS `act_re_model`;

CREATE TABLE `act_re_model` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_MODEL_SOURCE` (`EDITOR_SOURCE_VALUE_ID_`),
  KEY `ACT_FK_MODEL_SOURCE_EXTRA` (`EDITOR_SOURCE_EXTRA_VALUE_ID_`),
  KEY `ACT_FK_MODEL_DEPLOYMENT` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_re_procdef` */

DROP TABLE IF EXISTS `act_re_procdef`;

CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ru_event_subscr` */

DROP TABLE IF EXISTS `act_ru_event_subscr`;

CREATE TABLE `act_ru_event_subscr` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ru_execution` */

DROP TABLE IF EXISTS `act_ru_execution`;

CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  KEY `ACT_FK_EXE_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='执行对象表（一个流程实例，可以有多个执行对象）';

/*Table structure for table `act_ru_identitylink` */

DROP TABLE IF EXISTS `act_ru_identitylink`;

CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  KEY `ACT_FK_IDL_PROCINST` (`PROC_INST_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ru_job` */

DROP TABLE IF EXISTS `act_ru_job`;

CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ru_task` */

DROP TABLE IF EXISTS `act_ru_task`;

CREATE TABLE `act_ru_task` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `DUE_DATE_` datetime(3) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT '',
  `FORM_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `act_ru_variable` */

DROP TABLE IF EXISTS `act_ru_variable`;

CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `channel_partner` */

DROP TABLE IF EXISTS `channel_partner`;

CREATE TABLE `channel_partner` (
  `ID` int(12) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PARTNER_NAME` varchar(128) DEFAULT NULL COMMENT '渠道名称',
  `LOGO` varchar(500) DEFAULT NULL COMMENT '渠道的logo图片路径',
  `TITLE` varchar(128) DEFAULT NULL COMMENT '渠道所使用的title',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `IS_DELETE` tinyint(4) DEFAULT NULL COMMENT '是否删除：1代表可用，0代表已被删除',
  `REMARK` varchar(128) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `charge_data` */

DROP TABLE IF EXISTS `charge_data`;

CREATE TABLE `charge_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '押品名称',
  `status` int(10) DEFAULT NULL COMMENT '押品状态（1.在库2.借出3.出库）',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `process_instance_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '流程id',
  `file_number` varchar(64) DEFAULT NULL COMMENT '档案编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `charge_data_log` */

DROP TABLE IF EXISTS `charge_data_log`;

CREATE TABLE `charge_data_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT '' COMMENT '借出人/归还人',
  `create_time` datetime DEFAULT NULL COMMENT '借出时间/归还时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `charge_data_id` int(11) DEFAULT NULL COMMENT '押品资料id',
  `process_instance_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '流程id',
  `check_in_time` datetime DEFAULT NULL COMMENT '登记时间',
  `creator` varchar(64) DEFAULT NULL COMMENT '登记人',
  `type` varchar(10) DEFAULT NULL COMMENT '类型(1.借出2.归还)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `charge_data_store` */

DROP TABLE IF EXISTS `charge_data_store`;

CREATE TABLE `charge_data_store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(64) DEFAULT NULL COMMENT '客户名称',
  `cert_number` varchar(32) DEFAULT NULL COMMENT '客户身份证号码',
  `account` decimal(18,2) DEFAULT NULL COMMENT '贷款金额',
  `time_limit` int(11) DEFAULT NULL COMMENT '借款期限按/月',
  `repayment_rate` decimal(10,4) DEFAULT NULL COMMENT '底点利率',
  `single_rate` decimal(10,4) DEFAULT NULL COMMENT '成单利率',
  `business_origin` varchar(64) DEFAULT NULL COMMENT '业务来源',
  `institution_name` varchar(64) DEFAULT NULL COMMENT '机构名称',
  `customer_manager` varchar(64) DEFAULT NULL COMMENT '报单人',
  `charge_status` int(11) DEFAULT NULL COMMENT '押品状态(1.待入库2.已入库3.待出库4.已出库)',
  `in_store_time` date DEFAULT NULL COMMENT '入库日期',
  `out_store_time` date DEFAULT NULL COMMENT '出库日期',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `loan_date` date DEFAULT NULL COMMENT '放款日期',
  `lender` varchar(64) DEFAULT NULL COMMENT '出借人',
  `trustee_of_lender` varchar(64) DEFAULT NULL COMMENT '出借人受托人',
  `product_name` varchar(100) DEFAULT NULL COMMENT '产品名/订单类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `charge_disposal_log` */

DROP TABLE IF EXISTS `charge_disposal_log`;

CREATE TABLE `charge_disposal_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creator` int(11) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程实例ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='押品处置记录';

/*Table structure for table `fel_formula` */

DROP TABLE IF EXISTS `fel_formula`;

CREATE TABLE `fel_formula` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `chinese_name` varchar(255) DEFAULT NULL COMMENT '中文名',
  `english_name` varchar(255) DEFAULT NULL COMMENT '英文名',
  `unit` varchar(255) DEFAULT NULL COMMENT '单位',
  `data_source` varchar(255) DEFAULT NULL COMMENT '数据来源(1为系统计算,2为人工录入)',
  `formula_chinese` varchar(512) DEFAULT NULL,
  `formula` varchar(512) DEFAULT NULL COMMENT '公式内容,若内嵌公式,此为假公式',
  `note` varchar(255) DEFAULT NULL COMMENT '注释',
  `param_id` varchar(255) NOT NULL COMMENT '参数表,id例:(1,2,3,4,5,6,)',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_name` varchar(255) DEFAULT NULL COMMENT '修改人',
  `state` int(2) DEFAULT '0' COMMENT '是否启用(0为启用,1为不启用)',
  `delete_state` tinyint(2) DEFAULT '0' COMMENT '删除状态(0未删除,-1为删除)',
  `nested_state` tinyint(2) DEFAULT '0' COMMENT '是否内嵌公式(0:否,1为是)',
  `nested_formula` varchar(255) DEFAULT NULL COMMENT '内嵌公式,专属,此为真公式',
  `type_id` int(11) NOT NULL COMMENT '类型表的id',
  `formula_json` varchar(4096) DEFAULT NULL COMMENT '页面的,公式json',
  PRIMARY KEY (`id`),
  KEY `idk` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='公式配置------公式级';

/*Table structure for table `fel_param` */

DROP TABLE IF EXISTS `fel_param`;

CREATE TABLE `fel_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `chinese_name` varchar(255) DEFAULT NULL COMMENT '中文名',
  `english_name` varchar(255) DEFAULT NULL COMMENT '英文名',
  `data_type` varchar(16) DEFAULT NULL,
  `data_source` varchar(255) DEFAULT NULL COMMENT '数据来源',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_name` varchar(255) DEFAULT NULL COMMENT '修改人',
  `state` tinyint(2) DEFAULT '0' COMMENT '状态(0为业务,1为运算符)',
  `delete_state` tinyint(2) DEFAULT '0' COMMENT '删除状态(0未删除,-1为删除)',
  PRIMARY KEY (`id`),
  KEY `idp` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='公式配置------公式元素 映射 包含参数，数字，运算符，其他公式';

/*Table structure for table `fel_type` */

DROP TABLE IF EXISTS `fel_type`;

CREATE TABLE `fel_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `chinese_name` varchar(255) DEFAULT NULL COMMENT '中文名',
  `english_name` varchar(255) DEFAULT NULL COMMENT '英文名',
  `create_date` datetime DEFAULT NULL COMMENT '插入时间',
  `create_name` varchar(255) DEFAULT NULL COMMENT '插入人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_name` varchar(255) DEFAULT NULL COMMENT '修改人',
  `state` tinyint(2) DEFAULT '0' COMMENT '状态',
  `delete_state` tinyint(2) DEFAULT '0' COMMENT '删除状态(0未删除,-1为删除)',
  PRIMARY KEY (`id`),
  KEY `id_k` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='公式配置------类型级(后台查询使用)';

/*Table structure for table `hous_advance_apply` */

DROP TABLE IF EXISTS `hous_advance_apply`;

CREATE TABLE `hous_advance_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '主流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目ID',
  `advance_apply_operator` varchar(64) DEFAULT NULL COMMENT '垫资申请操作员',
  `advance_apply_time` datetime DEFAULT NULL COMMENT '申请垫资时间',
  `advance_apply_amount` decimal(20,2) DEFAULT NULL COMMENT '申请垫资金额',
  `advance_amount` decimal(20,2) DEFAULT NULL COMMENT '垫资费',
  `advance_rate_amount` decimal(10,4) DEFAULT '0.0000',
  `new_process_instance_id` varchar(64) DEFAULT NULL COMMENT '垫资分支流程ID',
  `account_holder` varchar(64) DEFAULT NULL COMMENT '开户人姓名',
  `bank_card` varchar(64) DEFAULT NULL COMMENT '银行卡号',
  `opening_bank` varchar(64) DEFAULT NULL COMMENT '开户行',
  `status` varchar(64) DEFAULT NULL COMMENT '垫资流程状态',
  `charged_amount` decimal(64,2) DEFAULT NULL COMMENT '已收取的垫资费',
  `time_limit` int(11) DEFAULT NULL COMMENT '垫资期限',
  `charge_way` varchar(4) DEFAULT NULL COMMENT '垫资收取方式(1.线上收取 2.线下收取)',
  `house_situation` varchar(4) DEFAULT NULL COMMENT '房屋现状(1.正常 2.不正常)',
  `is_closed_down` varchar(4) DEFAULT NULL COMMENT '有无查封(0.无 1.有)',
  `is_abnormal` varchar(4) DEFAULT NULL COMMENT '有无异常(0.无 1.有)',
  `apply_remark` varchar(512) DEFAULT NULL COMMENT '垫资申请备注',
  `transfer_file_remark` varchar(512) DEFAULT NULL COMMENT '建委调档备注',
  `advance_status` varchar(4) DEFAULT NULL COMMENT '垫资状态(1.垫资放款 2.垫资收回)',
  `take_back_time` datetime DEFAULT NULL COMMENT '垫资收回时间（财务放款时间）',
  `take_back_operator` varchar(64) DEFAULT NULL COMMENT '垫资收回操作人（放款财务姓名）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

/*Table structure for table `hous_advance_notarize` */

DROP TABLE IF EXISTS `hous_advance_notarize`;

CREATE TABLE `hous_advance_notarize` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '主流程ID',
  `new_process_instance_id` varchar(64) DEFAULT NULL COMMENT '垫资分支流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目ID',
  `lender` varchar(64) DEFAULT NULL COMMENT '出借人',
  `trustee_of_lender` varchar(64) DEFAULT NULL COMMENT '出借人受托人',
  `trustee` varchar(64) DEFAULT NULL COMMENT '受托人',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Table structure for table `hous_assessment_agencies` */

DROP TABLE IF EXISTS `hous_assessment_agencies`;

CREATE TABLE `hous_assessment_agencies` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `property_id` int(11) DEFAULT NULL COMMENT '房产信息表id',
  `assessment_agencies` tinyint(4) DEFAULT NULL COMMENT '评估机构',
  `assessed_value` decimal(20,2) DEFAULT NULL COMMENT '评估值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评估机构表';

/*Table structure for table `hous_bills` */

DROP TABLE IF EXISTS `hous_bills`;

CREATE TABLE `hous_bills` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` int(11) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `account_holder_name` varchar(100) DEFAULT NULL COMMENT '读取抵押登记开户名',
  `cardId` varchar(32) DEFAULT NULL COMMENT '读取抵押登记放款卡号/打款卡号',
  `bank_flag` smallint(4) DEFAULT NULL,
  `account` decimal(20,2) DEFAULT NULL COMMENT '放款金额/打款金额',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程id',
  `type` varchar(10) DEFAULT NULL COMMENT '类型',
  `bank_name` varchar(255) DEFAULT NULL COMMENT '读取抵押登记开户行',
  `remark` varchar(2048) DEFAULT NULL COMMENT '备注',
  `third_card_number` varchar(32) DEFAULT NULL COMMENT '读取抵押登记第三方卡号',
  `third_bank_account` varchar(64) DEFAULT NULL COMMENT '读取抵押登记第三方开户名',
  `third_account_opening` varchar(64) DEFAULT NULL COMMENT '读取抵押登记第三方开户行',
  `third_account` decimal(20,2) DEFAULT NULL COMMENT '第三方金额',
  `purpose` varchar(64) DEFAULT NULL COMMENT '用途',
  `third_transfer_fee` decimal(18,2) DEFAULT '0.00' COMMENT '三方卡转账手续费',
  PRIMARY KEY (`id`),
  KEY `hous_bills_process_instance_id` (`process_instance_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='放款单/打款单';

/*Table structure for table `hous_borrowing_basics` */

DROP TABLE IF EXISTS `hous_borrowing_basics`;

CREATE TABLE `hous_borrowing_basics` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `name` varchar(64) DEFAULT NULL COMMENT '贷款人姓名',
  `cert_number` varchar(32) DEFAULT NULL COMMENT '贷款人身份证号',
  `phone` varchar(20) DEFAULT NULL COMMENT '贷款人联系电话',
  `marry_status` tinyint(4) DEFAULT NULL COMMENT '婚姻状况',
  `spouse_name` varchar(64) DEFAULT NULL COMMENT '配偶姓名',
  `spouse_cert_number` varchar(32) DEFAULT NULL COMMENT '配偶身份证号',
  `spouse_phone` varchar(20) DEFAULT NULL COMMENT '配偶联系电话',
  `total_borrowed` varchar(64) DEFAULT NULL COMMENT '共借人',
  `total_borrowed_cert_number` varchar(32) DEFAULT NULL COMMENT '共借人身份证号',
  `residential_address_id` varchar(32) DEFAULT NULL COMMENT '居住地',
  `residential_address` varchar(128) DEFAULT NULL COMMENT '居住地详细地址',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `father_name` varchar(64) DEFAULT NULL COMMENT '贷款人父亲姓名',
  `mother_name` varchar(64) DEFAULT NULL COMMENT '贷款人母亲姓名',
  `father_number` varchar(64) DEFAULT NULL COMMENT '贷款人父亲身份证号',
  `mother_number` varchar(64) DEFAULT NULL COMMENT '贷款人母亲身份证号',
  `sex` tinyint(4) DEFAULT NULL COMMENT '贷款人性别',
  `age` varchar(64) DEFAULT NULL COMMENT '贷款人年龄',
  PRIMARY KEY (`id`),
  KEY `hous_borrowing_basics_process_instance_id` (`process_instance_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='借款基本信息表';

/*Table structure for table `hous_control_information` */

DROP TABLE IF EXISTS `hous_control_information`;

CREATE TABLE `hous_control_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `name_identification_consistent` tinyint(4) DEFAULT NULL COMMENT '姓名身份证是否一致',
  `affiliates_number` int(4) DEFAULT NULL COMMENT '关联企业数量',
  `follow_enterprises_category` tinyint(4) DEFAULT NULL COMMENT '关注类企业类别',
  `is_executed` tinyint(4) DEFAULT NULL COMMENT '是否有被执行',
  `items` int(4) DEFAULT NULL COMMENT '笔数',
  `money` decimal(20,2) DEFAULT NULL COMMENT '金额',
  `concluded` varchar(128) DEFAULT NULL COMMENT '结案',
  `occupied_channel` tinyint(4) DEFAULT NULL COMMENT '已被占通道',
  `maximum_mortgage` tinyint(4) DEFAULT NULL COMMENT '已上最高额抵押',
  `judicial_inquiry` tinyint(4) DEFAULT NULL COMMENT '司法查询/行政限制',
  `arrived_nature` varchar(64) DEFAULT NULL,
  `against_bank_name` varchar(64) DEFAULT NULL COMMENT '一抵银行名称',
  `arrived_amount` decimal(20,2) DEFAULT NULL COMMENT '一抵金额',
  `arrived_rates` decimal(10,4) DEFAULT '0.0000',
  `assessed_value` decimal(20,2) DEFAULT NULL COMMENT '评估值(元)',
  `fast_bid` decimal(20,2) DEFAULT NULL COMMENT '快出价(元)',
  `two_arrived_nature` varchar(64) DEFAULT NULL COMMENT '二抵性质',
  `two_against_bank_name` varchar(64) DEFAULT NULL COMMENT '二抵银行名称',
  `two_arrived_amount` decimal(20,2) DEFAULT NULL COMMENT '二抵金额',
  `two_arrived_rates` decimal(10,4) DEFAULT '0.0000',
  `three_arrived_nature` varchar(64) DEFAULT NULL COMMENT '三抵性质',
  `three_against_bank_name` varchar(64) DEFAULT NULL COMMENT '三抵银行名称',
  `three_arrived_amount` decimal(20,2) DEFAULT NULL COMMENT '三抵金额',
  `three_arrived_rates` decimal(10,4) DEFAULT '0.0000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='风控信息表(面审)';

/*Table structure for table `hous_credit_information` */

DROP TABLE IF EXISTS `hous_credit_information`;

CREATE TABLE `hous_credit_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `good_credit` tinyint(4) DEFAULT NULL COMMENT '征信良好',
  `current_overdue` tinyint(4) DEFAULT NULL COMMENT '当前逾期',
  `overdue_amounts` decimal(20,2) DEFAULT NULL COMMENT '逾期金额',
  `secured_loan` tinyint(4) DEFAULT NULL COMMENT '担保性贷款',
  `secured_loan_amounts` decimal(20,2) DEFAULT NULL COMMENT '担保性贷款金额',
  `bad_debt` tinyint(4) DEFAULT NULL COMMENT '呆账',
  `bad_debt_items` int(4) DEFAULT NULL COMMENT '呆账笔数',
  `bad_debt_amounts` decimal(20,2) DEFAULT NULL COMMENT '呆账金额',
  `nearly_twp_years` tinyint(4) DEFAULT NULL COMMENT '近2年连3累6',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `photo_matches_id_card` tinyint(4) DEFAULT NULL COMMENT '证件照片相符(身份证)',
  `photo_matches_marriage_certificate` tinyint(4) DEFAULT NULL COMMENT '证件照片相符(结婚证)',
  `photo_matches_divorce_certificate` tinyint(4) DEFAULT NULL COMMENT '证件照片相符(离婚证)',
  `identity_informaiton_id_card` tinyint(4) DEFAULT NULL COMMENT '证件信息一致(身份证)',
  `identity_informaiton_marriage_certificate` tinyint(4) DEFAULT NULL COMMENT '证件信息一致(结婚证)',
  `identity_informaiton_divorce_certificate` tinyint(4) DEFAULT NULL COMMENT '证件信息一致(离婚证)',
  `identity_informaiton_account_book` tinyint(4) DEFAULT NULL COMMENT '证件信息一致(户口本)',
  `location_property_consistent` tinyint(4) DEFAULT NULL COMMENT '户口与房屋所在地是否一致',
  `foreclosed_consistency` tinyint(4) DEFAULT NULL COMMENT '抵贷是否一致',
  `real_loan_name` varchar(64) DEFAULT NULL COMMENT '抵贷实际用款人',
  `whether_total` tinyint(4) DEFAULT NULL COMMENT '是否共借',
  `guarantor` tinyint(4) DEFAULT NULL COMMENT '是否有保证人',
  `only_housing` tinyint(4) DEFAULT NULL COMMENT '是否家庭名下唯一房产',
  `room_is_full_five_years` tinyint(4) DEFAULT NULL COMMENT '房本是否满五年',
  `alternate_property_address_id` varchar(32) DEFAULT NULL COMMENT '备用房屋地址',
  `alternate_property_address` varchar(128) DEFAULT NULL COMMENT '备用房屋详细地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='征信信息表(面审)';

/*Table structure for table `hous_data_list` */

DROP TABLE IF EXISTS `hous_data_list`;

CREATE TABLE `hous_data_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `account_book` tinyint(4) DEFAULT NULL COMMENT '户口本',
  `marriage_certificate` tinyint(4) DEFAULT NULL COMMENT '结婚证',
  `divorce_certificate` tinyint(4) DEFAULT NULL COMMENT '离婚证',
  `divorce_agreement` tinyint(4) DEFAULT NULL COMMENT '离婚协议',
  `civil_mediation` tinyint(4) DEFAULT NULL COMMENT '民事调解书',
  `court_verdict` tinyint(4) DEFAULT NULL COMMENT '法院判决书',
  `only_housing` tinyint(4) DEFAULT NULL COMMENT '是否唯一住房',
  `deed` tinyint(4) DEFAULT NULL COMMENT '房产证',
  `purchase_contract` tinyint(4) DEFAULT NULL COMMENT '购房合同',
  `lease_contract` tinyint(4) DEFAULT NULL COMMENT '租赁合同',
  `mortgage_loan_contract` tinyint(4) DEFAULT NULL COMMENT '抵押贷款合同',
  `loan_card` tinyint(4) DEFAULT NULL COMMENT '贷款卡',
  `credit_report` tinyint(4) DEFAULT NULL COMMENT '征信报告',
  `two_or_five` tinyint(4) DEFAULT NULL COMMENT '满二或满五',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资料清单表(权证下户)';

/*Table structure for table `hous_enquiry_institution` */

DROP TABLE IF EXISTS `hous_enquiry_institution`;

CREATE TABLE `hous_enquiry_institution` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `institution_type` tinyint(4) DEFAULT NULL COMMENT '机构名称',
  `mortgage` tinyint(4) DEFAULT NULL COMMENT '有无抵押',
  `seizure` tinyint(4) DEFAULT NULL COMMENT '有无查封',
  `business_occupancy` tinyint(4) DEFAULT NULL COMMENT '有无业务占用',
  `net_signed_occupancy` tinyint(4) DEFAULT NULL COMMENT '网签占用',
  `hochstbetragshypothek` tinyint(4) DEFAULT NULL COMMENT '是否为最高额抵押',
  `affiliate` tinyint(4) DEFAULT NULL COMMENT '是否有关联企业',
  `legal_process_performed` tinyint(4) DEFAULT NULL COMMENT '是否有进入法律程序的被执行信息',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='查询机构表';

/*Table structure for table `hous_face_trial` */

DROP TABLE IF EXISTS `hous_face_trial`;

CREATE TABLE `hous_face_trial` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `property_address_id` varchar(32) DEFAULT NULL COMMENT '房产地址',
  `property_address` varchar(128) DEFAULT NULL COMMENT '房产详细地址',
  `property_certificate` varchar(64) DEFAULT NULL COMMENT '房权证号',
  `property_properties` tinyint(4) DEFAULT NULL COMMENT '房产性质',
  `affordable_room_time` datetime DEFAULT NULL COMMENT '经适房房本时间',
  `purchase_invoices_time` datetime DEFAULT NULL COMMENT '经适房购房发票时间',
  `property_listed_proof` tinyint(4) DEFAULT NULL COMMENT '房改房上市证明',
  `planning_purposes` tinyint(4) DEFAULT NULL COMMENT '规划用途',
  `key_disk_query` tinyint(4) DEFAULT NULL COMMENT '钥匙盘查询',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='面审信息表';

/*Table structure for table `hous_intermediary_information` */

DROP TABLE IF EXISTS `hous_intermediary_information`;

CREATE TABLE `hous_intermediary_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `door_name` varchar(128) DEFAULT NULL COMMENT '门名名称',
  `principal_name` varchar(64) DEFAULT NULL COMMENT '负责人姓名',
  `principal_phone` varchar(20) DEFAULT NULL COMMENT '负责人联系方式',
  `tax_details` varchar(128) DEFAULT NULL COMMENT '税款详情',
  `normal_price` decimal(20,2) DEFAULT NULL COMMENT '正常价格(万)',
  `fast_transaction_price` decimal(20,2) DEFAULT NULL COMMENT '快速成交价格(万)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `school` tinyint(4) DEFAULT NULL COMMENT '学校',
  `hospital` tinyint(4) DEFAULT NULL COMMENT '医院',
  `shopping` tinyint(4) DEFAULT NULL COMMENT '购物等配套情况',
  `housing_value_faster` decimal(20,2) DEFAULT NULL COMMENT '房屋快出值(元)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房屋中介信息(权证下户)';

/*Table structure for table `hous_loanfees` */

DROP TABLE IF EXISTS `hous_loanfees`;

CREATE TABLE `hous_loanfees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_belongs` int(3) DEFAULT NULL COMMENT '报单来源',
  `financial_advisers` varchar(64) DEFAULT NULL COMMENT '金融顾问',
  `institution_name` varchar(64) DEFAULT NULL COMMENT '机构名称',
  `salesman` varchar(64) DEFAULT NULL COMMENT '业务员',
  `return_rate` decimal(10,4) DEFAULT '0.0000',
  `return_fee` decimal(20,2) DEFAULT NULL COMMENT '返费金额',
  `return_limit` int(3) DEFAULT NULL COMMENT '返费期限',
  `return_card` varchar(32) DEFAULT NULL COMMENT '返费卡号',
  `return_bank` int(4) DEFAULT NULL COMMENT '返费开户行',
  `service_fee` decimal(20,2) DEFAULT NULL COMMENT '服务费金额',
  `service_name` varchar(32) DEFAULT '' COMMENT '姓名（服务费）',
  `service_card` varchar(32) DEFAULT NULL COMMENT '放款卡号',
  `service_bank` int(4) DEFAULT NULL COMMENT '开户行（服务费）',
  `process_instance_id` varchar(64) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modifier` int(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `first_interest` decimal(18,2) DEFAULT '0.00',
  `sum_account` decimal(20,2) DEFAULT NULL COMMENT '打款总额',
  `return_bank_name` varchar(255) DEFAULT NULL COMMENT '返费开户行',
  `service_bank_name` varchar(255) DEFAULT NULL COMMENT '开户行',
  `collection_service_fee` decimal(20,2) DEFAULT NULL COMMENT '代收服务费金额',
  `return_service_fee` decimal(20,2) DEFAULT NULL COMMENT '返还服务费金额',
  `collection_service_name` varchar(64) DEFAULT NULL COMMENT '代收服务费姓名',
  `collection_service_card` varchar(64) DEFAULT NULL COMMENT '代收服务费卡号',
  `collection_service_bank` varchar(64) DEFAULT NULL COMMENT '代收服务费开户行',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='返费签单/代收服务费';

/*Table structure for table `hous_lower_cost` */

DROP TABLE IF EXISTS `hous_lower_cost`;

CREATE TABLE `hous_lower_cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `finance_specialist` varchar(64) DEFAULT NULL COMMENT '财务专员',
  `receivable_account` decimal(20,2) DEFAULT NULL COMMENT '应收下户费',
  `actual_fee` decimal(20,2) DEFAULT NULL COMMENT '实收下户费',
  `collection_form` tinyint(4) DEFAULT NULL COMMENT '收取形式',
  `refund_operator` varchar(64) DEFAULT NULL COMMENT '退费申请操作员',
  `apply_refund_time` datetime DEFAULT NULL COMMENT '申请退费时间',
  `apply_refund_amount` decimal(20,2) DEFAULT NULL COMMENT '申请退费金额(元)',
  `apply_refund_is_success` tinyint(4) DEFAULT '0' COMMENT '退费是否成功,0不成功,1成功',
  `pay_person` varchar(64) DEFAULT NULL COMMENT '缴纳人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='下户费表';

/*Table structure for table `hous_marriage_information` */

DROP TABLE IF EXISTS `hous_marriage_information`;

CREATE TABLE `hous_marriage_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `marital_status` tinyint(4) DEFAULT NULL COMMENT '婚姻状况',
  `documents_time` datetime DEFAULT NULL COMMENT '证件日期',
  `divorce_agreement` tinyint(4) DEFAULT NULL COMMENT '离婚协议',
  `court_verdict` tinyint(4) DEFAULT NULL COMMENT '法院判决',
  `room_time` datetime DEFAULT NULL COMMENT '房本日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='婚姻信息表(面审)';

/*Table structure for table `hous_mortgage_registration` */

DROP TABLE IF EXISTS `hous_mortgage_registration`;

CREATE TABLE `hous_mortgage_registration` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT '0000' COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `mortgage` tinyint(4) DEFAULT NULL COMMENT '有无抵押',
  `seizure` tinyint(4) DEFAULT NULL COMMENT '有无查封',
  `business_occupancy` tinyint(4) DEFAULT NULL COMMENT '有无业务占用',
  `net_signed_occupancy` tinyint(4) DEFAULT NULL COMMENT '有无网签占用',
  `credit_card_number` varchar(32) DEFAULT NULL COMMENT '卡号',
  `bank_account` varchar(64) DEFAULT NULL COMMENT '开户行',
  `account_opening` varchar(64) DEFAULT NULL COMMENT '开户网点',
  `charge_number` varchar(64) DEFAULT NULL COMMENT '押品编号',
  `his_right_certificate` varchar(64) DEFAULT NULL COMMENT '他项权利证名称(即解押押品名称)',
  `mortgage_right` varchar(64) DEFAULT NULL COMMENT '抵押权人',
  `client` varchar(64) DEFAULT NULL COMMENT '委托人',
  `collection_time` datetime DEFAULT NULL COMMENT '领取时间(即解押押品入库时间)',
  `registered_person` varchar(64) DEFAULT NULL COMMENT '登记人(即解押入库人)',
  `outbound_collection_time` datetime DEFAULT NULL COMMENT '押品出库时间',
  `outbound_person` varchar(50) DEFAULT NULL COMMENT '出库人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `third_card_number` varchar(32) DEFAULT NULL COMMENT '第三方卡号',
  `third_bank_account` varchar(64) DEFAULT NULL COMMENT '第三方开户名',
  `third_account_opening` varchar(64) DEFAULT NULL COMMENT '第三方开户行',
  `lenders_mortgage` varchar(64) DEFAULT NULL COMMENT '出借人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抵押登记表';

/*Table structure for table `hous_notarization_registration` */

DROP TABLE IF EXISTS `hous_notarization_registration`;

CREATE TABLE `hous_notarization_registration` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT '0000' COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `lender` varchar(64) DEFAULT NULL COMMENT '出借人',
  `trustee` varchar(64) DEFAULT NULL COMMENT '受托人',
  `trustee_of_lender` varchar(64) DEFAULT NULL COMMENT '出借人的受托人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公证登记表';

/*Table structure for table `hous_orito_information` */

DROP TABLE IF EXISTS `hous_orito_information`;

CREATE TABLE `hous_orito_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `property_address_id` varchar(32) DEFAULT NULL COMMENT '房产地址',
  `property_address` varchar(128) DEFAULT NULL COMMENT '房产详细地址',
  `community_name` varchar(128) DEFAULT NULL COMMENT '小区名称',
  `building_brands` tinyint(4) DEFAULT NULL COMMENT '楼牌号是否与房本一致',
  `living_people` varchar(32) DEFAULT NULL COMMENT '居住人含',
  `furnishing_status` tinyint(4) DEFAULT NULL COMMENT '装修状况',
  `room` int(3) DEFAULT NULL COMMENT '房屋户型(室)',
  `hall` int(3) DEFAULT NULL COMMENT '房屋户型(厅)',
  `guard` int(3) DEFAULT NULL COMMENT '房屋户型(卫)',
  `floor` int(3) DEFAULT NULL COMMENT '所在楼层',
  `total_floors` int(3) DEFAULT NULL COMMENT '总楼层',
  `housing_orientation` varchar(64) DEFAULT NULL COMMENT '房屋朝向',
  `living_conditions` tinyint(4) DEFAULT NULL COMMENT '居住情况',
  `purchasing_power` tinyint(4) DEFAULT NULL COMMENT '租户签署放弃优先购买权',
  `investigator` varchar(64) DEFAULT NULL COMMENT '调查员',
  `survey_time` datetime DEFAULT NULL COMMENT '调查日期',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='下户信息表(权证下户)';

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
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;

/*Table structure for table `hous_property_information` */

DROP TABLE IF EXISTS `hous_property_information`;

CREATE TABLE `hous_property_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `property_address_id` varchar(32) DEFAULT NULL COMMENT '房产地址',
  `property_address` varchar(128) DEFAULT NULL COMMENT '房产详细地址',
  `property_area` decimal(20,2) DEFAULT NULL COMMENT '房产面积',
  `property_properties` tinyint(4) DEFAULT NULL COMMENT '房产性质',
  `property_situation` tinyint(4) DEFAULT NULL COMMENT '房产现状',
  `whether_one_contact` tinyint(4) DEFAULT NULL COMMENT '是否一抵',
  `against_one_mortgagee` varchar(64) DEFAULT NULL COMMENT '一抵抵押权人',
  `against_one_amount` decimal(20,2) DEFAULT NULL COMMENT '一抵金额',
  `whether_two_contact` tinyint(4) DEFAULT NULL COMMENT '是否二抵',
  `against_two_mortgagee` varchar(64) DEFAULT NULL COMMENT '二抵抵押权人',
  `against_two_amount` decimal(20,2) DEFAULT NULL COMMENT '二抵金额',
  `house_number` varchar(64) DEFAULT NULL COMMENT '房本号码',
  `mortgage_situation` tinyint(4) DEFAULT NULL COMMENT '房屋抵押情况',
  `key_disk_query` tinyint(4) DEFAULT NULL COMMENT '钥匙盘查询',
  `planning_purposes` tinyint(4) DEFAULT NULL COMMENT '规划用途',
  `remark_one` varchar(64) DEFAULT NULL COMMENT '一抵备注',
  `remark_two` varchar(64) DEFAULT NULL COMMENT '二抵备注',
  PRIMARY KEY (`id`),
  KEY `hous_property_information_process_instance_id` (`process_instance_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1349 DEFAULT CHARSET=utf8 COMMENT='房产信息表';

/*Table structure for table `hous_quick_information` */

DROP TABLE IF EXISTS `hous_quick_information`;

CREATE TABLE `hous_quick_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `school` tinyint(4) DEFAULT NULL COMMENT '学校',
  `hospital` tinyint(4) DEFAULT NULL COMMENT '医院',
  `shopping` tinyint(4) DEFAULT NULL COMMENT '购物等配套情况',
  `bank_prepayment` tinyint(4) DEFAULT NULL COMMENT '银行可否代为提前还款',
  `housing_value_faster` decimal(20,2) DEFAULT NULL COMMENT '房屋快出值(元)',
  `property_taxes` decimal(20,2) DEFAULT NULL COMMENT '房产交易产生的税费(元)',
  `bank_material` tinyint(4) DEFAULT NULL COMMENT '银行可否代为领取解押材料',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `two_or_five` tinyint(4) DEFAULT NULL COMMENT '满二或满五(废弃)',
  `balance_payment` decimal(20,2) DEFAULT NULL COMMENT '尾款',
  `highest_mortgage` tinyint(4) DEFAULT NULL COMMENT '是否为最高额抵押贷款',
  `investigator` varchar(64) DEFAULT NULL COMMENT '调查员',
  `survey_time` datetime DEFAULT NULL COMMENT '调查日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房屋快出值信息表(权证下户)';

/*Table structure for table `hous_rebate` */

DROP TABLE IF EXISTS `hous_rebate`;

CREATE TABLE `hous_rebate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `rebate_points` decimal(20,2) DEFAULT NULL COMMENT '返佣点位',
  `rebate_amount` decimal(20,2) DEFAULT NULL COMMENT '返佣金额',
  `rebate_deadline` int(11) DEFAULT NULL COMMENT '返佣期限',
  `rebate_card` varchar(32) DEFAULT NULL COMMENT '返佣卡号',
  `bank_name` varchar(128) DEFAULT NULL COMMENT '开户行',
  `real_rebate_amount` decimal(20,2) DEFAULT NULL COMMENT '实际返佣金额',
  `financial_id` int(11) DEFAULT NULL COMMENT '财务专员id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='返佣表';

/*Table structure for table `pl_approval_results` */

DROP TABLE IF EXISTS `pl_approval_results`;

CREATE TABLE `pl_approval_results` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '借款需求表-主键',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询ID',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户ID',
  `product_id` int(11) DEFAULT NULL COMMENT '产品ID',
  `approval_account` decimal(18,2) DEFAULT '0.00' COMMENT '审批额度',
  `approval_time_limit` int(11) DEFAULT NULL COMMENT '审批期限',
  `mortgage_price` decimal(18,2) DEFAULT '0.00' COMMENT '抵押物价格',
  `REMARK` varchar(128) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `pl_area` */

DROP TABLE IF EXISTS `pl_area`;

CREATE TABLE `pl_area` (
  `id` int(10) NOT NULL COMMENT '主键id',
  `rd_areaname` varchar(100) DEFAULT NULL COMMENT '地区名',
  `rd_pareaid` int(11) DEFAULT NULL COMMENT '上级地区id',
  `rd_sort` int(11) DEFAULT NULL COMMENT '同等级排序',
  `rd_level` int(11) DEFAULT NULL COMMENT '等级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `pl_bankcard` */

DROP TABLE IF EXISTS `pl_bankcard`;

CREATE TABLE `pl_bankcard` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `bank_flag` tinyint(4) DEFAULT NULL COMMENT '每个银行固有的标识',
  `bank_card_id` varchar(32) DEFAULT NULL COMMENT '银行卡号',
  `bank_area` varchar(128) DEFAULT NULL COMMENT '开户区',
  `status` tinyint(3) unsigned zerofill DEFAULT NULL COMMENT '状态0表示正常-1无效',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `account_name` varchar(32) DEFAULT NULL COMMENT '账户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `pl_borrow` */

DROP TABLE IF EXISTS `pl_borrow`;

CREATE TABLE `pl_borrow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '借款信息表主键',
  `repayment_yes_account` decimal(18,2) DEFAULT '0.00',
  `repayment_yes_interest` decimal(18,2) DEFAULT '0.00',
  `repayment_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '还款利率 正常按这个利率计算',
  `time_limit` int(11) DEFAULT NULL COMMENT '借款期限/月',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` int(11) DEFAULT NULL COMMENT '记录增加者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modifier` int(11) DEFAULT NULL COMMENT '记录修改者',
  `is_delete` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '是否删除 0不删除1已删除',
  `account` decimal(18,2) DEFAULT '0.00',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询ID',
  `repayment_type` tinyint(3) DEFAULT NULL COMMENT '还款方式',
  `loan_time` date DEFAULT NULL COMMENT '实际放款日期',
  `repayment_day` tinyint(3) DEFAULT NULL COMMENT '预计还款日如：20 每月20日还',
  `product_id` int(11) DEFAULT NULL COMMENT '产品ID',
  `overdue_penalty_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '逾期罚息利率（M%/天）',
  `overdue_repayment_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '逾期还款利率  超过还款期按这个计算  [这个字段暂时作费]',
  `repayment_default` decimal(10,4) DEFAULT '0.0000' COMMENT '提前还款违约比率X%/月',
  `remark` varchar(512) DEFAULT NULL COMMENT '借款说明',
  `status` tinyint(1) DEFAULT '0' COMMENT '记录状态 0正常，-1无效',
  `lenders` int(11) DEFAULT NULL COMMENT '放款人',
  `lenders_office` char(64) DEFAULT NULL COMMENT '放款人所属机构',
  `loan_user` int(11) DEFAULT NULL COMMENT '申请进件人-提交这笔贷款的人',
  `expect_time` datetime DEFAULT NULL COMMENT '期望放款日期',
  `repayment_status` int(11) DEFAULT NULL COMMENT '还款中0，结清1，逾期2,已展期3',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `loan_office` char(64) DEFAULT NULL COMMENT '申请进件人所属机构',
  `requirement_id` int(11) DEFAULT NULL COMMENT '借款需求表ID',
  `single_rate` decimal(10,4) DEFAULT NULL COMMENT '成单利率',
  PRIMARY KEY (`id`),
  KEY `processInstanceId` (`process_instance_id`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE,
  KEY `consult_id` (`consult_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='借款信息表';

/*Table structure for table `pl_borrow_requirement` */

DROP TABLE IF EXISTS `pl_borrow_requirement`;

CREATE TABLE `pl_borrow_requirement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '借款需求表-主键',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询ID',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户ID',
  `account` decimal(18,2) DEFAULT '0.00',
  `borrowing_time` date DEFAULT NULL COMMENT '借款日期',
  `product_id` int(11) DEFAULT NULL COMMENT '产品ID',
  `repayment_type` tinyint(4) DEFAULT NULL COMMENT '还款方式',
  `repayment_rate` decimal(20,4) DEFAULT NULL COMMENT '月息',
  `time_limit` int(11) DEFAULT NULL COMMENT '借款期限按/月',
  `single_rate` decimal(10,4) DEFAULT NULL COMMENT '成单利率',
  `project_belongs` tinyint(4) DEFAULT NULL COMMENT '项目所属,1:赚赚自有,2:报单机构,3:报单个人',
  `financial_advisers` varchar(64) DEFAULT NULL COMMENT '金融顾问',
  `institution_name` varchar(64) DEFAULT NULL COMMENT '机构名称',
  `salesman` varchar(64) DEFAULT NULL COMMENT '业务员',
  `borrow_use` varchar(255) DEFAULT NULL COMMENT '借款用途',
  `repayment_source` varchar(255) DEFAULT NULL COMMENT '还款来源',
  `remark` varchar(255) DEFAULT NULL COMMENT '借款说明',
  `phone_number` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `mobile` varchar(32) DEFAULT NULL COMMENT '联系电话（报单人或报单机构）',
  `collection_service_fee` decimal(20,2) DEFAULT NULL COMMENT '代收服务费金额',
  `collection_rate` decimal(10,4) DEFAULT '0.0000',
  `collection_service_name` varchar(64) DEFAULT NULL COMMENT '代收服务费姓名',
  `collection_service_card` varchar(64) DEFAULT NULL COMMENT '代收服务费卡号',
  `collection_service_bank` varchar(64) DEFAULT NULL COMMENT '代收服务费开户行',
  PRIMARY KEY (`id`),
  KEY `consult_id` (`consult_id`) USING BTREE,
  KEY `project_id` (`project_id`) USING BTREE,
  KEY `process_instance_id` (`process_instance_id`) USING BTREE,
  KEY `creatorIndex` (`creator`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1271 DEFAULT CHARSET=utf8 COMMENT='借款需求表';

/*Table structure for table `pl_consult` */

DROP TABLE IF EXISTS `pl_consult`;

CREATE TABLE `pl_consult` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `product_id` int(11) DEFAULT NULL COMMENT '产品ID',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '移动电话',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `creator` int(11) DEFAULT NULL COMMENT '咨询录入者ID',
  `curuserid` int(11) DEFAULT NULL COMMENT '当前操作者ID',
  `customer_manager` int(11) DEFAULT NULL COMMENT '报单人',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `process_instance_id` varchar(16) DEFAULT NULL COMMENT '流程ID',
  `status` varchar(64) DEFAULT NULL COMMENT '咨询状态',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `extension_time` int(11) unsigned zerofill DEFAULT '00000000000' COMMENT '成功展期次数',
  `business_origin` varchar(64) DEFAULT NULL COMMENT '业务来源(0:平台商务专员添加、1：平台机构保单专员添加、2：APP)',
  `fee_success` tinyint(4) DEFAULT NULL COMMENT '1可退费 2退费申请',
  `advance_apply` tinyint(4) DEFAULT NULL COMMENT '1可垫资 2垫资申请',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户id',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  PRIMARY KEY (`id`),
  KEY `process_ins_index` (`process_instance_id`) USING BTREE,
  KEY `customer_manager` (`customer_manager`) USING BTREE,
  KEY `project_id_index` (`project_id`) USING BTREE,
  KEY `mobile` (`mobile`) USING BTREE,
  KEY `name` (`name`) USING BTREE,
  KEY `product_id` (`product_id`) USING BTREE,
  KEY `status` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1447 DEFAULT CHARSET=utf8 COMMENT='咨询表';

/*Table structure for table `pl_contract` */

DROP TABLE IF EXISTS `pl_contract`;

CREATE TABLE `pl_contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `contract_no` varchar(64) DEFAULT NULL COMMENT '借款协议编号',
  `qdhtdata` varchar(5000) DEFAULT NULL COMMENT '合同内容',
  `create_time` datetime DEFAULT NULL COMMENT '首次生成时间',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `signature_time` date DEFAULT NULL COMMENT '合同签属日期',
  `borrow_start` date DEFAULT NULL COMMENT '借款开始时间',
  `borrow_end` date DEFAULT NULL COMMENT '借款结束时间',
  `process_instance_id` varchar(64) NOT NULL COMMENT '流程ID',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改者',
  `signature_address` varchar(64) DEFAULT NULL COMMENT '签属地址',
  `contract_account` decimal(18,2) DEFAULT '0.00',
  `service_fee` decimal(18,2) DEFAULT '0.00',
  `status` tinyint(1) DEFAULT '0' COMMENT '记录状态 0未签订,1已签订,2签订失败,3退回重签',
  PRIMARY KEY (`id`),
  KEY `processInstanceId` (`process_instance_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同表';

/*Table structure for table `pl_contractfile` */

DROP TABLE IF EXISTS `pl_contractfile`;

CREATE TABLE `pl_contractfile` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `contract_id` int(11) DEFAULT NULL COMMENT '合同ID',
  `name` varchar(64) DEFAULT NULL COMMENT '文件名',
  `url` varchar(128) DEFAULT NULL COMMENT '文件路经',
  `type` tinyint(3) DEFAULT NULL COMMENT '业务类型：1借款协议 2咨询协议',
  `file_suffix` varchar(20) DEFAULT NULL COMMENT '文件类型 pdf \\word',
  `status` tinyint(3) unsigned zerofill DEFAULT NULL COMMENT '状态0正常1不存在-1删除 ',
  `processInstanceId` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `pl_feeinfo` */

DROP TABLE IF EXISTS `pl_feeinfo`;

CREATE TABLE `pl_feeinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) DEFAULT NULL COMMENT '项目ID',
  `process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `repayment_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '月息',
  `time_limit` int(11) DEFAULT '0' COMMENT '借款期数',
  `repayment_type` tinyint(4) DEFAULT NULL COMMENT '还款方式',
  `ptype` tinyint(3) DEFAULT NULL COMMENT '1信贷  2车贷 3房贷',
  `overdue_penalty_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '逾期罚息%日',
  `ahead_repay_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '提前还款罚息费率',
  `product_type` tinyint(3) DEFAULT '0' COMMENT '产品类型',
  `single_rate` decimal(10,4) DEFAULT NULL COMMENT '成单利率',
  PRIMARY KEY (`id`),
  KEY `processInstanceId_index` (`process_instance_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2787 DEFAULT CHARSET=utf8 COMMENT='费用信息表';

/*Table structure for table `pl_product` */

DROP TABLE IF EXISTS `pl_product`;

CREATE TABLE `pl_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '产品名',
  `ptype` tinyint(3) DEFAULT NULL COMMENT '担保类型1信贷  2抵押-车贷',
  `maxmlimit` decimal(18,2) DEFAULT '0.00',
  `maxtlimit` int(4) DEFAULT NULL COMMENT '期限上限',
  `prepayment` tinyint(3) DEFAULT NULL COMMENT '0不可提前还款1可提前还款',
  `tminmlimit` decimal(18,2) DEFAULT '0.00',
  `tmaxmlimit` decimal(18,2) DEFAULT '0.00',
  `opendate` datetime DEFAULT NULL COMMENT '开放日期',
  `createtime` datetime DEFAULT NULL COMMENT '产品创建时间',
  `overdue_penalty_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '逾期罚息%日',
  `repayment_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '借款利率%月  现底点利率',
  `repayment_default` decimal(10,4) DEFAULT '0.0000' COMMENT '提前还款违约金比率 月  暂作废',
  `isopen` tinyint(3) DEFAULT '1' COMMENT '产品是否已启用   0禁用   1启用',
  `is_delete` tinyint(3) DEFAULT '0' COMMENT '记录是否删除 0正常 -1已删除记录',
  `office_ids` varchar(1024) DEFAULT NULL COMMENT '试用机构',
  `overdue_period` char(3) DEFAULT NULL COMMENT '违约金周期',
  `overdue_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '逾期违约金利率%月',
  `remark` varchar(2000) DEFAULT NULL COMMENT '备注',
  `period` int(3) DEFAULT '0' COMMENT '借款期数',
  `three_prepayment` decimal(10,4) DEFAULT '0.0000' COMMENT '三个月内提前还款退还比例',
  `six_prepayment` decimal(10,4) DEFAULT '0.0000' COMMENT '六个月内提前还款退还比例',
  `product_type` tinyint(3) DEFAULT '0' COMMENT '产品类型 0A 1B 2C 3D',
  `margin_fee` decimal(10,6) unsigned zerofill DEFAULT '0000.000000' COMMENT '借款保证金',
  `one_prepayment` decimal(10,4) DEFAULT NULL COMMENT '一个月内提前还款退还比例',
  `two_prepayment` decimal(10,4) DEFAULT '0.0000' COMMENT '二个月内提前还款退还比例',
  `four_prepayment` decimal(10,4) DEFAULT '0.0000' COMMENT '四个月内提前还款退还比例',
  `five_prepayment` decimal(10,4) DEFAULT '0.0000' COMMENT '五个月内提前还款退还比例',
  `repayment_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '还款方式',
  `delay` tinyint(2) DEFAULT '0' COMMENT '是否展期(0为可以,1为否)',
  `carloan_type` tinyint(3) DEFAULT NULL COMMENT '车贷类型：0-分期 1-短期',
  `minmlimit` decimal(18,2) DEFAULT '0.00',
  `ahead_repay_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '提前还款罚息费率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Table structure for table `pl_settlementfee` */

DROP TABLE IF EXISTS `pl_settlementfee`;

CREATE TABLE `pl_settlementfee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `processInstanceId` varchar(64) NOT NULL,
  `project_id` int(11) NOT NULL,
  `capital` decimal(20,6) DEFAULT '0.000000' COMMENT '本金',
  `is_payoff` tinyint(4) DEFAULT '1' COMMENT '是否还清 0-未还清 1-已还清',
  `disposal_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '车辆处置金额',
  `disposal_balance_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '车辆处置余额',
  `disposal_back_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '退还处置余额',
  `margin_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '借款保证金',
  `is_breach_of_contract` tinyint(4) DEFAULT '0' COMMENT '是否违约',
  `back_margin_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '退还保证金',
  `illegal_disposal_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '违章处理费',
  `is_illegal` tinyint(4) DEFAULT '0' COMMENT '是否违章',
  `back_illegal_disposal_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '是否违章',
  `gps_install_fee` decimal(20,6) DEFAULT '0.000000' COMMENT 'gps安装费',
  `is_uninstall` tinyint(4) DEFAULT '0' COMMENT '是否拆除',
  `back_gps_install_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '退还gps安装费',
  `total_back_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '退还金额总计',
  `presbank_name` varchar(128) DEFAULT NULL COMMENT '付款行名称(提出行)',
  `presbank` varchar(32) DEFAULT NULL COMMENT '付款行账号(提出行)',
  `presback_time` datetime DEFAULT NULL COMMENT '退款时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '注释',
  `contract_no` varchar(64) DEFAULT NULL COMMENT '合同编号',
  `customer_name` varchar(32) DEFAULT NULL COMMENT '客户名称',
  `customer_cbank_name` varchar(128) DEFAULT NULL COMMENT '客户清算行名称',
  `customer_bank` varchar(32) DEFAULT NULL COMMENT '客户银行账号',
  `customer_obank_name` varchar(128) DEFAULT NULL COMMENT '客户开户网点名称',
  `is_settleaccounts` tinyint(4) DEFAULT '0' COMMENT '是否已结算 0-否 1-是',
  `handler_name` varchar(128) DEFAULT NULL COMMENT '处理人',
  `handler_time` datetime DEFAULT NULL COMMENT '处理时间',
  `back_account_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '退还账户余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `product_type` */

DROP TABLE IF EXISTS `product_type`;

CREATE TABLE `product_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_type` varchar(255) NOT NULL COMMENT '产品类型',
  `formula_id` varchar(255) DEFAULT NULL COMMENT '公式表,id例:(1,2,3,4,5,6)',
  `ptype` int(2) DEFAULT NULL COMMENT '贷款类型(1信贷  2车贷  3房贷)',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_name` varchar(255) DEFAULT NULL COMMENT '创建人',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_name` varchar(255) DEFAULT NULL COMMENT '修改人',
  `state` tinyint(2) DEFAULT '0' COMMENT '是否启用(0为启用,1为不启用)',
  `delete_state` tinyint(2) DEFAULT '0' COMMENT '删除状态(0未删除,-1为删除)',
  `note` varchar(255) DEFAULT NULL COMMENT '注释',
  PRIMARY KEY (`id`),
  KEY `id_k` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='公式配置------产品级';

/*Table structure for table `pub_anrong_attachment` */

DROP TABLE IF EXISTS `pub_anrong_attachment`;

CREATE TABLE `pub_anrong_attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id,自增字段',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(2) unsigned zerofill DEFAULT '00' COMMENT '0不删除1已删除',
  `file_type` varchar(255) DEFAULT '' COMMENT '附件类型:1 msp|2 个人关联企业|3 企业司法 ',
  `file_name` varchar(255) DEFAULT '' COMMENT '原文件名',
  `file_size` int(10) DEFAULT NULL COMMENT '文件大小',
  `file_path` varchar(2048) DEFAULT '' COMMENT '文件目录相对路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT='安融征信生成附件表';

/*Table structure for table `pub_ar_risk_result` */

DROP TABLE IF EXISTS `pub_ar_risk_result`;

CREATE TABLE `pub_ar_risk_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `risk_param` text COMMENT '接口参数',
  `risk_result` text COMMENT '风险结果',
  `project_id` int(11) DEFAULT NULL COMMENT '项目id',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `file_id` int(11) DEFAULT NULL COMMENT '附件id',
  `risk_type` int(4) DEFAULT NULL COMMENT '1 msp|2 个人关联企业|3 企业司法 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COMMENT='安融风控结果信息表';

/*Table structure for table `pub_attachment` */

DROP TABLE IF EXISTS `pub_attachment`;

CREATE TABLE `pub_attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id,自增字段',
  `state` int(3) DEFAULT '1' COMMENT '附件状态:0删除,1正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `file_name` varchar(255) DEFAULT '' COMMENT '原文件名',
  `newfile_name` varchar(255) DEFAULT '' COMMENT '新文件名',
  `file_size` int(10) DEFAULT NULL COMMENT '文件大小',
  `file_path` varchar(2048) DEFAULT '' COMMENT '上传后相对路径''',
  `signcod` varchar(32) DEFAULT NULL COMMENT '原文件签名',
  `operator_id` int(11) DEFAULT NULL COMMENT '操作者id',
  `btype` varchar(32) DEFAULT '' COMMENT '所属业务类型',
  `atype` int(11) DEFAULT NULL COMMENT '附件类型',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT '' COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `name` varchar(255) DEFAULT '' COMMENT '他项权利证名称',
  `remark` varchar(2000) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='附件表';

/*Table structure for table `pub_biz_attachment` */

DROP TABLE IF EXISTS `pub_biz_attachment`;

CREATE TABLE `pub_biz_attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id,自增字段',
  `state` int(3) DEFAULT '1' COMMENT '附件状态:0删除,1正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `file_name` varchar(255) DEFAULT '' COMMENT '原文件名',
  `newfile_name` varchar(255) DEFAULT '' COMMENT '新文件名',
  `file_size` int(10) DEFAULT NULL COMMENT '文件大小',
  `file_path` varchar(2048) DEFAULT '' COMMENT '上传后相对路径',
  `signcod` varchar(32) DEFAULT '' COMMENT '原文件签名',
  `operator_id` int(11) DEFAULT NULL COMMENT '操作者id',
  `biz_type` varchar(32) DEFAULT '' COMMENT '所属业务类型',
  `relation_id` int(11) DEFAULT NULL COMMENT '关联id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8 COMMENT='附件表';

/*Table structure for table `pub_contract_attachment` */

DROP TABLE IF EXISTS `pub_contract_attachment`;

CREATE TABLE `pub_contract_attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id,自增字段',
  `creator` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `state` int(3) DEFAULT NULL COMMENT '附件状态',
  `is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
  `name` varchar(255) DEFAULT '' COMMENT '合同名称',
  `file_name` varchar(255) DEFAULT '' COMMENT '原文件名',
  `newfile_name` varchar(255) DEFAULT '' COMMENT '新文件名',
  `effective_node` int(3) DEFAULT NULL COMMENT '生效节点',
  `file_size` int(10) DEFAULT NULL COMMENT '文件大小',
  `file_path` varchar(2048) DEFAULT '' COMMENT '上传后相对路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='合同附件表';

/*Table structure for table `pub_customer` */

DROP TABLE IF EXISTS `pub_customer`;

CREATE TABLE `pub_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '真实姓名',
  `sex` tinyint(3) DEFAULT NULL COMMENT '性别',
  `vocation` varchar(32) DEFAULT NULL COMMENT '职业',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `nationality` varchar(128) DEFAULT NULL COMMENT '国籍',
  `native_place` varchar(64) DEFAULT NULL COMMENT '籍贯',
  `cert_type` tinyint(3) DEFAULT NULL COMMENT '身份证件类型',
  `cert_number` varchar(32) NOT NULL COMMENT '证件号码',
  `cert_valid_time` date DEFAULT NULL COMMENT '证件有效期',
  `mobile` varchar(12) DEFAULT NULL COMMENT '手机号码',
  `fixed_phone` varchar(20) DEFAULT NULL COMMENT '固定电话',
  `email` varchar(128) DEFAULT NULL COMMENT '电子邮件',
  `area_id` int(11) DEFAULT NULL COMMENT '地区ID',
  `live_address` varchar(128) DEFAULT NULL COMMENT '现居住详细地址',
  `household_address` varchar(128) DEFAULT NULL COMMENT '户籍详细地址',
  `education` tinyint(3) DEFAULT NULL COMMENT '1高中及以下 2大专 3本科 4硕士及以上 5其他',
  `work_status` tinyint(3) DEFAULT NULL COMMENT '工作状况 在职1，离职2，退休3，自营4',
  `company` varchar(32) DEFAULT NULL COMMENT '工作单位名称',
  `company_addr` varchar(128) DEFAULT NULL COMMENT '工作单位地址',
  `company_pro` tinyint(3) DEFAULT NULL COMMENT '工作单位性质 国有企业1，私营企业2，外资企业3，个体经济4，其他5',
  `company_phone` varchar(20) DEFAULT NULL COMMENT '工作单位电话',
  `position` varchar(32) DEFAULT NULL COMMENT '工作职位',
  `work_life` tinyint(3) DEFAULT NULL COMMENT '工作年限',
  `years_income` tinyint(3) DEFAULT NULL COMMENT '个人年收入',
  `type` tinyint(3) DEFAULT NULL COMMENT '客户类型 工薪阶层1，个体工商户2，私营业主3，政府机关4，其他5   ',
  `credit_score` int(11) DEFAULT NULL COMMENT '客户信用评分',
  `marry_status` tinyint(3) DEFAULT NULL COMMENT '婚姻状况：未婚1，已婚2，离异3，丧偶4',
  `remark` varchar(256) DEFAULT NULL COMMENT '描述',
  `status` tinyint(3) unsigned zerofill NOT NULL DEFAULT '000' COMMENT '客户状态 0正常用户 1黑名单用户',
  `loans` tinyint(3) unsigned zerofill DEFAULT '000' COMMENT '贷款次数',
  `refusings` tinyint(3) unsigned zerofill DEFAULT '000' COMMENT '拒贷次数',
  `repayment_status` int(11) DEFAULT '-1' COMMENT '还款中0，结清1，逾期2',
  `uuid` varchar(32) DEFAULT NULL COMMENT '用户唯一UUID',
  `creator` int(11) DEFAULT NULL COMMENT '创建者（录入人）用于权限控制',
  `creator_name` varchar(32) DEFAULT NULL COMMENT '创建人',
  `is_local_house` tinyint(3) unsigned zerofill DEFAULT '000' COMMENT '本人本地名下房产 0无 1有',
  `live_state` tinyint(3) unsigned zerofill DEFAULT '000' COMMENT '居住情况:0自有住房 1租凭2与亲属同住3公司宿舍4其它',
  `other_case` varchar(128) DEFAULT NULL COMMENT '其它说明',
  `live_zip_code` varchar(6) DEFAULT NULL COMMENT '现住址邮编',
  `household_zip_code` varchar(6) DEFAULT NULL COMMENT '户籍地邮编',
  `company_zip_code` varchar(6) DEFAULT NULL COMMENT '公司地址邮编',
  `department` varchar(32) DEFAULT NULL COMMENT '所在部门',
  `in_time` date DEFAULT NULL COMMENT '入职时间',
  `customer_manager` varchar(32) DEFAULT NULL COMMENT '客户经理',
  `risk_level` tinyint(16) DEFAULT NULL COMMENT '风险等级',
  `photo_id` int(11) DEFAULT NULL COMMENT '照片',
  `car_status` tinyint(2) DEFAULT NULL COMMENT '车产情况',
  `rd_householdAddressid` int(11) DEFAULT NULL COMMENT '户籍所在地区ID',
  `rd_householddetailAddress` varchar(128) DEFAULT NULL COMMENT '户籍详细地址',
  `healthy` tinyint(3) DEFAULT NULL COMMENT '健康状况：0-良好，1-一般，2-较差',
  `spouse_iskown` tinyint(3) DEFAULT NULL COMMENT '配偶是否知晓：0-是，1-否',
  `is_have_children` tinyint(3) DEFAULT NULL COMMENT '是否有子女：0-是，1-否',
  `emergency_contact_number` varchar(20) DEFAULT NULL COMMENT '紧急联系电话',
  `is_self_support` tinyint(3) DEFAULT NULL COMMENT '公司是否自营：0-是，1-否',
  `create_time` datetime DEFAULT NULL,
  `salesman` varchar(64) DEFAULT NULL COMMENT '业务员',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `children_num` int(10) DEFAULT NULL COMMENT '子女人数',
  `feed_num` int(10) DEFAULT NULL COMMENT '供养人数',
  `household_area_id` int(10) DEFAULT NULL COMMENT '户籍地址ID',
  `family_month_expenses` decimal(20,2) DEFAULT NULL COMMENT '家庭月支出',
  `live_time` date DEFAULT NULL COMMENT '现居住地址时间',
  `come_city_date` date DEFAULT NULL COMMENT '来本市时间',
  `weixin` varchar(50) DEFAULT NULL COMMENT '微信',
  `weibo` varchar(50) DEFAULT NULL COMMENT '微博',
  `qq` varchar(15) DEFAULT NULL COMMENT 'qq',
  `customer_source` varchar(50) DEFAULT NULL COMMENT '客户来源',
  `position_type` int(10) DEFAULT NULL COMMENT '职位类型',
  `position_level` int(10) DEFAULT NULL COMMENT '职位级别',
  `position_industry` tinyint(3) DEFAULT NULL COMMENT '所属行业',
  `last_company` varchar(50) DEFAULT NULL COMMENT '前单位名称',
  `wages_type` tinyint(3) DEFAULT NULL COMMENT '工资发放形式',
  `salary` decimal(20,2) DEFAULT NULL COMMENT '每月基本薪金',
  `other_in` decimal(20,2) DEFAULT NULL COMMENT '其他收入',
  `grant_day` varchar(50) DEFAULT NULL COMMENT '每月发薪日',
  `management_type` int(10) DEFAULT NULL COMMENT '经营营类型',
  `management_name` int(10) DEFAULT NULL COMMENT '经营类型后缀名',
  `company_build_time` date DEFAULT NULL COMMENT '企业成立时间',
  `share_scale` decimal(10,4) DEFAULT NULL COMMENT '股份占比',
  `employee_num` int(10) DEFAULT NULL COMMENT '员工人数',
  `management_addr_type` int(10) DEFAULT NULL COMMENT '经营场所类型',
  `company_area_id` int(10) DEFAULT NULL COMMENT '单位地址ID',
  `team_manager` varchar(32) DEFAULT NULL COMMENT '团队经理',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=115451 DEFAULT CHARSET=utf8 COMMENT='客户表';

/*Table structure for table `pub_customer_relation` */

DROP TABLE IF EXISTS `pub_customer_relation`;

CREATE TABLE `pub_customer_relation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_delete` tinyint(3) NOT NULL COMMENT '是否删除 0正常1删除',
  `customer_id` int(11) NOT NULL COMMENT '客户表ID',
  `loan_count` varchar(16) DEFAULT NULL COMMENT '贷款次数',
  `user_name` varchar(32) DEFAULT NULL COMMENT '联系人',
  `type` tinyint(3) NOT NULL COMMENT '联系方式1电话；2上门；3邮件；4短信；',
  `contact_time` datetime NOT NULL COMMENT '联系时间',
  `remark` varchar(256) NOT NULL COMMENT '联系备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='客户联系表';

/*Table structure for table `pub_dundetail` */

DROP TABLE IF EXISTS `pub_dundetail`;

CREATE TABLE `pub_dundetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '催收表ID',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(3) NOT NULL COMMENT '是否删除 0正常1删除',
  `user_name` varchar(32) NOT NULL COMMENT '催收人',
  `repayment_id` int(11) NOT NULL COMMENT '还款明细ID',
  `type` tinyint(3) NOT NULL COMMENT ' 催收方式1电话；2上门；3邮件；4短信；等等',
  `dun_time` datetime DEFAULT NULL COMMENT '催收时间',
  `remark` varchar(512) NOT NULL COMMENT '催收备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `pub_loan` */

DROP TABLE IF EXISTS `pub_loan`;

CREATE TABLE `pub_loan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `creator` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(3) unsigned zerofill DEFAULT NULL COMMENT '是否删除 0正常 1删除',
  `account` decimal(20,2) NOT NULL COMMENT '放款金额',
  `bank_flag` smallint(4) DEFAULT NULL COMMENT '放款银行',
  `bank_account` varchar(32) DEFAULT NULL COMMENT '放款银行账号',
  `operator` int(11) NOT NULL COMMENT '放款',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注 放款',
  `processInstanceId` varchar(64) DEFAULT NULL,
  `loan_time` datetime DEFAULT NULL COMMENT '放款',
  `project_id` int(11) DEFAULT NULL,
  `customer_name` varchar(16) DEFAULT NULL COMMENT '客户名',
  `customer_bank_card` varchar(64) DEFAULT NULL COMMENT '客户银行账号',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户id',
  `customer_bank` varchar(64) DEFAULT NULL COMMENT '客户开户行',
  `customer_obank` varchar(64) DEFAULT NULL COMMENT '客户开户网点',
  `type` tinyint(4) DEFAULT NULL COMMENT '1-放款 ',
  `bank_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `processInstanceId` (`processInstanceId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Table structure for table `pub_loanprocess` */

DROP TABLE IF EXISTS `pub_loanprocess`;

CREATE TABLE `pub_loanprocess` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creator` int(11) DEFAULT NULL COMMENT '审批者',
  `create_time` datetime DEFAULT NULL COMMENT '审批时间',
  `is_delete` tinyint(3) unsigned zerofill DEFAULT NULL COMMENT '是否删除0正常 -1已删除',
  `office_id` varchar(64) DEFAULT NULL COMMENT '所属机构',
  `process_Instance_Id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '流程ID',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询ID',
  `customer_id` int(11) DEFAULT NULL COMMENT '客户ID',
  `receive_type` tinyint(4) DEFAULT NULL COMMENT '下户费收取形式',
  `type` varchar(64) DEFAULT NULL COMMENT '处理意见',
  `process_state` varchar(64) DEFAULT NULL COMMENT '流程状态',
  `category` varchar(512) DEFAULT NULL COMMENT '资料类别/拒贷类别',
  `remark` varchar(19000) DEFAULT NULL COMMENT '备注',
  `product_id` int(11) DEFAULT NULL COMMENT '产品ID',
  `amount` decimal(20,2) DEFAULT NULL COMMENT '批贷金额',
  `task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '原任务ID',
  `new_task_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '新任务ID(next task id)',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `housing_value_faster` decimal(20,2) DEFAULT NULL COMMENT '房屋快出值(元)',
  `manual_assignee` varchar(64) DEFAULT NULL COMMENT '任务分配人',
  `next_assignee` varchar(255) DEFAULT NULL COMMENT '下一步审批人',
  `next_assignee_name` varchar(255) DEFAULT NULL COMMENT '下步任务执行人真实姓名',
  `workflow_next_assignee_name` varchar(255) DEFAULT NULL COMMENT '流程状态审批人',
  `checkBank_assignee` varchar(255) DEFAULT NULL COMMENT '核行任务分配人',
  PRIMARY KEY (`id`),
  KEY `taskIdIndex` (`task_id`) USING BTREE,
  KEY `newTaskIdIndex` (`new_task_id`) USING BTREE,
  KEY `taskAndNewTaskComInx` (`task_id`,`new_task_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5762 DEFAULT CHARSET=utf8;

/*Table structure for table `pub_process_branching` */

DROP TABLE IF EXISTS `pub_process_branching`;

CREATE TABLE `pub_process_branching` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分支流程id',
  `branching_process_id` varchar(64) NOT NULL COMMENT '分支流程ID',
  `branching_process_type` tinyint(4) unsigned zerofill DEFAULT '0000' COMMENT '分支流程类型 提前1 减免2 强制3  返佣4 处置5 展期6(分支流程申请提交后即初始化该字段 和\r\n\r\nprocessing_opinion配合使用) 8垫资',
  `processing_opinion` varchar(32) DEFAULT NULL COMMENT '审批处理意见(提前还款、减免、强制、处置等):未审批noprocess,pass 通过 reject驳回，disposalRegisterDone登记完成 展期申请的时候用到该字段1：正常展期 0：超额展期',
  `task_id` varchar(64) DEFAULT NULL COMMENT '任务编号',
  `processInstanceId` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `default_situation` decimal(10,0) DEFAULT NULL COMMENT '违约情况:1-GPS拆除 2-驶出规定范围 3-其它',
  `remark1` varchar(256) DEFAULT NULL COMMENT '贷后变更备注(减免原因、GPS拆除)',
  `remark2` varchar(256) DEFAULT NULL COMMENT '审批备注',
  `make_repayment_time` date DEFAULT NULL COMMENT '预约还款时间',
  `repayment_process` tinyint(3) DEFAULT '0' COMMENT '还款处理（0正常 1提前 2减免 3强制 5处置 ）',
  `process_period` int(11) DEFAULT NULL COMMENT '处理期次',
  `other_amount` decimal(18,2) DEFAULT '0.00',
  `creator` varchar(32) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改日期',
  `is_delete` tinyint(3) unsigned zerofill DEFAULT '000' COMMENT '是否删除0正常 1删除',
  `project_id` int(11) DEFAULT NULL COMMENT '项目ID',
  `is_active` tinyint(4) DEFAULT NULL COMMENT '流程实例状态:0静止，1活动，2返佣',
  `process_status` varchar(128) DEFAULT NULL COMMENT '流程业务状态',
  `capital_sum` decimal(32,6) DEFAULT '0.000000' COMMENT '未还本金',
  `interest_sum` decimal(32,6) DEFAULT '0.000000' COMMENT '未还利息',
  `penalty_sum` decimal(32,6) DEFAULT '0.000000' COMMENT '违约金',
  `defaultinterest_sum` decimal(32,6) DEFAULT '0.000000' COMMENT '罚息',
  `parkingfee_sum` decimal(32,6) DEFAULT '0.000000' COMMENT '未还停车费',
  `gpsusingfee_sum` decimal(32,6) DEFAULT '0.000000' COMMENT '未还gps使用费',
  `platformfee_sum` decimal(32,6) DEFAULT '0.000000' COMMENT '未缴平台服务费',
  `violations_total_amount` decimal(32,6) DEFAULT '0.000000' COMMENT '违规罚款总额',
  `period` int(11) DEFAULT '0' COMMENT '起始未还款期次',
  `repay_capital_amount` decimal(32,6) DEFAULT '0.000000' COMMENT '展期提前还款金额',
  `extension_count` tinyint(4) DEFAULT NULL COMMENT '展期期限',
  `extension_amount` decimal(20,2) DEFAULT '0.00' COMMENT '展期金额',
  `extension_fee` decimal(20,2) DEFAULT '0.00' COMMENT '展期费',
  `extension_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '展期费率',
  `extension_returnfee_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '展期返佣点费',
  `finance_specialist` varchar(64) DEFAULT NULL COMMENT '展期费收取财务专员',
  `actual_extension_fee` decimal(20,2) DEFAULT '0.00' COMMENT '实收展期费',
  `collection_form` varchar(32) DEFAULT NULL COMMENT '收取形式:(0:现金 1：转账)',
  `deduct_time` date DEFAULT NULL COMMENT '划扣时间',
  `extension_fee_bottom_rate` decimal(20,2) DEFAULT '0.00' COMMENT '展期费底点利率',
  `extension_bottom_rate` decimal(20,2) DEFAULT '0.00' COMMENT '展期后底点利率',
  `extension_single_rate` decimal(20,2) DEFAULT '0.00' COMMENT '展期成单利率',
  PRIMARY KEY (`id`),
  UNIQUE KEY `branching_process_id_index` (`branching_process_id`) USING BTREE,
  KEY `processInstance_id_index` (`processInstanceId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `pub_profile` */

DROP TABLE IF EXISTS `pub_profile`;

CREATE TABLE `pub_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id,自增字段',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级id',
  `product_id` int(11) DEFAULT NULL COMMENT '所属产品id',
  `ptype` varchar(32) DEFAULT NULL COMMENT '所属业务类型',
  `is_required` int(3) DEFAULT NULL COMMENT '必填项',
  `remark` varchar(4000) DEFAULT NULL COMMENT '资料要求备注',
  `remark2` varchar(4000) DEFAULT NULL COMMENT '资料要求备注',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `disable` int(3) DEFAULT '0' COMMENT '是否启动',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人附件资料树表';

/*Table structure for table `pub_project` */

DROP TABLE IF EXISTS `pub_project`;

CREATE TABLE `pub_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(32) DEFAULT NULL COMMENT '项目编号-规则',
  `project_name` varchar(100) DEFAULT NULL COMMENT '项目名称',
  `type` tinyint(3) DEFAULT NULL COMMENT '类型',
  `creator` int(11) NOT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` int(11) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1245 DEFAULT CHARSET=utf8;

/*Table structure for table `pub_project_process` */

DROP TABLE IF EXISTS `pub_project_process`;

CREATE TABLE `pub_project_process` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_id` int(11) DEFAULT NULL COMMENT '项目ID',
  `process_instance_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '流程编号',
  `extension_number` int(11) DEFAULT '0' COMMENT '展期期数（第几次展期）：0非展期,1第一次展期...',
  `process_type` tinyint(3) DEFAULT '0' COMMENT '流程类型:0主流程(包括展期和非展期流程） 1提前 2减免  3强制 5处置流程 6返佣  7退费分支流程  8垫资分支流程',
  PRIMARY KEY (`id`),
  KEY `index_pro_ins` (`process_instance_id`) USING BTREE,
  KEY `combo_index_proj_proc` (`project_id`,`process_instance_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1582 DEFAULT CHARSET=utf8;

/*Table structure for table `pub_repayloaninfo` */

DROP TABLE IF EXISTS `pub_repayloaninfo`;

CREATE TABLE `pub_repayloaninfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `borrowId` int(11) NOT NULL COMMENT '借款id',
  `contract_no` varchar(64) DEFAULT NULL COMMENT '合同编号',
  `contractId` int(11) DEFAULT NULL COMMENT '合同id',
  `customer_name` varchar(64) DEFAULT NULL COMMENT '客户名称',
  `customerId` int(11) DEFAULT NULL COMMENT '客户id',
  `customer_account` varchar(32) DEFAULT NULL COMMENT '客户账户',
  `time_limit` int(11) NOT NULL COMMENT '借款期限',
  `officeId` varchar(32) DEFAULT NULL COMMENT '营业厅',
  `serviceFee` decimal(10,4) DEFAULT NULL COMMENT '服务费',
  `projectId` int(11) NOT NULL,
  `productId` int(11) DEFAULT NULL COMMENT '产品id',
  `mortgage_type` tinyint(4) DEFAULT NULL COMMENT '抵押类型(1移交2GPS)',
  `vouch_type` tinyint(4) DEFAULT NULL COMMENT '担保方式',
  `repaymentTime` datetime DEFAULT NULL COMMENT '还款开始时间',
  `repayment_capital_amount` decimal(20,6) DEFAULT NULL COMMENT '已还本金',
  `repayment_interest_amount` decimal(20,6) DEFAULT NULL COMMENT '已还利息',
  `capital_amount` decimal(20,6) DEFAULT NULL COMMENT '应还本金',
  `interest_amount` decimal(20,6) DEFAULT NULL COMMENT '应还利息',
  `repayment_status` tinyint(4) unsigned zerofill NOT NULL COMMENT '还款中0，结清1，逾期2',
  `repayment_process` tinyint(4) NOT NULL DEFAULT '0' COMMENT '还款处理（0正常 1提前 2减免 3强制 5处置 6追回 7拍卖）',
  `islocked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否锁定 0-未锁定  1-锁定',
  `disposal_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '车辆处置金额',
  `disposal_balance` decimal(20,6) DEFAULT '0.000000' COMMENT '车辆处置余额',
  `risk_level` tinyint(5) DEFAULT NULL COMMENT '风险等级',
  `end_repayTime` datetime DEFAULT NULL COMMENT '还款结束时间',
  PRIMARY KEY (`id`),
  KEY `borrow_id_index` (`borrowId`) USING BTREE,
  KEY `contract_id_index` (`contractId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='偿还贷款信息';

/*Table structure for table `pub_repaymentdetail` */

DROP TABLE IF EXISTS `pub_repaymentdetail`;

CREATE TABLE `pub_repaymentdetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '还款明细表ID',
  `default_interest` decimal(20,6) DEFAULT '0.000000' COMMENT '罚息',
  `penalty` decimal(20,6) DEFAULT '0.000000' COMMENT '违约金',
  `real_account` decimal(20,6) DEFAULT '0.000000' COMMENT '实际还款金额',
  `is_payoff` tinyint(3) unsigned zerofill NOT NULL COMMENT '是否还清 0未还清；1已还清',
  `customer_id` int(11) NOT NULL COMMENT '客户ID',
  `account` decimal(20,6) NOT NULL DEFAULT '0.000000' COMMENT '本期应还款金额',
  `capital` decimal(20,6) DEFAULT '0.000000' COMMENT '本金',
  `interest` decimal(20,6) DEFAULT '0.000000' COMMENT '利息',
  `gps_use_fee` decimal(20,6) DEFAULT '0.000000' COMMENT 'gps使用费',
  `other_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '其他费用',
  `parking_fee` decimal(20,6) DEFAULT '0.000000' COMMENT '停车费',
  `creator` int(11) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(3) unsigned zerofill NOT NULL COMMENT '0正常1删除',
  `period` int(11) NOT NULL COMMENT '还款期次',
  `realpayment_time` datetime DEFAULT NULL COMMENT '实际还款日期',
  `repayment_type` tinyint(3) NOT NULL COMMENT '还款方式',
  `repayment_time` datetime NOT NULL COMMENT '还款日期',
  `repayment_staus` tinyint(3) NOT NULL COMMENT '1正常还款；2逾期未还款；3逾期还款；4提前还款；5押品拍卖；0还款中；',
  `processInstanceId` varchar(64) DEFAULT NULL COMMENT '流程ID',
  `borrow_id` int(11) DEFAULT NULL COMMENT '借款表ID',
  `violations_penalty` decimal(18,2) DEFAULT '0.00',
  `violations_truck_fee` decimal(18,2) DEFAULT '0.00',
  `violations_travel_fee` decimal(18,2) DEFAULT '0.00',
  `violations_other_fee` decimal(18,2) DEFAULT '0.00',
  `violations_total_amount` decimal(18,2) DEFAULT '0.00',
  `reduction_penalty` decimal(18,2) DEFAULT '0.00',
  `breach_contract` decimal(18,2) DEFAULT '0.00',
  `all_breaks` decimal(18,2) DEFAULT '0.00',
  `cancel_overdue` tinyint(4) DEFAULT '0' COMMENT '取消逾期',
  `repayment_platform_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '已还本期平台服务费',
  `repayment_parking_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '已还本期停车费',
  `repayment_gpsuse_amount` decimal(20,6) DEFAULT NULL COMMENT '已还本期gps使用费',
  `repayment_capital_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '已还本期本金',
  `repayment_interest_amount` decimal(20,6) DEFAULT '0.000000' COMMENT '已还本期利息',
  `customer_balance` decimal(20,6) DEFAULT NULL COMMENT '本期账户余额',
  `needrepay_capital` decimal(20,6) DEFAULT '0.000000' COMMENT '本期剩余待还本金',
  `real_interest` decimal(20,6) DEFAULT '0.000000' COMMENT '实际罚息金额',
  PRIMARY KEY (`id`),
  KEY `processInstanceId` (`processInstanceId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=488 DEFAULT CHARSET=utf8 COMMENT='还款明细表';

/*Table structure for table `pub_supplymaterial` */

DROP TABLE IF EXISTS `pub_supplymaterial`;

CREATE TABLE `pub_supplymaterial` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id,自增字段',
  `state` int(3) DEFAULT '1' COMMENT '附件状态:0删除,1正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `operator_id` int(11) DEFAULT NULL COMMENT '操作者id',
  `consult_id` int(11) DEFAULT NULL COMMENT '咨询id',
  `process_instance_id` varchar(64) DEFAULT '' COMMENT '流程ID',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `other_material_name` varchar(255) DEFAULT '' COMMENT '他项权利证名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='附件表';

/*Table structure for table `pub_supplymaterial_log` */

DROP TABLE IF EXISTS `pub_supplymaterial_log`;

CREATE TABLE `pub_supplymaterial_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id,自增字段',
  `supplymaterial_id` int(11) DEFAULT NULL COMMENT '补充材料id',
  `operator_id` int(11) DEFAULT NULL COMMENT '操作者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `operate_type` varchar(20) DEFAULT '' COMMENT '补充类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='补充资料日志表';

/*Table structure for table `sys_dict` */

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_code` varchar(64) DEFAULT NULL,
  `type_name` varchar(32) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `remark` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_code` (`type_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=288 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_dict_detail` */

DROP TABLE IF EXISTS `sys_dict_detail`;

CREATE TABLE `sys_dict_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_code` varchar(64) DEFAULT NULL,
  `item_value` varchar(32) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item_code` (`item_code`) USING BTREE,
  KEY `parent_id_index` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=839 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `add_user` varchar(64) DEFAULT '' COMMENT '创建者',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remote_addr` varchar(256) DEFAULT '' COMMENT '操作IP地址',
  `request_uri` varchar(1024) DEFAULT '' COMMENT '请求URI',
  `method` varchar(1024) DEFAULT '' COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键标示',
  `sys_type` tinyint(3) DEFAULT '0' COMMENT '0公用 1信贷 2车贷',
  `name` varchar(128) DEFAULT '' COMMENT '菜单名称',
  `parent_id` int(11) unsigned DEFAULT NULL COMMENT '父级ID',
  `href` varchar(512) DEFAULT '' COMMENT '链接地址',
  `icon_cls` varchar(512) DEFAULT '' COMMENT '图标',
  `sort` int(11) unsigned zerofill DEFAULT '00000000000' COMMENT '排序',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `add_user` varchar(128) DEFAULT '' COMMENT '添加者',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(128) DEFAULT '' COMMENT '修改者',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  `is_delete` tinyint(2) DEFAULT NULL COMMENT '是否删除：0不删除，1删除',
  `is_menu` tinyint(2) DEFAULT '1' COMMENT '0否，1是',
  `scriptid` varchar(255) DEFAULT NULL COMMENT '脚本名称',
  `controller_name` varchar(255) DEFAULT NULL COMMENT '控制器名字',
  `leaf` tinyint(1) DEFAULT NULL COMMENT '是否为子节点  1 true 0 false',
  `level` tinyint(2) unsigned zerofill DEFAULT NULL COMMENT '树状层数据',
  `scriptid_old` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11185 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_office` */

DROP TABLE IF EXISTS `sys_office`;

CREATE TABLE `sys_office` (
  `id` char(64) NOT NULL COMMENT '主键',
  `name` varchar(128) DEFAULT '' COMMENT '机构名称',
  `parent_id` char(64) DEFAULT NULL COMMENT '父级ID',
  `area` varchar(11) DEFAULT '' COMMENT '区名字',
  `type` tinyint(4) DEFAULT NULL COMMENT '机构类型:职能部门默认0 公司1 营业厅2 销售部门3',
  `grade` tinyint(4) DEFAULT NULL COMMENT '机构登记',
  `address` varchar(256) DEFAULT '' COMMENT '联系地址',
  `zip_code` varchar(128) DEFAULT '' COMMENT '邮政编码',
  `master` varchar(128) DEFAULT '' COMMENT '负责人',
  `phone` varchar(128) DEFAULT '' COMMENT '电话',
  `fax` varchar(128) DEFAULT '' COMMENT '传真',
  `email` varchar(128) DEFAULT '' COMMENT '邮箱',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `add_user` varchar(128) DEFAULT '' COMMENT '添加者',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(128) DEFAULT '' COMMENT '修改者',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  `is_delete` tinyint(4) DEFAULT NULL COMMENT '是否删除：0不删除，1删除',
  `city` varchar(50) DEFAULT NULL COMMENT '市',
  `province` varchar(50) DEFAULT NULL COMMENT '省',
  `sort` int(3) DEFAULT NULL COMMENT '排序',
  `office_number` varchar(32) DEFAULT '' COMMENT '机构序号',
  `office_bank` varchar(32) DEFAULT '' COMMENT '银行',
  `office_bank_card` varchar(32) DEFAULT '' COMMENT '银行卡号',
  `office_card` varchar(32) DEFAULT '' COMMENT '机构代码证号',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_office_user_count` */

DROP TABLE IF EXISTS `sys_office_user_count`;

CREATE TABLE `sys_office_user_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `office_id` int(11) DEFAULT NULL COMMENT '机构ID',
  `office_number` varchar(256) DEFAULT NULL COMMENT '机构账号',
  `user_count` smallint(6) DEFAULT NULL COMMENT '账号用户数',
  `push_time` datetime DEFAULT NULL COMMENT '推送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键标示',
  `name` varchar(128) DEFAULT '' COMMENT '角色名',
  `nid` varchar(64) DEFAULT '' COMMENT '角色唯一标示',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `add_user` varchar(128) DEFAULT '' COMMENT '添加者',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(128) DEFAULT '' COMMENT '修改者',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  `is_delete` tinyint(4) DEFAULT NULL COMMENT '是否删除：0不删除，1删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nid_index` (`nid`) USING BTREE,
  KEY `primary_key` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=307 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色主键',
  `menu_id` int(11) NOT NULL COMMENT '菜单主键',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`,`menu_id`) USING BTREE,
  KEY `role_id_index` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=134916 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键标示',
  `name` varchar(128) DEFAULT '' COMMENT '姓名',
  `user_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '登陆名',
  `password` varchar(128) DEFAULT '' COMMENT '密码',
  `job_num` varchar(128) DEFAULT '' COMMENT '工号',
  `company_id` char(64) NOT NULL COMMENT '公司',
  `office_id` char(64) NOT NULL COMMENT '部门',
  `office_over` varchar(1024) DEFAULT NULL COMMENT '管辖机构',
  `position` int(3) unsigned zerofill DEFAULT '001' COMMENT '职位 0普通职员 1主管  2部门经理',
  `email` varchar(256) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(128) DEFAULT '' COMMENT '电话',
  `mobile` varchar(128) DEFAULT '' COMMENT '手机',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态：0正常 1禁用',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登陆IP',
  `login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `add_user` varchar(128) DEFAULT '' COMMENT '添加者',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(128) DEFAULT '' COMMENT '修改者',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  `is_delete` tinyint(4) DEFAULT NULL COMMENT '是否删除:0不删除，1删除',
  `is_receive_order` varchar(4) DEFAULT '1' COMMENT '是否接单(1.接单 2.不接单)',
  PRIMARY KEY (`id`),
  KEY `userNameIndex` (`user_name`) USING BTREE,
  KEY `officeIdIndex` (`office_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=279 DEFAULT CHARSET=utf8;

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色主键',
  `user_id` int(11) NOT NULL COMMENT '用户主键',
  `level` tinyint(1) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id_index` (`role_id`) USING BTREE,
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1953 DEFAULT CHARSET=utf8;

/* Trigger structure for table `sys_user` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `t_sys_user_update` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'%' */ /*!50003 TRIGGER `t_sys_user_update` AFTER UPDATE ON `sys_user` FOR EACH ROW BEGIN
	insert into sys_log(type,add_user,add_time) values(1,new.update_user,new.update_time);
END */$$


DELIMITER ;

/* Function  structure for function  `calculateDefautInterest` */

/*!50003 DROP FUNCTION IF EXISTS `calculateDefautInterest` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `calculateDefautInterest`(dateReal_ 			datetime,
	dateShould_ 		datetime,
	money_ 				decimal(10,2),
	interestRate_ 		double) RETURNS decimal(10,2)
    READS SQL DATA
    SQL SECURITY INVOKER
    COMMENT '计算罚息'
begin
	if dateShould_ > dateReal_ then
		return 0;
	else
		return money_*interestRate_*datediff(dateReal_,dateShould_);
	end if;
	
end */$$
DELIMITER ;

/* Function  structure for function  `calculatePendingRepayCapital` */

/*!50003 DROP FUNCTION IF EXISTS `calculatePendingRepayCapital` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `calculatePendingRepayCapital`(processInstanceId_	varchar(64)) RETURNS decimal(10,2)
    READS SQL DATA
    SQL SECURITY INVOKER
    COMMENT '计算待还本金'
begin
	select
		round(sum(capital),2)
		into @account
	from pub_repaymentdetail
	where
		processInstanceId=processInstanceId_
		and realpayment_time is null;
	return @account;
end */$$
DELIMITER ;

/* Function  structure for function  `calculatePendingRepayInterest` */

/*!50003 DROP FUNCTION IF EXISTS `calculatePendingRepayInterest` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `calculatePendingRepayInterest`(processInstanceId_	varchar(64)) RETURNS decimal(10,2)
    READS SQL DATA
    SQL SECURITY INVOKER
    COMMENT '计算待还利息'
begin
	select
		round(sum(interest),2)
	into @account
	from pub_repaymentdetail
	where
		processInstanceId=processInstanceId_
		and realpayment_time is null;
	return @account;
end */$$
DELIMITER ;

/* Function  structure for function  `custAssociateWarningCheck` */

/*!50003 DROP FUNCTION IF EXISTS `custAssociateWarningCheck` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `custAssociateWarningCheck`(phone_	varchar(50),name_	varchar(100),sfz_	varchar(20),consultId_ int) RETURNS varchar(5000) CHARSET utf8
    READS SQL DATA
    SQL SECURITY INVOKER
    COMMENT '检测关联客户警告,有警告则返回咨询标ids'
BEGIN
	set @phone=phone_;
	set @sfz=sfz_;
	set @name=name_;
	set @consultId=consultId_;
	set @consultIds='';
	if(phone_ is not null and phone_!='') THEN
		SELECT
			group_concat(id)	ids
			into @ids
		from(
			select
				id
			from pl_creditconsult consult
			where
				consult.id!=@consultId
				and consult.rd_mobile=@phone
		)a;
		if(@ids is not null) THEN
			set @consultIds=concat(@consultIds,',',@ids);
		end if;
	end if;
	if(name_ is not null and name_!='') THEN
		SELECT
			group_concat(id)	ids
			into @ids
		from(
			select
				id
			from pl_creditconsult consult
			where
				consult.id!=@consultId
				and consult.rd_name=@name
		)a;
		if(@ids is not null) THEN
			set @consultIds=concat(@consultIds,',',@ids);
		end if;
	end if;
	if(sfz_ is not null and sfz_!='') THEN
		SELECT
			group_concat(id)	ids
			into @ids
		from(
			select
				id
			from pl_creditconsult consult
			where
				consult.id!=@consultId
				and consult.rd_idcard=@sfz
		)a;
		if(@ids is not null) THEN
			set @consultIds=concat(@consultIds,',',@ids);
		end if;
	end if;
	return substr(@consultIds,2);
END */$$
DELIMITER ;

/* Function  structure for function  `debj_zlx` */

/*!50003 DROP FUNCTION IF EXISTS `debj_zlx` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `debj_zlx`(money double,yearInterest double,monthCnt int) RETURNS varchar(255) CHARSET utf8
    NO SQL
    SQL SECURITY INVOKER
BEGIN
	-- 计算等额本息的总利息，参数接受3个参数，如 debj_zlx(10000,0.12,3) 代表，借款1万元，年利率为12%，分3个月还清
	return money*yearInterest/12*(monthCnt+1)/2;
END */$$
DELIMITER ;

/* Function  structure for function  `getOfficeIdByUserId` */

/*!50003 DROP FUNCTION IF EXISTS `getOfficeIdByUserId` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `getOfficeIdByUserId`(id_ int) RETURNS int(11)
    READS SQL DATA
    SQL SECURITY INVOKER
BEGIN
	declare officeId_ int;
	select
		office.id
		into officeId_ 
	from sys_user u
	join sys_office office on office.id=u.office_id	
	where 
		u.id=id_;
	return officeId_;
END */$$
DELIMITER ;

/* Function  structure for function  `officeQueryRootIdByLeafId` */

/*!50003 DROP FUNCTION IF EXISTS `officeQueryRootIdByLeafId` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `officeQueryRootIdByLeafId`(leafId_ varchar(30)) RETURNS varchar(50) CHARSET utf8
    READS SQL DATA
    SQL SECURITY INVOKER
begin
    set @officeId=leafId_;
    set @type='';
    set @index=20;
    set @id=null;
    while @index>0 and @type!='2'  do
        set @index=@index-1;
        set @id=@officeId;
        SELECT
            type,parent_id
            into @TYPE,@officeId
        from sys_office
        where
            id=@officeId;
    end while;
    if @type!='2' THEN
        set @officeId='';
    end if;
    return @id;
end */$$
DELIMITER ;

/* Function  structure for function  `queryLoanProcessCategory` */

/*!50003 DROP FUNCTION IF EXISTS `queryLoanProcessCategory` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `queryLoanProcessCategory`(type_ VARCHAR(50),ids_ VARCHAR(500)) RETURNS varchar(500) CHARSET utf8
    READS SQL DATA
    SQL SECURITY INVOKER
    COMMENT 'pub_loanprocess catetory 转换成中文'
BEGIN
	DECLARE result_ VARCHAR(500);
	IF type_ = 'supply-material' THEN
		SELECT CONCAT('补充资料:[',GROUP_CONCAT(rd_remark),']') INTO result_ FROM pl_pprofile WHERE FIND_IN_SET(id,ids_);
	ELSEIF type_='unpass' THEN
		SELECT CONCAT('拒贷类别:[',GROUP_CONCAT(item_value),']') INTO result_ FROM sys_dict_detail WHERE FIND_IN_SET(id,ids_);
	END IF;
	RETURN result_;
END */$$
DELIMITER ;

/* Function  structure for function  `queryProfileLeafIds` */

/*!50003 DROP FUNCTION IF EXISTS `queryProfileLeafIds` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `queryProfileLeafIds`(parentId_ VARCHAR(30)) RETURNS varchar(5000) CHARSET utf8
    READS SQL DATA
    SQL SECURITY INVOKER
    COMMENT '通过parentid(s) 查找所有子孙叶子节点id'
BEGIN
		DECLARE leafIds VARCHAR(5000) DEFAULT '';
		DECLARE parentIds VARCHAR(1000);
		SET parentIds=parentId_;
		WHILE parentIds IS NOT NULL DO
				SET leafIds=CONCAT(leafIds,',',parentIds);
				SELECT
					GROUP_CONCAT(id)
				INTO parentIds
				FROM pl_pprofile
				WHERE
					FIND_IN_SET(rd_parent_id,parentIds)
				;
		END WHILE;
		SET leafIds=SUBSTRING(leafIds,2);
		SELECT
				GROUP_CONCAT(id)
				INTO leafIds
		FROM pl_pprofile p
		WHERE
				FIND_IN_SET(id,leafIds)
				AND NOT EXISTS(
						SELECT 1 FROM pl_pprofile WHERE rd_parent_id=p.id LIMIT 1
				);
		RETURN leafIds;
END */$$
DELIMITER ;

/* Function  structure for function  `queryProfileTreeIds` */

/*!50003 DROP FUNCTION IF EXISTS `queryProfileTreeIds` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `queryProfileTreeIds`(leafIds VARCHAR(1000)) RETURNS varchar(5000) CHARSET utf8
    READS SQL DATA
    SQL SECURITY INVOKER
BEGIN
	DECLARE ids VARCHAR(5000);
	DECLARE tmp VARCHAR(1000);
	SET ids='';
	SET tmp=leafIds;
	WHILE tmp IS NOT NULL DO
		SET ids=CONCAT(ids,',',tmp);
		SELECT 
			GROUP_CONCAT(id)
		INTO tmp
		FROM pl_pprofile 
		WHERE id IN (
			SELECT rd_parent_id FROM pl_pprofile WHERE FIND_IN_SET(id,tmp)>0
		);
	END WHILE;
	RETURN SUBSTRING(ids,2);
END */$$
DELIMITER ;

/* Function  structure for function  `rmbDx` */

/*!50003 DROP FUNCTION IF EXISTS `rmbDx` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` FUNCTION `rmbDx`(MONEY decimal(14,2)) RETURNS varchar(150) CHARSET utf8
    NO SQL
    SQL SECURITY INVOKER
BEGIN
		declare RV_MONEY0 VARCHAR(20);
		declare RV_MONEY1 VARCHAR(4);
		declare RV_MONEY2 VARCHAR(4);
		declare V_MONEY0 VARCHAR(28);
		declare V_MONEY1 VARCHAR(4);
		declare V_MONEY2 VARCHAR(4);
		declare V_MONEY3 VARCHAR(4);
		declare V_MONEY4 VARCHAR(4);
		declare V_MONEY5 VARCHAR(4);
		declare V_MONEY6 VARCHAR(4);
		declare V_MONEY7 VARCHAR(4);
		declare V_MONEY8 VARCHAR(4);
		declare V_MONEY9 VARCHAR(4);
		declare V_MONEY10 VARCHAR(4);
		declare V_MONEY11 VARCHAR(4);
		declare V_MONEY12 VARCHAR(4);
		declare RPV_MONEY1 VARCHAR(4);
		declare RPV_MONEY2 VARCHAR(4);
		declare PV_MONEY0 VARCHAR(14);
		declare PV_MONEY1 VARCHAR(4);
		declare PV_MONEY2 VARCHAR(4);
		declare PV_MONEY3 VARCHAR(4);
		declare PV_MONEY4 VARCHAR(4);
		declare PV_MONEY5 VARCHAR(4);
		declare PV_MONEY6 VARCHAR(4);
		declare PV_MONEY7 VARCHAR(4);
		declare PV_MONEY8 VARCHAR(4);
		declare PV_MONEY9 VARCHAR(4);
		declare PV_MONEY10 VARCHAR(4);
		declare PV_MONEY11 VARCHAR(4);
		declare PV_MONEY12 VARCHAR(4);
		declare Z_MONEY VARCHAR(28);
		set Z_MONEY=FLOOR(MONEY);
		set V_MONEY0=LTRIM(RTRIM(Z_MONEY));
		set Z_MONEY=FLOOR(MONEY*100);
		set RV_MONEY0=LTRIM(RTRIM(Z_MONEY));
		set RV_MONEY1=SUBSTR(RV_MONEY0,LENGTH(RV_MONEY0),1);
		IF LENGTH(RV_MONEY0)>1 THEN
			set RV_MONEY2=SUBSTR(RV_MONEY0,LENGTH(RV_MONEY0)-1,1);
		ELSE
			set RV_MONEY2='0';
		END IF;
		set V_MONEY1=SUBSTR(V_MONEY0,LENGTH(V_MONEY0),1);
		IF LENGTH(V_MONEY0)-1>0 THEN
			set V_MONEY2=SUBSTR(V_MONEY0,LENGTH(V_MONEY0)-1,1);
		ELSE
			set V_MONEY2='0';
		END IF;
		IF LENGTH(V_MONEY0)-2>0 THEN
			set V_MONEY3=SUBSTR(V_MONEY0,LENGTH(V_MONEY0)-2,1);
		ELSE
			set V_MONEY3='0';
		END IF;
		IF LENGTH(V_MONEY0)-3>0 THEN
			set V_MONEY4=SUBSTR(V_MONEY0,LENGTH(V_MONEY0)-3,1);
		ELSE
			set V_MONEY4='0';
		END IF;
		IF LENGTH(V_MONEY0)-4>0 THEN
			set V_MONEY5=SUBSTR(V_MONEY0,LENGTH(V_MONEY0)-4,1);
		ELSE
			set V_MONEY5='0';
		END IF;
		IF LENGTH(V_MONEY0)-5>0 THEN
			set V_MONEY6=SUBSTR(V_MONEY0,LENGTH(V_MONEY0)-5,1);
		ELSE
			set V_MONEY6='0';
		END IF;
		IF LENGTH(V_MONEY0)-6>0 THEN
			set V_MONEY7=SUBSTR(V_MONEY0,LENGTH(V_MONEY0)-6,1);
		ELSE
			set V_MONEY7='0';
		END IF;
		IF LENGTH(V_MONEY0)-7>0 THEN
			set V_MONEY8=SUBSTR(V_MONEY0,LENGTH(V_MONEY0)-7,1);
		ELSE
			set V_MONEY8='0';
		END IF;
		IF LENGTH(V_MONEY0)-8>0 THEN
			set V_MONEY9=SUBSTR(V_MONEY0,LENGTH(V_MONEY0)-8,1);
		ELSE
			set V_MONEY9='0';
		END IF;
		IF LENGTH(V_MONEY0)-9>0 THEN
			set V_MONEY10=SUBSTR(V_MONEY0,LENGTH(V_MONEY0)-9,1);
		ELSE
			set V_MONEY10='0';
		END IF;
		IF LENGTH(V_MONEY0)-10>0 THEN
			set V_MONEY11=SUBSTR(V_MONEY0,LENGTH(V_MONEY0)-10,1);
		ELSE
			set V_MONEY11='0';
		END IF;
		IF LENGTH(V_MONEY0)-11>0 THEN
			set V_MONEY12=SUBSTR(V_MONEY0,LENGTH(V_MONEY0)-11,1);
		ELSE
			set V_MONEY12='0';
		END IF;
		select if(RV_MONEY1='0','零分',if(RV_MONEY1='1','壹分',if(RV_MONEY1='2','贰分',if(RV_MONEY1='3','叁分',
																					if(RV_MONEY1='4','肆分',if(RV_MONEY1='5','伍分',if(RV_MONEY1='6','陆分',if(RV_MONEY1='7','柒分',if(
																						RV_MONEY1='8','捌分','玖分'))))))))) into rpv_money1;
		select if(RV_MONEY2='0','零角',if(RV_MONEY2='1','壹角',if(RV_MONEY2='2','贰角',if(RV_MONEY2='3','叁角',
																					if(RV_MONEY2='4','肆角',if(RV_MONEY2='5','伍角',if(RV_MONEY2='6','陆角',if(RV_MONEY2='7','柒角',if(
																						RV_MONEY2='8','捌角','玖角'))))))))) into rpv_money2;
		select if(V_MONEY1='0','元',if(V_MONEY1='1','壹元',if(V_MONEY1='2','贰元',if(V_MONEY1='3','叁元',
																				if(V_MONEY1='4','肆元',if(V_MONEY1='5','伍元',if(V_MONEY1='6','陆元',if(V_MONEY1='7','柒元',if(
																					V_MONEY1='8','捌元','玖元'))))))))) into pv_money1;
		select if(V_MONEY2='0','',if(V_MONEY2='1','壹拾',if(V_MONEY2='2','贰拾',if(V_MONEY2='3','叁拾',
																			   if(V_MONEY2='4','肆拾',if(V_MONEY2='5','伍拾',if(V_MONEY2='6','陆拾',if(V_MONEY2='7','柒拾',if(
																				   V_MONEY2='8','捌拾','玖拾'))))))))) into pv_money2;
		select if(V_MONEY3='0','',if(V_MONEY3='1','壹佰',if(V_MONEY3='2','贰佰',if(V_MONEY3='3','叁佰',
																			   if(V_MONEY3='4','肆佰',if(V_MONEY3='5','伍佰',if(V_MONEY3='6','陆佰',if(V_MONEY3='7','柒佰',if(
																				   V_MONEY3='8','捌佰','玖佰'))))))))) into pv_money3;
		select if(V_MONEY4='0','',if(V_MONEY4='1','壹仟',if(V_MONEY4='2','贰仟',if(V_MONEY4='3','叁仟',
																			   if(V_MONEY4='4','肆仟',if(V_MONEY4='5','伍仟',if(V_MONEY4='6','陆仟',if(V_MONEY4='7','柒仟',if(
																				   V_MONEY4='8','捌仟','玖仟'))))))))) into pv_money4;
		select if(V_MONEY5='0','万',if(V_MONEY5='1','壹万',if(V_MONEY5='2','贰万',if(V_MONEY5='3','叁万',
																				if(V_MONEY5='4','肆万',if(V_MONEY5='5','伍万',if(V_MONEY5='6','陆万',if(V_MONEY5='7','柒万',if(
																					V_MONEY5='8','捌万','玖万'))))))))) into pv_money5;
		select if(V_MONEY6='0','',if(V_MONEY6='1','壹拾',if(V_MONEY6='2','贰拾',if(V_MONEY6='3','叁拾',
																			   if(V_MONEY6='4','肆拾',if(V_MONEY6='5','伍拾',if(V_MONEY6='6','陆拾',if(V_MONEY6='7','玖拾',if(
																				   V_MONEY6='8','捌拾','玖万'))))))))) into pv_money6;
		select if(V_MONEY7='0','',if(V_MONEY7='1','壹佰',if(V_MONEY7='2','贰佰',if(V_MONEY7='3','叁佰',
																			   if(V_MONEY7='4','肆佰',if(V_MONEY7='5','伍佰',if(V_MONEY7='6','陆佰',if(V_MONEY7='7','柒佰',if(
																				   V_MONEY7='8','捌佰','玖佰'))))))))) into pv_money7;
		select if(V_MONEY8='0','',if(V_MONEY8='1','壹仟',if(V_MONEY8='2','贰仟',if(V_MONEY8='3','叁仟',
																			   if(V_MONEY8='4','肆仟',if(V_MONEY8='5','伍仟',if(V_MONEY8='6','陆仟',if(V_MONEY8='7','柒仟',if(
																				   V_MONEY8='8','捌仟','玖仟'))))))))) into pv_money8;
		select if(V_MONEY9='0','亿',if(V_MONEY9='1','壹亿',if(V_MONEY9='2','贰亿',if(V_MONEY9='3','叁亿',
																				if(V_MONEY9='4','肆亿',if(V_MONEY9='5','伍亿',if(V_MONEY9='6','陆亿',if(V_MONEY9='7','柒亿',if(
																					V_MONEY9='8','捌亿','玖亿'))))))))) into pv_money9;
		select if(V_MONEY10='0','',if(V_MONEY10='1','壹拾',if(V_MONEY10='2','贰拾',if(V_MONEY10='3','叁拾',
																				  if(V_MONEY10='4','肆拾',if(V_MONEY10='5','伍拾',if(V_MONEY10='6','陆拾',if(V_MONEY10='7','玖拾',if(
																					  V_MONEY10='8','捌拾','玖万'))))))))) into pv_money10;
		select if(V_MONEY11='0','',if(V_MONEY11='1','壹佰',if(V_MONEY11='2','贰佰',if(V_MONEY11='3','叁佰',
																				  if(V_MONEY11='4','肆佰',if(V_MONEY11='5','伍佰',if(V_MONEY11='6','陆佰',if(V_MONEY11='7','柒佰',if(
																					  V_MONEY11='8','捌佰','玖佰'))))))))) into pv_money11;
		select if(V_MONEY12='0','',if(V_MONEY12='1','壹仟',if(V_MONEY12='2','贰仟',if(V_MONEY12='3','叁仟',
																				  if(V_MONEY12='4','肆仟',if(V_MONEY12='5','伍仟',if(V_MONEY12='6','陆仟',if(V_MONEY12='7','柒仟',if(
																					  V_MONEY12='8','捌仟','玖仟'))))))))) into pv_money12;
		set V_MONEY0=CONCAT(PV_MONEY12,PV_MONEY11,PV_MONEY10,PV_MONEY9,PV_MONEY8,PV_MONEY7,PV_MONEY6,PV_MONEY5,PV_MONEY4,
							PV_MONEY3,PV_MONEY2,PV_MONEY1,RPV_MONEY2,RPV_MONEY1);
		if SUBSTR(V_MONEY0,1,1)='亿' then
			set V_MONEY0=SUBSTR(V_MONEY0,2,LENGTH(V_MONEY0)-1);
		end if;
		if SUBSTR(V_MONEY0,1,1)='万' then
			set V_MONEY0=SUBSTR(V_MONEY0,2,LENGTH(V_MONEY0)-1);
		end if;
/* 判断亿和万之间间隔的位数 locate */
		set V_MONEY0=REPLACE(V_MONEY0,'亿万','亿');
/* 判断亿和元之间间隔的位数 locate */
		if locate('元',V_MONEY0)-locate('亿',V_MONEY0)=2 then
			set V_MONEY0=REPLACE(V_MONEY0,'亿壹','亿零壹');
			set V_MONEY0=REPLACE(V_MONEY0,'亿贰','亿零贰');
			set V_MONEY0=REPLACE(V_MONEY0,'亿叁','亿零叁');
			set V_MONEY0=REPLACE(V_MONEY0,'亿肆','亿零肆');
			set V_MONEY0=REPLACE(V_MONEY0,'亿伍','亿零伍');
			set V_MONEY0=REPLACE(V_MONEY0,'亿陆','亿零陆');
			set V_MONEY0=REPLACE(V_MONEY0,'亿柒','亿零柒');
			set V_MONEY0=REPLACE(V_MONEY0,'亿捌','亿零捌');
			set V_MONEY0=REPLACE(V_MONEY0,'亿玖','亿零玖');
		end if;
		set V_MONEY0=REPLACE(V_MONEY0,'亿壹拾','亿零壹拾');
		set V_MONEY0=REPLACE(V_MONEY0,'亿贰拾','亿零贰拾');
		set V_MONEY0=REPLACE(V_MONEY0,'亿叁拾','亿零叁拾');
		set V_MONEY0=REPLACE(V_MONEY0,'亿肆拾','亿零肆拾');
		set V_MONEY0=REPLACE(V_MONEY0,'亿伍拾','亿零伍拾');
		set V_MONEY0=REPLACE(V_MONEY0,'亿陆拾','亿零陆拾');
		set V_MONEY0=REPLACE(V_MONEY0,'亿柒拾','亿零柒拾');
		set V_MONEY0=REPLACE(V_MONEY0,'亿捌拾','亿零捌拾');
		set V_MONEY0=REPLACE(V_MONEY0,'亿玖拾','亿零玖拾');
		set V_MONEY0=REPLACE(V_MONEY0,'亿壹佰','亿零壹佰');
		set V_MONEY0=REPLACE(V_MONEY0,'亿贰佰','亿零贰佰');
		set V_MONEY0=REPLACE(V_MONEY0,'亿叁佰','亿零叁佰');
		set V_MONEY0=REPLACE(V_MONEY0,'亿肆佰','亿零肆佰');
		set V_MONEY0=REPLACE(V_MONEY0,'亿伍佰','亿零伍佰');
		set V_MONEY0=REPLACE(V_MONEY0,'亿陆佰','亿零陆佰');
		set V_MONEY0=REPLACE(V_MONEY0,'亿柒佰','亿零柒佰');
		set V_MONEY0=REPLACE(V_MONEY0,'亿捌佰','亿零捌佰');
		set V_MONEY0=REPLACE(V_MONEY0,'亿玖佰','亿零玖佰');
/* 判断最后一个万和元之间间隔的位数 */
		if locate('元',V_MONEY0)-locate('万',V_MONEY0)=2 then
			set V_MONEY0=REPLACE(V_MONEY0,'万壹','万零壹');
			set V_MONEY0=REPLACE(V_MONEY0,'万贰','万零贰');
			set V_MONEY0=REPLACE(V_MONEY0,'万叁','万零叁');
			set V_MONEY0=REPLACE(V_MONEY0,'万肆','万零肆');
			set V_MONEY0=REPLACE(V_MONEY0,'万伍','万零伍');
			set V_MONEY0=REPLACE(V_MONEY0,'万陆','万零陆');
			set V_MONEY0=REPLACE(V_MONEY0,'万柒','万零柒');
			set V_MONEY0=REPLACE(V_MONEY0,'万捌','万零捌');
			set V_MONEY0=REPLACE(V_MONEY0,'万玖','万零玖');
		end if;
		set V_MONEY0=REPLACE(V_MONEY0,'万壹拾','万零壹拾');
		set V_MONEY0=REPLACE(V_MONEY0,'万贰拾','万零贰拾');
		set V_MONEY0=REPLACE(V_MONEY0,'万叁拾','万零叁拾');
		set V_MONEY0=REPLACE(V_MONEY0,'万肆拾','万零肆拾');
		set V_MONEY0=REPLACE(V_MONEY0,'万伍拾','万零伍拾');
		set V_MONEY0=REPLACE(V_MONEY0,'万陆拾','万零陆拾');
		set V_MONEY0=REPLACE(V_MONEY0,'万柒拾','万零柒拾');
		set V_MONEY0=REPLACE(V_MONEY0,'万捌拾','万零捌拾');
		set V_MONEY0=REPLACE(V_MONEY0,'万玖拾','万零玖拾');
		set V_MONEY0=REPLACE(V_MONEY0,'万壹佰','万零壹佰');
		set V_MONEY0=REPLACE(V_MONEY0,'万贰佰','万零贰佰');
		set V_MONEY0=REPLACE(V_MONEY0,'万叁佰','万零叁佰');
		set V_MONEY0=REPLACE(V_MONEY0,'万肆佰','万零肆佰');
		set V_MONEY0=REPLACE(V_MONEY0,'万伍佰','万零伍佰');
		set V_MONEY0=REPLACE(V_MONEY0,'万陆佰','万零陆佰');
		set V_MONEY0=REPLACE(V_MONEY0,'万柒佰','万零柒佰');
		set V_MONEY0=REPLACE(V_MONEY0,'万捌佰','万零捌佰');
		set V_MONEY0=REPLACE(V_MONEY0,'万玖佰','万零玖佰');
/* 判断最后一个千和元之间间隔的位数 */
		if locate('元',V_MONEY0)-locate('仟',V_MONEY0)=2 then
			set V_MONEY0=REPLACE(V_MONEY0,'仟壹','仟零壹');
			set V_MONEY0=REPLACE(V_MONEY0,'仟贰','仟零贰');
			set V_MONEY0=REPLACE(V_MONEY0,'仟叁','仟零叁');
			set V_MONEY0=REPLACE(V_MONEY0,'仟肆','仟零肆');
			set V_MONEY0=REPLACE(V_MONEY0,'仟伍','仟零伍');
			set V_MONEY0=REPLACE(V_MONEY0,'仟陆','仟零陆');
			set V_MONEY0=REPLACE(V_MONEY0,'仟柒','仟零柒');
			set V_MONEY0=REPLACE(V_MONEY0,'仟捌','仟零捌');
			set V_MONEY0=REPLACE(V_MONEY0,'仟玖','仟零玖');
		end if;
		set V_MONEY0=REPLACE(V_MONEY0,'仟壹拾','仟零壹拾');
		set V_MONEY0=REPLACE(V_MONEY0,'仟贰拾','仟零贰拾');
		set V_MONEY0=REPLACE(V_MONEY0,'仟叁拾','仟零叁拾');
		set V_MONEY0=REPLACE(V_MONEY0,'仟肆拾','仟零肆拾');
		set V_MONEY0=REPLACE(V_MONEY0,'仟伍拾','仟零伍拾');
		set V_MONEY0=REPLACE(V_MONEY0,'仟陆拾','仟零陆拾');
		set V_MONEY0=REPLACE(V_MONEY0,'仟柒拾','仟零柒拾');
		set V_MONEY0=REPLACE(V_MONEY0,'仟捌拾','仟零捌拾');
		set V_MONEY0=REPLACE(V_MONEY0,'仟玖拾','仟零玖拾');
/* 判断最后一个佰和元之间间隔的位数 */
		if locate('元',V_MONEY0)-locate('佰',V_MONEY0)=2 then
			set V_MONEY0=REPLACE(V_MONEY0,'佰壹','佰零壹');
			set V_MONEY0=REPLACE(V_MONEY0,'佰贰','佰零贰');
			set V_MONEY0=REPLACE(V_MONEY0,'佰叁','佰零叁');
			set V_MONEY0=REPLACE(V_MONEY0,'佰肆','佰零肆');
			set V_MONEY0=REPLACE(V_MONEY0,'佰伍','佰零伍');
			set V_MONEY0=REPLACE(V_MONEY0,'佰陆','佰零陆');
			set V_MONEY0=REPLACE(V_MONEY0,'佰柒','佰零柒');
			set V_MONEY0=REPLACE(V_MONEY0,'佰捌','佰零捌');
			set V_MONEY0=REPLACE(V_MONEY0,'佰玖','佰零玖');
		end if;
		if SUBSTR(V_MONEY0,LENGTH(V_MONEY0)/2-1,1)='零' then
			set V_MONEY0=SUBSTR(V_MONEY0,1,LENGTH(V_MONEY0)/2-2);
		end if;
		if SUBSTR(V_MONEY0,LENGTH(V_MONEY0)/2-1,1)='零' then
			set V_MONEY0=SUBSTR(V_MONEY0,1,LENGTH(V_MONEY0)/2-2);
			set V_MONEY0=CONCAT(V_MONEY0,'整');
		end if;
/* 判断有没有零角,有就替换成零 */
		set V_MONEY0=REPLACE(V_MONEY0,'零角','零');
		if V_MONEY0='元整' then
			set V_MONEY0='零元';
		end if;
		return V_MONEY0;
	END */$$
DELIMITER ;

/* Procedure structure for procedure `normalRepayment` */

/*!50003 DROP PROCEDURE IF EXISTS  `normalRepayment` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `normalRepayment`(processInstanceId_ varchar(64),realPaymentDate_ date,type_ varchar(10))
    SQL SECURITY INVOKER
    COMMENT '按期还款'
begin
	set @processInstanceId=processInstanceId_;
	set @realPaymentDate=realPaymentDate_;
	select
		round(a.account + a.default_interest,2)	real_account,
		a.*
	from(
		select
			detail.id,
			left(detail.repayment_time,10)		repayment_time,/*还款日期*/
			@realPaymentDate	realpayment_time,		/*实际还款日期*/
			detail.period,				/*还款期次*/
			detail.account,				/*本息*/
			/*datediff(@realPaymentDate,detail.repayment_time) overDayCnt,	*//*超过天数*/
			/*detail.capital,
			borrow.overdue_penalty_rate,		*//*罚息利率*/
			if(@realPaymentDate<=detail.repayment_time,0,
				round(detail.capital*borrow.overdue_penalty_rate*datediff(@realPaymentDate,detail.repayment_time)-detail.reduction_penalty,2)
			)			default_interest,		/*罚息*/
			0			penalty			/*违约金*/
		from pub_repaymentdetail detail
		join pl_borrow borrow on borrow.processInstanceId=detail.processInstanceId
		where detail.id=(
			select
				min(id)
			from pub_repaymentdetail
			where
				processInstanceId=@processInstanceId
				and (real_account is null or real_account=0)
		)
	)a
	;
end */$$
DELIMITER ;

/* Procedure structure for procedure `rebuild_statistics_day` */

/*!50003 DROP PROCEDURE IF EXISTS  `rebuild_statistics_day` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `rebuild_statistics_day`()
    SQL SECURITY INVOKER
    COMMENT '初始化统计中间表 statistics_day'
begin
	drop table if exists statistics_day;
	create table statistics_day(
		day 		date	primary key comment '日期',
		intoCnt		int default 0	comment '进件量',
		unPassCnt	int	default 0	comment '拒单量',
		passCnt		int default 0	comment '通过量',
		signCnt		int default 0	comment '签约量',
		loanCnt		int default 0 	comment '放款量',
		consultCnt	int default 0	comment '咨询量',
		loanSum		decimal(20,6) default 0	comment '放款金额(万)',
		contractSum	decimal(20,6) default 0	comment '合同金额(万)'
	) comment '按日统计中间表';
	/**
	初始化统计中间表
	 */
	insert into statistics_day(day,consultCnt)
		select
			left(rd_createtime,10)	day,
			count(1)				cnt
		from pl_creditconsult consult
		group by
			left(rd_createtime,10)
	on duplicate key update consultCnt=values(consultCnt)
	;
	-- 初始化合同金额
	insert into statistics_day(day,contractSum)
		select
			left(signature_date,10)	day,
			sum(contract_account)/10000	account
		from pl_contract c
		where
			c.status=1		/*已经生效的合同*/
		group by
			left(rd_createtime,10)
	on duplicate key update contractSum=values(contractSum)
	;
	-- 初始化放款金额，放款量
	insert into statistics_day(day,loanSum,loanCnt)
		select
			left(create_time,10)	day,
			sum(account)/10000			account,
			count(1)				cnt
		from pub_loan c
		group by
			left(create_time,10)
	on duplicate key update
		loanSum=values(loanSum),
		loanCnt=values(loanCnt)
	;
	-- 初始化签约量
	insert into statistics_day(day,signCnt)
		select
			left(signature_date,10)	day,
			count(1)				cnt
		from pl_contract c
		where
			c.status=1
		group by
			left(signature_date,10)
	on duplicate key update signCnt=values(signCnt)
	;
	-- 初始化拒单量
	insert into statistics_day(day,unPassCnt)
		select
			left(modify_time,10)	day,
			count(1)				cnt
		from pl_creditconsult c
		where
			c.status='rejected'
		group by
			left(modify_time,10)
	on duplicate key update unPassCnt=values(unPassCnt)
	;
	-- 初始化进件量
	insert into statistics_day(day,intoCnt)
		select
			left(add_time,10)	day,
			count(1)			cnt
		from pl_borrowrequirement c
		group by
			left(add_time,10)
	on duplicate key update intoCnt=values(intoCnt)
	;
	select * from statistics_day;
end */$$
DELIMITER ;

/* Procedure structure for procedure `repaymentDisposable` */

/*!50003 DROP PROCEDURE IF EXISTS  `repaymentDisposable` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `repaymentDisposable`(processInstanceId_ varchar(50),
	realHkDate_ char(10),
	modifier_ varchar(10),
	modifyTime_ datetime)
    SQL SECURITY INVOKER
    COMMENT '还款管理'
begin
	set @processInstanceId=processInstanceId_;
	set @realHkDate=realHkDate_;
	set @modifier=modifier_;
	
	drop table if exists temp;
	create temporary table temp
		select
			repay.processInstanceId,
			repay.id,
			repay.period,
			repay.repayment_staus,/*还款状态*/
			repay.realpayment_time,/*实际还款金额*/
			left(repay.repayment_time,10)	repayment_time,	/*应还款时间*/
			repay.account,		/*本息*/
			repay.capital,		/*本金*/
			repay.interest,		/*利息*/
			repay.reduction_penalty,/*减免罚息*/
			borrow.overdue_penalty_rate	faxiLilv,/*罚息利率 每天*/
			borrow.repayment_default	penaltyLilv,/*违约金利率 每月*/
			@realHkDate			realHkDate
		from pub_repaymentdetail repay
		join pl_borrow borrow on borrow.processInstanceId=repay.processInstanceId
		where 
			repay.processInstanceId = @processInstanceId
			and realpayment_time is null
			;
	drop table if exists temp2,temp3;
	create temporary table temp2 select * from temp;
	create temporary table temp3 select * from temp;
	alter table temp 
		add faxi		decimal(20,2) default 0,/*罚息*/
		add faxiAfter	decimal(20,2) default 0,/*减免后的罚息*/
		add penalty		decimal(20,3) default 0,/*违约金*/
		add benxi		decimal(20,2),/*本息*/
		add shouldPay	decimal(20,2)/*应支付*/;
	update temp t join(
		select
			id,
			capital*faxiLilv*datediff(@realHkDate,repayment_time) 						faxi,
			capital*faxiLilv*datediff(@realHkDate,repayment_time)-reduction_penalty		faxiAfter
		from temp2
		where 
			repayment_time<@realHkDate
	)faxi on faxi.id=t.id
	set 
		t.faxi=faxi.faxi,
		t.faxiAfter=faxi.faxiAfter,
		t.repayment_staus=3 /*逾期还款*/
		;
	update temp t join(
		select
			id,
			capital,
			capital*penaltyLilv		penalty
		from temp3 where id>(
			select
				min(id)
			from pub_repaymentdetail
			where
				processInstanceId = @processInstanceId
				and repayment_time >= @realHkDate
		)
	)p on p.id=t.id
	set
		t.penalty=p.penalty,
		t.benxi=p.capital,
		t.repayment_staus=4 /*提前还款*/
	;
	
	
	update temp set 
		faxi=ifnull(faxi,0),
		faxiAfter=ifnull(faxiAfter,0),
		penalty=ifnull(penalty,0),
		benxi=ifnull(benxi,account),
		shouldPay=benxi+penalty+faxiAfter;
	update temp set		
		repayment_staus=1
	where
		repayment_staus!=3
		and repayment_staus!=4;
	select
		sum(penalty),max(id)
	into @penaltySum,@maxId
	from temp;
	alter table temp modify penalty decimal(20,2);
	select
		ifnull(sum(penalty),0)
		into @penaltySum2
	from temp
	where
		id!=@maxId;
	update temp set
	 	penalty=@penaltySum-@penaltySum2
	where
		id=@maxId;
		
	
	if @modifier is not null  then
		/*1正常还款；2逾期未还款；3逾期还款；4提前还款；0还款中*/
		update pub_repaymentdetail repay 
		join temp  t on t.id=repay.id
		set 
			repay.realpayment_time = @realHkDate,
			repay.real_account=t.shouldPay,
			repay.penalty=t.penalty,
			repay.default_interest=t.faxi,
			repay.is_payoff=1,
			repay.repayment_staus=t.repayment_staus,
			repay.modifier=@modifier,
			repay.modify_time=modifyTime_
			;
		update pl_borrow borrow,(
			select
				sum(repay.capital)	capital,
				sum(repay.interest)-sum(repay.reduction_penalty)	interest
			from
			pub_repaymentdetail repay
			where
				processInstanceId = @processInstanceId
				and real_account is not null and real_account>0
		)c
		set
			borrow.repayment_yes_account=c.capital,
			borrow.repayment_yes_interest=c.interest,
			borrow.repayment_status=1			/*0还款中 1结清状态  2逾期*/
		where 
			processInstanceId = @processInstanceId;
	else
		select
			group_concat(period)	period,
			sum(penalty)			penalty,
			sum(faxi)				faxi,
			sum(faxiAfter)			faxiAfter,
			sum(benxi)				benxi,
			sum(shouldPay)			shouldPay
		from temp;
	end if;
end */$$
DELIMITER ;

/* Procedure structure for procedure `test1` */

/*!50003 DROP PROCEDURE IF EXISTS  `test1` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `test1`(processInstanceId_ varchar(50),realHkDate_ char(10),modifier_ varchar(10),modifyTime_ datetime)
begin
	set @processInstanceId=processInstanceId_;
	set @realHkDate=realHkDate_;
	set @modifier=modifier_;
	
	drop table if exists hk_temp;
	create temporary table hk_temp
		select
			repay.processInstanceId,
			repay.id,
			repay.period,
			repay.repayment_staus,/*还款状态*/
			repay.realpayment_time,/*实际还款金额*/
			left(repay.repayment_time,10)	repayment_time,	/*应还款时间*/
			repay.account,		/*本息*/
			repay.capital,		/*本金*/
			repay.interest,		/*利息*/
			repay.reduction_penalty,/*减免罚息*/
			borrow.overdue_penalty_rate	faxiLilv,/*罚息利率 每天*/
			borrow.repayment_default	penaltyLilv,/*违约金利率 每月*/
			@realHkDate			realHkDate
		from pub_repaymentdetail repay
		join pl_borrow borrow on borrow.processInstanceId=repay.processInstanceId
		where 
			repay.processInstanceId = @processInstanceId
			and realpayment_time is null
			;
	drop table if exists hk_temp2,hk_temp3;
	create temporary table hk_temp2 select * from hk_temp;
	create temporary table hk_temp3 select * from hk_temp;
select * from hk_temp;
	alter table hk_temp 
		add faxi		decimal(20,2) default 0,/*罚息*/
		add faxiAfter	decimal(20,2) default 0,/*减免后的罚息*/
		add penalty		decimal(20,3) default 0,/*违约金*/
		add benxi		decimal(20,2),/*本息*/
		add shouldPay	decimal(20,2)/*应支付*/;
		
/*逾期还款，罚息*/
	update hk_temp t join(
		select
			id,
			capital*faxiLilv*datediff(@realHkDate,repayment_time) 						faxi,
			capital*faxiLilv*datediff(@realHkDate,repayment_time)-reduction_penalty		faxiAfter
		from hk_temp2
		where 
			repayment_time<@realHkDate
	)faxi on faxi.id=t.id
	set 
		t.faxi=faxi.faxi,
		t.faxiAfter=faxi.faxiAfter,
		t.repayment_staus=3 
		;
/*提前还款，违约金*/
	update hk_temp t join(
		select
			id,
			capital,
			capital*penaltyLilv		penalty
		from hk_temp3 where id>(
			select
				min(id)
			from pub_repaymentdetail
			where
				processInstanceId = @processInstanceId
				and repayment_time >= @realHkDate
		)
	)p on p.id=t.id
	set
		t.penalty=p.penalty,
		t.benxi=p.capital,
		t.repayment_staus=4 
	;
	
select * from hk_temp;
	
	update hk_temp set 
		faxi=ifnull(faxi,0),
		faxiAfter=ifnull(faxiAfter,0),
		penalty=ifnull(penalty,0),
		benxi=ifnull(benxi,account),
		shouldPay=benxi+penalty+faxiAfter;
	update hk_temp set		
		repayment_staus=1
	where
		repayment_staus!=3
		and repayment_staus!=4;
	select
		sum(penalty),max(id)
	into @penaltySum,@maxId
	from hk_temp;
	alter table hk_temp modify penalty decimal(20,2);
	select
		ifnull(sum(penalty),0)
		into @penaltySum2
	from hk_temp
	where
		id!=@maxId;
	update hk_temp set
	 	penalty=@penaltySum-@penaltySum2
	where
		id=@maxId;
select * from hk_temp;
end */$$
DELIMITER ;

/*Data for the table `act_property` */
insert  into `act_ge_property`(`NAME_`,`VALUE_`,`REV_`) values ('next.dbid','10000',1),('schema.history','create(5.20.0.2)',1),('schema.version','5.20.0.2',1);

/*Data for the table `pl_product` */

insert  into `pl_product`(`id`,`name`,`ptype`,`maxmlimit`,`maxtlimit`,`prepayment`,`tminmlimit`,`tmaxmlimit`,`opendate`,`createtime`,`overdue_penalty_rate`,`repayment_rate`,`repayment_default`,`isopen`,`is_delete`,`office_ids`,`overdue_period`,`overdue_rate`,`remark`,`period`,`three_prepayment`,`six_prepayment`,`product_type`,`margin_fee`,`one_prepayment`,`two_prepayment`,`four_prepayment`,`five_prepayment`,`repayment_type`,`delay`,`carloan_type`,`minmlimit`,`ahead_repay_rate`) values (16,'房抵贷',2,NULL,NULL,NULL,NULL,NULL,NULL,'2018-01-01 00:00:00','0.0600','0.0600',NULL,1,0,'',NULL,NULL,'1',NULL,NULL,NULL,22,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,'0.0100');

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`sys_type`,`name`,`parent_id`,`href`,`icon_cls`,`sort`,`add_time`,`add_user`,`update_time`,`update_user`,`remark`,`is_delete`,`is_menu`,`scriptid`,`controller_name`,`leaf`,`level`,`scriptid_old`) values (1,0,'系统管理',0,NULL,'icon-xitongguanli',00000009999,NULL,NULL,NULL,NULL,NULL,0,1,'sysManage',NULL,NULL,NULL,NULL),(2,0,'菜单管理',1,NULL,'icon-caidanguanli',00000000005,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'sysMenuManage',NULL,NULL,NULL,NULL),(3,0,'用户管理',1,NULL,'icon-iconfontcolor17',00000000010,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'sysUserManage',NULL,NULL,NULL,NULL),(4,0,'角色管理',1,NULL,'icon-jiaoseguanli',00000000015,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'sysRoleManage',NULL,NULL,NULL,NULL),(5,0,'组织管理',1,NULL,'icon-zuzhiguanli',00000000020,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'sysOfficeManage',NULL,NULL,NULL,NULL),(7,0,'字典管理',1,NULL,'icon-zidianguanli',00000000030,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'sysDicManage',NULL,NULL,NULL,NULL),(45,0,'工作台',9999,NULL,'icon-gongzuotai',00000000001,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'workbench',NULL,1,02,NULL),(48,0,'计算公式配置',1,NULL,'icon-gongshipeizhi',00000000035,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'sysFelManage',NULL,NULL,NULL,NULL),(61,0,'产品参数管理',1,NULL,'icon-chanpinguanli',00000000040,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'sysProductParametersManage',NULL,NULL,NULL,NULL),(66,0,'产品类型管理',1,NULL,'icon-chanpinguanli',00000000045,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'sysProductTypeManage',NULL,NULL,NULL,NULL),(11068,0,'财务管理',0,NULL,'icon-caiwuguanli',00000000008,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'FinancialManagement',NULL,NULL,NULL,NULL),(11069,0,'放款管理',11068,NULL,'icon-fangkuanguanli',00000000001,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'LoanManagement',NULL,NULL,NULL,NULL),(11080,0,'贷后管理',0,NULL,'icon-daihouguanli',00000000009,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'PostLoanManagement',NULL,NULL,NULL,NULL),(11081,0,'还款管理',11080,NULL,'icon-huankuanguanli',00000000001,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'RepaymentManagement',NULL,NULL,NULL,NULL),(11129,0,'我的任务',0,NULL,'icon-woderenwu',00000000002,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'MyTask',NULL,NULL,NULL,NULL),(11130,0,'我的任务',11129,NULL,'icon-woderenwu1',00000000001,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'MyTaskPending',NULL,NULL,NULL,NULL),(11148,0,'客户管理',0,NULL,'icon-kehuguanli',00000000003,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'CustomerManageMJS',NULL,NULL,NULL,NULL),(11149,0,'客户管理',11148,NULL,'icon-gerenkehu',00000000001,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'CustMang',NULL,NULL,NULL,NULL),(11150,0,'风控审核',0,NULL,'icon-buquanziliao ',00000000004,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'FirstTrialManageMJS',NULL,NULL,NULL,NULL),(11152,0,'机构初审',11150,NULL,'icon-buquanziliao ',00000000003,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'OrganizationTrial',NULL,NULL,NULL,NULL),(11153,0,'押品管理',0,NULL,'icon-yapinguanli',00000000005,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'CollateralManageMJS',NULL,NULL,NULL,NULL),(11154,0,'抵押办理',11153,NULL,'icon-yapinguanli',00000000001,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'MortgageDealt',NULL,NULL,NULL,NULL),(11155,0,'放款审核',0,NULL,'icon-shenheguanli',00000000006,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'RecheckMJS',NULL,NULL,NULL,NULL),(11156,0,'放款复审',11155,NULL,'icon-shenheguanli',00000000003,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'RiskControlReview',NULL,NULL,NULL,NULL),(11158,0,'机构放款',11155,NULL,'icon-zhongshen',00000000004,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'InstitutionalReview',NULL,NULL,NULL,NULL),(11159,0,'催收管理',11080,NULL,'icon-daihoubiangeng',00000000005,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'CollectionManageMJS',NULL,NULL,NULL,NULL),(11160,0,'贷款申请',0,NULL,'icon-shenpi',00000000018,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'LoanApplicationMJS',NULL,NULL,NULL,NULL),(11161,0,'贷款申请记录',11160,NULL,'icon-shenpi',00000000001,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'LoanApplicationRecord',NULL,NULL,NULL,NULL),(11162,0,'客户关系管理',0,NULL,'icon-mianshen',00000000019,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'CustRelationshipManageMJS',NULL,NULL,NULL,NULL),(11163,0,'客户关系管理',11162,NULL,'icon-mianshen',00000000001,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'CustRelationship',NULL,NULL,NULL,NULL),(11172,0,'风控复审',11150,NULL,'icon-mianshenqianqueren',00000000002,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'ControlReview',NULL,NULL,NULL,NULL),(11173,0,'放款初审',11155,NULL,'icon-shenheguanli',00000000001,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'FirstTrialLoan',NULL,NULL,NULL,NULL),(11183,0,'风控初审',11150,NULL,'icon-mianshenqianqueren',NULL,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'FirstTrialMJS',NULL,NULL,NULL,NULL),(11184,0,'下户',11148,NULL,'icon-mianshenqianqueren',00000000002,NULL,NULL,'2018-01-01 00:00:00',NULL,NULL,0,1,'InspectMJS',NULL,NULL,NULL,NULL);

/*Data for the table `sys_office` */

insert  into `sys_office`(`id`,`name`,`parent_id`,`area`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`add_time`,`add_user`,`update_time`,`update_user`,`remark`,`is_delete`,`city`,`province`,`sort`,`office_number`,`office_bank`,`office_bank_card`,`office_card`) values ('11','系统使用公司','0','1',1,0,NULL,NULL,NULL,NULL,NULL,NULL,'2018-01-01 00:00:00','system','2018-01-01 00:00:00','system',NULL,0,'110101',NULL,1,'100','1',NULL,'1'),('1101','部门A','11','1',0,0,NULL,NULL,NULL,NULL,NULL,NULL,'2018-01-01 00:00:00','system','2018-01-01 00:00:00','system',NULL,0,NULL,NULL,2,'2',NULL,NULL,NULL),('1102','部门B','11','1',0,0,NULL,NULL,NULL,NULL,NULL,NULL,'2018-01-01 00:00:00','system','2018-01-01 00:00:00','system',NULL,0,NULL,NULL,4,'4',NULL,NULL,NULL),('1103','部门C','11','1',0,0,NULL,NULL,NULL,NULL,NULL,NULL,'2018-01-01 00:00:00','system','2018-01-01 00:00:00','system',NULL,0,'110101',NULL,3,'BJ',NULL,NULL,NULL),('1104','放款机构','11',NULL,2,0,NULL,NULL,NULL,NULL,NULL,NULL,'2018-01-01 00:00:00','system','2018-01-01 00:00:00','system',NULL,0,NULL,NULL,5,'5',NULL,NULL,NULL),('110401','放款机构A','1104','010',0,0,NULL,NULL,NULL,NULL,NULL,NULL,'2018-01-01 00:00:00','system','2018-01-01 00:00:00','system',NULL,0,NULL,NULL,5,'5',NULL,NULL,NULL),('110402','放款机构B','1104','010',0,0,NULL,NULL,NULL,NULL,NULL,NULL,'2018-01-01 00:00:00','system','2018-01-01 00:00:00','system',NULL,0,NULL,NULL,6,'6',NULL,NULL,NULL);

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`nid`,`add_time`,`add_user`,`update_time`,`update_user`,`remark`,`is_delete`) values (1,'系统管理员','system','2018-01-01 00:00:00','system','2018-01-01 00:00:00','system','系统管理员',0),(253,'放款复审','loanStaffReview','2018-01-01 00:00:00','system','2018-01-01 00:00:00','system','放款复审',0),(254,'放款初审','loanStaffAuditor','2018-01-01 00:00:00','system','2018-01-01 00:00:00','system','放款初审',0),(255,'放款管理员','loanStaff','2018-01-01 00:00:00','system','2018-01-01 00:00:00','system','放款管理员',0),(285,'机构放款','orgWorkerLoan','2018-01-01 00:00:00','system','2018-01-01 00:00:00','system','机构放款',0),(291,'风控初审','initialAuditor','2018-01-01 00:00:00','system','2018-01-01 00:00:00','system','风控初审',0),(292,'风控复审','reviewAuditor','2018-01-01 00:00:00','system','2018-01-01 00:00:00','system','风控复审',0),(293,'机构初审','orgWorker','2018-01-01 00:00:00','system','2018-01-01 00:00:00','system','机构初审',0),(295,'抵押登记员','salesman','2018-01-01 00:00:00','system','2018-01-01 00:00:00','system','抵押登记员',0),(296,'客户经理','clientManager','2018-01-01 00:00:00','system','2018-01-01 00:00:00','system','客户经理',0),(297,'测试角色','testid','2018-01-01 00:00:00','system','2018-01-01 00:00:00','system','测试角色添加',0),(306,'下户','xiahu','2018-01-01 00:00:00','system','2018-01-01 00:00:00','system','下户',0);

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`id`,`role_id`,`menu_id`) values (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,7),(7,1,45),(8,1,48),(9,1,61),(10,1,66),(11,1,11068),(12,1,11069),(13,1,11080),(14,1,11081),(15,1,11129),(16,1,11130),(17,1,11149),(18,1,11150),(19,1,11152),(20,1,11153),(21,1,11154),(22,1,11155),(23,1,11156),(24,1,11158),(25,1,11159),(26,1,11160),(27,1,11161),(28,1,11162),(29,1,11163),(30,1,11172),(31,1,11173),(32,1,11183),(33,1,11184),(34,6,2),(35,6,4),(36,253,11129),(37,253,11130),(38,253,11156),(39,254,11129),(40,254,11130),(41,254,11173),(42,255,11068),(43,255,11069),(44,255,11129),(45,255,11130),(46,285,11129),(47,285,11130),(48,285,11158),(49,291,11129),(50,291,11130),(51,291,11148),(52,291,11149),(53,291,11150),(54,291,11152),(55,291,11172),(56,291,11183),(57,291,11184),(58,292,11129),(59,292,11130),(60,292,11172),(61,293,11129),(62,293,11130),(63,293,11152),(64,295,11129),(65,295,11130),(66,295,11153),(67,295,11154),(68,296,11148),(69,296,11149),(70,296,11160),(71,296,11161),(72,296,11162),(73,296,11163),(74,296,11184),(75,305,11129),(76,305,11130),(77,305,11184),(78,306,11129),(79,306,11130),(80,306,11184);

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`name`,`user_name`,`password`,`job_num`,`company_id`,`office_id`,`office_over`,`position`,`email`,`phone`,`mobile`,`status`,`login_ip`,`login_time`,`add_time`,`add_user`,`update_time`,`update_user`,`remark`,`is_delete`,`is_receive_order`) values (1,'system','system','fpdfjj4dle2bs5znim3ih4iycqr5mtzqifs25ha','1','11','11','1101,1102,1103,1104,110401,110402',001,NULL,NULL,NULL,0,NULL,NULL,NULL,'system','2018-01-01 00:00:00','system',NULL,0,'1'),(279,'user1','user1','fpdfjj4dle2bs5znim3ih4iycqr5mtzqifs25ha','001','11','11',NULL,001,NULL,NULL,NULL,0,NULL,NULL,'2018-08-10 17:30:53','system','2018-08-10 17:30:53','system',NULL,0,'1'),(280,'user2','user2','fpdfjj4dle2bs5znim3ih4iycqr5mtzqifs25ha','002','11','11',NULL,001,NULL,NULL,NULL,0,NULL,NULL,'2018-08-10 17:31:55','system','2018-08-10 17:31:55','system',NULL,0,'1'),(281,'user3','user3','fpdfjj4dle2bs5znim3ih4iycqr5mtzqifs25ha','003','11','11',NULL,001,NULL,NULL,NULL,0,NULL,NULL,'2018-08-10 17:32:31','system','2018-08-10 17:32:31','system',NULL,0,'1'),(282,'uesr4','uesr4','fpdfjj4dle2bs5znim3ih4iycqr5mtzqifs25ha','004','11','11',NULL,001,NULL,NULL,NULL,0,NULL,NULL,'2018-08-10 17:33:26','system','2018-08-10 17:33:26','system',NULL,0,'1'),(283,'user5','user5','fpdfjj4dle2bs5znim3ih4iycqr5mtzqifs25ha','005','11','11',NULL,001,NULL,NULL,NULL,0,NULL,NULL,'2018-08-10 17:34:09','system','2018-08-10 17:34:09','system',NULL,0,'1'),(284,'user6','user6','fpdfjj4dle2bs5znim3ih4iycqr5mtzqifs25ha','006','11','11',NULL,001,NULL,NULL,NULL,0,NULL,NULL,'2018-08-10 17:35:10','system','2018-08-10 17:35:10','system',NULL,0,'1');

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`role_id`,`user_id`,`level`) values (1,1,1,NULL),(2,253,1,NULL),(3,254,1,NULL),(4,255,1,NULL),(5,285,1,NULL),(6,291,1,NULL),(7,292,1,NULL),(8,293,1,NULL),(9,295,1,NULL),(10,296,1,NULL),(11,297,1,NULL),(12,306,1,NULL),(1953,1,279,NULL),(1954,253,279,NULL),(1955,254,279,NULL),(1956,255,279,NULL),(1957,285,279,NULL),(1958,291,279,NULL),(1959,292,279,NULL),(1960,293,279,NULL),(1961,295,279,NULL),(1962,296,279,NULL),(1963,297,279,NULL),(1964,306,279,NULL),(1965,285,280,NULL),(1966,292,280,NULL),(1967,291,280,NULL),(1968,1,280,NULL),(1969,253,280,NULL),(1970,254,280,NULL),(1971,255,280,NULL),(1972,293,280,NULL),(1973,295,280,NULL),(1974,296,280,NULL),(1975,297,280,NULL),(1976,306,280,NULL),(1977,1,281,NULL),(1978,253,281,NULL),(1979,254,281,NULL),(1980,255,281,NULL),(1981,285,281,NULL),(1982,291,281,NULL),(1983,292,281,NULL),(1984,293,281,NULL),(1985,295,281,NULL),(1986,296,281,NULL),(1987,297,281,NULL),(1988,306,281,NULL),(1989,1,282,NULL),(1990,253,282,NULL),(1991,254,282,NULL),(1992,255,282,NULL),(1993,285,282,NULL),(1994,291,282,NULL),(1995,292,282,NULL),(1996,293,282,NULL),(1997,295,282,NULL),(1998,296,282,NULL),(1999,297,282,NULL),(2000,306,282,NULL),(2001,1,283,NULL),(2002,253,283,NULL),(2003,254,283,NULL),(2004,255,283,NULL),(2005,285,283,NULL),(2006,291,283,NULL),(2007,292,283,NULL),(2008,293,283,NULL),(2009,295,283,NULL),(2010,296,283,NULL),(2011,297,283,NULL),(2012,306,283,NULL),(2013,1,284,NULL),(2014,253,284,NULL),(2015,254,284,NULL),(2016,255,284,NULL),(2017,285,284,NULL),(2018,291,284,NULL),(2019,292,284,NULL),(2020,293,284,NULL),(2021,295,284,NULL),(2022,296,284,NULL),(2023,297,284,NULL),(2024,306,284,NULL);



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
