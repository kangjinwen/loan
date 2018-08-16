ALTER TABLE hous_quick_information ADD bank_material tinyint(4) DEFAULT NULL COMMENT '银行可否代为领取解押材料';
ALTER TABLE hous_quick_information ADD remark varchar(255) DEFAULT NULL COMMENT '备注';
ALTER TABLE hous_quick_information ADD two_or_five tinyint(4) DEFAULT NULL COMMENT '满二或满五';