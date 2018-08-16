package com.company.modules.audit.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.audit.domain.HousFaceTrial;

/**
* User:    fzc
* DateTime:2016-08-14 01:28:09
* details: 面审信息表,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface HousFaceTrialDao extends BaseDao<HousFaceTrial,Long> {

    /**
     * 面审信息表表,分页查询数据
     * @param map
     * @return List<HousFaceTrial>
     */
    public List<HousFaceTrial> getPageListByMap(Map<String, Object> map);
    
    /**
     * 面审信息表表,根据id查询数据
     * @param id
     * @return HousFaceTrial
     */
    public HousFaceTrial getItemInfoById(long id);
    
    /**
     * 面审信息表表,根据流程id查询数据
     * @param processInstanceId
     * @return HousFaceTrial
     */
    public HousFaceTrial getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 面审信息表表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
}
