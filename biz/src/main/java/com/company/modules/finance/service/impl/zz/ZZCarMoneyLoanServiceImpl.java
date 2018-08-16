package com.company.modules.finance.service.impl.zz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.domain.CommonEachPlan;
import com.company.common.domain.EachPlanFactory;
import com.company.common.domain.EachPlanFactory.PLANTYPE;
import com.company.common.domain.PlBorrow;
import com.company.common.utils.DateUtil;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.EachPlan;
import com.company.modules.fel.context.FelConstant;
import com.company.modules.fel.service.FelService;
import com.company.modules.finance.service.impl.CarMoneyLoanServiceImpl;

/**
 * @Description 赚赚CarMoneyLoanService
 * @author wtc,wtc@erongdu.com
 * @CreatTime 2015年6月26日 下午4:34:18
 * @since version 1.0.0
 */
@Service("zzCarMoneyLoanServiceImpl")
public class ZZCarMoneyLoanServiceImpl extends CarMoneyLoanServiceImpl {
	@Autowired
	private FelService felServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(ZZCarMoneyLoanServiceImpl.class);

	/**
	 * 正常情况，放款日T-1为下期还款日
	 * 特殊情况：
	 * ①如下期还款日无此日期，则往前推到可取日期为止。比如：1月31日放款，下期还款日为2月28日，之后的每期还款日都为28日
	 * ②如月初1号放款，则之后的还款日均为1号。比如：1月1日放款，下期还款日为2月1日，之后的每期还款日都为1日
	 */
	@Override
	public List<EachPlan> createEachPlans(PlBorrow borrowInfo) throws ServiceException {
		List<EachPlan> plans = new ArrayList<EachPlan>();
		try {
			int timeLimit = (int) borrowInfo.getTimeLimit() + 1;
			// 还款开始时间
			Date reoayDate = borrowInfo.getLoanTime();
			BigDecimal total = borrowInfo.getAccount();
			// 先息后本
			Calendar calendar = Calendar.getInstance();
			if (reoayDate != null) {
				calendar.setTime(reoayDate);
			}
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			for (int i = 1; i <= timeLimit; i++) {
				if (timeLimit == i) {
					// 最后一期还本金
					CommonEachPlan ep = (CommonEachPlan) EachPlanFactory.createEachPlan(PLANTYPE.common);
					ep.setTotal(total.doubleValue());
//					if (day == 1) {
//						if (reoayDate != null) {
//							ep.setRepayTime(DateUtil.rollMon(reoayDate, i - 1));
//						}
//					} else {
//						if (reoayDate != null) {
//							ep.setRepayTime(DateUtil.rollMonAndLoanDateOneDay(reoayDate, i - 1));
//						}
//					}
					
					if (reoayDate != null) {
						ep.setRepayTime(DateUtil.rollMonAndLoanDateOneDay(reoayDate, i - 1));
					}

					ep.setInterest(0);
					ep.setPeriod( i);
					ep.setCapital(total.doubleValue());
					ep.setNeedRepay(total.doubleValue());
					plans.add(ep);
				} else {
					CommonEachPlan ep = (CommonEachPlan) EachPlanFactory.createEachPlan(PLANTYPE.common);
					ep.setTotal(felServiceImpl
							.calculateUseFeeInfo(borrowInfo.getProcessInstanceId(), total, FelConstant.MONTH_REPAY)
							.doubleValue());
					if (i == 1) {// 首期利息还款时间与放款时间相同
						ep.setRepayTime(reoayDate);
					} else {
//						if (day == 1) {
//							ep.setRepayTime(DateUtil.rollMon(reoayDate, i - 1));
//						} else {
//							ep.setRepayTime(DateUtil.rollMonAndLoanDateOneDay(reoayDate, i - 1));
//						}
						
						ep.setRepayTime(DateUtil.rollMonAndLoanDateOneDay(reoayDate, i - 1));
					}
					ep.setPeriod(i);
					ep.setInterest(felServiceImpl
							.calculateUseFeeInfo(borrowInfo.getProcessInstanceId(), total, FelConstant.MONTH_REPAY)
							.doubleValue());
					ep.setCapital(0);
					plans.add(ep);
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return plans;
	}

}