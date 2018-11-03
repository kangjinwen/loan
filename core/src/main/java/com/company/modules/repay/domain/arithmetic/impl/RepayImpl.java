package com.company.modules.repay.domain.arithmetic.impl;

import com.company.modules.repay.domain.arithmetic.Repay;
import com.company.modules.repay.domain.arithmetic.RepayDetail;
import com.company.modules.repay.domain.arithmetic.RepaySettings;
import com.company.modules.repay.domain.arithmetic.RepayStatusType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepayImpl implements Repay {
    private RepaySettings repaySettings;//还款设定
    private Date loanDate;//放款日期
    private Double repayedInterest;//已还利息
    private Double repayedCapital;//已还本金
    private Double serviceFee;//服务费
    private RepayStatusType  repayStatusType;//还款状态
    private List<RepayDetail> repayDetails = new ArrayList<>();//还款计划表
    private Double overdueAmount;//逾期金额
    private Integer overdueDay;//逾期天数



    @Override
    public RepaySettings getRepaySettings() {
        return repaySettings;
    }

    @Override
    public void setRepaySettings(RepaySettings repaySettings) {
        this.repaySettings = repaySettings;
    }


    @Override
    public Date getLoanDate() {
        return loanDate;
    }

    @Override
    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    @Override
    public double getRepayedInterest() {
        return repayedInterest;
    }

    @Override
    public void setRepayedInterest(Double repayedInterest) {
        this.repayedInterest = repayedInterest;
    }

    @Override
    public double getRepayedCapital() {
        return repayedCapital;
    }

    @Override
    public void setRepayedCapital(Double repayedCapital) {
        this.repayedCapital = repayedCapital;
    }

    @Override
    public double getServiceFee() {
        return serviceFee;
    }

    @Override
    public void setServiceFee(Double serviceFee) {
        this.serviceFee = serviceFee;
    }

    @Override
    public RepayStatusType getRepayStatusType() {
        return repayStatusType;
    }

    @Override
    public void setRepayStatusType(RepayStatusType repayStatusType) {
        this.repayStatusType = repayStatusType;
    }

    @Override
    public List<RepayDetail> getRepayDetails() {
        return repayDetails;
    }

    @Override
    public void setRepayDetails(List<RepayDetail> list) {
        this.repayDetails = list;
    }

    @Override
    public double getOverdueAmount() {
        return overdueAmount;
    }

    @Override
    public void setOverdueAmount(Double overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    @Override
    public Integer getOverdueDay() {
        return overdueDay;
    }

    @Override
    public void setOverdueDay(Integer day) {
        this.overdueDay = day;
    }

    @Override
    public Repay clone() {
        return null;
    }
}
