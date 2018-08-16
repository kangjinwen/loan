package com.company.modules.rebate.workflow.service;

import com.company.modules.rebate.databean.RebateAuditDataBean;

public interface RebateAuditService {
	
	
	void audit(RebateAuditDataBean dataBean) throws Exception;

}
