package com.company.modules.repay.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.repay.domain.RpRepay;

@RDBatisDao
public interface RpRepayDao extends BaseDao<RpRepay, Long> {
	/**
	 * 还款表,根据主键删除数据
	 * 
	 * @param id
	 * @return
	 */
	public int deleteByPrimaryKey(Integer id);

	/**
	 * 还款表,插入记录
	 * 
	 * @param RpRepay record
	 * @return
	 */
	public long insert(RpRepay record);

	/**
	 * 还款表,根据具体字段属性插入记录
	 * 
	 * @param map
	 * @return
	 */
	public int insertSelective(RpRepay record);

	/**
	 * 还款表,根据主键查询记录
	 * 
	 * @param Integer id
	 * @return
	 */
	public RpRepay selectByPrimaryKey(Integer id);

	/**
	 * 还款表,根据主键选择性update记录
	 * 
	 * @param RpRepay record
	 * @return
	 */
	public int updateByPrimaryKeySelective(RpRepay record);

	/**
	 * 还款表,根据主键update整条记录
	 * 
	 * @param RpRepay record
	 * @return
	 */
	public int updateByPrimaryKey(RpRepay record);

	/**
	 * 还款表,分页查询数据
	 * 
	 * @param map
	 * @return List<RpRepay>
	 */
	public List<RpRepay> getPageListByMap(Map<String, Object> map);

}
