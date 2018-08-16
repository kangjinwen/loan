package com.company.modules.collateral.workflow.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.company.modules.common.domain.ExportHousMortgageRegistration;

public interface CollateralTaskService{
	
	 /**
     * 押品登记表表,查询数据
     * @param housMortgageRegistration 押品登记表类
     * @return           返回页面map
     * @throws Exception
     */
  	PageInfo<Map<String, Object>> getCollateralRegistList (Map<String, Object> map)throws Exception;
  	
  	
  	/**
  	 * 
  	* @Description: 查询押品解押列表
  	* @param @param map
  	* @param @param pageBounds
  	* @param @return
  	* @param @throws Exception    设定文件 
  	* @author wangmc
  	* @return PageInfo<Map<String,Object>>    返回类型 
  	* @throws
  	 */
	PageInfo<Map<String, Object>> getCollateralRelieveList (Map<String, Object> map)throws Exception;
	
	
	
 	/**
  	 * 
  	* @Description: 查询抵押任务分配列表
  	* @param @param map
  	* @param @param pageBounds
  	* @param @return
  	* @param @throws Exception    设定文件 
  	* @author wangmc
  	* @return PageInfo<Map<String,Object>>    返回类型 
  	* @throws
  	 */
	PageInfo<Map<String, Object>> getCollateralRegistAssignTaskList (Map<String, Object> map)throws Exception;
	
	
	/**
  	 * 
  	* @Description: 查询解押任务分配列表
  	* @param @param map
  	* @param @param pageBounds
  	* @param @return
  	* @param @throws Exception    设定文件 
  	* @author wangmc
  	* @return PageInfo<Map<String,Object>>    返回类型 
  	* @throws
  	 */
	PageInfo<Map<String, Object>> getCollateralRelieveAssignTaskList (Map<String, Object> map)throws Exception;
	
	
	public void startRelieveHouse(String processInstanceId,Long projectId,long userId) throws Exception;


	List<ExportHousMortgageRegistration> queryCollateralRegistList(Map<String, Object> paramap) throws Exception;
}
