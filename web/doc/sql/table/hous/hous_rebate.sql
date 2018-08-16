CREATE TABLE `hous_rebate` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
	`creator` int(11) DEFAULT NULL COMMENT '创建人',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modifier` int(11) DEFAULT NULL COMMENT '修改者',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	`is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
	`process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
	`project_id` int(11) DEFAULT NULL COMMENT '项目编号',
	`rebate_points` decimal(20,2) DEFAULT NULL COMMENT '返佣点位',
	`rebate_amount` decimal(20,2) DEFAULT NULL COMMENT '返佣金额',
	`rebate_deadline` int(11) DEFAULT NULL COMMENT '返佣期限',
	`rebate_card` varchar(32) DEFAULT NULL COMMENT '返佣卡号',
	`bank_name` varchar(128) DEFAULT NULL COMMENT '开户行',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='返佣表';