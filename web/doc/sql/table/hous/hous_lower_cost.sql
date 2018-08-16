CREATE TABLE `hous_lower_cost` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
	`creator` int(11) DEFAULT NULL COMMENT '创建人',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modifier` int(11) DEFAULT NULL COMMENT '修改者',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	`is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
	`process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
	`project_id` int(11) DEFAULT NULL COMMENT '项目编号',
	`finance_specialist` varchar(64) DEFAULT NULL COMMENT '财务专员',
	`receivable_account` decimal(20,2) DEFAULT NULL COMMENT '应收下户费',
	`actual_fee` decimal(20,2) DEFAULT NULL COMMENT '实收下户费',
	`collection_form` tinyint(4) DEFAULT NULL COMMENT '收取形式',
	`refund_operator` varchar(64) DEFAULT NULL COMMENT '退费申请操作员',
	`apply_refund_time` datetime DEFAULT NULL COMMENT '申请退费时间',
	`apply_refund_amount` decimal(20,2) DEFAULT NULL COMMENT '申请退费金额(元)',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='下户费表';