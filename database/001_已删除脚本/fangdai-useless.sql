

#非必要数据。
DROP TABLE IF EXISTS `pl_creditcustomer`;

CREATE TABLE `pl_creditcustomer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `rd_edustate` int(3) DEFAULT NULL COMMENT '教育状况/学历',
  `rd_workstate` int(3) DEFAULT NULL COMMENT '工作状况',
  `rd_certnumber` varchar(30) DEFAULT NULL COMMENT '证件号码',
  `rd_certtype` int(3) DEFAULT NULL COMMENT '证件类型',
  `rd_certvalidtime` datetime DEFAULT NULL COMMENT '证件有效时间',
  `rd_shareroom` int(3) DEFAULT NULL COMMENT '是否共同居住',
  `rd_yearincome` decimal(10,2) DEFAULT NULL COMMENT '年收入',
  `rd_creditvalue` decimal(10,2) DEFAULT NULL COMMENT '信用卡信用额度',
  `rd_phone` varchar(20) DEFAULT NULL COMMENT '住址电话',
  `rd_mobile1` varchar(20) DEFAULT NULL COMMENT '移动电话号码1',
  `rd_xdconsultid` int(11) DEFAULT NULL COMMENT '所属咨询ID',
  `rd_mobile2` varchar(20) DEFAULT NULL COMMENT '移动电话号码2',
  `rd_postalcode` varchar(10) DEFAULT NULL COMMENT '邮政编码',
  `rd_email` varchar(50) DEFAULT NULL COMMENT '电子邮件',
  `rd_qq` varchar(20) DEFAULT NULL COMMENT '腾讯QQ',
  `rd_hjaddress` varchar(255) DEFAULT NULL COMMENT '户籍地址',
  `rd_liveareaid` int(11) DEFAULT NULL COMMENT '所在地区ID',
  `rd_xxjzaddress` varchar(255) DEFAULT NULL COMMENT '详细居住地址',
  `rd_borrowuse` varchar(255) DEFAULT NULL COMMENT '详细借款用途',
  `rd_mrepayamount` decimal(10,2) DEFAULT NULL COMMENT '月还款金额',
  `rd_minamount` decimal(10,2) DEFAULT NULL COMMENT '最低需求金额',
  `rd_realname` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `rd_maxamount` decimal(10,2) DEFAULT NULL COMMENT '最高需求金额',
  `rd_fsupport` int(3) DEFAULT NULL COMMENT '家庭是否支持0请选择1支持2不支持',
  `rd_repaylimit` int(11) DEFAULT NULL COMMENT '还款期限(月)',
  `rd_wcompany` varchar(255) DEFAULT NULL COMMENT '工作单位',
  `rd_waddress` varchar(255) DEFAULT NULL COMMENT '单位地址',
  `rd_wpostalcode` varchar(10) DEFAULT NULL COMMENT '单位邮政编码',
  `rd_wphone` varchar(20) DEFAULT NULL COMMENT '单位电话',
  `rd_wjoindate` datetime DEFAULT NULL COMMENT '入职日期',
  `rd_wdepartment` varchar(100) DEFAULT NULL COMMENT '所在部门',
  `rd_wjob` varchar(255) DEFAULT NULL COMMENT '职务',
  `rd_oldname` varchar(50) DEFAULT NULL COMMENT '曾用名',
  `rd_wcompanytype` int(3) DEFAULT NULL COMMENT '单位类型',
  `rd_remark` varchar(4000) DEFAULT NULL COMMENT '备注',
  `rd_birthday` datetime DEFAULT NULL COMMENT '生日',
  `rd_sex` int(3) DEFAULT NULL COMMENT '性别0---请选择---1女2男',
  `rd_marriagestate` int(3) DEFAULT NULL COMMENT '婚姻状态',
  `rd_housestate` int(3) DEFAULT NULL COMMENT '房产状况',
  `rd_childrenstate` int(3) DEFAULT NULL COMMENT '有无子女 0无，1有',
  `rd_userid` int(3) DEFAULT NULL COMMENT '录入者ID',
  `rd_roleid` int(3) DEFAULT NULL COMMENT '团队角色ID',
  `rd_officeid` char(64) DEFAULT NULL COMMENT '部门ID',
  `rd_curuserid` int(3) DEFAULT NULL COMMENT '当前操作者ID',
  `rd_createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `temporary` tinyint(3) DEFAULT NULL COMMENT '暂住证0无1有',
  `provides` tinyint(3) DEFAULT NULL COMMENT '供养人数',
  `city_years` varchar(4) DEFAULT NULL COMMENT '来本市年份',
  `start_living` date DEFAULT NULL COMMENT '起始居住时间',
  `work_years` tinyint(3) DEFAULT NULL COMMENT '就职年限 如5',
  `unit_nature` tinyint(3) DEFAULT NULL COMMENT '单位性质',
  `private_owner` tinyint(3) DEFAULT NULL COMMENT '私营业主0否 1是',
  `unit_time` date DEFAULT NULL COMMENT '单位成立时间',
  `uuid` varchar(32) DEFAULT NULL COMMENT '用户唯一标识',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `credit_number` tinyint(3) unsigned zerofill DEFAULT '000' COMMENT '信用卡多少张',
  `credit_bank` varchar(32) DEFAULT NULL COMMENT '信用卡最高额银行',
  `credit_overdue` varchar(128) DEFAULT NULL COMMENT '信用卡逾期情况',
  `is_loan` tinyint(3) unsigned zerofill DEFAULT '000' COMMENT '有无贷款 0无 1有',
  `loan_amount` decimal(10,2) unsigned zerofill DEFAULT '00000000.00' COMMENT '贷款金额',
  `loan_bank` varchar(32) DEFAULT NULL COMMENT '贷款银行',
  `loan_overdue` varchar(128) DEFAULT NULL COMMENT '贷款逾期情况',
  `healthy` tinyint(3) DEFAULT NULL COMMENT '健康状况：0-良好，1-一般，2-较差',
  `spouse_iskown` tinyint(3) DEFAULT NULL COMMENT '配偶是否知晓：0-是，1-否',
  `is_have_children` tinyint(3) DEFAULT NULL COMMENT '是否有子女：0-是，1-否',
  `emergency_contact_number` varchar(20) DEFAULT NULL COMMENT '紧急联系电话',
  `is_self_support` tinyint(3) DEFAULT NULL COMMENT '公司是否自营：0-是，1-否',
  `age` tinyint(3) DEFAULT NULL COMMENT '年龄',
  `hk_nature` tinyint(3) DEFAULT NULL COMMENT '户口类型：0-本市户口，1-其他',
  `position_nature` tinyint(3) DEFAULT NULL COMMENT '岗位性质：0-管理人员，1-普通职员，2-个体户企业主，3-其他',
  `source_income` tinyint(3) DEFAULT NULL COMMENT '收入来源：0-工资薪金，1-租金利息，2-利润股息红利，3-其他',
  `month_income` decimal(10,2) DEFAULT NULL COMMENT '月收入',
  `family_support` tinyint(3) DEFAULT NULL COMMENT '家人是否支持：0-支持，1-不知情，2-不支持',
  `quality` tinyint(3) DEFAULT NULL COMMENT '素质：0-良好，1-一般，2-较差',
  `cooperate` tinyint(3) DEFAULT NULL COMMENT '配合程度：0-积极配合，1-相对配合，2-问题较多',
  PRIMARY KEY (`id`),
  UNIQUE KEY `consultIdIndex` (`rd_xdconsultid`) USING BTREE,
  KEY `rd_mobile1` (`rd_mobile1`) USING BTREE,
  KEY `idIndex` (`id`) USING BTREE,
  KEY `UUID_index` (`uuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

/*Data for the table `pl_creditcustomer` */
#非必要数据。
DROP TABLE IF EXISTS `pl_pprofile`;

CREATE TABLE `pl_pprofile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rd_parent_id` int(11) DEFAULT NULL,
  `rd_productid` int(11) DEFAULT NULL COMMENT '所属产品id',
  `rd_ptype` char(32) DEFAULT NULL COMMENT '所属业务类型',
  `rd_isrequired` int(3) DEFAULT NULL COMMENT '必填项',
  `rd_remark` varchar(8000) DEFAULT NULL COMMENT '资料要求备注',
  `rd_remark2` varchar(8000) DEFAULT NULL COMMENT '资料要求备注',
  `rd_sort` int(11) DEFAULT NULL,
  `disable` tinyint(1) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rd_ptype` (`rd_ptype`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

/*Data for the table `pl_pprofile` */
#非必要数据。
/*Table structure for table `product_param` */

DROP TABLE IF EXISTS `product_param`;

CREATE TABLE `product_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id,自增字段',
  `name` varchar(100) DEFAULT '' COMMENT '产品名',
  `product_type` int(3) DEFAULT '0' COMMENT '产品类型id',
  `ptype` int(3) DEFAULT NULL COMMENT '担保类型1信贷  2抵押-车贷',
  `repayment_type` int(3) DEFAULT '0' COMMENT '还款方式',
  `delay` int(2) DEFAULT '0' COMMENT '是否展期(0为可以,1为否)',
  `ahead_repay_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '提前还款罚息费率',
  `overdue_day_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '逾期罚息%日',
  `bottom_month_rate` decimal(10,4) DEFAULT NULL COMMENT '底点利率',
  `office_ids` varchar(2000) DEFAULT NULL COMMENT '适用机构',
  `creator` varchar(100) DEFAULT '' COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(100) DEFAULT '' COMMENT '修改者',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` int(3) DEFAULT '0' COMMENT '记录是否删除 0正常 -1已删除记录',
  `remark` varchar(2000) DEFAULT '' COMMENT '备注',
  `is_run` int(11) DEFAULT '0' COMMENT '是否已有业务运行（0无，1有）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='产品参数表';

/*Data for the table `product_param` */

#非必要数据。
DROP TABLE IF EXISTS `pub_add_reportforms`;

CREATE TABLE `pub_add_reportforms` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `classify_type` int(3) DEFAULT NULL COMMENT '大类 分类类型 1-客户类型 2-产品类型 3-贷款期限 4-性别 5-户籍类型 6-教育程度 7-房屋类型',
  `detail_type` int(3) DEFAULT NULL COMMENT '小类 详细类型(大类基础上) 客户类型：1-新客户 2-老客户   产品类型：1-GPS类 2-移交类 3-其他类  贷款期限：1-1期 2-3期 3-6期 4-9期 5-12期 6-24期 7-36期 8-其他  用户性别：1-男 2-女 3-其他  户籍类型：1-本地 2-外地 3-其他  教育程度：1-高中及以下 2-大专 3-本科 4-硕士及以上 5-其他  房屋类型：1-自置无抵押 2-自置已抵押 3-与父母同住 4-租房及其他',
  `account` int(11) DEFAULT NULL COMMENT '数量',
  `account_percent` decimal(10,4) DEFAULT NULL COMMENT '数量所占百分比(%)',
  `amount` decimal(18,2) DEFAULT '0.00',
  `amount_percent` decimal(10,4) DEFAULT NULL COMMENT '贷款总额所占百分比(%)',
  `year` int(4) DEFAULT NULL COMMENT '年份',
  `month` int(2) DEFAULT NULL COMMENT '月份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='查询统计管理模块 新增统计';

/*Data for the table `pub_add_reportforms` */

/*Table structure for table `pub_alarmsetting` */
#非必要数据。
DROP TABLE IF EXISTS `pub_alarmsetting`;

CREATE TABLE `pub_alarmsetting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `early_warning_category` tinyint(4) NOT NULL DEFAULT '1' COMMENT '预警类别(1-到期 2-逾期...)',
  `early_warning_content` varchar(32) NOT NULL DEFAULT '1' COMMENT '预警内容(还有xx天到期 到期还款 逾期xx天...)',
  `early_warning_period` tinyint(4) DEFAULT NULL COMMENT '预警周期(1每日日终 2即时)',
  `risk_level` tinyint(5) NOT NULL DEFAULT '1' COMMENT '风险等级(1正常2关注3次级4可疑5损失)',
  `processing_mode` tinyint(4) DEFAULT NULL COMMENT '处理方式(1短信提醒 2邮件提醒 3拒贷-自动)',
  `inform` tinyint(4) DEFAULT NULL COMMENT '通知给(1客户 2相关业务人员)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` int(3) DEFAULT '0',
  `customerId` int(11) DEFAULT NULL,
  `days` int(2) DEFAULT NULL,
  `level` tinyint(4) NOT NULL DEFAULT '1' COMMENT '等级(1.级特大风险  2.级重大风险  3.级较大风险 4.一般风险 5.低风险)',
  `is_enabled` tinyint(4) NOT NULL COMMENT '是否启用(0-启用 1-停用)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pub_alarmsetting` */

/*Table structure for table `pub_blackcustomer` */
#非必要数据。
DROP TABLE IF EXISTS `pub_blackcustomer`;

CREATE TABLE `pub_blackcustomer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户编号ID',
  `customer_name` varchar(50) DEFAULT NULL COMMENT '客户名称',
  `cert_type` tinyint(3) DEFAULT NULL COMMENT '证件类型',
  `cert_number` varchar(30) DEFAULT NULL COMMENT '证件号码',
  `is_system_enter` tinyint(3) DEFAULT NULL COMMENT '是否本系统录入0否 1是',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `creator` varchar(32) NOT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '是否删除 0默认 1删除状态',
  PRIMARY KEY (`id`),
  KEY `certType+certNumber` (`cert_type`,`cert_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pub_blackcustomer` */

/*Table structure for table `pub_notice` */
#非必要数据。
DROP TABLE IF EXISTS `pub_notice`;

CREATE TABLE `pub_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表主键',
  `mobile1` varchar(32) DEFAULT NULL COMMENT '手机号码1',
  `mobile2` varchar(32) DEFAULT NULL COMMENT '手机号码2',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `real_name` varchar(32) DEFAULT NULL COMMENT '用户真实姓名',
  `is_post` tinyint(3) unsigned zerofill DEFAULT NULL COMMENT '是否发送 0未发送 1已发送',
  `repayment_time` datetime DEFAULT NULL COMMENT '还款时间',
  `period` int(11) DEFAULT NULL COMMENT '还款期次',
  `account` decimal(18,2) DEFAULT '0.00',
  `repayment_type` tinyint(3) DEFAULT NULL COMMENT '还款方式',
  `notice_type` tinyint(3) unsigned zerofill DEFAULT NULL COMMENT '通知类型0邮件,1短信',
  `content` varchar(512) DEFAULT NULL COMMENT '邮件内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pub_notice` */

/*Table structure for table `sys_car_config` */
#非必要数据。
DROP TABLE IF EXISTS `sys_car_config`;

CREATE TABLE `sys_car_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(3) DEFAULT '0' COMMENT '是否删除0不删除 1删除',
  `office_id` varchar(64) NOT NULL COMMENT '营业厅ID',
  `office_name` varchar(32) DEFAULT NULL COMMENT '营业厅名称',
  `parking_fee` decimal(18,2) DEFAULT '0.00',
  `gps_install_fee` decimal(18,2) DEFAULT '0.00',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_car_config` */

/*Table structure for table `sys_file_template` */
#非必要数据。
DROP TABLE IF EXISTS `sys_file_template`;

CREATE TABLE `sys_file_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '模板名称',
  `type` int(11) NOT NULL COMMENT '模板类型',
  `upload_time` datetime DEFAULT NULL COMMENT '上传时间',
  `url` varchar(256) DEFAULT NULL COMMENT '文件相对路经',
  `is_delete` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '0未删除   -1已删除',
  `remark` varchar(128) DEFAULT NULL COMMENT '说明，可为空',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `sys_file_template` */

/*Table structure for table `sys_holiday` */
#非必要数据。
DROP TABLE IF EXISTS `sys_holiday`;

CREATE TABLE `sys_holiday` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `year` int(4) DEFAULT '0' COMMENT '年份',
  `holiday_date` date DEFAULT NULL COMMENT '节假日期',
  `description` varchar(256) DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_holiday` */


/*Table structure for table `sys_msg_config` */
#非必要数据。
DROP TABLE IF EXISTS `sys_msg_config`;

CREATE TABLE `sys_msg_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `msg_module_id` int(11) DEFAULT NULL COMMENT '通知模块ID',
  `msg_template_id` int(11) DEFAULT NULL COMMENT '通知模板ID',
  `is_use` int(11) DEFAULT '1' COMMENT '是否启用:1：启用，2：禁用',
  `send_type` tinyint(4) DEFAULT '0' COMMENT '发放方式:0自动，2手动',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_msg_config` */

/*Table structure for table `sys_msg_instation` */

DROP TABLE IF EXISTS `sys_msg_instation`;

CREATE TABLE `sys_msg_instation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(128) NOT NULL COMMENT '标题',
  `content` varchar(512) NOT NULL COMMENT '内容',
  `send_id` int(11) NOT NULL COMMENT '发送人',
  `receive_id` int(11) NOT NULL COMMENT '接收人',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `look_time` datetime DEFAULT NULL COMMENT '查看时间',
  `is_read` tinyint(3) unsigned zerofill DEFAULT NULL COMMENT '是否已读 0未读；1已读',
  `is_delete` tinyint(3) unsigned zerofill DEFAULT NULL COMMENT '是否删除 0正常;-1删除',
  PRIMARY KEY (`id`),
  KEY `receive_id_index` (`receive_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_msg_instation` */

/*Table structure for table `sys_msg_module` */

DROP TABLE IF EXISTS `sys_msg_module`;

CREATE TABLE `sys_msg_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `nid` varchar(128) DEFAULT NULL COMMENT '模块编码',
  `name` varchar(128) DEFAULT '' COMMENT '模块名',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_msg_module` */

/*Table structure for table `sys_msg_result` */

DROP TABLE IF EXISTS `sys_msg_result`;

CREATE TABLE `sys_msg_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) DEFAULT '1' COMMENT '信息类型:1邮件，2短信',
  `send_user` int(11) DEFAULT NULL COMMENT '发送人',
  `receive_user` int(11) DEFAULT NULL COMMENT '接收人',
  `title` varchar(128) DEFAULT '' COMMENT '标题',
  `content` varchar(2048) DEFAULT '' COMMENT '内容',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `send_result` varchar(128) DEFAULT '' COMMENT '发送结果',
  `status` tinyint(4) DEFAULT '1' COMMENT '发送状态:0失败,1成功',
  `nid` varchar(128) DEFAULT '',
  `receive_addr` varchar(128) DEFAULT '' COMMENT '接受地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_msg_result` */

/*Table structure for table `sys_msg_template` */

DROP TABLE IF EXISTS `sys_msg_template`;

CREATE TABLE `sys_msg_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(128) DEFAULT '' COMMENT '模板名',
  `type` tinyint(4) DEFAULT '1' COMMENT '模板类型:1：短信，2：邮件',
  `title` varchar(128) DEFAULT '' COMMENT '标题',
  `content` varchar(2048) DEFAULT '' COMMENT '模板内容',
  `is_use` tinyint(4) DEFAULT '1' COMMENT '是否启用::0禁用，1启用',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `sys_msg_template` */

/*Table structure for table `sys_no_rules` */
#非必要数据。
DROP TABLE IF EXISTS `sys_no_rules`;

CREATE TABLE `sys_no_rules` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `s_name` varchar(32) NOT NULL COMMENT '编号名称',
  `s_code` varchar(32) DEFAULT NULL COMMENT '编号代码',
  `s_length` tinyint(3) unsigned zerofill DEFAULT '000' COMMENT '字符长度',
  `s_content` varchar(128) DEFAULT NULL COMMENT '字符含义',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` varchar(32) DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除0正常-1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

/*Data for the table `sys_no_rules` */


/*Table structure for table `sys_notice` */
#非必要数据。
DROP TABLE IF EXISTS `sys_notice`;

CREATE TABLE `sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `add_id` int(11) DEFAULT NULL COMMENT '创建者',
  `office_id` varchar(1024) DEFAULT NULL COMMENT '部门ID',
  `company_id` char(64) DEFAULT NULL COMMENT '公司ID',
  `is_delete` tinyint(3) unsigned zerofill DEFAULT '000' COMMENT '是否删除 0正常;-1删除',
  `is_send` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '是否发布0未发 1已发',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_notice` */
