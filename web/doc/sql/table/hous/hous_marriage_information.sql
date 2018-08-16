CREATE TABLE `hous_marriage_information` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
	`creator` int(11) DEFAULT NULL COMMENT '创建人',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modifier` int(11) DEFAULT NULL COMMENT '修改者',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	`is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
	`process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
	`project_id` int(11) DEFAULT NULL COMMENT '项目编号',
	`marital_status` tinyint(4) DEFAULT NULL COMMENT '婚姻状况',
	`documents_time` datetime DEFAULT NULL COMMENT '证件日期',
	`divorce_agreement` tinyint(4) DEFAULT NULL COMMENT '离婚协议',
	`court_verdict` tinyint(4) DEFAULT NULL COMMENT '法院判决',
	`room_time` datetime DEFAULT NULL COMMENT '房本日期',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='婚姻信息表(面审)';