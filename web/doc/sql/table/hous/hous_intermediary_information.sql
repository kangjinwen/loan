CREATE TABLE `hous_intermediary_information` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
	`creator` int(11) DEFAULT NULL COMMENT '创建人',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modifier` int(11) DEFAULT NULL COMMENT '修改者',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	`is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
	`process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
	`project_id` int(11) DEFAULT NULL COMMENT '项目编号',
	`door_name` varchar(128) DEFAULT NULL COMMENT '门名名称',
	`principal_name` varchar(64) DEFAULT NULL COMMENT '负责人姓名',
	`principal_phone` varchar(20) DEFAULT NULL COMMENT '负责人联系方式',
	`tax_details` varchar(128) DEFAULT NULL COMMENT '税款详情',
	`normal_price` decimal(20,2) DEFAULT NULL COMMENT '正常价格(万)',
	`fast_transaction_price` decimal(20,2) DEFAULT NULL COMMENT '快速成交价格(万)',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='房屋中介信息(权证下户)';