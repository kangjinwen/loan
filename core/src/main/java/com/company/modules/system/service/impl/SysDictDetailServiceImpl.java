package com.company.modules.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.dao.SysDictDetailDao;
import com.company.modules.system.domain.SysDictDetail;
import com.company.modules.system.service.SysDictDetailService;

/**
 * Service
 * 
 * @author wgc
 * @version 1.0
 * @since 2014-11-03
 */
@Service(value = "sysDictDetailService")
public class SysDictDetailServiceImpl extends BaseServiceImpl implements
		SysDictDetailService {

	@Autowired
	private SysDictDetailDao sysDictDetailDao;

	@Override
	public Boolean deleteSysDictDetail(Long id) throws ServiceException {
		Boolean isTrue = false;
		try {
			 int num = sysDictDetailDao.deleteByPrimary(id);
			 if(num > 0){
				 isTrue = true;
			 }
		} catch (Exception e) {
			 throw new ServiceException(Constant.FAIL_CODE_VALUE,e.getMessage());
		}
		return isTrue;
	}

	@Override
	public Long getItemCountMap(Map<String, Object> arg) throws ServiceException {
		long num ;
		try {
			num = sysDictDetailDao.getCount(arg);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return num;
	}

	@Override
	public void addOrModify(SysDictDetail agr, String stauts)
			throws ServiceException {
		try {
			if (stauts != null && Constant.INSERT.equals(stauts)) {
				sysDictDetailDao.insert(agr);
			} else if (stauts != null && Constant.UPDATE.equals(stauts)) {
				sysDictDetailDao.update(agr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public BaseDao getMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> queryAllDic() throws ServiceException {
		return sysDictDetailDao.queryAllDic();
	}

	@Override
	public List<SysDictDetail> getBizFileTypes(String type) throws Exception {
		// TODO Auto-generated method stub
		try {
			return sysDictDetailDao.getBizFileTypes(type);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

}