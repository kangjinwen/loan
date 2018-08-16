package com.company.modules.system.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.dao.BaseDao;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.system.dao.SysUserDao;
import com.company.modules.system.service.ZzjfSysUserService;

@Service(value="zzjfSysUserService")
@SuppressWarnings("all")
public class ZzjfSysUserServiceImpl extends BaseServiceImpl implements ZzjfSysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	@Override
	public BaseDao getMapper() {
		// TODO Auto-generated method stub
		return sysUserDao;
	}
	@Override
	public int updateByMap(Map<String, Object> paramMap) throws Exception {
		return sysUserDao.updateSysUserById(paramMap);
	}

	
}
