package com.company.modules.repay.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.repay.domain.RpAheadRepaySetting;
import com.company.modules.repay.domain.RpRepaySetting;

@RDBatisDao
public interface RpAheadRepaySettingDao extends BaseDao<RpRepaySetting, Long> {
	/**
	 * 提前还款设置表,根据主键删除记录
	 * 
	 * @param id
	 * @return
	 */
	public int deleteByPrimaryKey(Integer id);

	/**
	 * 前还款设置表,插入整条记录
	 * 
	 * @param RpAheadRepaySetting record
	 * @return
	 */
	public int insert(RpAheadRepaySetting record);

	/**
	 * 前还款设置表,根据具体字段属性插入记录
	 * 
	 * @param RpAheadRepaySetting record
	 * @return
	 */
	public int insertSelective(RpAheadRepaySetting record);

	/**
	 * 前还款设置表,根据id查询单条记录
	 * 
	 * @param Integer id
	 * @return RpAheadRepaySetting record
	 */
	public RpAheadRepaySetting selectByPrimaryKey(Integer id);

	/**
	 * 前还款设置表,根据id与属性update整条记录
	 * 
	 * @param map
	 * @return
	 */
	public int updateByPrimaryKeySelective(RpAheadRepaySetting record);

	/**
	 * 前还款设置表,根据id update整条记录
	 * 
	 * @param map
	 * @return
	 */
	public int updateByPrimaryKey(RpAheadRepaySetting record);

	/**
	 * 前还款设置表,分页查询数据
	 * 
	 * @param map
	 * @return List<RpRepaySetting>
	 */
	public List<RpRepaySetting> getPageListByMap(Map<String, Object> map);

	/**
	 * 前还款设置表,根据rp_repay表的id查询数据
	 * 
	 * @param id
	 * @return RpRepaySetting
	 */
	public List<RpRepaySetting> getItemInfoByRepayId(int id);

	/**
	 * 前还款设置表,根据rp_repay表的id删除数据
	 * 
	 * @param id 外键
	 * @return
	 */
	public int deleteByRepayId(int id);

}
