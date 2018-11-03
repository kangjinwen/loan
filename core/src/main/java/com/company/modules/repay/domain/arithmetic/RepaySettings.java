package com.company.modules.repay.domain.arithmetic;

/**
 * 贷款明细，用于执行还款计划算法
 */
public interface RepaySettings {

    //期限
    int getTerm();
    void setTerm(int term);

    //借款金额
    double getAccount();
    void setAccount(double account);

    //还款日
    int getRepayDay();
    void setRepayDay(int repayDay);

    //成本利率
    double getRepayRate();
    void setRepayRate(double repayRate);

    //是否跳过首月
    boolean getIsSkipFirstMonth();
    void setIsSkipFirstMonth(boolean isSkipFirstMonth);
    //还款方式
    RepayType getRepayType();
    void setRepayType(RepayType repayType);


    //服务费收取方式(True:服务费均摊到首12期)
    boolean getIsCalcServiceFee();
    void setIsCalcServiceFee(boolean isCalcServiceFee);
    //提前还款设定
    AheadRepaySettings getAheadRepaySettings();
    void setAheadRepaySettings(AheadRepaySettings aheadRepaySettings);

    //逾期罚息率
    double getOverduePenaltyRate();
    void setOverduePenaltyRate(double overduePenaltyRate);
    //服务费率
    double getServiceFee();
    void  setServiceFee(double serviceFee);
    //该贷款项目的主键id
    long getProjectId();
    void setProjectId(long projectId);
    //放款日期
    long getLoanDate();
    void setLoanDate(long loanDate);
}
