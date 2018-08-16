package com.company.modules.extension.service;

import com.company.modules.extension.utils.databean.ExtendedAuditDataBean;

public interface ExtendedAuditService {
	
	void audit(ExtendedAuditDataBean dataBean) throws Exception;
	


}
