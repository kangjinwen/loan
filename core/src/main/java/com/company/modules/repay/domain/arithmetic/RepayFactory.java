package com.company.modules.repay.domain.arithmetic;


import com.company.modules.repay.domain.arithmetic.impl.*;

public class RepayFactory {
    private static RepayFactory repayFactory;
    private RepayFactory(){}
    public static synchronized RepayFactory getInstance() {
        if (repayFactory==null){
            repayFactory = new RepayFactory();
        }
        return repayFactory;
    }



    public RepayDetailCalculator CreateRepayDetailCalculator(RepayType repayType) {
        if (repayType.equals(RepayType.averageCapitalAverageInterest)){
            return new RepayDetailCalculatorAcaiImpl();//等本等息
        }
        if (repayType.equals(RepayType.averageCapital)){
            return  new RepayDetailCalculatorAcImpl();//等额本金
        }
        if (repayType.equals(RepayType.averageCapitalPlusInterest)){
            return new RepayDetailCalculatorAcpiImpl();//等额本息
        }
        if (repayType.equals(RepayType.firstInterest)){
            return  new RepayDetailCalculatorFirstInterestImpl();//先息后本
        }
        if (repayType.equals(RepayType.combine)){
            return new RepayDetailCalculatorCombineImpl();//休假
        }

        return new RepayDetailCalculatorBaseImpl();//uknown
    }

    public RepayDetail CreateRepayDetail() {
        return new RepayDetailImpl();
    }

    public Repay CreateRepay() {
        return new RepayImpl();
    }

    public RepaySettings CreateRepaySettings(){
        return new RepaySettingsImpl();
    }
}
