package com.company.common.context;

/**
 * 通用常量
 * @author JDM
 * @date 2016年9月26日
 *
 */
public class CommonConstant {
	/***/
	// 业务来源
	/** 商务专员 */
	public static final String BUSINESS_ORIGIN_CUSTOMERSERVICESTAFFB_KEY = "customerServiceStaffB";
	public static final String BUSINESS_ORIGIN_CUSTOMERSERVICESTAFFB_VALUE = "0";
	
	/** 报单专员 */
	public static final String BUSINESS_ORIGIN_DECLARATIONSTAFF_KEY = "declarationStaff";
	public static final String BUSINESS_ORIGIN_DECLARATIONSTAFF_VALUE = "1";
	
	/** 报单专员 */
	public static final String BUSINESS_ORIGIN_APP_KEY = "APP";
	public static final String BUSINESS_ORIGIN_APP_VALUE = "2";
	
	// 还款类型
	/** 还款类型-正常还款 */
	public static final int REPAYMENT_TYPE_NORMAL = 1;
	/** 还款类型-提前还款 */
	public static final int REPAYMENT_TYPE_PREPAY = 2;
	/** 还款类型-强制结清 */
	public static final int REPAYMENT_TYPE_FORCE_SETTLE_REPAY = 4;
	/** 还款类型-押品处置 */
	public static final int REPAYMENT_TYPE_CHARGE_DISPOSAL = 5;
	
	// 还款状态
	/** 还款状态-还款中 */
	public static final int REPAYMENT_STATUS_REPAYING = 0;
	/** 还款状态-结清 */
	public static final int REPAYMENT_STATUS_PAID_OFF = 1;
	/** 还款状态-逾期 */
	public static final int REPAYMENT_STATUS_OVERDUE = 2;
	
	/** 还款未结清 */
	public static final int PAY_OFF_FALSE = 0;
	/** 还款已结清 */
	public static final int PAY_OFF_TRUE = 1;
	
	public static final int OVER_DUE_DAY = 4;
	
	/** 还款详情状态-逾期 */
	public static final int REPAYMENT_DETAIL_STATUS_OVERDUE = 3;
	
}
