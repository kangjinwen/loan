package com.company.modules.warrant.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.warrant.domain.HousDataList;

/**
* User:    fzc
* DateTime:2016-08-10 05:14:20
* details: 资料清单表(权证下户),Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousDataListDao extends BaseDao<HousDataList,Long> {

    /**
     * 资料清单表(权证下户)表,分页查询数据
     * @param map
     * @return List<HousDataList>
     */
    public List<HousDataList> getPageListByMap(Map<String, Object> map);
    
    /**
     * 资料清单表(权证下户)表,根据id查询数据
     * @param id
     * @return HousDataList
     */
    public HousDataList getItemInfoById(long id);
    
    /**
     * 资料清单表(权证下户)表,根据流程id查询数据
     * @param processInstanceId
     * @return HousDataList
     */
    public HousDataList getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 资料清单表(权证下户)表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
