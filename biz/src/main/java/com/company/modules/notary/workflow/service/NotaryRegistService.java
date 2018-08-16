package com.company.modules.notary.workflow.service;

import com.company.modules.notary.databean.NotaryRegistDataBean;

public interface NotaryRegistService {
	void regist(NotaryRegistDataBean dataBean) throws Exception;
	
	//展期超额公证登记
	void registExtensionExcessiveNotarization(NotaryRegistDataBean dataBean) throws Exception;

}
