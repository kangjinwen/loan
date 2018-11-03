package com.company.modules.repay.domain.arithmetic.impl;

import com.company.modules.repay.domain.arithmetic.*;

import java.util.Calendar;
import java.util.Date;

public class RepayDetailCalculatorBaseImpl implements RepayDetailCalculator {

    protected double monthRate = 0;

    protected long dayOfFirstMonth = 30;

    public RepayType getRepayType() {
        return RepayType.unknown;
    }

    ;


    protected void CalcCapitalInterest(Repay repay, RepayDetail repayDetail) {

    }

    ;

    //
    public boolean Calc(Repay repay) {

        monthRate = repay.getRepaySettings().getRepayRate() / 12;


        if (repay.getRepayDetails().size() == 0) {
            //计算第一期还款日期
            Calendar cal = Calendar.getInstance();
            cal.setTime(repay.getLoanDate());
            int firstYear = cal.get(Calendar.YEAR);
            int firstMonth = cal.get(Calendar.MONTH);
            int firstDay = cal.get(Calendar.DAY_OF_MONTH);
            boolean needAddMonth = true;
            //只有还款日大于放款日才有跳过首月
            if (repay.getRepaySettings().getRepayDay() > firstDay) {
                needAddMonth = repay.getRepaySettings().getIsSkipFirstMonth();
            }
            ;
            if (needAddMonth) {
                cal.add(Calendar.MONTH, 1);
            }
            Date firstRepayDate = cal.getTime();
            dayOfFirstMonth = (firstRepayDate.getTime() - repay.getLoanDate().getTime()) / (1000 * 3600 * 24);


            for (int i = 0; i < repay.getRepaySettings().getTerm(); i++) {
                RepayDetail repayDetail = RepayFactory.getInstance().CreateRepayDetail();

                //期数
                repayDetail.setTerm(i + 1);

                //计算还款日
                cal.setTime(firstRepayDate);
                cal.add(Calendar.MONTH, i);
                repayDetail.setDate(cal.getTime());

                //计算服务费
                if ((i < 12) || (repay.getRepaySettings().getIsCalcServiceFee())) {
                    repayDetail.setServiceFee(repay.getServiceFee() / 12);
                }

                //计算费用
                CalcCapitalInterest(repay, repayDetail);

                //计算总费用
                repayDetail.setTotalAmount(repayDetail.getCapital() + repayDetail.getInterest() +
                        repayDetail.getServiceFee());
                repay.getRepayDetails().add(repayDetail);


            }
            return true;
        } else {
            return false;
        }

    }

    ;
}
