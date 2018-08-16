package com.company.modules.instance.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.instance.domain.HousPersonType;

/**
* User:    wulb
* DateTime:2016-11-15 13:47:18
* details: 新增人员类型,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousPersonTypeDao extends BaseDao<HousPersonType,Long> {

    /**
     * 新增人员类型表,分页查询数据
     * @param map
     * @return List<HousPersonType>
     */
    public List<HousPersonType> getPageListByMap(Map<String, Object> map);
    
    public List<HousPersonType> getItemInfoByConsultId(Long consultId);
    
    /**
     * 新增人员类型表,根据id查询数据
     * @param id
     * @return HousPersonType
     */
    public HousPersonType getItemInfoById(long id);
    
    /**
     * 新增人员类型表,根据流程id查询数据
     * @param processInstanceId
     * @return HousPersonType
     */
    public List<HousPersonType> getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 新增人员类型表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
