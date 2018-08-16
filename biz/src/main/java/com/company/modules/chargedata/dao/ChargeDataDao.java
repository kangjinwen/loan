package com.company.modules.chargedata.dao;

import java.util.List;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.chargedata.domain.ChargeData;

/**
* User:    JDM
* DateTime:2016-11-30 02:08:46
* details: 押品管理,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface ChargeDataDao extends BaseDao<ChargeData,Long> {

    /**
     * 押品管理表,分页查询数据
     * @param chargeData
     * @return List<ChargeData>
     */
    public List<ChargeData> getPageListByMap(ChargeData chargeData);
    
    /**
     * 押品管理表,根据id查询数据
     * @param id
     * @return ChargeData
     */
    public ChargeData getItemInfoById(Long id);
    
    /**
     * 押品管理表,根据流程id查询数据
     * @param processInstanceId
     * @return ChargeData
     */
    public ChargeData getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 押品管理表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(Long id);
}
