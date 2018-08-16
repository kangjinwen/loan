package com.company.modules.credit.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.credit.domain.PubRepaymentdetail;

/**
 * 还款明细表DAO接口
 * @author wgc
 * @version 1.0
 * @since 2014-11-17
 */
@RDBatisDao
public interface CreditRepaymentdetailDao extends BaseDao<PubRepaymentdetail, Long> {

	/**
	 * 根据ID更新 通用更新
	 * 
	 * @param map
	 * @return Boolean
	 * @throws PersistentDataException
	 */
	Boolean updateRepaymentdetailById(Map<String, Object> data);

	Boolean cancelOverdueRepaymentdetailByProcessInstanceId(Map<String, Object> data);

	/**
	 * 分页查询出的列表
	 * 
	 * @param mapdata
	 * @return 分页列表
	 * @throws PersistentDataException
	 */
	List<? extends PubRepaymentdetail> getRepaymentdetailPageList(Map<String, Object> data);

	/**
	 * 获取总记录数
	 * 
	 * @param param
	 * @return 记录数
	 * @throws PersistentDataException
	 */
	int getRepaymentdetailCount(Map<String, Object> data);

	/**
	 * 催收分页列表
	 * 
	 * @param data
	 * @return
	 * @throws PersistentDataException
	 * @version 1.0
	 * @author 吴国成
	 * @created 2014年11月19日
	 */
	List<Map<String, Object>> getRepaymentdetailListForDun(Map<String, Object> data);

	int getRepaymentdetailCountForDun(Map<String, Object> data);

	/**
	 * 催收统计分页列表
	 * 
	 * @param data
	 * @return
	 * @throws PersistentDataException
	 * @author htz
	 */
	List<? extends PubRepaymentdetail> getCollectionList(Map<String, Object> data);

	int getCollectionCountList(Map<String, Object> data);

	/**
	 * 查询还款明细，最早一期未结清明细
	 * 
	 * @description
	 * @param params
	 * @return
	 * @throws PersistentDataException
	 * @author wtc
	 * @return Repaymentdetail
	 * @since 1.0.0
	 */
	Map<String, Object> getMinUnPayedRepaymentdetail(Map<String, Object> params) throws PersistentDataException;

	List<Map<String, Object>> getUnPayedRepaymentdetails(Map<String, Object> searchParam);

	Map<String, Object> getRepaymentDetailForMap(Map<String, Object> searchParam) throws PersistentDataException;

	/**
	 * @description 根据日期（天）查找还款明细
	 * @param day
	 * @author wtc
	 * @return List<Map<String,Object>>
	 * @since 1.0.0
	 */
	List<Map<String, Object>> queryRepaymentdetailByRepaymentDay(Date day, Map<String, Object> searchParam) throws PersistentDataException;

	/**
	 * @description 统计未还款本金和利息
	 * @param processInstanceId
	 * @return
	 * @throws PersistentDataException
	 * @author wtc
	 * @return Map<String,Object>
	 * @since 1.0.0
	 */
	Map<String, Object> queryNeedPay(String processInstanceId) throws PersistentDataException;

	/**
	 * @description 获取最大期数
	 * @param processInstanceId
	 * @return
	 * @throws PersistentDataException
	 * @author wtc
	 * @return Integer
	 * @since 1.0.0
	 */
	Integer queryMaxPeriod(String processInstanceId) throws PersistentDataException;

	/**
	 * @description 查看最后一期还款明细
	 * @param processInstanceId
	 * @return
	 * @throws PersistentDataException
	 * @author wtc
	 * @return Repaymentdetail
	 * @since 1.0.0
	 */
	PubRepaymentdetail queryLastRepaymentdetail(String processInstanceId) throws PersistentDataException;

	List<Map<String, Object>> queryList(Map<String, Object> param) throws PersistentDataException;

	/**
	 * @description 统计未还款金额
	 * @param processInstanceId
	 * @return
	 * @throws PersistentDataException
	 * @author wtc
	 * @return Map<String,Object>
	 * @since 1.0.0
	 */
	Map<String, Object> queryUnRepayedAmountByProcessInstanceId(String processInstanceId) throws PersistentDataException;

	List<Map<String, Object>> queryAllByMap(Map<String, Object> map) throws PersistentDataException;

	List<Map<String, Object>> queryAllOverdue(Map<String, Object> params) throws PersistentDataException;

	Long insertRepaymentDetail(PubRepaymentdetail repaymentdetail) throws PersistentDataException;
	
}
