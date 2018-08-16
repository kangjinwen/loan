package com.company.modules.warrant.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.warrant.domain.HousOritoInformation;

/**
* User:    fzc
* DateTime:2016-08-10 03:35:36
* details: 下户信息表(权证下户),Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousOritoInformationDao extends BaseDao<HousOritoInformation,Long> {

    /**
     * 借款基本信息表,分页查询数据
     * @param map
     * @return List<HousOritoInformation>
     */
    public List<HousOritoInformation> getPageListByMap(Map<String, Object> map);
    
    /**
     * 借款基本信息表,根据id查询数据
     * @param id
     * @return HousOritoInformation
     */
    public HousOritoInformation getItemInfoById(long id);
    
    /**
     * 借款基本信息表,根据流程id查询数据
     * @param processInstanceId
     * @return HousOritoInformation
     */
    public HousOritoInformation getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 借款基本信息表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
