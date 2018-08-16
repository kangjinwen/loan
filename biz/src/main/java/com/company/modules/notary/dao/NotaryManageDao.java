package com.company.modules.notary.dao;

import com.company.common.dao.BaseDao;

import java.util.Map;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.notary.domain.HousNotarizationRegistration;

/**
* User:    mcwang
* DateTime:2016-08-10 03:59:46
* details: 公证登记,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface NotaryManageDao extends BaseDao<HousNotarizationRegistration,Long> {

    /**
     * 公证登记表,根据id查询数据
     * @param id
     * @return HousNotarizationRegistration
     */
    public HousNotarizationRegistration getItemInfoById(long id);
    
    /**
     * 公证登记表,根据流程id查询数据
     * @param processInstanceId
     * @return HousNotarizationRegistration
     */
    public Map<String, Object> getNotaryRegistDataByInstanceId(String processInstanceId);
    
    
    public HousNotarizationRegistration getHousNotarizationRegistrationByInstanceId(String processInstanceId);
}
