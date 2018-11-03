package com.company.modules.repay.domain.arithmetic.impl;


import com.company.modules.repay.domain.arithmetic.Repay;
import com.company.modules.repay.domain.arithmetic.RepayDetail;
import com.company.modules.repay.domain.arithmetic.RepayType;

//等额本息
public class RepayDetailCalculatorAcpiImpl extends RepayDetailCalculatorFisrtMonthBaseImpl {
    public RepayType getRepayType() {
        return RepayType.averageCapitalPlusInterest;
    }

    ;

    protected void CalcCapitalInterest(Repay repay, RepayDetail repayDetail) {


        //每月应还本金=贷款本金×月利率×(1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕
        repayDetail.setCapital(repay.getRepaySettings().getAccount() * monthRate *
                Math.pow(1 + monthRate, repayDetail.getTerm() - 1) /
                (Math.pow(1 + monthRate, repay.getRepaySettings().getTerm()) - 1));


        //每月应还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕
        double interest = repay.getRepaySettings().getAccount() / repay.getRepaySettings().getTerm() *
                (repay.getRepaySettings().getTerm() - repayDetail.getTerm() + 1) *
                monthRate;
        interest = handleFirstMonthInterest(repayDetail.getTerm(), repay.getRepaySettings().getTerm(), interest);
        repayDetail.setInterest(interest);


    }

    ;
}
