package com.company.modules.repay.domain.arithmetic.impl;

import com.company.modules.repay.domain.arithmetic.AheadRepaySettings;
import com.company.modules.repay.domain.arithmetic.RepaySettings;
import com.company.modules.repay.domain.arithmetic.RepayType;

public class RepaySettingsImpl implements RepaySettings {
    private int term;
    private double account;
    private int repayDay;
    private double repayRate;
    private boolean isSkipFirstMonth;
    private RepayType repayType;
    private boolean isCalcServiceFee;
    private AheadRepaySettings aheadRepaySettings;
    private double overduePenaltyRate;
    private double serviceFee;//服务费比例
    private long projectId;//贷款项目编号的主键
    private long loanDate;//放款日期

    public long getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(long loanDate) {
        this.loanDate = loanDate;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    @Override
    public int getTerm() {
        return term;
    }

    @Override
    public void setTerm(int term) {
        this.term = term;
    }

    @Override
    public double getAccount() {
        return account;
    }

    @Override
    public void setAccount(double account) {
        this.account = account;
    }

    @Override
    public int getRepayDay() {
        return repayDay;
    }

    @Override
    public void setRepayDay(int repayDay) {
        this.repayDay = repayDay;
    }

    @Override
    public double getRepayRate() {
        return repayRate;
    }

    @Override
    public void setRepayRate(double repayRate) {
        this.repayRate = repayRate;
    }

    @Override
    public boolean getIsSkipFirstMonth() {
        return isSkipFirstMonth;
    }

    @Override
    public void setIsSkipFirstMonth(boolean isSkipFirstMonth) {
        this.isSkipFirstMonth = isSkipFirstMonth;
    }

    @Override
    public RepayType getRepayType() {
        return repayType;
    }

    @Override
    public void setRepayType(RepayType repayType) {
        this.repayType = repayType;
    }

    @Override
    public boolean getIsCalcServiceFee() {
        return isCalcServiceFee;
    }

    @Override
    public void setIsCalcServiceFee(boolean isCalcServiceFee) {
        this.isCalcServiceFee = isCalcServiceFee;
    }

    @Override
    public AheadRepaySettings getAheadRepaySettings() {
        return aheadRepaySettings;
    }

    @Override
    public void setAheadRepaySettings(AheadRepaySettings aheadRepaySettings) {
        this.aheadRepaySettings = aheadRepaySettings ;
    }

    @Override
    public double getOverduePenaltyRate() {
        return overduePenaltyRate;
    }

    @Override
    public void setOverduePenaltyRate(double overduePenaltyRate) {
        this.overduePenaltyRate = overduePenaltyRate;
    }
}
