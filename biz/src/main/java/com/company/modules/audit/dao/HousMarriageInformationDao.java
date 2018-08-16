package com.company.modules.audit.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.audit.domain.HousMarriageInformation;

/**
* User:    fzc
* DateTime:2016-08-14 01:28:36
* details: 婚姻信息表(面审),Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousMarriageInformationDao extends BaseDao<HousMarriageInformation,Long> {

    /**
     * 婚姻信息表(面审)表,分页查询数据
     * @param map
     * @return List<HousMarriageInformation>
     */
    public List<HousMarriageInformation> getPageListByMap(Map<String, Object> map);
    
    /**
     * 婚姻信息表(面审)表,根据id查询数据
     * @param id
     * @return HousMarriageInformation
     */
    public HousMarriageInformation getItemInfoById(long id);
    
    /**
     * 婚姻信息表(面审)表,根据流程id查询数据
     * @param processInstanceId
     * @return HousMarriageInformation
     */
    public HousMarriageInformation getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 婚姻信息表(面审)表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
