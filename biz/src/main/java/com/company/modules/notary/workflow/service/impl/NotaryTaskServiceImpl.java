package com.company.modules.notary.workflow.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.context.Constant;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.notary.workflow.dao.NotaryTaskDao;
import com.company.modules.notary.workflow.service.NotaryTaskService;
import com.company.modules.system.dao.SysRoleDao;
import com.company.modules.system.domain.SysRole;

@SuppressWarnings("rawtypes")
@Service(value = "notaryTaskServiceImpl")
public class NotaryTaskServiceImpl implements NotaryTaskService{
	private static final Logger log = LoggerFactory.getLogger(NotaryTaskServiceImpl.class);
	
	@Autowired
	private	NotaryTaskDao  notaryTaskDao;
	
	@Autowired
	private SysRoleDao sysRoleDao;
	
	private static final String USERTASK_NOTARIZEHANDLE= "usertask-notarizeHandle";
	
	/**
	 * 公证登记表,分页查询数据
	 * @param data
	 * @return           返回页面map
	 * @throws Exception
	 */
	@Override
	public PageInfo<Map<String, Object>> getNotaryRegistPageList(Map<String , Object> data) throws Exception {
		List<Map<String, Object>> returnMap = null;
		try {
			data.put("taskDefinition",USERTASK_NOTARIZEHANDLE);
			Boolean isCompleted=(Boolean) data.get("isCompleted");
		
			String roleName = getRoleName((Long)data.get("roleId"));
			data.put("nid", roleName);
			
			PageHelper.startPage((Integer)data.get("currentPage"),(Integer)data.get("pageSize"));
			if(isCompleted){
				returnMap=notaryTaskDao.getDoneRegistListByMap(data);
			} else{
				returnMap=notaryTaskDao.getUnDoneRegistListByMap(data);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new ServiceException(e.getMessage(),e,Constant.FAIL_CODE_VALUE);
		}
		//返回已经查询完毕的参数
		PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(returnMap);
		return page;
	}
	
	
	
	
	private String getRoleName(Long roleId) throws ServiceException {
		SysRole role = null;
		try {
			role = sysRoleDao.getByPrimary(roleId);
		} catch (Exception e) {
			throw new ServiceException("角色查询失败:" + e.getMessage(), e);
		}
		return role.getNid();
	}


}
