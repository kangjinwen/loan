package com.company.modules.repay.domain.arithmetic;

import java.util.Date;

public interface RepayDetail {
    //期次
    int getTerm();

    void setTerm(int term);

    //还款日期
    Date getDate();

    void setDate(Date date);

    //本期本金
    double getCapital();

    void setCapital(double capital);

    //本期利息
    double getInterest();

    void setInterest(double interest);

    //本期服务费
    double getServiceFee();

    void setServiceFee(double serviceFee);

    //本期应还
    double getTotalAmount();

    void setTotalAmount(double totalAmount);

    //本期已还
    double getRepayInTerm();

    void setRepayInTerm(double repayInTerm);

    //罚息金额
    double getPenaltyAmount();

    void setPenaltyAmount(double penaltyAmount);

    //实际还款日期
    Date getActualDate();

    void setActualDate(Date actualDate);

    //还款状态
    RepayStatusType getRepayStatusType();

    void setRepayStatusType(RepayStatusType repayStatusType);

    //是否还清
    boolean isPayOff();
    void setIsPayOff(boolean isPayOff);
}
