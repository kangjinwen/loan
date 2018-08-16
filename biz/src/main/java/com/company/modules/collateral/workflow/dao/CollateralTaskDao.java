package com.company.modules.collateral.workflow.dao;

import java.util.List;
import java.util.Map;

import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.domain.ExportHousMortgageRegistration;

@RDBatisDao
public interface CollateralTaskDao {
	
	/**
     * 押品登记表,查询数据
     * @param map,pageBounds
     * @return List<HousMortgageRegistration>
     * @throws PersistentDataException
     */
	List<Map<String, Object>> getUnCollateralRegistList(Map<String, Object> map);

    
    /**
     * 押品登记表,查询数据
     * @param map,pageBounds
     * @return List<HousMortgageRegistration>
     * @throws PersistentDataException
     */
    List<Map<String, Object>> getDoneCollateralRegistList(Map<String, Object> map);
    
    List<ExportHousMortgageRegistration> getExportDoneCollateralRegistList(Map<String, Object> map);
    
    
    
    /**
     * 押品解押查询
     * @param map,pageBounds
     * @return List<HousMortgageRegistration>
     * @throws PersistentDataException
     */
	List<Map<String, Object>> getUnCollateralRelieveList(Map<String, Object> map);

    
    List<Map<String, Object>> getDoneCollateralRelieveList(Map<String, Object> map);
    
    
    
    /**
     * 抵押任务分配查询
     * @param map,pageBounds
     * @return List<HousMortgageRegistration>
     * @throws PersistentDataException
     */
	List<Map<String, Object>> getUnCollateralRegistAssignList(Map<String, Object> map);

    
    List<Map<String, Object>> getDoneCollateralRegistAssignList(Map<String, Object> map);
    
    
    /**
     * 解押押任务分配查询
     * @param map,pageBounds
     * @return List<HousMortgageRegistration>
     * @throws PersistentDataException
     */
	List<Map<String, Object>> getUnCollateralRelieveAssignList(Map<String, Object> map);

    
    List<Map<String, Object>> getDoneCollateralRelieveAssignList(Map<String, Object> map);
    
    
	

}
