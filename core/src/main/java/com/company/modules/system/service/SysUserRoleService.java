package com.company.modules.system.service;

import java.util.List;
import java.util.Map;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.domain.SysUserRole;


/**
 * 
 * 用户角色关联信息service接口
 * @version 1.0
 * @author 吴国成
 * @created 2014年9月23日 上午9:55:01
 */
public interface SysUserRoleService {
	/**
	 * 用户角色关联信息查询
	 * @param userId 角色ID
	 * @return 关联信息List
	 * @throws ServiceException 
	 */
	List<SysUserRole> getSysUserRoleList(Long userId) throws ServiceException;
	
	/**
	 * 根据条件查询用户角色信息
	 * @description
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 * @author huy
	 * @return SysUserRole
	 * @since  1.0.0
	 */
	SysUserRole getSysUserRoleByNid(Map<String, Object> paramMap) throws ServiceException;
	
	
}
