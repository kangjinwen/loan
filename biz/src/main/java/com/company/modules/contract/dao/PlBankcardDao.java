package com.company.modules.contract.dao;

import com.company.common.dao.BaseDao;
import java.util.List;
import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.contract.domain.PlBankcard;

/**
* User:    wulb
* DateTime:2016-08-15 11:44:43
* details: 放款银行信息,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface PlBankcardDao extends BaseDao<PlBankcard,Long> {

    /**
     * 放款银行信息表,分页查询数据
     * @param map
     * @return List<PlBankcard>
     */
    public List<PlBankcard> getPageListByMap(Map<String, Object> map);
    
    /**
     * 放款银行信息表,查询数据
     * @param map
     * @return PlBankcard
     */
    public PlBankcard getItemByMap(Map<String, Object> map);
    
    /**
     * 放款银行信息表,根据id查询数据
     * @param id
     * @return PlBankcard
     */
    public PlBankcard getItemInfoById(long id);
    
    /**
     * 放款银行信息表,根据流程id查询数据
     * @param processInstanceId
     * @return PlBankcard
     */
    public PlBankcard getItemInfoByProcessInstanceId(String processInstanceId);

    /**
    * 放款银行信息表,删除数据
    * @param id 主键
    * @return
    */
    public int deleteById(long id);

	public void updateByMap(Map<String, Object> bankMap);
}
