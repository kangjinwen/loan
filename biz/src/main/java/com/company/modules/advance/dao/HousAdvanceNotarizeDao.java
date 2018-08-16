package com.company.modules.advance.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.advance.domain.HousAdvanceNotarize;

/**
* User:    wulb
* DateTime:2016-09-21 09:24:32
* details: 垫资公证信息,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousAdvanceNotarizeDao extends BaseDao<HousAdvanceNotarize,Long> {

    /**
     * 垫资公证信息表,分页查询数据
     * @param map
     * @return List<HousAdvanceNotarize>
     */
    public List<HousAdvanceNotarize> getPageListByMap(Map<String, Object> map);
    
    public Map<String, Object> getHousAdvanceNotarizeInfo(long projectId);
    
    public Map<String, Object> getHousAdvanceNotarize(long processInstanceId) throws Exception;
    
    /**
     * 借垫资公证信息表,根据id查询数据
     * @param id
     * @return HousAdvanceNotarize
     */
    public HousAdvanceNotarize getItemInfoById(long id);
    
    /**
     * 垫资公证信息表,根据流程id查询数据
     * @param processInstanceId
     * @return HousAdvanceNotarize
     */
    public HousAdvanceNotarize getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 垫资公证信息表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
