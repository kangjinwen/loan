package com.company.modules.repay.domain.arithmetic;

public enum RepayStatusType {
    prepare,   //待还款
    normal,    //正常还款
    overdue,    //逾期未还本息未还罚息
    overdueRefundedCipalAndInterest,//逾期已还本息未还罚息
    overdueRefundedAll//逾期已还本息已还罚息（即全部还完）
}
