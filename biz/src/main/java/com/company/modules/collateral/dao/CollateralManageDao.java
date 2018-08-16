package com.company.modules.collateral.dao;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;

import java.util.Map;

import com.company.modules.collateral.domain.HousMortgageRegistration;

/**
* User:    mcwang
* DateTime:2016-08-08 04:18:34
* details: 押品登记表,Dao接口层
* source:  代码生成器
*/
@RDBatisDao
public interface CollateralManageDao extends BaseDao<HousMortgageRegistration,Long> {


    /**
     * 
    * @Description: 根据流程id获取抵押信息对象
    * @param @return    设定文件 
    * @author wangmc
    * @return List<HousMortgageRegistration>    返回类型 
    * @throws
     */
    HousMortgageRegistration getHousMortgageRegistrationsByInstanceId(String processInstanceId);
    
    /**
    * @Description: 根据流程id获取抵押物信息
    * @param @param processInstanceId
    * @param @return    设定文件 
    * @author wangmc
    * @return Map<String,Object>    返回类型 
    * @throws
     */
    Map<String, Object> getCollateralRegistDataByInstanceId(String processInstanceId);
    
    Map<String, Object> returnCollateralRegistData(String processInstanceId);
    
    HousMortgageRegistration getHousMortgageRegistrationsByProjectId(String projectId);
    
}

