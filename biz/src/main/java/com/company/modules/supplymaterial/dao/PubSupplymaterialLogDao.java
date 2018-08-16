package com.company.modules.supplymaterial.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.supplymaterial.domain.PubSupplymaterialLog;

/**
* User:    JDM
* DateTime:2016-08-18 03:51:48
* details: 补充资料,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PubSupplymaterialLogDao extends BaseDao<PubSupplymaterialLog,Long> {

    /**
     * 补充资料表,分页查询数据
     * @param map
     * @return List<PubSupplymaterialLog>
     */
    public List<PubSupplymaterialLog> getPageListByMap(Map<String, Object> map);
    
    /**
     * 补充资料表,根据id查询数据
     * @param id
     * @return PubSupplymaterialLog
     */
    public PubSupplymaterialLog getItemInfoById(long id);
    
    /**
     * 补充资料表,根据流程id查询数据
     * @param processInstanceId
     * @return PubSupplymaterialLog
     */
    public PubSupplymaterialLog getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 补充资料表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public List<PubSupplymaterialLog> getItemList(Map<String, Object> paramMap);

	public PubSupplymaterialLog getItemInfoByMap(Map params);
}
