package com.company.modules.finance.dao;

import com.company.common.dao.BaseDao;

import java.util.List;
import java.util.Map;

import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.finance.domain.HousLowerCost;

/**
* User:    fzc
* DateTime:2016-08-12 10:23:50
* details: 下户费表,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousLowerCostDao extends BaseDao<HousLowerCost,Long> {

    /**
     * 下户费表表,分页查询数据
     * @param map
     * @return List<HousLowerCost>
     */
    public List<HousLowerCost> getPageListByMap(Map<String, Object> map);
    
    /**
     * 下户费表表,根据id查询数据
     * @param id
     * @return HousLowerCost
     */
    public HousLowerCost getItemInfoById(long id);
    
    /**
     * 下户费表表,根据流程id查询数据
     * @param processInstanceId
     * @return HousLowerCost
     */
    public HousLowerCost getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 下户费表表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public Map<String, Object> getHousLowerCostBasicInfo(long projectId);
	
	public HousLowerCost getHousLowerCostByProjectId(long projectId);

	public Map<String, Object> getHousLowerCostInfo(long processInstanceId);
}
