CREATE TABLE `hous_notarization_registration` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号id',
	`creator` int(11) DEFAULT NULL COMMENT '创建人',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	`modifier` int(11) DEFAULT NULL COMMENT '修改者',
	`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	`is_delete` tinyint(4) unsigned zerofill DEFAULT NULL COMMENT '0不删除1已删除',
	`process_instance_id` varchar(64) DEFAULT NULL COMMENT '流程ID',
	`project_id` int(11) DEFAULT NULL COMMENT '项目编号',
	`lender` varchar(64) DEFAULT NULL COMMENT '出借人',
	`trustee` varchar(64) DEFAULT NULL COMMENT '受托人',
	`trustee_of_lender` varchar(64) DEFAULT NULL COMMENT '出借人的受托人',
	`remark` varchar(255) DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='公证登记表';