DELETE FROM pl_borrow;
DELETE FROM pl_borrow_requirement;
DELETE FROM pl_contract;
DELETE FROM pl_consult;
DELETE FROM pub_loan;
DELETE FROM pub_loanprocess;
DELETE FROM pub_repaymentdetail;
DELETE FROM sys_log;
DELETE FROM pub_attachment;
DELETE FROM pl_bankcard;
DELETE FROM pl_contractfile;

DELETE FROM pub_project;
DELETE FROM pub_project;
DELETE FROM pub_project_process;
DELETE FROM pub_process_branching;
DELETE FROM pub_repayloaninfo;
DELETE FROM pl_feeinfo;
DELETE FROM pl_settlementfee;
DELETE FROM pub_blackCustomer;
DELETE FROM pub_notice;
DELETE FROM sys_notice;
DELETE FROM sys_msg_instation;
DELETE FROM sys_msg_result;
DELETE FROM pub_blackCustomer;
DELETE FROM pub_alarmsetting;

DELETE FROM ACT_HI_ACTINST;
DELETE FROM ACT_HI_ATTACHMENT;
DELETE FROM ACT_HI_COMMENT;
DELETE FROM ACT_HI_DETAIL;
DELETE FROM ACT_HI_IDENTITYLINK;
DELETE FROM ACT_HI_VARINST;
DELETE FROM ACT_HI_PROCINST;
DELETE FROM ACT_HI_TASKINST;
DELETE FROM ACT_RU_VARIABLE;
DELETE FROM ACT_GE_BYTEARRAY;
DELETE FROM ACT_RE_DEPLOYMENT;
DELETE FROM ACT_RU_IDENTITYLINK;
DELETE FROM ACT_RU_TASK;
DELETE FROM hous_assessment_agencies;
DELETE FROM hous_bills;
DELETE FROM hous_borrowing_basics;
DELETE FROM hous_control_information;
DELETE FROM hous_credit_information;
DELETE FROM hous_data_list;
DELETE FROM hous_enquiry_institution;
DELETE FROM hous_face_trial;
DELETE FROM hous_intermediary_information;
DELETE FROM hous_loanfees;
DELETE FROM hous_lower_cost;
DELETE FROM hous_marriage_information;
DELETE FROM hous_mortgage_registration;
DELETE FROM hous_notarization_registration;
DELETE FROM hous_orito_information;
DELETE FROM hous_property_information;
DELETE FROM hous_quick_information;
DELETE FROM hous_rebate;

update ACT_RU_EXECUTION set 
	PARENT_ID_=null,
	PROC_INST_ID_=null,
	SUPER_EXEC_=null;
delete from ACT_RU_EXECUTION;

DELETE FROM ACT_RE_PROCDEF;
