package com.company.modules.contract.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.contract.domain.PlContract;

/**
* User:    wulb
* DateTime:2016-08-15 11:21:07
* details: 合同信息,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PlContractDao extends BaseDao<PlContract,Long> {

    /**
     * 合同信息表,分页查询数据
     * @param map
     * @return List<PlContract>
     */
    public List<PlContract> getPageListByMap(Map<String, Object> map);
    
    /**
     * 合同信息表,查询数据
     * @param map
     * @return PlContract
     */
    public PlContract getItemByMap(Map<String, Object> map);
    
    /**
     * 合同信息表,根据id查询数据
     * @param id
     * @return PlContract
     */
    public PlContract getItemInfoById(long id);
    
    /**
     * 合同信息表,根据流程id查询数据
     * @param processInstanceId
     * @return PlContract
     */
    public PlContract getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 合同信息表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public void updateByMap(Map<String, Object> contractMap);

}
