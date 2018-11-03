package com.company.modules.repay.domain.arithmetic.impl;

import com.company.modules.repay.domain.arithmetic.*;

//休假
public class RepayDetailCalculatorCombineImpl extends RepayDetailCalculatorFisrtMonthBaseImpl {

    private int firstPartTerm = 36;
    private RepayType firstRepayType;
    private RepayType restRepayType;

    public RepayType getRepayType() {
        return RepayType.combine;
    }

    ;

    protected void CalcCapitalInterest(Repay repay, RepayDetail repayDetail) {
        Repay firstPart = repay.clone();
        Repay restPart = repay.clone();


        RepayDetailCalculator firstCalc = RepayFactory.getInstance().CreateRepayDetailCalculator(firstRepayType);
        RepayDetailCalculator restCalc = RepayFactory.getInstance().CreateRepayDetailCalculator(restRepayType);

        //firstPart.getRepaySettings()


    }

    ;
}
