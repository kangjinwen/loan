package com.company.modules.audit.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.audit.domain.HousControlInformation;

/**
* User:    fzc
* DateTime:2016-08-14 01:29:02
* details: 风控信息表(面审),Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousControlInformationDao extends BaseDao<HousControlInformation,Long> {

    /**
     * 风控信息表(面审)表,分页查询数据
     * @param map
     * @return List<HousControlInformation>
     */
    public List<HousControlInformation> getPageListByMap(Map<String, Object> map);
    
    /**
     * 风控信息表(面审)表,根据id查询数据
     * @param id
     * @return HousControlInformation
     */
    public HousControlInformation getItemInfoById(long id);
    
    /**
     * 风控信息表(面审)表,根据流程id查询数据
     * @param processInstanceId
     * @return HousControlInformation
     */
    public HousControlInformation getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 风控信息表(面审)表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
