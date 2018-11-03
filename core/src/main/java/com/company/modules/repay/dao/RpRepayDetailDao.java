package com.company.modules.repay.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.repay.domain.RpRepayDetail;
import com.company.modules.repay.domain.arithmetic.BaseBean;
import com.company.modules.repay.domain.arithmetic.RepayDetail;

@RDBatisDao
public interface RpRepayDetailDao extends BaseDao<RpRepayDetail, Long> {
	/**
	 * 还款明细表,根据id删除数据
	 * 
	 * @param id 主键
	 * @return
	 */
	public int deleteByPrimaryKey(Integer id);

	/**
	 * 还款明细表,插入整条记录
	 * 
	 * *@param RpRepayDetail record
	 * 
	 * @return
	 */
	public long insert(RpRepayDetail record);

	/**
	 * 还款明细表,根据具体属性值插入记录
	 * 
	 * *@param RpRepayDetail record
	 * 
	 * @return
	 */
	public int insertSelective(RpRepayDetail record);

	/**
	 * 还款明细表,根据id选择记录
	 * 
	 * *@param Integer id
	 * 
	 * @return
	 */
	public RpRepayDetail selectByPrimaryKey(Integer id);

	/**
	 * 还款明细表,根据id 选择性update字段
	 * 
	 * *@param RpRepayDetail record
	 * 
	 * @return
	 */
	public int updateByPrimaryKeySelective(RpRepayDetail record);

	/**
	 * 还款明细表,根据id update整条记录
	 * 
	 * *@param RpRepayDetail record
	 * 
	 * @return
	 */
	public int updateByPrimaryKey(RpRepayDetail record);

	/**
	 * 还款明细表,分页查询数据
	 * 
	 * @param map
	 * @return List<RpRepayDetail>
	 */
	public List<RpRepayDetail> getPageListByMap(Map<String, Object> map);

	/**
	 * 还款明细表,根据rp_repay表的id查询数据
	 * 
	 * @param id
	 * @return RpRepayDetail
	 */
	public List<RpRepayDetail> getItemInfoByRepayId(int id);

	/**
	 * 还款明细表,根据rp_repay表的id删除数据
	 * 
	 * @param id 外键
	 * @return
	 */
	public int deleteByRepayId(int id);

	/**
	 * 还款明细表,根据生成的list生成还款明细
	 * 
	 * @param repayDetails
	 * @return
	 */
	public int insertRepayDetails(@Param("list") List<RepayDetail> repayDetails, @Param("projectId") Integer projectId,
			@Param("repaySettingId") Integer repaySettingId);

	/**
	 * 根据projectId查询该项目下的还款计划列表数量
	 * 
	 * @param projectId
	 * @return
	 */
	Integer getCountOfRepayDetailByProjectId(long projectId);

	/**
	 * 根据projectId查询该项目下的还款计划列表数量
	 * 
	 * @param projectId *@param pageNum *@param pageSize
	 * @return
	 */
	List<Map<String, Object>> getListOfRepayDetailByProjectId(@Param("projectId") long projectId,
			@Param("bb") BaseBean bb);

	/**
	 * 根据projectId和term查询该期次的还款计划是否存在
	 * 
	 * @param rpRepayDetail
	 * @return
	 */
	RpRepayDetail selectByProjectIdAndTerm(RpRepayDetail rpRepayDetail);

	/**
	 * 还款明细表,根据生成的list与projectId生成还款明细
	 * 
	 * @param repayDetails
	 * @return
	 */
	 int insertRepayDetailsBylistAndProjectId(@Param("list") List<RepayDetail> repayDetails,
			@Param("projectId") Integer projectId);
}
