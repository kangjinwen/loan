package com.company.modules.repayment.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.repayment.domain.ChargeDisposalLog;

/**
 * 
 * @author JDM
 * @date 2016年8月25日
 *
 */
@RDBatisDao
@SuppressWarnings("all")
public interface PubRepaymentDao extends BaseDao<Object,Long> {

    /**
     * 分页查询数据
     * @param map
     * @return List<Map>
     */
    public List<Map> getPageListByMap(Map<String, Object> map);

	public List<Map> getRepaymentDetailPageByMap(Map<String, Object> paramMap);

	public Map<String, Object> getMinUnPayedRepaymentdetail(Map<String, Object> searchParam);

	public List<Map<String, Object>> queryRepayLoanList(Map<String, Object> params);

	public List<Map> validateWhetherCanPay(Map<String, Object> params);

	public void updateBorrowCnt(String processInstanceId);

	public void updateRepayCnt(Map<String, Object> params);

	public void updatePayOffStatus(long projectId);

	public Integer queryMaxPeriod(String processInstanceId);

	public void updateRepaymentdetailById(Map<String, Object> detail);

	public List<Map<String, Object>> queryAllOverdue(Map<String, Object> params);
    

	public void updatePubRepayloaninfoById(Map<String, Object> params);

	public Map<String, Object> getItemByMap(Map<String, Object> params);

	/**
	 * 贷后变更申请列表
	 * @param params
	 * @return
	 */
	public List<Map> pendingApplicationList(Map<String, Object> params);
	/**
	 * 贷后变更列表
	 * @param params
	 * @return
	 */
	public List<Map> postLoanChangeList(Map<String, Object> params);

	public List<Map<String, Object>> getUnPayedRepaymentdetails(Map<String, Object> searchParam);

	/**
	 * 获取押品处置信息
	 * @param processInstanceId
	 * @return
	 */
	public ChargeDisposalLog getChargeDisposal(String processInstanceId);
	/**
	 * 新增押品处置信息
	 * @param chargeDisposalLog
	 * @return
	 */
	public ChargeDisposalLog addChargeDisposal(ChargeDisposalLog chargeDisposalLog);
}
