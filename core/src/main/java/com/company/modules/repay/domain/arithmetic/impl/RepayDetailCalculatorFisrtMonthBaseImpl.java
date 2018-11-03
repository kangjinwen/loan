package com.company.modules.repay.domain.arithmetic.impl;

public class RepayDetailCalculatorFisrtMonthBaseImpl extends RepayDetailCalculatorBaseImpl {
    protected double firstMonthExtraInterest = 0;

    protected double handleFirstMonthInterest(int term, int totalTerm, double interest) {
        if (term == 1) {
            double firstInterest = interest / 30 * dayOfFirstMonth;
            firstMonthExtraInterest = firstInterest - interest;
            interest = firstInterest;
        } else if (totalTerm == term) {
            interest = interest - firstMonthExtraInterest;
        }
        return interest;
    }
}
