package com.company.modules.repayment.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.company.modules.common.exception.ServiceException;

/**
 * 还款管理
 * @author JDM
 * @date 2016年8月25日
 *
 */
public interface PubRepaymentService {

	/**
     * 分页查询还款数据
     * @param paramMap
     * @return
     * @throws Exception
     */
    public List<Map> getPageListByMap(Map<String , Object> paramMap) throws Exception;

    /**
     * 还款详情分页列表
     * @param paramMap
     * @return
     * @throws ServiceException
     */
	public List<Map> getRepaymentDetailPageByMap(Map<String, Object> paramMap) throws ServiceException;
	
	/**
	 * @description 查询还款，（强制结清时计算了本期违规金额）
	 * @param type
	 * @param processInstanceId
	 * @param realPaymentDate yyyy-MM-dd
	 * @return
	 * @throws ServiceException
	 * @return Map<String,Object>
	 * @since  1.0.0
	*/
	Map<String,Object> queryRepaymentDetail(Integer type ,String processInstanceId, Date realPaymentDate)throws ServiceException;

	public List<Map<String, Object>> queryRepaymentDetailList(Map<String, Object> params) throws ServiceException;

	/**
	 * 还款提交
	 * @param type 还款类型 (1.正常还款 2.提前还款)
	 * @param repaymengdetail
	 * @param userId
	 * @throws ServiceException
	 */
	public void pay(Integer type, Map<String, Object> repaymengdetail, Long userId) throws ServiceException;

	public Boolean validateWhetherCanPay(Map<String, Object> params);
	
	public List<Map<String, Object>> queryFactBorrowList(Map<String, Object> params);
    
	/**
	 * 启动信息筛查分支流程
	 * @description
	 * @param processInstanceId
	 * @param userId
	 * @throws ServiceException
	 * @author huy
	 * @return void
	 * @since  1.0.0
	 */
	public void startSupplyInvestigateProcess(String processInstanceId,Long userId)throws ServiceException;
}
