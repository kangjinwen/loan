package com.company.modules.common.util.calculate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.company.modules.common.exception.ServiceException;

/**
 *	@Description 违约金罚息计算器
 *  @author wtc,wtc@erongdu.com
 *  @CreatTime 2015年6月26日 下午4:31:39
 *  @since version 1.0.0
 */
public interface CalculateService {
    
    /**
     * PENALTY_RATE:违约金比率
     *
     * @since 1.0.0
     */
    String PENALTY_RATE="PENALTY_RATE";
    
    /**
     * OVERDUE_DEFAULTINTEREST_RATE:逾期罚息比率
     *
     * @since 1.0.0
     */
    String OVERDUE_DEFAULTINTEREST_RATE="overdueDefaultInterestRate";
    
    /**
     * OVERDUE_PERIOD:逾期罚款金额计算周期
     *
     * @since 1.0.0
     */
    String OVERDUE_PERIOD="overduePeriod";
    /**
     * @description 逾期违约金
     * @param customerId
     * @param processInstanceId
     * @param projectId
     * @return
     * @author wtc
     * @return BigDecimal
     * @since  1.0.0
    */
    BigDecimal calculateOverduePenalty(String processInstanceId,Long projectId,Date calculateTime)throws ServiceException;
    /**
     * @description 计算某一期的逾期违约金
     * @param detailForcalculate 待计算的还款明细信息
     * @param calculateTime 计算时间
     * @param repaymentdetails 还款明细
     * @return
     * @author wtc
     * @return BigDecimal
     * @throws ServiceException 
     * @since  1.0.0
    */
    BigDecimal calculateOverduePenalty(Map<String, Object> detailForCalculate,Date calculateTime)throws ServiceException;
    BigDecimal calculateOverduePenalty(Map<String, Object> detailForCalculate,Date calculateTime,Byte productId)throws ServiceException;
    /**
     * @description 逾期罚息
     * @param principal
     * @param rate
     * @param repaymentTime
     * @param realRepaymentTime
     * @return
     * @author wtc
     * @return BigDecimal
     * @since  1.0.0
    */
    BigDecimal calculateOverdue (String processInstanceId,Long projectId,Date calculateTime)throws ServiceException;
    
    /**
     * @description 逾期罚息
     * @param repaymentdetails 还款明细
     * @param calculateTime 计算时间
     * @return
     * @throws ServiceException
     * @author wtc
     * @return BigDecimal
     * @since  1.0.0
    */
    BigDecimal calculateOverdueByRepaymentdetails(List<Map<String, Object>> repaymentdetails,Date calculateTime)throws ServiceException;
    BigDecimal calculateOverdueByRepaymentdetails(List<Map<String, Object>> repaymentdetails,Date calculateTime,Byte productId)throws ServiceException;
    /**
     * @description 计算服务费
     * @param processInstanceId
     * @return
     * @throws ServiceException
     * @author wtc
     * @return BigDecimal
     * @since  1.0.0
    */
    BigDecimal calculateServiceFee (String processInstanceId)throws ServiceException;
    
    
    /**
     * 未还利息总和
     * @description
     * @param customerId
     * @param processInstanceId
     * @param projectId
     * @return
     * @throws ServiceException
     * @author wtc
     * @return BigDecimal
     * @since  1.0.0
    */
    BigDecimal calculateNotPayoffInterest(String processInstanceId,Long projectId)throws ServiceException;
    
    /**
     * @description 是否逾期.
     * 还款逾期确认标准：借款人未能在约定还款日16:00应还款金额存入指定还款账户的，视为还款逾期，出借人有权自还款日16：00以后开始计收逾期违约金及罚息
     * @param repaymentTime 约定还款日
     * @param realRepaymentTime 实际还款时间
     * @return
     * @author wtc
     * @return boolean
     * @since  1.0.0
    */
    boolean isOverdue(Date repaymentTime,Date realRepaymentTime);
    
    int calculateMonth(Date repaymentTime,Date calculateTime);
    int calculateOverdueDays(Date repaymentTime, Date current);
    BigDecimal calculateAheadPenalty(Map<String, Object> d, Byte productType) throws ServiceException;
    Map<String, Object> calculateAheadPenaltyAndInterest(Map<String, Object> d, Byte productType) throws ServiceException;
    
    BigDecimal calculatePerRepayDetailOverduePenalty(Map<String, Object> detailForCalculate, Date calculateTime) throws ServiceException;
}
