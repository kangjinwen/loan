package com.company.modules.workflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.company.common.dao.PubProjectProcessDao;
import com.company.common.domain.PubProjectProcess;
import com.company.modules.common.dao.PubLoanprocessDao;
import com.company.modules.common.exception.ServiceException;
import com.company.modules.common.utils.databean.AfterLoan;
import com.company.modules.workflow.dao.RDZZTaskDao;
import com.company.modules.workflow.service.RDZZHistoryService;

@Service
public class RDZZHistoryServiceImpl implements RDZZHistoryService {

	@Autowired
	private PubLoanprocessDao pubLoanprocessDao;
	
	@Autowired
	private PubProjectProcessDao pubProjectProcessDao;
	
	@Autowired
	private RDZZTaskDao rDZZTaskDao;
	
	@Override
	public PageInfo<Map<String, Object>> queryAllHistoricLoanProcessListToCustomerByProjectId(
			String projectId, int currentPage,int pageSize) throws ServiceException {
		PageHelper.startPage(currentPage, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		List<Map<String,Object>> historicLoanProcess =new ArrayList<Map<String,Object>>();
		try {
			historicLoanProcess = pubLoanprocessDao.queryAllHistoricLoanProcessListToCustomerByProjectId(params);
		} catch (Exception e) {
			throw new ServiceException("数据库查询历史出错!", e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(historicLoanProcess);
		return page;
	}

	@Override
	public PageInfo<Map<String, Object>> queryAllHistoricLoanProcessListByProjectId(
			String projectId, int currentPage,int pageSize) throws ServiceException {
		PageHelper.startPage(currentPage, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", projectId);
		List<Map<String,Object>> historicLoanProcess =new ArrayList<Map<String,Object>>();
		try {
			historicLoanProcess = pubLoanprocessDao.queryAllHistoricLoanProcessListByProjectId(params);
			for (Map<String, Object> map : historicLoanProcess) {
				PubProjectProcess projectProcess = pubProjectProcessDao.getItemByProcessInstanceId(Long.valueOf((String) map.get("processInstanceId")));
				String processState = (String) map.get("processState");
				if(projectProcess.getProcessType() == 0 && projectProcess.getExtensionNumber() > 0){
					map.put("processState", AfterLoan.EXTENSION.getName() + processState);
				}else if(projectProcess.getProcessType() == 1){
					map.put("processState", AfterLoan.INADVANCE.getName() + processState);
				}
			}
		} catch (Exception e) {
			throw new ServiceException("数据库查询历史出错!", e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<>(historicLoanProcess);
		return page;
	}

	@Override
	public Map<String, Object> queryHistoryIdentityLinkByMap(
			Map<String, Object> paramMap) throws ServiceException {
		// TODO Auto-generated method stub
		return rDZZTaskDao.queryHistoryIdentityLinkByMap(paramMap);
	}

	@Override
	public Map<String, Object> queryHistoryTaskInfo(Map<String, Object> paramMap) {
		return rDZZTaskDao.queryHistoryTaskInfo(paramMap);
	}



}
