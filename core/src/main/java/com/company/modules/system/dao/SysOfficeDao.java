package com.company.modules.system.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.system.domain.SysOffice;

/**
 * 
 * 织级机构DAO
 * @version 1.0
 * @author 吴国成
 * @created 2014年9月22日 下午3:02:03
 */
@RDBatisDao
public interface SysOfficeDao extends BaseDao<SysOffice,Long>{
	/**
	 * 软删除机构
	 * @param office 机构实体
	 * @return  Boolean
	 */
	Boolean deleteOffice(SysOffice office);
	
    /**
     *根据ID更新  通用更新
     *@param map
     *@return Boolean
     */ 
    int updateSysOfficeById(Map<String , Object> map);

    /**
     * 根据父ID获得子类记录的下一个ID
     * @param param
     * @return 
     * @version 1.0
     * @author 吴国成
     * @created 2014年12月25日
     */
    String getIdByParentId(String parentId);
    /**
     * 根据office获取子列表
     * @param param
     * @return 
     * @version 1.0
     * @author 吴国成
     * @created 2014年12月25日
     */
    List<SysOffice> getListByMap(Map<String, Object> param);
    
    /**
	 * 根据officeID获取sysOffice.
	 *
	 * @param params the params
	 * @return the office by id
	 * @throws PersistentDataException the persistent data exception
	 */
	SysOffice getOfficeById(Long id) ;

	Map<String,Object> getOfficeInfoById(Long id) ;
	
	/**
	 * 
	 * @param
	 * @return
	 * @throws PersistentDataException
	 */
	String getRoleNameByMap(Map<String,Object> params) ;
	
	
	List<Map<String,Object>> getOfficeTreeList() ;

	List<SysOffice> getListByOfficeId(Map<String, Object> param);

	Map<String, Object> getProjectBelongInfo(Long userId);
	
	/**
	 * 获取指定类型的本级或下级单位列表
	 * @description
	 * @return
	 * @author huy
	 * @return List<Map<String,Object>>
	 * @since  1.0.0
	 */
	List<Map<String,Object>> getOfficeListByTypeAndParentId(Map<String,Object> params);
}
