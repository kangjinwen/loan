package com.company.modules.anrong.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.anrong.domain.PubArRiskResult;

/**
* User:    mcwang
* DateTime:2016-09-13 03:00:19
* details: 安融风控结果,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PubArRiskResultDao extends BaseDao<PubArRiskResult,Long> {

    /**
     * 安融风控结果表,分页查询数据
     * @param map
     * @return List<PubArRiskResult>
     */
    public List<PubArRiskResult> getPageListByMap(Map<String, Object> map);
    
    /**
     * 安融风控结果表,根据id查询数据
     * @param id
     * @return PubArRiskResult
     */
    public PubArRiskResult getItemInfoById(long id);
    

    /**
    * 安融风控结果表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);
    
    
    public List<PubArRiskResult>  getItemCountsByMap(Map<String, Object> map);
    
    
    public List<PubArRiskResult>  checkStatusByMap(Map<String, Object> map);
}
