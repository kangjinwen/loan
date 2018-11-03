package com.company.modules.repay.domain.arithmetic.impl;

import com.company.modules.repay.domain.arithmetic.Repay;
import com.company.modules.repay.domain.arithmetic.RepayDetail;
import com.company.modules.repay.domain.arithmetic.RepayType;
//先息后本
public class RepayDetailCalculatorFirstInterestImpl extends RepayDetailCalculatorFisrtMonthBaseImpl {


    public RepayType getRepayType() {
        return RepayType.firstInterest;
    }


    protected void CalcCapitalInterest(Repay repay, RepayDetail repayDetail) {
        double capital = 0;
        if (repay.getRepaySettings().getTerm() == repayDetail.getTerm()) {
            capital = repay.getRepaySettings().getAccount();
        }
        repayDetail.setCapital(capital);

        double interest = repay.getRepaySettings().getAccount() * monthRate;
        interest = handleFirstMonthInterest(repayDetail.getTerm(), repay.getRepaySettings().getTerm(), interest);
        repayDetail.setInterest(interest);
    }

    ;

}
