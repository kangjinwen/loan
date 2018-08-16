/* 2016-11-14 放款管理添加三方卡转账手续费 */
ALTER TABLE hous_bills ADD third_transfer_fee decimal(18,2) DEFAULT '0.00' COMMENT '三方卡转账手续费';