package com.company.modules.audit.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.audit.domain.HousCreditInformation;

/**
* User:    fzc
* DateTime:2016-08-14 01:30:18
* details: 征信信息表(面审),Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousCreditInformationDao extends BaseDao<HousCreditInformation,Long> {

    /**
     * 征信信息表(面审)表,分页查询数据
     * @param map
     * @return List<HousCreditInformation>
     */
    public List<HousCreditInformation> getPageListByMap(Map<String, Object> map);
    
    /**
     * 征信信息表(面审)表,根据id查询数据
     * @param id
     * @return HousCreditInformation
     */
    public HousCreditInformation getItemInfoById(long id);
    
    /**
     * 征信信息表(面审)表,根据流程id查询数据
     * @param processInstanceId
     * @return HousCreditInformation
     */
    public HousCreditInformation getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 征信信息表(面审)表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
