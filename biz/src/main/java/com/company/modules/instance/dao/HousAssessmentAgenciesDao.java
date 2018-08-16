package com.company.modules.instance.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.instance.domain.HousAssessmentAgencies;

/**
* User:    wulb
* DateTime:2016-08-06 02:33:32
* details: 评估机构,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousAssessmentAgenciesDao extends BaseDao<HousAssessmentAgencies,Long> {

    /**
     * 评估机构表,分页查询数据
     * @param map
     * @return List<HousAssessmentAgencies>
     */
    public List<HousAssessmentAgencies> getPageListByMap(Map<String, Object> map);
    
    /**
     * 评估机构表,根据id查询数据
     * @param id
     * @return HousAssessmentAgencies
     */
    public HousAssessmentAgencies getItemInfoById(long id);
    
    /**
     * 评估机构表,根据流程id查询数据
     * @param processInstanceId
     * @return List<HousAssessmentAgencies>
     */
    public List<HousAssessmentAgencies> getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 评估机构表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
