package com.company.modules.advance.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.advance.domain.HousAdvanceApply;

/**
* User:    gaoshan
* DateTime:2016-09-14 11:54:13
* details: 垫资申请,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousAdvanceApplyDao extends BaseDao<HousAdvanceApply,Long> {

    /**
     * 垫资申请表,分页查询数据
     * @param map
     * @return List<HousAdvanceApply>
     */
    public List<HousAdvanceApply> getPageListByMap(Map<String, Object> map);
    
    public Map<String, Object> getHousAdvanceApplyInfo(long projectId);
    
    public Map<String, Object> getHousAdvanceApply(long processInstanceId) throws Exception;
    
    /**
     * 垫资申请表,根据id查询数据
     * @param id
     * @return HousAdvanceApply
     */
    public HousAdvanceApply getItemInfoById(long id);
    
    /**
     * 垫资申请表,根据流程id查询数据
     * @param processInstanceId
     * @return HousAdvanceApply
     */
    public HousAdvanceApply getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 垫资申请表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
