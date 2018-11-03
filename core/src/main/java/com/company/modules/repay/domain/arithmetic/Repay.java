package com.company.modules.repay.domain.arithmetic;

import java.util.Date;
import java.util.List;

public interface Repay {
    //还款设定
    RepaySettings getRepaySettings();
    void setRepaySettings(RepaySettings RepaySettings);


    //放款日期
    Date getLoanDate();
    void setLoanDate(Date loanDate);

    //已还利息
    double getRepayedInterest();
    void setRepayedInterest(Double repayedInterest);

    //已还本金
    double getRepayedCapital();
    void setRepayedCapital(Double repayedCapital);


    //服务费
    double getServiceFee();
    void setServiceFee(Double serviceFee);

    //还款状态
    RepayStatusType getRepayStatusType();
    void setRepayStatusType(RepayStatusType repayStatusType);

    //还款计划表
    List<RepayDetail> getRepayDetails();
    void setRepayDetails(List<RepayDetail> list);
    //逾期金额
    double getOverdueAmount();
    void setOverdueAmount(Double overdueAmount);
    //逾期天数
    Integer getOverdueDay();
    void setOverdueDay(Integer day);

    Repay clone();
}
