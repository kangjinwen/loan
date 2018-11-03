package com.company.modules.repay.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.repay.domain.RpRepaySetting;
import com.company.modules.repay.domain.arithmetic.RepaySettings;

@RDBatisDao
public interface RpRepaySettingDao extends BaseDao<RpRepaySetting, Long> {
	/**
	 * 还款基本信息表,根据id删除数据
	 * 
	 * @param id 主键
	 * @return
	 */
	public int deleteByPrimaryKey(Integer id);

	/**
	 * 还款基本信息表,插入一条记录
	 * 
	 * *@param RpRepaySetting record
	 * 
	 * @return
	 */
	public long insert(RpRepaySetting record);

	/**
	 * 还款基本信息表,根据字段选择性地插入记录
	 * 
	 * *@param RpRepaySetting record
	 * 
	 * @return
	 */
	public Integer insertSelective(RpRepaySetting record);

	/**
	 * 还款基本信息表,根据字段选择性地插入记录
	 * 
	 * *@param *RepaySettings record *Integer projectId
	 * 
	 * @return
	 */
	public Integer insertSelectiveByProjectId(@Param("projectId") Integer projectId,
			@Param("record") RepaySettings record);

	/**
	 * 还款基本信息表,根据id选择记录
	 * 
	 * @param id 主键
	 * @return
	 */
	public RpRepaySetting selectByPrimaryKey(Integer id);

	/**
	 * 还款基本信息表,根据id选择性update数据
	 * 
	 * *@param RpRepaySetting record
	 * 
	 * @return
	 */
	public int updateByPrimaryKeySelective(RpRepaySetting record);

	/**
	 * 还款基本信息表,根据id update数据
	 * 
	 * *@param RpRepaySetting record
	 * 
	 * @return
	 */
	public int updateByPrimaryKey(RpRepaySetting record);

	/**
	 * 还款基本信息表,分页查询数据
	 * 
	 * @param map
	 * @return List<RpRepaySetting>
	 */
	public List<RpRepaySetting> getPageListByMap(Map<String, Object> map);

	/**
	 * 还款基本信息表,根据rp_repay表的id查询数据
	 * 
	 * @param id
	 * @return RpRepaySetting
	 */
	public List<RpRepaySetting> getItemInfoByRepayId(int id);

	/**
	 * 还款基本信息表,根据rp_repay表的id删除数据
	 * 
	 * @param id 外键
	 * @return
	 */
	public int deleteByRepayId(int id);

	/**
	 * 根据projectId查询borrowRequirementId，customerId，housPropertyInformationId
	 * 
	 * *@param PartnerName
	 * 
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getForeignKeyByProjectId(Integer projectId);
}
