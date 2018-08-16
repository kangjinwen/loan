package com.company.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.dao.SysRoleMenuDao;
import com.company.modules.system.domain.SysRoleMenu;
import com.company.modules.system.service.SysRoleMenuService;

@Service(value = "sysRoleMenuServiceImpl")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Override
	public List<SysRoleMenu> getRoleMenuList(Long roleId) throws ServiceException {
		try {
			return this.sysRoleMenuDao.getRoleMenuList(roleId);
		} catch (PersistentDataException e) {
			throw new ServiceException(Constant.FAIL_CODE_VALUE,
					"get data fail");
		}
	}

	public SysRoleMenuDao getSysRoleMenuDao() {
		return sysRoleMenuDao;
	}

	public void setSysRoleMenuDao(SysRoleMenuDao sysRoleMenuDao) {
		this.sysRoleMenuDao = sysRoleMenuDao;
	}



}
