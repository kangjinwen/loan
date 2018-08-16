package com.company.modules.chargedata.service;

import java.util.List;
import  com.company.modules.chargedata.domain.ChargeData;
import com.company.modules.chargedata.domain.ChargeDataLog;
import com.company.modules.chargedata.domain.ChargeDataStore;
import com.company.modules.system.domain.SysUser;

/**
* User:    JDM
* DateTime:2016-11-30 02:08:46
* details: 押品管理,Service接口层
* source:  代码生成器
*/
public interface ChargeDataService {

    /**
     * 押品管理表,插入数据
     * @param chargeData 押品管理类
     * @return           返回页面map
     * @throws Exception
     */
    public Long insert(ChargeData chargeData) throws Exception;

    /**
     * 押品管理表,修改数据
     * @param chargeData 押品管理类
     * @return           返回页面map
     * @throws Exception
     */
    public Long update(ChargeData chargeData) throws Exception;

	/**
     * 押品管理表,分页查询数据
     * @param chargeData
     * @return
     * @throws Exception
     */
    public List<ChargeData> getPageListByMap(ChargeData chargeData) throws Exception;
    
	/**
     * 押品管理表,根据id查询数据
     * @param id
     * @return
     * @throws Exception
     */
    public ChargeData getItemInfoById(Long id) throws Exception;
    
	/**
     * 押品管理表,根据流程id查询数据
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public ChargeData getItemInfoByProcessInstanceId(String processInstanceId) throws Exception;

    /**
    * 押品管理表,删除数据
    * @param id 主键
    * @return   返回页面map
    * @throws Exception
    */
    public int deleteById(Long id) throws Exception;

	public Long lendOut(ChargeDataLog chargeDataLog,SysUser sysUser);

	public Long giveBack(ChargeDataLog chargeDataLog,SysUser sysUser);

	public Long inStore(List<ChargeData> chargeData);

	public Long outStore(ChargeDataStore chargeDataStore);
}
