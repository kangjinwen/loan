package com.company.modules.audit.dao;

import com.company.common.dao.BaseDao;

import java.util.List;
import java.util.Map;

import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.audit.domain.HousBills;

/**
* User:    fzc
* DateTime:2016-08-17 03:46:40
* details: 放款单/打款单,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousBillsDao extends BaseDao<HousBills,Long> {

    /**
     * 放款单/打款单表,分页查询数据
     * @param map
     * @return List<HousBills>
     */
    public List<HousBills> getPageListByMap(Map<String, Object> map);
    
    /**
     * 放款单/打款单表,根据id查询数据
     * @param id
     * @return HousBills
     */
    public HousBills getItemInfoById(long id);
    
    /**
     * 放款单/打款单表,根据流程id查询数据
     * @param processInstanceId
     * @return HousBills
     */
    public HousBills getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 放款单/打款单表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public Map<String, Object> getHousBillBasicInfo(String processInstanceId);

	public Map<String, Object> getLoanInfo(String processInstanceId);

	public HousBills getItemInfoByProcessInstanceIdAndType(Map<String, Object> map);
}
