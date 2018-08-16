package com.company.modules.chargedata.dao;

import com.company.common.dao.BaseDao;
import java.util.List;

import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.chargedata.domain.ChargeDataLog;

/**
* User:    JDM
* DateTime:2016-11-30 02:08:46
* details: 押品管理,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface ChargeDataLogDao extends BaseDao<ChargeDataLog,Long> {

    /**
     * 押品管理表,分页查询数据
     * @param map
     * @return List<ChargeDataLog>
     */
    public List<ChargeDataLog> getPageListByMap(ChargeDataLog chargeDataLog);
    
    /**
     * 押品管理表,根据id查询数据
     * @param id
     * @return ChargeDataLog
     */
    public ChargeDataLog getItemInfoById(Long id);
    
    /**
     * 押品管理表,根据流程id查询数据
     * @param processInstanceId
     * @return ChargeDataLog
     */
    public ChargeDataLog getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 押品管理表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(Long id);
}
