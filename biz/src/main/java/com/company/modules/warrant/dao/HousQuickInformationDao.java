package com.company.modules.warrant.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.warrant.domain.HousQuickInformation;

/**
* User:    fzc
* DateTime:2016-08-10 05:17:32
* details: 房屋快出值信息表(权证下户),Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousQuickInformationDao extends BaseDao<HousQuickInformation,Long> {

    /**
     * 房屋快出值信息表(权证下户)表,分页查询数据
     * @param map
     * @return List<HousQuickInformation>
     */
    public List<HousQuickInformation> getPageListByMap(Map<String, Object> map);
    
    /**
     * 房屋快出值信息表(权证下户)表,根据id查询数据
     * @param id
     * @return HousQuickInformation
     */
    public HousQuickInformation getItemInfoById(long id);
    
    /**
     * 房屋快出值信息表(权证下户)表,根据流程id查询数据
     * @param processInstanceId
     * @return HousQuickInformation
     */
    public HousQuickInformation getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 房屋快出值信息表(权证下户)表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
