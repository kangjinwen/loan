package com.company.modules.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.dao.SysUserRoleDao;
import com.company.modules.system.domain.SysUserRole;
import com.company.modules.system.service.SysUserRoleService;

@Service(value = "sysUserRoleServiceImpl")
public class SysUserRoleServiceImpl implements SysUserRoleService {

	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	/*@Override
	public List<SysUserRole> getSysUserRoleList(Long userId) throws ServiceException {
		try {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
	
			return this.sysUserRoleDao.getItemListByMap(map, "SysUserRole");
		} catch (PersistentDataException e) {
			throw new ServiceException(Constant.FAIL_CODE_VALUE,
					"get  data fail");
		}
	}*/

	public SysUserRoleDao getSysUserRoleDao() {
		return sysUserRoleDao;
	}

	public void setSysUserRoleDao(SysUserRoleDao sysUserRoleDao) {
		this.sysUserRoleDao = sysUserRoleDao;
	}

	@Override
	public List<SysUserRole> getSysUserRoleList(Long userId) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return sysUserRoleDao.getItemListByMap(map);
	}

	@Override
	public SysUserRole getSysUserRoleByNid(Map<String, Object> paramMap)
			throws ServiceException {
		return sysUserRoleDao.getSysUserRoleByNid(paramMap);
	}
}
