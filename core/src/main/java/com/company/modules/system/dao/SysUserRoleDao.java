package com.company.modules.system.dao;

import java.util.List;
import java.util.Map;

import com.company.common.dao.BaseDao;
import com.company.common.utils.annotation.RDBatisDao;
import com.company.modules.system.domain.SysUserRole;

/**
 * 
 * 用户角色DAO
 * @version 1.0
 * @author 吴国成
 * @created 2014年9月22日 下午2:47:14
 */
@RDBatisDao
public interface SysUserRoleDao extends BaseDao<SysUserRole, Long> {
	
	
	/**
	 * 根据用户ID删除
	 * @param userId 
	 * @version 1.0
	 * @author 吴国成
	 * @created 2014年9月22日
	 */
	void deleteByUserId(long userId);
	
	List<SysUserRole> getItemListByMap(Map<String, Object> param);
	
	public SysUserRole getSysUserRoleByNid(Map<String, Object> paramMap);

}
