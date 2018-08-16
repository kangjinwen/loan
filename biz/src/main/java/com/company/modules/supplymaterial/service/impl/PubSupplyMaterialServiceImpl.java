package com.company.modules.supplymaterial.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.common.dao.BaseDao;
import com.company.common.service.impl.BaseServiceImpl;
import com.company.modules.supplymaterial.dao.PubSupplyMaterialDao;
import com.company.modules.supplymaterial.dao.PubSupplymaterialLogDao;
import com.company.modules.supplymaterial.domain.PubSupplymaterial;
import com.company.modules.supplymaterial.domain.PubSupplymaterialLog;
import com.company.modules.supplymaterial.service.PubSupplyMaterialService;

@Service(value="supplyMaterialService")
@SuppressWarnings("all")
public class PubSupplyMaterialServiceImpl extends BaseServiceImpl implements PubSupplyMaterialService {
	
	@Autowired
	private PubSupplyMaterialDao supplyMaterialDao;
	@Autowired
    private PubSupplymaterialLogDao pubSupplymaterialLogDao;
	
	@Override
	public long insert(PubSupplymaterial supplyMaterialInfo) throws Exception {
		long result = supplyMaterialDao.insert(supplyMaterialInfo);
		
		PubSupplymaterialLog smLog = new PubSupplymaterialLog();
		smLog.setSupplymaterialId(supplyMaterialInfo.getId());
		smLog.setCreateTime(new Date());
		smLog.setOperateType(PubSupplymaterialLog.OPERATE_TYPE_OTHER_MATERIAL_NAME);
		smLog.setOperatorId(supplyMaterialInfo.getOperatorId());
		pubSupplymaterialLogDao.insert(smLog);
		
		batchInsertFileAndLog(supplyMaterialInfo);
		return result;
	}

	@Override
	public long update(PubSupplymaterial supplyMaterialInfo) throws Exception {
		int result = supplyMaterialDao.update(supplyMaterialInfo);
		batchInsertFileAndLog(supplyMaterialInfo);
		return result;
	}

	private void batchInsertFileAndLog(PubSupplymaterial supplyMaterialInfo) {
		Map params = new HashMap();
		params.put("supplymaterialId", supplyMaterialInfo.getId());
		
		if (supplyMaterialInfo.getOtherFileUploaded()) {
			params.put("operateType", PubSupplymaterialLog.OPERATE_TYPE_OTHER_MATERIAL);
			PubSupplymaterialLog smLog = pubSupplymaterialLogDao.getItemInfoByMap(params);
			if (smLog == null) {
				smLog = new PubSupplymaterialLog();
				smLog.setSupplymaterialId(supplyMaterialInfo.getId());
				smLog.setCreateTime(new Date());
				smLog.setOperateType(PubSupplymaterialLog.OPERATE_TYPE_OTHER_MATERIAL);
				smLog.setOperatorId(supplyMaterialInfo.getOperatorId());
				pubSupplymaterialLogDao.insert(smLog);
			}
//			PubSupplymaterialLog smLog = new PubSupplymaterialLog();
//			smLog.setSupplymaterialId(supplyMaterialInfo.getId());
//			smLog.setCreateTime(new Date());
//			smLog.setOperateType(PubSupplymaterialLog.OPERATE_TYPE_OTHER_MATERIAL);
//			smLog.setOperatorId(supplyMaterialInfo.getOperatorId());
//			pubSupplymaterialLogDao.insert(smLog);
		}
		if (supplyMaterialInfo.getNotarizationFileUploaded()) {
			params.put("operateType", PubSupplymaterialLog.OPERATE_TYPE_Notarization_MATERIAL);
			PubSupplymaterialLog smLog = pubSupplymaterialLogDao.getItemInfoByMap(params);
			if (smLog == null) {
				smLog = new PubSupplymaterialLog();
				smLog.setSupplymaterialId(supplyMaterialInfo.getId());
				smLog.setCreateTime(new Date());
				smLog.setOperateType(PubSupplymaterialLog.OPERATE_TYPE_Notarization_MATERIAL);
				smLog.setOperatorId(supplyMaterialInfo.getOperatorId());
				pubSupplymaterialLogDao.insert(smLog);
			}
//			PubSupplymaterialLog smLog = new PubSupplymaterialLog();
//			smLog.setSupplymaterialId(supplyMaterialInfo.getId());
//			smLog.setCreateTime(new Date());
//			smLog.setOperateType(PubSupplymaterialLog.OPERATE_TYPE_Notarization_MATERIAL);
//			smLog.setOperatorId(supplyMaterialInfo.getOperatorId());
//			pubSupplymaterialLogDao.insert(smLog);
		}
		if (supplyMaterialInfo.getOtherFileUploaded() && supplyMaterialInfo.getNotarizationFileUploaded()) {
			supplyMaterialInfo.setProcessInstanceId("1");
			supplyMaterialDao.update(supplyMaterialInfo);
		}
	}

	@Override
	public List<Map> getPageListByMap(Map<String, Object> data,Boolean isCompleted) throws Exception {
		if (isCompleted) {
			return supplyMaterialDao.getCompletedPageListByMap(data);
		} else {
			return supplyMaterialDao.getUnCompletePageListByMap(data);
		}
		
	}

	@Override
	public PubSupplymaterial getItemInfoById(long id) throws Exception {
		return supplyMaterialDao.getItemInfoById(id);
	}

	@Override
	public PubSupplymaterial getItemInfoByProcessInstanceId(String processInstanceId) throws Exception {
		return supplyMaterialDao.getItemInfoByProcessInstanceId(processInstanceId);
	}

	@Override
	public int deleteById(long id) throws Exception {
		return supplyMaterialDao.deleteById(id);
	}

	@Override
	public BaseDao getMapper() {
		return supplyMaterialDao;
	}

	@Override
	public PubSupplymaterial getItemByProjectId(long projectId) {
		return supplyMaterialDao.getItemByProjectId(projectId);
	}

	@Override
	public List<PubSupplymaterial> getItemList(Map<String, Object> paramMap) {
		return supplyMaterialDao.getItemList(paramMap);
	}

}
