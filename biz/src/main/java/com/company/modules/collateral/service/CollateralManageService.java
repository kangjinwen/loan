package com.company.modules.collateral.service;

import java.util.Map;

import  com.company.modules.collateral.domain.HousMortgageRegistration;

/**
* User:    mcwang
* DateTime:2016-08-08 04:18:34
* details: 押品登记表,Service接口层
*/
public interface CollateralManageService {

    /**
     * 押品登记表表,插入数据
     * @param housMortgageRegistration 押品登记表类
     * @return           返回页面map
     * @throws Exception
     */
    long insert(HousMortgageRegistration housMortgageRegistration)throws Exception;

    /**
     * 押品登记表表,修改数据
     * @param housMortgageRegistration 押品登记表类
     * @return           返回页面map
     * @throws Exception
     */
  	long update(HousMortgageRegistration housMortgageRegistration)throws Exception;

  	
  	Map<String, Object> getCollateralRegistData(String instanceId) throws Exception ;
  	
  	Map<String, Object> returnCollateralRegistData(String instanceId) throws Exception ;
  	
  	
  	HousMortgageRegistration getHousMortgageRegistrationByInstanceId(String processInstanceId) throws Exception;
    
    
}
