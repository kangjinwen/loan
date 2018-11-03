package com.company.modules.repay.domain.arithmetic;

public interface AheadRepaySettings {
    //是否允许提前还款
    boolean CanAheadRepay();

    //提前还款方式
    AheadRepayType getAheadRepayType();

    //免罚息期限
    int getProtectedTerm();

    //罚息方式
    PenaltyInterestType getPenaltyInterestType();

    //罚息利率
    double getPenaltyInterestRate();
}
