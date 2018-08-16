package com.company.modules.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.context.Constant;
import com.company.common.dao.BaseDao;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.common.utils.ListUtil;
import com.company.common.utils.ParamChecker;
import com.company.common.utils.StringUtil;
import com.company.modules.common.constant.SystemConstant;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.system.dao.SysOfficeDao;
import com.company.modules.system.domain.SysOffice;
import com.company.modules.system.service.SysOfficeService;

@Service(value = "sysOfficeServiceImpl")
public class SysOfficeServiceImpl extends BaseServiceImpl implements
		SysOfficeService {
	private static final String CUSTOMERSERVICESTAFFA ="customerServiceStaffA" ;//客服专员
	private static final String DECLARATIONSTAFF ="declarationStaff" ;//客服专员
	@Autowired
	private SysOfficeDao sysOfficeDao;
	@Override
	public long officeAdd(SysOffice office) throws ServiceException {
		long num;
		try{
			num = sysOfficeDao.insert(office);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return num;
	}

	@Override
	public SysOffice getOfficeById(Long id) throws ServiceException {
		SysOffice office = null;
		try {
			office = sysOfficeDao.getOfficeById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return office;
	}

	@Override
	public List<SysOffice> getOfficeUseList() throws ServiceException {
		Map<String, Object> param = new HashMap<>();
		return sysOfficeDao.getListByMap(param);
	}

	@Override
	public void deleteOfficeAll(Long id) throws ServiceException {
		try {
			SysOffice office = sysOfficeDao.getOfficeById(id);
			// 获取office对象
			// 获取当前节点下的子节点
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parentId", office.getId());
			Map<String, Object> updateMap = new HashMap<String, Object>();
			updateMap.put("id", office.getId());
			updateMap.put("isDelete", 1);
			sysOfficeDao.updateSysOfficeById(updateMap);
			List<SysOffice> parentOffice = sysOfficeDao.getListByMap(map);
			if (parentOffice != null && parentOffice.size() > 0) {
				for (SysOffice off : parentOffice) {
					this.deleteOfficeAll(Long.parseLong(off.getId()));
				}
			}
		} catch (Exception e) {
			throw new ServiceException(Constant.FAIL_CODE_VALUE,"get data fail");
		}
	}

	@Override
	public Boolean updateSysOfficeById(Map<String, Object> map) throws ServiceException {
		boolean isTrue =false;
		try{
			int num = sysOfficeDao.updateSysOfficeById(map);
			if(num >0){
				isTrue=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return isTrue;
	}

	@Override
	public String getIdByParentId(String parentId) throws ServiceException {
		String id=null;
		try{
			id = sysOfficeDao.getIdByParentId(parentId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return id;
	}

	@Override
	public List<SysOffice> getListByMap(Map<String, Object> param)
			throws ServiceException {
		return sysOfficeDao.getListByMap(param);
	}
	
	

	@Override
	public List<Map<String, Object>>  getMapListByMap(Map<String, Object> param) {
		List<Map<String, Object>> sysOfficeList = new ArrayList<>();
		List<SysOffice> sysoffices = sysOfficeDao.getListByMap(param);
		for (SysOffice sysOffice : sysoffices) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", sysOffice.getId());
			data.put("name", sysOffice.getName());
			sysOfficeList.add(data);
		}
		return sysOfficeList;
	}

	@Override
	public Map<String,Object> getOfficeInfoById(Long id) throws ServiceException {
		Map<String,Object> map = null;
		try {
			map = sysOfficeDao.getOfficeInfoById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return map;
	}

	@Override
	public List<Map<String,Object>> getOfficeTreeList() throws ServiceException {
		List<Map<String,Object>> list ;
		try {
			list  = sysOfficeDao.getOfficeTreeList();
			
			list=ListUtil.list2Tree((List<Map<String,Object>>)list,"value","parent_id");
			list=ListUtil.treeForExt(list,null,null,true);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return list;
	}

	@Override
	public BaseDao getMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SysOffice> getListByOfficeId(Map<String, Object> param) throws ServiceException{
		List<SysOffice> list =null;
		try {
			list = sysOfficeDao.getListByOfficeId(param);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return list;
	}

	@Override
	public SysOffice getBusinessHallByOrganizationId(String organizationId)
			throws ServiceException {
		 ParamChecker.checkEmpty(organizationId, "organizationId");
	        SysOffice bizHall = null;
	        String upLevelOrgId = organizationId;
	        while (!StringUtil.isBlank(upLevelOrgId)) {
	            SysOffice sysOffice = null;
	            try {
	                sysOffice = sysOfficeDao.getOfficeById(Long.valueOf(upLevelOrgId));
	            } catch (Exception e) {
	                throw new ServiceException("查询营业厅信息失败");
	            }
	            if (sysOffice == null) {
	                throw new ServiceException("组织机构ID有误");
	            }
	            if (sysOffice.getType() != SystemConstant.OFFICE_TYPE_BUSINESS_HALL) {
	                upLevelOrgId = upLevelOrgId.substring(0, upLevelOrgId.length() - 2);
	            } else {
	                bizHall = sysOffice;
	                break;
	            }
	        }
	        return bizHall;
	}

	@Override
	public Map<String, Object> getProjectBelongInfo(String roleNid, Long userId) {
		Map<String, Object> data = new HashMap<>();
		if(CUSTOMERSERVICESTAFFA.equals(roleNid)){
			data.put("projectBelongs", 1);//赚赚自有
		}else if(DECLARATIONSTAFF.equals(roleNid)){
			data = sysOfficeDao.getProjectBelongInfo(userId);
			data.put("projectBelongs", 2);//报单机构
		}
		return data;
	}

	@Override
	public List<Map<String, Object>> getOfficeListByTypeAndParentId(
			Map<String, Object> param) throws ServiceException {
		// TODO Auto-generated method stub
		return sysOfficeDao.getOfficeListByTypeAndParentId(param);
	}

	
}
