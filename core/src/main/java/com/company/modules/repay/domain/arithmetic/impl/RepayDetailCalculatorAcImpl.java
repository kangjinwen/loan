package com.company.modules.repay.domain.arithmetic.impl;

import com.company.modules.repay.domain.arithmetic.Repay;
import com.company.modules.repay.domain.arithmetic.RepayDetail;
import com.company.modules.repay.domain.arithmetic.RepayType;


//等额本金
public class RepayDetailCalculatorAcImpl extends RepayDetailCalculatorFisrtMonthBaseImpl {
    public RepayType getRepayType() {
        return RepayType.averageCapital;
    }



    protected void CalcCapitalInterest(Repay repay, RepayDetail repayDetail) {
        //每月应还本金=贷款本金÷还款月数
        repayDetail.setCapital(repay.getRepaySettings().getAccount() / repay.getRepaySettings().getTerm());

        //每月应还利息=剩余本金×月利率=(贷款本金-已归还本金累计额)×月利率
        double interest = repay.getRepaySettings().getAccount() / repay.getRepaySettings().getTerm() *
                (repay.getRepaySettings().getTerm() - repayDetail.getTerm() + 1) * monthRate;
        interest = handleFirstMonthInterest(repayDetail.getTerm(), repay.getRepaySettings().getTerm(), interest);
        repayDetail.setInterest(interest);

    }

    ;
}
