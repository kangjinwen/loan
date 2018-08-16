package com.company.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.modules.common.exception.PersistentDataException;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.dao.SysOfficeUserCountDao;
import com.company.modules.system.domain.SysOfficeUserCount;
import com.company.modules.system.service.SysOfficeUserCountService;

@Service(value = "sysOfficeUserCount")
public class SysOfficeUserCountServiceImpl implements SysOfficeUserCountService {

	@Autowired
	private SysOfficeUserCountDao officeUserCountDao;

	@Override
	public SysOfficeUserCount getUserCountById(Long id) throws ServiceException {
		return this.officeUserCountDao.getByPrimary(id);
	}

	@Override
	public SysOfficeUserCount getNewUserCount() throws ServiceException {
		try {
			List<SysOfficeUserCount> list = officeUserCountDao.getCountList();
			if(list.size() > 0){
				SysOfficeUserCount count = list.get(0);
				return count;
			}else{
				return null;
			}
		} catch (PersistentDataException e) {
			throw new ServiceException(Constant.FAIL_CODE_VALUE,
					"get data fail");
		}
	}

	@Override
	public SysOfficeUserCount addUserCount(SysOfficeUserCount userCount) throws ServiceException {
		Long id = this.officeUserCountDao.insert(userCount);
		return getUserCountById(id);
	}

	@Override
	public SysOfficeUserCount updateUserCount(SysOfficeUserCount userCount) throws ServiceException {
		try {
			this.officeUserCountDao.update(userCount);
		} catch (Exception e) {
			throw new ServiceException(Constant.FAIL_CODE_VALUE, "save data fail");
		}
		return userCount;
	}

}
