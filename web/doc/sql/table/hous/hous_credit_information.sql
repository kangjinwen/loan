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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='征信信息表(面审)';