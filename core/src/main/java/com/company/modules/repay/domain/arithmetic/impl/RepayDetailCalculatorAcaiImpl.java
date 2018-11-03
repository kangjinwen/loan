package com.company.modules.repay.domain.arithmetic.impl;

import com.company.modules.repay.domain.arithmetic.Repay;
import com.company.modules.repay.domain.arithmetic.RepayDetail;
import com.company.modules.repay.domain.arithmetic.RepayType;

//等本等息
public class RepayDetailCalculatorAcaiImpl extends RepayDetailCalculatorFisrtMonthBaseImpl {
    public RepayType getRepayType() {
        return RepayType.averageCapitalAverageInterest;
    }

    ;

    protected void CalcCapitalInterest(Repay repay, RepayDetail repayDetail) {
        double capital = repay.getRepaySettings().getAccount() / repay.getRepaySettings().getTerm();
        repayDetail.setCapital(capital);

        double interest = repay.getRepaySettings().getAccount() * monthRate;
        interest = handleFirstMonthInterest(repayDetail.getTerm(), repay.getRepaySettings().getTerm(), interest);
        repayDetail.setInterest(interest);
    }

    ;
}
