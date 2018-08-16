package com.company.modules.warrant.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.warrant.domain.HousIntermediaryInformation;

/**
* User:    fzc
* DateTime:2016-08-10 05:04:19
* details: 房屋中介信息(权证下户),Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousIntermediaryInformationDao extends BaseDao<HousIntermediaryInformation,Long> {

    /**
     * 房屋中介信息(权证下户)表,分页查询数据
     * @param map
     * @return List<HousIntermediaryInformation>
     */
    public List<HousIntermediaryInformation> getPageListByMap(Map<String, Object> map);
    
    /**
     * 房屋中介信息(权证下户)表,根据id查询数据
     * @param id
     * @return HousIntermediaryInformation
     */
    public HousIntermediaryInformation getItemInfoById(long id);
    
    /**
     * 房屋中介信息(权证下户)表,根据流程id查询数据
     * @param processInstanceId
     * @return HousIntermediaryInformation
     */
    public HousIntermediaryInformation getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 房屋中介信息(权证下户)表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
