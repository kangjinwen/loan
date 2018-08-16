package com.company.modules.contract.service;

import com.company.common.service.BaseService;
import com.company.modules.contract.utils.databean.ContractDataBean;

@SuppressWarnings("rawtypes")
public interface ContractService extends BaseService {
	/**
	 * 签合同
	 * @param contractDataBean
	 * @throws Exception
	 */
	void signContract(ContractDataBean contractDataBean) throws Exception;
}
