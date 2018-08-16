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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='合同附件表';