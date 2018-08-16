CREATE TABLE `hous_quick_information` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
	`creator` int(11) DEFAULT NULL COMMENT '创建人',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modifier` int(11) DEFAULT NULL COMMENT '修改者',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	`is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
	`process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
	`project_id` int(11) DEFAULT NULL COMMENT '项目编号',
	`school` tinyint(4) DEFAULT NULL COMMENT '学校',
	`hospital` tinyint(4) DEFAULT NULL COMMENT '医院',
	`shopping` tinyint(4) DEFAULT NULL COMMENT '购物等配套情况',
	`bank_prepayment` tinyint(4) DEFAULT NULL COMMENT '银行可否代为提前还款',
	`housing_value_faster` decimal(20,2) DEFAULT NULL COMMENT '房屋快出值(元)',
	`property_taxes` decimal(20,2) DEFAULT NULL COMMENT '房产交易产生的税费(元)',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='房屋快出值信息表(权证下户)';