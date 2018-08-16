package com.company.modules.chargedata.dao;

import java.util.List;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.chargedata.domain.ChargeDataStore;

/**
* User:    JDM
* DateTime:2016-12-02 11:45:24
* details: 押品管理,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface ChargeDataStoreDao extends BaseDao<ChargeDataStore,Long> {

    /**
     * 押品管理表,分页查询数据
     * @param chargeDataStore
     * @return List<ChargeDataStore>
     */
    public List<ChargeDataStore> getPageListByMap(ChargeDataStore chargeDataStore);
    
    /**
     * 押品管理表,根据id查询数据
     * @param id
     * @return ChargeDataStore
     */
    public ChargeDataStore getItemInfoById(Long id);
    
    /**
     * 押品管理表,根据流程id查询数据
     * @param processInstanceId
     * @return ChargeDataStore
     */
    public ChargeDataStore getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 押品管理表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(Long id);

	public ChargeDataStore queryBorrowInfoByProcessInstanceId(String processInstanceId);
}
