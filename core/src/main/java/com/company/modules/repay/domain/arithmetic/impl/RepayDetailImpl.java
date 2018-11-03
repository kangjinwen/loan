package com.company.modules.repay.domain.arithmetic.impl;

import com.company.modules.repay.domain.arithmetic.RepayDetail;
import com.company.modules.repay.domain.arithmetic.RepayStatusType;

import java.util.Date;

public class RepayDetailImpl implements RepayDetail {
    private int term;
    private Date date;
    private double capital;
    private double interest;
    private double serviceFee;
    private double totalAmount;
    private double repayInTerm;
    private double penaltyAmount;
    private Date actualDate;
    private RepayStatusType repayStatusType;
    private boolean isPayOff;
    @Override
    public int getTerm() {
        return term;
    }

    @Override
    public void setTerm(int term) {
        this.term = term;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public double getCapital() {
        return capital;
    }

    @Override
    public void setCapital(double capital) {
        this.capital = capital;
    }

    @Override
    public double getInterest() {
        return interest;
    }

    @Override
    public void setInterest(double interest) {
        this.interest = interest;
    }

    @Override
    public double getServiceFee() {
        return serviceFee;
    }

    @Override
    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    @Override
    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public double getRepayInTerm() {
        return repayInTerm;
    }

    @Override
    public void setRepayInTerm(double repayInTerm) {
        this.repayInTerm = repayInTerm;
    }

    @Override
    public double getPenaltyAmount() {
        return penaltyAmount;
    }

    @Override
    public void setPenaltyAmount(double penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    @Override
    public Date getActualDate() {
        return actualDate;
    }

    @Override
    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
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
    public boolean isPayOff() {
        return isPayOff;
    }
    @Override
    public void setIsPayOff(boolean isPayOff){
        this.isPayOff = isPayOff;
    }
}
