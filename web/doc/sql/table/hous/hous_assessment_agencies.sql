CREATE TABLE `hous_assessment_agencies` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
	`creator` int(11) DEFAULT NULL COMMENT '创建人',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modifier` int(11) DEFAULT NULL COMMENT '修改者',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	`is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
	`process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
	`project_id` int(11) DEFAULT NULL COMMENT '项目编号',
	`property_id` int(11) DEFAULT NULL COMMENT '房产信息表id',
	`assessment_agencies` tinyint(4) DEFAULT NULL COMMENT '评估机构',
	`institution_name` varchar(128) DEFAULT NULL COMMENT '机构名称',
	`assessed_value` decimal(20,2) DEFAULT NULL COMMENT '评估值',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='评估机构表';