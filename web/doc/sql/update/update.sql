/*2016-8-31 lyc*/
alter table pl_feeinfo add `ptype` tinyint(3) DEFAULT NULL COMMENT '1信贷  2车贷 3房贷';
alter table pl_feeinfo add `overdue_penalty_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '逾期罚息%日';
alter table pl_feeinfo add `ahead_repay_rate` decimal(10,4) DEFAULT '0.0000' COMMENT '提前还款罚息费率'
alter table pl_feeinfo add `product_type` tinyint(3) DEFAULT '0' COMMENT '产品类型';
alter table pl_feeinfo add `single_rate` decimal(10,4) DEFAULT NULL COMMENT '成单利率';

/*2016-8-31 fzc*/
ALTER TABLE hous_loanfees ADD sum_account decimal(20,2) DEFAULT NULL COMMENT '打款总额';
ALTER TABLE pub_repayloaninfo ADD end_repayTime datetime DEFAULT NULL COMMENT '还款结束时间';
ALTER TABLE pl_borrow ADD single_rate decimal(10,4) DEFAULT NULL COMMENT '成单利率';

/*2016-9-8*/
ALTER TABLE pl_product change `delay` `delay` tinyint(2) DEFAULT '0' COMMENT '是否展期(0为可以,1为否)';

/*2016-9-21*/
ALTER TABLE pl_consult ADD fee_success tinyint(4)  COMMENT '1可退费 2退费申请';
ALTER TABLE pl_consult ADD advance_apply tinyint(4)  COMMENT '1可垫资 2垫资申请';
ALTER TABLE hous_lower_cost ADD hous_lower_cost tinyint(4) DEFAULT '0' COMMENT '退费是否成功,0不成功,1成功';

ALTER TABLE pub_process_branching ADD extension_amount decimal(20,2) DEFAULT '0.000000' COMMENT '展期金额';
ALTER TABLE pub_process_branching ADD extension_fee decimal(20,2) DEFAULT '0.000000' COMMENT '展期费';
ALTER TABLE pub_process_branching ADD extension_rate decimal(10,4) DEFAULT '0.000000' COMMENT '展期利率';
ALTER TABLE pub_process_branching ADD extension_returnfee_rate decimal(10,4) DEFAULT '0.000000' COMMENT '展期返佣点费';

ALTER TABLE hous_intermediary_information ADD remark varchar(255) DEFAULT NULL COMMENT '备注';
ALTER TABLE hous_intermediary_information ADD school tinyint(4) DEFAULT NULL COMMENT '学校';
ALTER TABLE hous_intermediary_information ADD hospital tinyint(4) DEFAULT NULL COMMENT '医院';
ALTER TABLE hous_intermediary_information ADD shopping tinyint(4) DEFAULT NULL COMMENT '购物等配套情况';
ALTER TABLE hous_intermediary_information ADD housing_value_faster decimal(20,2) DEFAULT '0.000000' COMMENT '房屋快出值(元)';

ALTER TABLE hous_quick_information ADD balance_payment decimal(20,2) DEFAULT '0.000000' COMMENT '尾款';

ALTER TABLE hous_mortgage_registration ADD third_card_number varchar(32) DEFAULT NULL COMMENT '第三方卡号';
ALTER TABLE hous_mortgage_registration ADD third_bank_account varchar(32) DEFAULT NULL COMMENT '第三方开户名';
ALTER TABLE hous_mortgage_registration ADD third_account_opening varchar(32) DEFAULT NULL COMMENT '第三方开户行';

ALTER TABLE hous_bills ADD third_card_number varchar(32) DEFAULT NULL COMMENT '读取抵押登记第三方卡号';
ALTER TABLE hous_bills ADD third_bank_account varchar(32) DEFAULT NULL COMMENT '读取抵押登记第三方开户名';
ALTER TABLE hous_bills ADD third_account_opening varchar(32) DEFAULT NULL COMMENT '读取抵押登记第三方开户行';
ALTER TABLE hous_bills ADD third_account decimal(20,2) DEFAULT '0.000000' COMMENT '第三方金额';
ALTER TABLE hous_bills ADD purpose varchar(64) DEFAULT NULL COMMENT '用途';

ALTER TABLE hous_property_information ADD planning_purposes tinyint(4)  COMMENT '规划用途';
ALTER TABLE hous_control_information MODIFY arrived_nature VARCHAR(64);

ALTER TABLE hous_borrowing_basics ADD father_name varchar(64) DEFAULT NULL COMMENT '贷款人父亲姓名';
ALTER TABLE hous_borrowing_basics ADD mother_name varchar(64) DEFAULT NULL COMMENT '贷款人母亲姓名';
ALTER TABLE hous_borrowing_basics ADD father_number varchar(64) DEFAULT NULL COMMENT '贷款人父亲身份证号';
ALTER TABLE hous_borrowing_basics ADD mother_number varchar(64) DEFAULT NULL COMMENT '贷款人母亲身份证号';

ALTER TABLE hous_control_information ADD two_arrived_nature varchar(64) DEFAULT NULL  COMMENT '二抵性质';
ALTER TABLE hous_control_information ADD two_against_bank_name varchar(64) DEFAULT NULL   COMMENT '二抵银行名称';
ALTER TABLE hous_control_information ADD two_arrived_amount decimal(20,2) DEFAULT '0.000000'   COMMENT '二抵金额';
ALTER TABLE hous_control_information ADD two_arrived_rates decimal(10,4) DEFAULT '0.000000'   COMMENT '二抵利率';
ALTER TABLE hous_control_information ADD three_arrived_nature varchar(64) DEFAULT NULL  COMMENT '三抵性质';
ALTER TABLE hous_control_information ADD three_against_bank_name varchar(64) DEFAULT NULL   COMMENT '三抵银行名称';
ALTER TABLE hous_control_information ADD three_arrived_amount decimal(20,2) DEFAULT '0.000000'   COMMENT '三抵金额';
ALTER TABLE hous_control_information ADD three_arrived_rates decimal(10,4) DEFAULT '0.000000'   COMMENT '三抵利率';

ALTER TABLE hous_loanfees ADD collection_service_fee decimal(20,2) DEFAULT '0.000000'   COMMENT '代收服务费金额';
ALTER TABLE hous_loanfees ADD return_service_fee decimal(20,2) DEFAULT '0.000000'   COMMENT '返还服务费金额';
ALTER TABLE hous_loanfees ADD collection_service_name varchar(64) DEFAULT NULL COMMENT '代收服务费姓名';
ALTER TABLE hous_loanfees ADD collection_service_card varchar(64) DEFAULT NULL COMMENT '代收服务费卡号';
ALTER TABLE hous_loanfees ADD collection_service_bank varchar(64) DEFAULT NULL COMMENT '代收服务费开户行';

/*2016-9-29 jdm 修改利率精度 */
alter table pl_borrow MODIFY `single_rate` decimal(10,4) DEFAULT NULL COMMENT '成单利率';
alter table pl_borrow_requirement MODIFY `single_rate` decimal(10,4) DEFAULT NULL COMMENT '成单利率';
alter table pl_feeinfo MODIFY `single_rate` decimal(10,4) DEFAULT NULL COMMENT '成单利率';

/*2016-10-09 jdm 更新金额精度 */
alter table pl_borrow modify repayment_yes_account decimal(18,2) default 0.00;
alter table pl_borrow modify repayment_yes_interest decimal(18,2) default 0.00;
alter table pl_borrow modify account decimal(18,2) default 0.00;
alter table pl_borrow_requirement modify account decimal(18,2) default 0.00;
alter table pl_contract modify contract_account decimal(18,2) default 0.00;
alter table pl_contract modify service_fee decimal(18,2) default 0.00;
alter table pl_product modify maxmlimit decimal(18,2) default 0.00;
alter table pl_product modify tminmlimit decimal(18,2) default 0.00;
alter table pl_product modify tmaxmlimit decimal(18,2) default 0.00;
alter table pl_product modify minmlimit decimal(18,2) default 0.00;
alter table pub_add_reportforms modify amount decimal(18,2) default 0.00;
alter table pub_notice modify account decimal(18,2) default 0.00;
alter table pub_process_branching modify other_amount decimal(18,2) default 0.00;
alter table pub_repaymentdetail modify violations_penalty decimal(18,2) default 0.00;
alter table pub_repaymentdetail modify violations_truck_fee decimal(18,2) default 0.00;
alter table pub_repaymentdetail modify violations_travel_fee decimal(18,2) default 0.00;
alter table pub_repaymentdetail modify violations_other_fee decimal(18,2) default 0.00;
alter table pub_repaymentdetail modify violations_total_amount decimal(18,2) default 0.00;
alter table pub_repaymentdetail modify reduction_penalty decimal(18,2) default 0.00;
alter table pub_repaymentdetail modify breach_contract decimal(18,2) default 0.00;
alter table pub_repaymentdetail modify all_breaks decimal(18,2) default 0.00;
alter table sys_car_config modify parking_fee decimal(18,2) default 0.00;
alter table sys_car_config modify gps_install_fee decimal(18,2) default 0.00;
alter table hous_loanfees modify first_interest decimal(18,2) default 0.00;

ALTER TABLE pub_loan MODIFY `bank_flag` smallint(4) DEFAULT NULL COMMENT '放款银行';
ALTER TABLE pub_loan MODIFY `bank_account` varchar(32) DEFAULT NULL COMMENT '放款银行账号';

ALTER TABLE pub_loan ADD bank_name varchar(255) DEFAULT NULL COMMENT '开户行';
ALTER TABLE hous_loanfees ADD return_bank_name varchar(255) DEFAULT NULL COMMENT '返费开户行';
ALTER TABLE hous_loanfees ADD service_bank_name varchar(255) DEFAULT NULL COMMENT '开户行';

/*======== 20161011 附件打包下载数据字典Start  ==========*/
delete from sys_dict_detail where item_code in('WHTHIN','JIEPING','CREDIT','queryOrg1','queryOrg4','HUIZHIDAN','MortgageReceipt','CardNumber','BankAccount','MATERIAL','JUSTICE');

/*初审*/
delete from sys_dict_detail where item_code in('HOUSE','evaOrg1','evaOrg2','evaOrg3');
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'HOUSE','房本',(select id from sys_dict where type_code = 'FIRST_INSTANCE_FILE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'evaOrg1','评估值',(select id from sys_dict where type_code = 'FIRST_INSTANCE_FILE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'evaOrg2','评估值',(select id from sys_dict where type_code = 'FIRST_INSTANCE_FILE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'evaOrg3','评估值',(select id from sys_dict where type_code = 'FIRST_INSTANCE_FILE'));

insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'WHTHIN','查档说明',(select id from sys_dict where type_code = 'FIRST_INSTANCE_FILE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'CREDIT','征信材料',(select id from sys_dict where type_code = 'FIRST_INSTANCE_FILE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'queryOrg1','建委',(select id from sys_dict where type_code = 'FIRST_INSTANCE_FILE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'queryOrg4','安融',(select id from sys_dict where type_code = 'FIRST_INSTANCE_FILE'));

/*复审*/
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'JIEPING','定位截屏',(select id from sys_dict where type_code = 'AGAIN_INSTANCE_FILE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'HUIZHIDAN','公证回执单',(select id from sys_dict where type_code = 'AGAIN_INSTANCE_FILE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'MortgageReceipt','抵押回执单',(select id from sys_dict where type_code = 'AGAIN_INSTANCE_FILE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'CardNumber','银行卡',(select id from sys_dict where type_code = 'AGAIN_INSTANCE_FILE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'BankAccount','开户行信息',(select id from sys_dict where type_code = 'AGAIN_INSTANCE_FILE'));

/*贷后*/
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'MATERIAL','他像权利证',(select id from sys_dict where type_code = 'AFTER_LOAN_FILE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'JUSTICE','借款公证',(select id from sys_dict where type_code = 'AFTER_LOAN_FILE'));
/*======== 20161011 附件打包下载数据字典End  ==========*/

/* 20161012 放款单据填写*/
ALTER TABLE hous_bills ADD remark varchar(2048) DEFAULT NULL COMMENT '备注';


/* 20161020 还款处理数据字典 */
delete from sys_dict where type_code = 'REPAYMENT_PROCESS';
insert into sys_dict(id,type_code,type_name,sort,remark) VALUES (null,	'REPAYMENT_PROCESS',	'还款处理'	,1,	'还款处理（0正常 1提前 2减免）');


delete from sys_dict_detail where parent_id = (select id from sys_dict where type_code='REPAYMENT_PROCESS');
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,0,'正常还款',(select id from sys_dict where type_code='REPAYMENT_PROCESS'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,1,'提前还款',(select id from sys_dict where type_code='REPAYMENT_PROCESS'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,2,'罚息减免',(select id from sys_dict where type_code='REPAYMENT_PROCESS'));

/* 20161025 */
update sys_dict_detail set item_value = '公证登记' where id = (select id from (select id from sys_dict_detail where item_code = 'usertask-notarizeHandle') temp) ;

UPDATE sys_dict_detail SET item_value='下户费管理' WHERE parent_id=(select id from sys_dict where type_code='WORKFLOW_STATE') AND item_code='usertask-collectAssessmentFee';
UPDATE sys_dict_detail SET item_value='放款单据填写' WHERE parent_id=(select id from sys_dict where type_code='WORKFLOW_STATE') AND item_code='usertask-writeLoanInfo';

/*2016-10-28 */
ALTER TABLE pub_loanprocess ADD next_assignee varchar(255) DEFAULT NULL COMMENT '下一步审批人';
alter table hous_orito_information MODIFY housing_orientation VARCHAR(64);
ALTER TABLE hous_data_list ADD two_or_five tinyint(4)  COMMENT '满二或满五';

/*2016-10-29 */
ALTER TABLE hous_mortgage_registration ADD lenders_mortgage varchar(64) DEFAULT NULL COMMENT '出借人';

ALTER TABLE `sys_user`
ADD COLUMN `is_receive_order`  varchar(4) NULL DEFAULT 1 COMMENT '是否接单(1.接单 2.不接单)' AFTER `is_delete`;
update sys_user set is_receive_order=1;

/* 20161029删除计算公式配置-打款金额类别 */
delete from fel_type where chinese_name = '打款金额';
delete from fel_param where chinese_name = '打款金额';

/* 20161031 */
/* 所有角色分配接单设置  */
insert into sys_role_menu(role_id,menu_id) select id,11132 from sys_role where id not in(1);

/*2016-11-4展期费管理增加字段*/
ALTER TABLE pub_process_branching ADD finance_specialist varchar(64) DEFAULT NULL COMMENT '展期费收取财务专员';
ALTER TABLE pub_process_branching ADD collection_form varchar(64) DEFAULT NULL COMMENT '收取形式:(0:现金 1：转账)';
ALTER TABLE pub_process_branching ADD actual_extension_fee decimal(20,2) DEFAULT '0.000000'   COMMENT '实收展期费';

/*2016-11-8展期划扣增加字段*/
ALTER TABLE pub_process_branching ADD deduct_time datetime DEFAULT NULL COMMENT '划扣时间';
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'deductMoneySuccess','划款成功',(select id from sys_dict where type_code='OPERATION_TYPE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'deducted','已放款',281);
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'approvedApply','同意申请',(select id from sys_dict where type_code='OPERATION_TYPE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'refuseApply','拒绝申请',(select id from sys_dict where type_code='OPERATION_TYPE'));

/*2016-11-14新增申请增加字段*/
ALTER TABLE hous_property_information ADD remark_one varchar(64) DEFAULT NULL COMMENT '一抵备注';
ALTER TABLE hous_property_information ADD remark_two varchar(64) DEFAULT NULL COMMENT '二抵备注';
ALTER TABLE hous_borrowing_basics ADD sex tinyint(4)  COMMENT '贷款人性别';
ALTER TABLE hous_borrowing_basics ADD age varchar(64) DEFAULT NULL COMMENT '贷款人年龄';
ALTER TABLE hous_lower_cost ADD pay_person varchar(64) DEFAULT NULL COMMENT '缴纳人';

/*2016-11-17权证下户增加字段*/
alter table hous_orito_information MODIFY living_people VARCHAR(32);
ALTER TABLE hous_orito_information ADD remark varchar(64) DEFAULT NULL COMMENT '备注';
ALTER TABLE hous_quick_information ADD highest_mortgage tinyint(4)  COMMENT '是否为最高额抵押贷款';

ALTER TABLE pl_borrow_requirement ADD collection_service_fee decimal(20,2) DEFAULT '0.000000'   COMMENT '代收服务费金额';
ALTER TABLE pl_borrow_requirement ADD collection_rate decimal(10,4) DEFAULT '0.000000'   COMMENT '代收服务费利率';
ALTER TABLE pl_borrow_requirement ADD collection_service_name varchar(64) DEFAULT NULL COMMENT '代收服务费姓名';
ALTER TABLE pl_borrow_requirement ADD collection_service_card varchar(64) DEFAULT NULL COMMENT '代收服务费卡号';
ALTER TABLE pl_borrow_requirement ADD collection_service_bank varchar(64) DEFAULT NULL COMMENT '代收服务费开户行';

/*2016-11-17增加补充信息筛查节点*/
INSERT INTO sys_dict_detail(item_code,item_value,parent_id)values('usertask-faceaudit-investigate','面审补充信息筛查',(select id from sys_dict where type_code='WORKFLOW_STATE'));
INSERT INTO sys_dict_detail(item_code,item_value,parent_id)values('usertask-supplyInvestigate','补充信息筛查',(select id from sys_dict where type_code='WORKFLOW_STATE'));
INSERT INTO sys_dict_detail(item_code,item_value,parent_id)values('usertask-faceConfirm-investigate','面审前确认补充信息筛查',(select id from sys_dict where type_code='WORKFLOW_STATE'));
insert into sys_dict_detail (item_code,item_value,parent_id)values('faceAuditInvestigate','面审补充信息筛查',(select id from sys_dict where type_code='OPERATION_TYPE'));
insert into sys_dict_detail (item_code,item_value,parent_id)values('supplyPass','补充完成',(select id from sys_dict where type_code='OPERATION_TYPE'));
ALTER TABLE `pub_loanprocess`
ADD COLUMN `next_assignee_name`  varchar(255) NULL COMMENT '下步任务执行人真实姓名' AFTER `next_assignee`;

/* 2016-11-19 修改利率精度 */
alter table hous_advance_apply modify advance_rate_amount decimal(10,4) default 0.00;
alter table hous_control_information modify arrived_rates decimal(10,4) default 0.00;
alter table hous_control_information modify two_arrived_rates decimal(10,4) default 0.00;
alter table hous_control_information modify three_arrived_rates decimal(10,4) default 0.00;
alter table hous_loanfees modify return_rate decimal(10,4) default 0.00;
alter table pl_borrow_requirement modify collection_rate decimal(10,4) default 0.00;
alter table pub_process_branching modify extension_rate decimal(10,4) default 0.00;
alter table pub_process_branching modify extension_returnfee_rate decimal(10,4) default 0.00;
/* 2016-11-21 修改返佣点位精度 */
alter table hous_rebate modify rebate_points decimal(10,4) default 0.00;

/* 2016-11-22 下户任务分配核行审批意见 */
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'needCheckBank','需要核行',(select id from sys_dict where type_code='OPERATION_TYPE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'notCheckBank','不需要核行',(select id from sys_dict where type_code='OPERATION_TYPE'));
INSERT INTO sys_dict_detail(item_code,item_value,parent_id)values('usertask-houseCheckBank','权证核行',(select id from sys_dict where type_code='WORKFLOW_STATE'));
INSERT INTO sys_dict_detail(item_code,item_value,parent_id)values('usertask-householdInvestigateTwo','权证下户',(select id from sys_dict where type_code='WORKFLOW_STATE'));

/* 2016-11-22 核行增加调查员和调查时间 */
ALTER TABLE hous_quick_information ADD investigator varchar(64) DEFAULT NULL COMMENT '调查员';
ALTER TABLE hous_quick_information ADD survey_time datetime DEFAULT NULL COMMENT '调查日期';

/* 2016-11-23 数据字典流程状态增加拒代 */
delete from sys_dict_detail where item_code = 'rejected' and parent_id = (select id from sys_dict where type_code='WORKFLOW_STATE');
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'rejected','拒贷',(select id from sys_dict where type_code='WORKFLOW_STATE'));

/* 2016-11-24 驳回到核行审批意见 */
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'backCheckBank','驳回到核行',(select id from sys_dict where type_code='OPERATION_TYPE'));


INSERT INTO sys_dict_detail(item_code,item_value,parent_id)values('usertask-houseCheckBankTwo','权证核行',(select id from sys_dict where type_code='WORKFLOW_STATE'));

/* 2016-11-28 打包下载所有附件*/
insert into sys_dict(id,type_code,type_name,sort,remark) VALUES (null,	'FILE_DOWNLOAD',	'附件下载'	,56,	'所有附件下载');

insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'IDCARD','身份证',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'HOUSEHOLD','户口本',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'MARRIAGE','婚姻证明',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'CREDIT','征信资料',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'queryOrg1','建委',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'queryOrg2','人法',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'queryOrg3','同盾',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'queryOrg4','安融',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'queryOrg5','工商网',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'SPOUSE','配偶',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'WEREBORROWED','共借人',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'WHTHIN','查档说明',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'HOUSE','房产资料',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'evaOrg1','世联',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'evaOrg3','仁达',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'evaOrg2','链家',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'REALTOR','中介名片',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'NEXTDOOR','下户照片',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'JIEPING','定位截屏',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'GiveUp','放弃优先购买权协议',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'QUICKLY','核行资料（银行贷款资料）',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'RISK','风控单',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'Contract_Pic','合同与签合同照片',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'HUIZHIDAN','公证回执单',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'JUSTICE','公证材料',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'MortgageReceipt','抵押回执单',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'CardNumber','放款卡',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'ThirdCardNumber','三方卡',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'MATERIAL','他项权利证材料',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'RenewalAgreement','展期协议',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'CreditReport','征信报告',(select id from sys_dict where type_code = 'FILE_DOWNLOAD'));



/* 2016-11-30 展期流程节点*/
delete from sys_dict_detail where parent_id = (select id from sys_dict where type_code='EXTENSION_WORKFLOW_STATE');

insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'usertask-extension-customer-survey','展期客户调查',(select id from sys_dict where type_code = 'EXTENSION_WORKFLOW_STATE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'usertask-extension-collateralAssess','展期房产评估',(select id from sys_dict where type_code = 'EXTENSION_WORKFLOW_STATE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'usertask-extension-customer-surveyTwo','展期客户调查',(select id from sys_dict where type_code = 'EXTENSION_WORKFLOW_STATE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'usertask-extension-confirm','确认展期',(select id from sys_dict where type_code = 'EXTENSION_WORKFLOW_STATE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'usertask-extension-firstTrial','展期初审',(select id from sys_dict where type_code = 'EXTENSION_WORKFLOW_STATE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'usertask-extension-recheck','展期复审',(select id from sys_dict where type_code = 'EXTENSION_WORKFLOW_STATE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'usertask-gather-extendedfee','收取展期费用及首期利息',(select id from sys_dict where type_code = 'EXTENSION_WORKFLOW_STATE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'usertask-extension-reject','拒绝展期',(select id from sys_dict where type_code = 'EXTENSION_WORKFLOW_STATE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'usertask-extension-deduct','展期划扣',(select id from sys_dict where type_code = 'EXTENSION_WORKFLOW_STATE'));

/* 2016-12-5 核行增加驳回到下户任务分配*/
update sys_dict_detail set item_value='驳回核行' where item_code='backCheckBank';
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'backTaskAssign','驳回任务分配',(select id from sys_dict where type_code='OPERATION_TYPE'));
insert into sys_dict_detail(id,item_code,item_value,parent_id) values(null,'backHouseholdInvestigate','驳回权证下户',(select id from sys_dict where type_code='OPERATION_TYPE'));
ALTER TABLE pub_loanprocess ADD workflow_next_assignee_name varchar(255) DEFAULT NULL COMMENT '流程状态审批人';

